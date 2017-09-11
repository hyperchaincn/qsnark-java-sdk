package cn.qsnark.sdk.rpc.returns;


import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午4:50
 */
public class DiscardConReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private int code;
    private String status;
    private JSONArray transaction;

    public int getCode() {
        return code;
    }

    public DiscardConReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {

            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            this.status = jsonObject.getString("Status");
            this.code = jsonObject.getInt("Code");
            if (code == 0) {
                if (jsonObject.containsKey("Transactions"))
                    this.transaction = jsonObject.getJSONArray("Transactions");
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public JSONArray getTransaction() {
        return transaction;
    }
}