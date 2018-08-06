package cn.qsnark.sdk.rpc.JsonBean;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-14
 * Time: 下午3:00
 */
public class JsonDeploy {

    private String token;
    private String bin;
    private String from;

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    public String getToken() {
        return token;
    }

    public String getBin() {
        return bin;
    }

    public String getFrom() {
        return from;
    }

    public JsonDeploy(String jsonString) {

        logger.debug("[REQUEST] " + jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        this.token = jsonObject.getString("token");
        this.bin = jsonObject.getString("bin");
        this.from = jsonObject.getString("from");
    }
}
