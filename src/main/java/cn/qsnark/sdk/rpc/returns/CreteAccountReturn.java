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
public class CreteAccountReturn {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String address;
    private String status;
    private int code;
    private String message;
    private String error;

    public CreteAccountReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);

        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            if (jsonObject.containsKey("address"))
                this.address = jsonObject.getString("address");
            if (jsonObject.containsKey("Status"))
                this.status = jsonObject.getString("Status");
            if (this.address == null || this.address.equals("")) {
                this.error = this.status;
                this.message = this.status;
                this.code = -1;
            } else {

                this.message = "success";
                this.code = 0;
            }

        }
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}

