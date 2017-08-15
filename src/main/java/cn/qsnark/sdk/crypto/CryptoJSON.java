package cn.qsnark.sdk.crypto;

import cn.qsnark.sdk.rpc.utils.ByteUtil;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * description:
 * <p>
 * date 10/28/16
 *
 * @author chenquan
 * @version 1.0
 */
public class CryptoJSON {
    String cipher;
    String ciphertext;
    CipherparamsJSON cipherParams;
    String KDF;
    Map<String, Object> KDFParams;
    String MAC;

    public CryptoJSON(JSONObject jsonObject) {
        Map map = jsonObject;
        this.cipher = (String) map.get("cipher");
        this.ciphertext = (String) map.get("ciphertext");
        Map cipherParamsMap = (Map) map.get("cipherparams");
        this.cipherParams = new CipherparamsJSON((String) cipherParamsMap.get("iv"));
        this.KDF = (String) map.get("kdf");
        JSONObject kdfParamsJsonObj = (JSONObject) map.get("kdfparams");
        Map<String, Object> KDFmap = kdfParamsJsonObj;
        this.KDFParams = KDFmap;
        this.MAC = (String) map.get("mac");
    }

    public CryptoJSON(String cipher, String ciphertext, CipherparamsJSON cipherParams, String KDF, Map<String, Object> KDFParams, String MAC) {
        this.cipher = cipher;
        this.ciphertext = ciphertext;
        this.cipherParams = cipherParams;
        this.KDF = KDF;
        this.KDFParams = KDFParams;
        this.MAC = MAC;
    }

    @Override
    public String toString() {
        return "cipher: " + this.cipher + "\n" +
                "ciphertext: " + this.ciphertext + "\n" +
                "KDF: " + this.KDF + "\n" +
                "MAC: " + this.MAC + "\n";
    }

    public String getJSON(byte[] address) {
        String JsonStr =
                "{" +
                        "\"address\":\"" + ByteUtil.toHexString(address) + "\"," +
                        "\"crypto\":{" +
                        "\"cipher\":\"" + this.cipher + "\"," +
                        "\"ciphertext\":\"" + this.ciphertext + "\"," +
                        "\"cipherparams\":{\"iv\":\"" + this.cipherParams.IV + "\"}," +
                        "\"kdf\":\"scrypt\"," +
                        "\"kdfparams\":{" +
                        "\"dklen\":" + String.valueOf((Integer) this.KDFParams.get("dklen")) + "," +
                        "\"n\":" + String.valueOf((Integer) this.KDFParams.get("n")) + "," +
                        "\"p\":" + String.valueOf((Integer) this.KDFParams.get("p")) + "," +
                        "\"r\":" + String.valueOf((Integer) this.KDFParams.get("r")) + "," +
                        "\"salt\":\"" + (String) this.KDFParams.get("salt") + "\"}," +
                        "\"mac\":\"" + this.MAC + "\"},\"version\":3}";
        return JsonStr;
    }
}

