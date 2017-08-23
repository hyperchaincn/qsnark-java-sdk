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
public class QueryBlockReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String status;
    private JSONObject blocks;
    private String error;
    private String message;
    private int code;

    public QueryBlockReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else {
            if (jsonString.contains("Status")) {
                JSONObject jsonObject = JSONObject.fromObject(jsonString);
                if (jsonObject.containsKey("Status"))
                    this.status = jsonObject.getString("Status");
                if (jsonObject.containsKey("block")) {
                    if (jsonObject.getString("block") == null || jsonObject.getString("block").equals("null") || jsonObject.getString("block").equals("")) {
                        this.blocks = null;
                    } else {
                        this.blocks = jsonObject.getJSONObject("block");
                    }
                }
            } else {
                logger.debug("Incoming parameters are incorrect, please re-pass the parameters");
            }
            if (this.blocks == null || this.blocks.equals("")) {
                this.error = this.status;
                this.message = this.status;
                this.code = -1;
            } else {

                this.message = "success";
                this.code = 0;
            }
        }

    }

    public String getStatus() {
        return status;
    }

    public JSONObject getBlocks() {
        return blocks;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}