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

    private int code;
    private String status;
    private int id;
    private String address;
    private String time;
    private boolean isDisabled;
    private String appName;

    public CreteAccountReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            if (jsonObject.containsKey("Code"))
                this.code = jsonObject.getInt("Code");
            if (jsonObject.containsKey("Status"))
                this.status = jsonObject.getString("Status");
            if (code == 0) {
                if (jsonObject.containsKey("id"))
                    this.id = jsonObject.getInt("id");
                if (jsonObject.containsKey("address"))
                    this.address = jsonObject.getString("address");
                if (jsonObject.containsKey("time"))
                    this.time = jsonObject.getString("time");
                if (jsonObject.containsKey("isDisabled"))
                    this.isDisabled = jsonObject.getBoolean("isDisabled");
                if(jsonObject.containsKey("appName")){
                    this.appName = jsonObject.getString("appName");
                }
            }
        }
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public boolean isDisabled() {
        return isDisabled;
    }
}


