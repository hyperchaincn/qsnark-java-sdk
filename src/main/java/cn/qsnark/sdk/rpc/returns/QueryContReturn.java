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
public class QueryContReturn {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    private String status;
    private String message;

    public QueryContReturn(String jsonString) {
//        System.out.println(jsonString);

        logger.debug("[RESPONSE] " + jsonString);

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if (jsonObject.containsKey("status"))
            this.status = jsonObject.getString("status");
        if (jsonObject.containsKey("status"))
            this.message = jsonObject.getString("message");
    }

}
