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
    private String status;
    private String txHash;
    private String error;
    private String message;
    private int code;

    public DeployConReturn(String jsonString) {
//        System.out.println(jsonString);
        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else {
            logger.debug("[RESPONSE] " + jsonString);
            if (jsonString.contains("Status")) {
                JSONObject jsonObject = JSONObject.fromObject(jsonString);
                if (jsonObject.containsKey("Status"))
                    this.status = jsonObject.getString("Status");
                if (jsonObject.containsKey("TxHash"))
                    this.txHash = jsonObject.getString("TxHash");
            } else {
                logger.debug("Incoming parameters are incorrect, please re-pass the parameters");
            }
            if (this.txHash == null || this.txHash.equals("")) {
                this.error = this.status;
                this.message = this.status;
                this.code = -1;
            } else {

                this.message = "success";
                this.code = 0;
            }
        }

    }

    public String getStatus() {
        return status;
    }

    public String getTxHash() {
        return txHash;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}