package cn.qsnark.sdk.rpc.JsonBean;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-14
 * Time: 下午3:57
 */

public class JsonInvoke {

    private String func_name;
    private String token;
    private boolean _const;
    private String from;
    private String payload;
    private String to;
    private String abi;
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    public JsonInvoke(String func_name, String token,  boolean _const, String from, String payload,  String to, String abi) {
        this.func_name = func_name;
        this.token = token;
        this._const = _const;
        this.from = from;
        this.payload = payload;
        this.to = to;
        this.abi = abi;
    }

    public String getAbi() {
        return abi;
    }

    public String getToken() {
        return token;
    }


    public boolean is_const() {
        return _const;
    }

    public String getFrom() {
        return from;
    }

    public String getFunc_name() {
        return func_name;
    }

    public String getPayload() {
        return payload;
    }

    public String getTo() {
        return to;
    }

    public JsonInvoke(String account_token, boolean _const, String payload, String jsonString) {

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        this.func_name = jsonObject.getString("func_name");
        this.token = jsonObject.getString("token");
        this._const = _const;
        this.payload = payload;
        this.from = jsonObject.getString("from");
        this.to = jsonObject.getString("to");
        if (jsonObject.containsKey("abi"))
            this.abi = jsonObject.getString("abi");

    }

    public JsonInvoke( boolean _const, String jsonString) {

        logger.debug("[REQUEST] " + jsonString);

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        this.func_name = jsonObject.getString("func_name");
        this.token = jsonObject.getString("token");
        this._const = _const;
        this.payload = jsonObject.getString("payload");
        this.from = jsonObject.getString("from");
        this.to = jsonObject.getString("to");
        this.abi = jsonObject.getString("abi");


    }


}
