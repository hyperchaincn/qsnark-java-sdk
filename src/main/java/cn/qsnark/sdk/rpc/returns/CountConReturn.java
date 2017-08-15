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
public class CountConReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String status;
    private String count;
    private String timeStamp;
    private String error;
    private String message;
    private int code;


    public CountConReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else {

            if (jsonString.contains("Status")) {
                JSONObject jsonObject = JSONObject.fromObject(jsonString);
                if (jsonObject.containsKey("Cts")){
                    if(jsonObject.getString("Cts")==null){
                        this.error = "Rpc Server error";
                        this.message = jsonObject.getString("Status");
                        this.code = -1;
                    }
                }
                if (jsonObject.containsKey("Status"))
                    this.status = jsonObject.getString("Status");
                if (jsonObject.containsKey("Count"))
                    this.count = jsonObject.getString("Count");
                if (jsonObject.containsKey("Timestamp"))
                    this.timeStamp = jsonObject.getString("Timestamp");
            } else {
                logger.debug("Incoming parameters are incorrect, please re-pass the parameters");
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