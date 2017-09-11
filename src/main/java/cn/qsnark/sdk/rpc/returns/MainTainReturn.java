package cn.qsnark.sdk.rpc.returns;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 上午9:43
 */
public class MainTainReturn {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    private int code;
    private String status;
    private String txHash;


    public MainTainReturn(String jsonString) {

        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {

            JSONObject jsonObject = JSONObject.fromObject(jsonString);

            this.code = jsonObject.getInt("Code");
            this.status = jsonObject.getString("Status");
            if (code == 0) {
                if (jsonObject.has("TxHash")) {
                    this.txHash = jsonObject.getString("TxHash");
                }
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public String getTxHash() {
        return txHash;
    }

    public int getCode() {
        return code;
    }
}
