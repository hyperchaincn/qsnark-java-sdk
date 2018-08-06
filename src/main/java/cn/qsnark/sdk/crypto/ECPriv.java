package cn.qsnark.sdk.crypto;

import cn.qsnark.sdk.rpc.utils.Utils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.PrivateKey;
import java.security.PublicKey;

import static cn.qsnark.sdk.rpc.utils.ByteUtil.toHexString;

public class ECPriv {
    private ECKey ecKey;
    private byte[] address;
    private byte[] privateKey;
    public ECPriv (ECKey eckey){
        this.ecKey = eckey;
        this.address = eckey.getAddress();
        this.privateKey = eckey.getPrivKeyBytes();
    }

    @Deprecated
    public byte[] getAddressByte() {
        return address;
    }

    public String address(){return toHexString(address);}

    public String getPrivateKey(){ return toHexString(privateKey); }

    public PrivateKey getECPrivateKey(){
        return this.ecKey.GetPrivateKey();
    }

    public PublicKey getECPublicKey(){
        return this.ecKey.GetPublickey();
    }

    public byte[] getPublicKeyByte(){
        return this.ecKey.getPubKey();
    }

    public byte[] getPrivateKeyByte(){
        return this.ecKey.getPrivKeyBytes();
    }

    public String getECPublicKeyPEM(String pubkeyPath){
        //TODO this should complete this return PEM string
        //需要返回公钥的PEM格式srting
        PemObject pemObject  =  new PemObject("ECDSA PUBLIC KEY",this.ecKey.getPubKey());
        PemWriter pemWriter = null;
        try {
            pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream("/tmp/tcert.pub")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            pemWriter.writeObject(pemObject);
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pemWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String pub = "";
        try {
           pub = Utils.readFile(pubkeyPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pub;
    }

    public byte[] signData(byte[] messageHash){
        return this.ecKey.sign(messageHash).toByteArray();
    }

    public ECKey getEcKey() {
        return ecKey;
    }
}
