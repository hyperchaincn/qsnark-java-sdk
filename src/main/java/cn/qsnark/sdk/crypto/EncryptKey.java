package cn.qsnark.sdk.crypto;

import cn.qsnark.sdk.crypto.cryptohash.DigestEngine;
import cn.qsnark.sdk.crypto.cryptohash.Keccak256;
import cn.qsnark.sdk.exception.PwdException;
import cn.qsnark.sdk.rpc.utils.ByteUtil;
import com.lambdaworks.crypto.SCrypt;
import net.sf.json.JSONObject;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 * <p>
 * date 10/30/16
 *
 * @author chenquan
 * @version 1.0
 */
public class EncryptKey {
    // EncryptKey encrypts a key using the specified scrypt parameters into a json
// blob that can be decrypted later on.
    public static String EncryptKey(byte[] address, byte[] privateKey, String auth,boolean isDes) throws GeneralSecurityException {
        int scryptR = 8;
        int scryptP = 1;
        int dklen = 32;
        int scryptN = scryptP * scryptR * dklen * 1024;
//        System.out.println("privateKey => " + ByteUtil.toHexString(privateKey));
        if (isDes){
            try {
                return genDESkey(address,privateKey,auth);
            } catch (PwdException e) {
                e.printStackTrace();
            }
        }
        return genKDFKey(address, privateKey, scryptN, scryptR, scryptP, dklen, auth);
    }

    private static String genKDFKey(byte[] address, byte[] priKey, int scryptN, int scryptR, int scryptP, int dkLen, String auth) throws GeneralSecurityException {

        SecureRandom random = new SecureRandom();

        byte[] authArray = auth.getBytes();

        byte[] salt = new byte[32];
        random.nextBytes(salt);

        byte[] derivedKey = SCrypt.scrypt(authArray, salt, scryptN, scryptR, scryptP, dkLen);

        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);

        ECKey ecKey = ECKey.fromPrivate(priKey);
        byte[] keyBytes = ecKey.getPrivKeyBytes();

        byte[] iv = new byte[16];
        random.nextBytes(iv);

        byte[] cipherText;

        try {
            SecretKey secret = new SecretKeySpec(encryptKey, "AES");
            /* Decrypt the message, given derived key and initialization vector. */
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

            cipherText = cipher.doFinal(keyBytes);


//            System.out.println("plainText: " + ByteUtil.toHexString(cipherText));

        } catch (IllegalBlockSizeException e){
            e.printStackTrace();
            return null;
        }
        catch(BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
        catch( InvalidKeyException e){
            e.printStackTrace();
            return null;
        }
        catch( InvalidAlgorithmParameterException e){
            e.printStackTrace();
            return null;
        }
        catch( NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }


//        mac := crypto.Keccak256(derivedKey[16:32], cipherText)

        byte[] subDerivedKey = Arrays.copyOfRange(derivedKey, 16, 32);

        DigestEngine digestEngine = new Keccak256();
        digestEngine.update(subDerivedKey);
        byte[] mac = digestEngine.digest(cipherText);

        CipherparamsJSON cipherparamsJSON = new CipherparamsJSON(ByteUtil.toHexString(iv));

        Map<String, Object> kdfParams = new HashMap<String, Object>();
        kdfParams.put("n", scryptN);
        kdfParams.put("r", scryptR);
        kdfParams.put("p", scryptP);
        kdfParams.put("dklen", dkLen);
        kdfParams.put("salt", ByteUtil.toHexString(salt));

        CryptoJSON cryptoJSON = new CryptoJSON("aes-128-ctr", ByteUtil.toHexString(cipherText), cipherparamsJSON, "scrypt", kdfParams, ByteUtil.toHexString(mac));

        return cryptoJSON.getJSON(address);

    }

    private static String genDESkey(byte[] address,byte[] priKey,String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, PwdException {
        byte[] encryptedData = EncryptoUtils.encrypt(priKey,password);
        return JSONObject.fromObject(new DESKeyJSON(ByteUtil.toHexString(address),ByteUtil.toHexString(encryptedData))).toString();
    }

    public static String GenPlainKey(byte[]address,byte[] privateKey,String auth){
        return JSONObject.fromObject(new PlainKey(ByteUtil.toHexString(address),ByteUtil.toHexString(privateKey))).toString();

    }
    public static String GenPlainKey(byte[]address,byte[] privateKey){
        return JSONObject.fromObject(new PlainKey(ByteUtil.toHexString(address),ByteUtil.toHexString(privateKey))).toString();

    }
}
