package cn.qsnark.sdk.crypto;

import cn.qsnark.sdk.rpc.utils.Utils;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * description:
 * <p>
 * date 10/28/16
 *
 * @author chenquan
 * @version 1.0
 */
public class EncryptedKeyJSONV3 {
    String address;
    CryptoJSON crypto;
    int version;

    public EncryptedKeyJSONV3(String keyfilePath) throws IOException {
        String jsonString = Utils.readFile(keyfilePath);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map map = jsonObject;
        this.address = (String) map.get("address");
        this.crypto = new CryptoJSON((JSONObject) map.get("crypto"));
        this.version = (Integer) map.get("version");
//        System.out.println(map);
    }

    public EncryptedKeyJSONV3(String jsonString, boolean isJsonString) throws Exception {
        if (!isJsonString) {
            throw new Exception("not json string error");
        }
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map map = jsonObject;
        this.address = (String) map.get("address");
        this.crypto = new CryptoJSON((JSONObject) map.get("crypto"));
        this.version = (Integer) map.get("version");
//        System.out.println(map);
    }

    public String getAddress(){
        return address;
    }
    public CryptoJSON getCrypto() {
        return crypto;
    }
}
