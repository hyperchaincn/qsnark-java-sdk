package cn.qsnark.sdk.rpc.returns;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 上午11:02
 */
public class SignatureReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    public String status;
    public String message;

    public SignatureReturn(String jsonString) {
//        System.out.println(jsonString);
        logger.debug("[RESPONSE] " + jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if (jsonObject.containsKey("status"))
            this.status = jsonObject.getString("status");
        if (jsonObject.containsKey("message"))
            this.message = jsonObject.getString("message");


    }
}