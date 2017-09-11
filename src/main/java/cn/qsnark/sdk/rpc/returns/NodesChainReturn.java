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
    private int code;
    private String status;
    private JSONArray nodes;

    public NodesChainReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            this.status = jsonObject.getString("Status");
            this.code = jsonObject.getInt("Code");
            if (code == 0) {
                if (jsonObject.containsKey("Nodes"))
                    this.nodes = jsonObject.getJSONArray("Nodes");
            }
        } else {
            logger.debug("Incoming parameters are incorrect, please re-pass the parameters");
        }
    }


    public String getStatus() {
        return status;
    }

    public JSONArray getNodes() {
        return nodes;
    }

    public int getCode() {
        return code;
    }
}