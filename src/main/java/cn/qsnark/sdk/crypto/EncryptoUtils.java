package cn.qsnark.sdk.crypto;


import cn.qsnark.sdk.exception.PwdException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class EncryptoUtils {
    /**

     * 加密
     * @param datasource byte[]
     * @param password String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) throws PwdException {
        if (password.length()>16){
            throw new PwdException(1,null);
        }
        if (password.length() % 8 != 0){
            StringBuffer sb = new StringBuffer(password);
            for(int i = 0;i<(8 - (password.length() % 8));i++){
                sb.append("0");
            }
            password = sb.toString();
        }
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(datasource);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     * @param src byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
        if (password.length()>16){
            throw new PwdException(1,null);
        }
        if (password.length() % 8 != 0){
            StringBuffer sb = new StringBuffer(password);
            for(int i = 0;i<(8 - (password.length() % 8));i++){
                sb.append("0");
            }
            password = sb.toString();
        }
// DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
// 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
// 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
// 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
// Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
// 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
// 真正开始解密操作
        return cipher.doFinal(src);
    }
}
