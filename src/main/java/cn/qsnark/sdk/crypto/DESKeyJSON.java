package cn.qsnark.sdk.crypto;

import net.sf.json.JSONObject;

import java.util.Map;

public class DESKeyJSON {
    private String address;
    private String encrypted;
    private String version;
    private String algo;


    public DESKeyJSON(String address,String encrypted){
        this.address = address;
        this.encrypted = encrypted;
        this.version = "1.0";
        // algo
        // 0x01 KDF2
        // 0x02 DES
        this.algo="0x02";
    }

    public DESKeyJSON(String jsonString) throws Exception {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map map = jsonObject;
        this.address = (String) map.get("address");
        this.encrypted = (String) map.get("encrypted");
        this.version = (String) map.get("version");
        this.algo = (String) map.get("algo");
    }


    public String getAddress() {
        return address;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public String getVersion() {
        return version;
    }

    public String getAlgo() {
        return algo;
    }
}
