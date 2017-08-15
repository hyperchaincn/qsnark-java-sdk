package cn.qsnark.sdk.rpc.JsonBean;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-14
 * Time: 下午1:32
 */

public class JsonGetTxRecipt {

    private String token;

    private String txhash;

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    public String getToken() {
        return token;
    }

    public String getTxhash() {
        return txhash;
    }

    public JsonGetTxRecipt(String jsonString) {

        logger.debug("[REQUEST] " + jsonString);

        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        this.token = jsonObject.getString("token");

        this.txhash = jsonObject.getString("txhash");
    }
}
