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
public class RangeBlocksReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private int code;
    private String status;
    private JSONArray blocks;

    public RangeBlocksReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            this.status = jsonObject.getString("Status");
            this.code = jsonObject.getInt("Code");
            if (code == 0) {
                if (jsonObject.containsKey("Blocks")) {
                    if (jsonObject.getString("Blocks") == null || jsonObject.getString("Blocks").equals("null") || jsonObject.getString("Blocks").equals("")) {
                        this.blocks = null;
                    } else {
                        this.blocks = jsonObject.getJSONArray("Blocks");
                    }
                }
            }
        }
    }
    public String getStatus() {
        return status;
    }

    public JSONArray getBlocks() {
        return blocks;
    }

    public int getCode() {
        return code;
    }
}