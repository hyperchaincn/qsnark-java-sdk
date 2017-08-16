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
public class QueryATReturn {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String access_token;
    private String expires_in;
    private int error_code;
    private String error_msg;
    private String error;
    private String message;
    private int code;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public QueryATReturn(String jsonString) {
//        System.out.println(jsonString);
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            if (jsonObject.containsKey("access_token"))
                this.access_token = jsonObject.getString("access_token");
            if (jsonObject.containsKey("expires_in"))
                this.expires_in = jsonObject.getString("expires_in");
            if (jsonObject.containsKey("error_code"))
                this.error_code = jsonObject.getInt("error_code");
            if (jsonObject.containsKey("error_msg"))
                this.error_msg = jsonObject.getString("error_msg");

        }
        if(this.access_token.equals("")){
            this.error =error_msg;
            this.message = error_msg;
            this.code = -1;
        }else{

            this.message = "success";
            this.code = 0;
        }
    }
    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public int getError_code() {
        return error_code;
    }

    public String getError_msg() {
        return error_msg;
    }
}
