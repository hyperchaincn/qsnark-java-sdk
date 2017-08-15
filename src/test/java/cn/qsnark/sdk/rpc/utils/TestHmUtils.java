package cn.qsnark.sdk.rpc.utils;

import cn.qsnark.sdk.crypto.DecryptKey;
import cn.qsnark.sdk.crypto.ECKey;
import cn.qsnark.sdk.crypto.EncryptedKeyJSONV3;
import cn.qsnark.sdk.rpc.types.SplitEcc;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Ignore;
import org.junit.Test;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import static cn.qsnark.sdk.rpc.utils.ByteUtil.intToByteArray;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-13
 * Time: 下午3:00
 */
public class TestHmUtils {

    @Test
    public void TestSplit() throws Exception {
        String eccamountStr = "369654069768716923993589128279657320254767041061964570147298966132322295898333964696156028555681247992527053237715051647381158124748986433112961764201760767393990033792057840620029786377286590295699342923661904417";
        System.out.println(eccamountStr);

        byte[] data = new BigInteger(eccamountStr,10).toByteArray();
        String eccamount32Str = new BigInteger(1,data).toString(32);
        System.out.println(data.length);
        System.out.println(eccamount32Str);
        System.out.println(eccamount32Str.length());
        SplitEcc splitEcc = HmUtils.SplitEccamount(data);
        System.out.println(Hex.toHexString(splitEcc.eccamount1).length());
        System.out.println(Hex.toHexString(splitEcc.eccamount2));
        System.out.println(Hex.toHexString(splitEcc.eccamount3));
    }

    @Ignore("must be passed recipient's public EC key for encryption")
    @Test
    public void TestECencryption() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        String accountJson = "{\"address\":\"3b51253f00aa70a30fb434963caf0409f5a9f92d\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"708d1193dd5d0429be7d15eda369c8fb3a56b4a825e32e8c0a72aa5691cdbe0d\",\"cipherparams\":{\"iv\":\"c1817b4490e525a792f9b99a2fad3861\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"e3741996c48f8c3ffc8ef4fad1d7be148416e826fde1d58b83b8ab9058d50d1e\"},\"mac\":\"7ab34008588062a0cf8b4fef8be0e8d789aa1993a21f602ca1554d0f1ed2ebeb\"},\"version\":3}";
        String passwd = "123456";
        byte[] privatebyte = DecryptKey.decryptKeyV3(new EncryptedKeyJSONV3(accountJson,true),passwd);
        ECKey eckey = ECKey.fromPrivate(privatebyte);
        PrivateKey pri = eckey.GetPrivateKey();
        PublicKey pub = eckey.GetPublickey();

        Cipher iesCipher = Cipher.getInstance("ECIES");
        iesCipher.init(Cipher.ENCRYPT_MODE,pub);
        byte[] payerEccamountByte = iesCipher.doFinal(intToByteArray(100));
        BigInteger payerEccbig = new BigInteger(1,payerEccamountByte);
        SplitEcc splitEcc = HmUtils.SplitEccamount(payerEccamountByte);
        System.out.println(payerEccbig.toString(10));
        System.out.println(payerEccbig.toString(10).length());
        System.out.println(splitEcc.eccamount1);
        System.out.println(splitEcc.eccamount2);
        System.out.println(splitEcc.eccamount3);

        Security.addProvider(new BouncyCastleProvider());
        Cipher iesCipher1 = Cipher.getInstance("ECIES");
        iesCipher1.init(Cipher.DECRYPT_MODE,pri);
        //iesCipher.init(Cipher.DECRYPT_MODE,pri);
        byte[] endata = iesCipher1.doFinal(payerEccamountByte);
        int amount = ByteUtil.byteArrayToInt(endata);
        System.out.println(amount);



    }
}
