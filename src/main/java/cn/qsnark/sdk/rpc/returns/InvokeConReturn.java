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
public class InvokeConReturn {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    private String status;
    private String data;
    private String txHash;
    private String error;
    private String message;
    private int code;


    public InvokeConReturn(String jsonString) {
//        System.out.println(jsonString);
        logger.debug("[RESPONSE] " + jsonString);

        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            if (jsonObject.has("Status")) {
                this.status = jsonObject.getString("Status");
            }
            if (jsonObject.has("Data")) {
                this.data = jsonObject.getString("Data");
            }
            if (jsonObject.has("TxHash")) {
                this.txHash = jsonObject.getString("TxHash");
            }
        }
        if (this.txHash.equals("")) {
            this.error = this.status;
            this.message = this.status;
            this.code = -1;
        } else {

            this.message = "success";
            this.code = 0;
        }
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
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
