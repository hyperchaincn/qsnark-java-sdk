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
public class DeleteConReturn {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private int error_code;
    private String error_msg;


    public DeleteConReturn(String jsonString) {
//        System.out.println(jsonString);
        logger.debug("[RESPONSE] " + jsonString);

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if (jsonObject.containsKey("error_code"))
            this.error_code = jsonObject.getInt("error_code");
        if (jsonObject.containsKey("error_msg"))
            this.error_msg = jsonObject.getString("error_msg");


    }

    public int getError_code() {
        return error_code;
    }

    public String getError_msg() {
        return error_msg;
    }
}


