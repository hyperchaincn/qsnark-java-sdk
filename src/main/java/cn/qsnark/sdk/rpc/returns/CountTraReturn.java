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
public class CountTraReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String status;
    private String count;
    private String timeStamp;
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

    public CountTraReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            if (jsonObject.containsKey("Status")) {
                this.status = jsonObject.getString("Status");
            }
            if (jsonObject.containsKey("Count")) {
                this.count = jsonObject.getString("Count");
            }
            if (jsonObject.containsKey("Timestamp")) {
                this.timeStamp = jsonObject.getString("Timestamp");
            }
            if (this.timeStamp == null || this.timeStamp.equals("")) {
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

    public String getCount() {
        return count;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}