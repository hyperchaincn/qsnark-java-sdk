package cn.qsnark.sdk.crypto;

import cn.qsnark.sdk.rpc.utils.ByteUtil;
import cn.qsnark.sdk.rpc.utils.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.UnsupportedEncodingException;
import java.security.*;

public class UniquePriv {
    private PrivateKey priKey;
    private String publiKeyTansEncoded;
    public UniquePriv (PrivateKey privateKey,String PublicPem){
        this.priKey = privateKey;
        this.publiKeyTansEncoded = Utils.transportEncode(PublicPem);
    }

    public String getPubliKeyTansEncoded() {
        return publiKeyTansEncoded;
    }

    public PrivateKey getPriKey() {
        return priKey;
    }

    public String signData(String message)
    {
        Signature ecdsaSign = null;
        try {
            ecdsaSign = Signature.getInstance("SHA256withECDSA", new BouncyCastleProvider());
            ecdsaSign.initSign(this.priKey);
            ecdsaSign.update(message.getBytes("UTF-8"));
            byte[] signature = ecdsaSign.sign();
            return ByteUtil.toHexString(signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "0x0";
        } catch (SignatureException e) {
            e.printStackTrace();
            return "0x0";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0x0";
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return "0x0";
        }

    }

}
