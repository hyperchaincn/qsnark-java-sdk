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

    public QueryATReturn(String jsonString) {
//        System.out.println(jsonString);
        logger.debug("[RESPONSE] " + jsonString);

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
