package cn.qsnark.sdk.rpc.returns;


import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午4:50
 */
public class DeployConReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private int code;
    private String status;
    private String txHash;

    public DeployConReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            this.status = jsonObject.getString("Status");
            this.code = jsonObject.getInt("Code");
            if (code == 0) {
                if (jsonObject.containsKey("TxHash"))
                    this.txHash = jsonObject.getString("TxHash");

            }
        }
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getTxHash() {
        return txHash;
    }
}