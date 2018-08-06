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
public class NodesChainReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String status;
    private JSONArray nodes;
    private String error;
    private String message;
    private int code;

    public NodesChainReturn(String jsonString) {
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
                if (jsonObject.containsKey("Nodes"))
                    this.nodes = jsonObject.getJSONArray("Nodes");

            } else {
                logger.debug("Incoming parameters are incorrect, please re-pass the parameters");
            }
            if (this.nodes == null || this.nodes.equals("")) {
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

    public JSONArray getNodes() {
        return nodes;
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