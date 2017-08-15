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


    public StatusConReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            if (jsonObject.containsKey("Status"))
                this.status = jsonObject.getString("Status");
        } else {
            logger.debug("Incoming parameters are incorrect, please re-pass the parameters");
        }
    }

    public String getStatus() {
        return status;
    }

}