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
public class StatusConReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String status;
    private int code;
    private String ctStatus;

    public StatusConReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            this.code = jsonObject.getInt("Code");
            this.status = jsonObject.getString("Status");
            if(this.code == 0){
                this.ctStatus = jsonObject.getString("ctStatus");
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getCtStatus() {
        return ctStatus;
    }
}