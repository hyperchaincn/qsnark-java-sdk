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
    private String account_token;
    private String bin;
    private String from;
    private int id;
    private String _private;
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    public String getToken() {
        return token;
    }

    public String getAccount_token() {
        return account_token;
    }

    public String getBin() {
        return bin;
    }

    public String getFrom() {
        return from;
    }

    public int getId() {
        return id;
    }

    public String get_private() {
        return _private;
    }

    public JsonDeploy(String jsonString) {

        logger.debug("[REQUEST] " + jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        this.token = jsonObject.getString("token");
        this.account_token = jsonObject.getString("access_token");
        this.bin = jsonObject.getString("bin");
        this.from = jsonObject.getString("from");
        this.id = jsonObject.getInt("id");
        this._private = jsonObject.getString("private");
    }
}
