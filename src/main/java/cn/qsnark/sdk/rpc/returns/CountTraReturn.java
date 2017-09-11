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
    private int code;
    private String status;
    private String count;
    private String timeStamp;

    public CountTraReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        this.status = jsonObject.getString("Status");
        this.code = jsonObject.getInt("Code");
        if (code == 0) {
            if (jsonObject.containsKey("Count")) {
                this.count = jsonObject.getString("Count");
            }
            if (jsonObject.containsKey("Timestamp")) {
                this.timeStamp = jsonObject.getString("Timestamp");
            }
        }
    }

    public int getCode() {
        return code;
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