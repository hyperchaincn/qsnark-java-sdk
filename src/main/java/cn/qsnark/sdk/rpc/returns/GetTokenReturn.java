package cn.qsnark.sdk.rpc.returns;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-05
 * Time: 下午2:48
 */
public class GetTokenReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;
    private int code;
    private String message;
    private String error;

    public GetTokenReturn(String jsonString) {

        logger.debug("[RESPONSE] " + jsonString);

        if (!jsonString.contains("appkey doesn't exist")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);

            if (jsonObject.containsKey("error")) {
                this.error = jsonObject.getString("error");
                this.message = jsonObject.getString("error_description");
                this.code = -1;
            } else {
                this.code = 0;
                this.message = "success";
            }
            if (jsonObject.containsKey("access_token"))
                this.access_token = jsonObject.getString("access_token");
            if (jsonObject.containsKey("expires_in"))
                this.expires_in = jsonObject.getString("expires_in");
            if (jsonObject.containsKey("refresh_token"))
                this.refresh_token = jsonObject.getString("refresh_token");
            if (jsonObject.containsKey("scope"))
                this.scope = jsonObject.getString("scope");
            if (jsonObject.containsKey("token_type"))
                this.token_type = jsonObject.getString("token_type");

        }else{
            this.error = "appkey doesn't exist";
            this.message = "appkey doesn't exist";
            this.code = -1;
        }
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public String getToken_type() {
        return token_type;
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