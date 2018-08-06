package cn.qsnark.sdk.crypto;

import cn.qsnark.sdk.crypto.cryptohash.DigestEngine;
import cn.qsnark.sdk.crypto.cryptohash.Keccak256;
import cn.qsnark.sdk.crypto.jce.ECKeyFactory;
import cn.qsnark.sdk.rpc.utils.ByteUtil;
import cn.qsnark.sdk.rpc.utils.Utils;
import com.lambdaworks.crypto.SCrypt;
import de.rtner.security.auth.spi.PBKDF2Engine;
import de.rtner.security.auth.spi.PBKDF2Parameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * description:
 * <p>
 * date 10/28/16
 *
 * @author chenquan
 * @version 1.0
 */
public class DecryptKey {
    private static int version = 3;

    /**
     * 解密私钥文件,私钥文件通过json格式保存
     *
     * @param keyProtected EncryptedKeyJSONV3 对象,通过读取json文件得到
     * @param auth         用户密码
     * @return 解密之后的keyByte 需要通过这个keybytes 得到真正的keypair
     * @throws GeneralSecurityException 解密秘钥无效将会抛出 mac not equal 异常
     */
    public static byte[] decryptKeyV3(EncryptedKeyJSONV3 keyProtected, String auth) throws GeneralSecurityException {

        if (keyProtected.version != version) {
            throw new GeneralSecurityException("version not equal 3");
        }

        if (!keyProtected.crypto.cipher.equals("aes-128-ctr")) {
            throw new GeneralSecurityException("Cipher not supported");

        }
        //mac is ok
        byte[] mac = Utils.hexStringToByteArray(keyProtected.crypto.MAC);
//        System.out.println("mac: " + ByteUtil.toHexString(mac));


        //iv is ok
        byte[] iv = Utils.hexStringToByteArray(keyProtected.crypto.cipherParams.IV);
//        System.out.println("iv: " + ByteUtil.toHexString(iv));

        //cipherText is ok
        byte[] cipherText = Utils.hexStringToByteArray(keyProtected.crypto.ciphertext);
//        System.out.println("cipherText: " + ByteUtil.toHexString(cipherText));


        //derivedKey is ok
        byte[] derivedKey = DecryptKey.getKDFKey(keyProtected.crypto, auth);
//        System.out.println("derivedKey: " + ByteUtil.toHexString(derivedKey));

        //calculatedMAC is ok
        byte[] subDerivedKey = Arrays.copyOfRange(derivedKey, 16, 32);

        DigestEngine digestEngine = new Keccak256();
        digestEngine.update(subDerivedKey);
        byte[] calculatedMAC = digestEngine.digest(cipherText);
//        System.out.println("calculatedMAC: " + ByteUtil.toHexString(calculatedMAC));

        if (!ByteUtil.toHexString(calculatedMAC).equals(ByteUtil.toHexString(mac))) {
            System.out.println("error mac: " + ByteUtil.toHexString(mac));
            System.out.println("This problem may cause by invalid password");
            System.out.println("Please ensure your address,accountJson and password are matched");
            throw new GeneralSecurityException("GeneralSecurityException error,calculatedMAC not equal mac");
        }

        // aes-128 is selected due to size of encryptKey.
        byte[] subDerivedKeyAgain = Arrays.copyOfRange(derivedKey, 0, 16);


//        byte[] plainText= aesCTRXOR(subDerivedKeyAgain, cipherText, iv);
        try {
            SecretKey secret = new SecretKeySpec(subDerivedKeyAgain, "AES");
            /* Decrypt the message, given derived key and initialization vector. */
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            byte[] plainText = cipher.doFinal(cipherText);
            return plainText;

        }catch (IllegalBlockSizeException e){
            e.printStackTrace();
            return null;
        }catch(BadPaddingException e){
            e.printStackTrace();
            return null;
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
        catch(InvalidKeyException e){
            e.printStackTrace();
            return null;
        }
        catch(InvalidAlgorithmParameterException e){
            e.printStackTrace();
            return null;
        }
        catch(NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decryptKeyDES(DESKeyJSON keyProtecetd,String auth) throws Exception {
        String textEncryptedStr = keyProtecetd.getEncrypted();
        return EncryptoUtils.decrypt(ByteUtil.hexStringToBytes(textEncryptedStr),auth);
    }
    /**
     * 返回ECKey
     *
     * @param keyBytes 传入的秘钥byte
     * @return
     */
    public static ECKey.ECDSASignature sign(byte[] keyBytes, byte[] toSignHash) {
        if ((keyBytes.length) == 0) {
            throw new SecurityException("key bytes lenth is 0");
        }
        ECKey ecKey = ECKey.fromPrivate(keyBytes);

        ecKey.getPubKey();
        return ecKey.sign(toSignHash);
    }

    public static String signToString(ECKey.ECDSASignature signature) {
        return signature.toHex();
    }

    private static byte[] getKDFKey(CryptoJSON cryptoJSON, String auth) throws GeneralSecurityException {
        byte[] authArray = auth.getBytes();

        byte[] salt = Utils.hexStringToByteArray((String) cryptoJSON.KDFParams.get("salt"));


        int dkLen = (Integer) cryptoJSON.KDFParams.get("dklen");

        if (cryptoJSON.KDF.equals("scrypt")) {
            int n = (Integer) cryptoJSON.KDFParams.get("n");
            int r = (Integer) cryptoJSON.KDFParams.get("r");
            int p = (Integer) cryptoJSON.KDFParams.get("p");
            return SCrypt.scrypt(authArray, salt, n, r, p, dkLen);

        } else {
            if (cryptoJSON.KDF.equals("pbkdf2")) {
                int c = (Integer) cryptoJSON.KDFParams.get("c");
                String prf = (String) cryptoJSON.KDFParams.get("prf");
                if (!prf.equals("hmac-sha256")) {
                    System.out.println("Unsupported PBKDF2 PRF: hmac-sha256 ");
                    return null;
                }
                PBKDF2Parameters p = new PBKDF2Parameters("HmacSHA256", "UTF-8", salt, c);
                byte[] dk = new PBKDF2Engine(p).deriveKey(auth);
                return dk;
            }
        }
        System.out.println("Unsupported KDF!");
        return null;
    }

    /**
     * 作用：将保存在文件中的公钥文件进行解开，返回一个公钥类型对象
     * @param encodepub　　公钥文件内容
     * @return            公钥类型对象
     * @throws InvalidKeySpecException
     */

    public static PublicKey DecryptPublickey(String encodepub) throws InvalidKeySpecException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] pubByte = new BigInteger(encodepub,10).toByteArray();
        KeyFactory kf = ECKeyFactory.getInstance();
        X509EncodedKeySpec ks = new X509EncodedKeySpec(pubByte);
        //PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(llByte);
        PublicKey pub = kf.generatePublic(ks);
        return pub;
    }
}
