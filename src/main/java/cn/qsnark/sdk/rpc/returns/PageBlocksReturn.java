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
    private int code;
    private String status;
    private JSONArray list;
    private int count;

    public PageBlocksReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        this.status = jsonObject.getString("Status");
        this.code = jsonObject.getInt("Code");
        if (jsonObject.containsKey("List")) {
            if (jsonObject.getString("List") == null || jsonObject.getString("List").equals("null") || jsonObject.getString("List").equals("")) {
                this.list = null;
            } else {
                this.list = jsonObject.getJSONArray("List");
            }
        }
        if (jsonObject.containsKey("Count")) {
            this.count = jsonObject.getInt("Count");
        }
    }

    public int getCode() {
        return code;
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
}
