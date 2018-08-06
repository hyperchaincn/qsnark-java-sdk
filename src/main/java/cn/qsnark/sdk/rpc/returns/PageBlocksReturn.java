package cn.qsnark.sdk.rpc.returns;


import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午4:50
 */
public class PageBlocksReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String status;
    private JSONArray list;
    private int count;
    private String error;
    private String message;
    private int code;

    public PageBlocksReturn(String jsonString) {
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
                if (jsonObject.containsKey("List")) {
                    if (jsonObject.getString("List") == null || jsonObject.getString("List").equals("null") || jsonObject.getString("List").equals("")) {
                        this.list = null;
                    } else {
                        this.list = jsonObject.getJSONArray("List");
                    }
                }
                if(jsonObject.containsKey("Count")){
                    this.count = jsonObject.getInt("Count");
                }
            } else {
                logger.debug("Incoming parameters are incorrect, please re-pass the parameters");
            }
            if (this.list == null || this.list.equals("")) {
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

    public JSONArray getList() {
        return list;
    }

    public int getCount() {
        return count;
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