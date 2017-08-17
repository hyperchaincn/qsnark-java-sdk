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
public class ChangeAccountReturn {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String status;
    private String message;
    private String address;
    private String type;
    private int error_code;
    private String error_msg;

    public ChangeAccountReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if (jsonObject.containsKey("address"))
            this.address = jsonObject.getString("address");
        if (jsonObject.containsKey("type"))
            this.type = jsonObject.getString("type");
        if (jsonObject.containsKey("error_code"))
            this.error_code = jsonObject.getInt("error_code");
        if (jsonObject.containsKey("status"))
            this.status = jsonObject.getString("status");
        if (jsonObject.containsKey("message"))
            this.message = jsonObject.getString("message");
        if (jsonObject.containsKey("error_msg"))
            this.error_msg = jsonObject.getString("error_msg");

    }


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public int getError_code() {
        return error_code;
    }

    public String getError_msg() {
        return error_msg;
    }
}
