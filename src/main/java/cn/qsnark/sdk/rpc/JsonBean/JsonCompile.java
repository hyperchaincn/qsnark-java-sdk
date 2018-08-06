package cn.qsnark.sdk.rpc.JsonBean;

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
public class JsonCompile {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    private String token;

    private String sourceCode;

    public String getToken() {
        return token;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public JsonCompile(String jsonString) {

        logger.debug("[REQUEST] " + jsonString);

        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        this.token = jsonObject.getString("token");
        String temp = jsonObject.getString("sourceCode");
        this.sourceCode = temp.substring(2, temp.length() - 2);



    }


}


