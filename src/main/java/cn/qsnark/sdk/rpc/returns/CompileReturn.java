package cn.qsnark.sdk.rpc.returns;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 上午9:43
 */
public class CompileReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private int code;
    private String status;
    private JSONArray cts;
    private int cts_code;
    private String cts_status;
    private int cts_id;
    private String cts_bin;
    private String cts_abi;
    private boolean cts_ok;
    private String cts_name;

    public CompileReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            if (jsonObject.containsKey("Status"))
                this.status = jsonObject.getString("Status");
            if (jsonObject.containsKey("Code"))
                this.code = jsonObject.getInt("Code");
            if (this.code == 0) {
                if (jsonObject.containsKey("Cts")) {
                    String ctsJsonString = jsonObject.getString("Cts");
                    if (ctsJsonString != null && !ctsJsonString.equals("") && ctsJsonString != "null") {
                        this.cts = JSONArray.fromObject(ctsJsonString);
                        if (this.cts != null && !this.cts.equals("")) {
                            JSONObject jsObject = (JSONObject) this.cts.get(0);
                            if (jsObject.containsKey("Id"))
                                this.cts_id = jsObject.getInt("Id");
                            if (jsObject.containsKey("Bin"))
                                this.cts_bin = jsObject.getString("Bin");
                            if (jsObject.containsKey("Abi"))
                                this.cts_abi = jsObject.getString("Abi");
                            if (jsObject.containsKey("OK"))
                                this.cts_ok = jsObject.getBoolean("OK");
                            if (jsonObject.containsKey("Code"))
                                this.cts_code = jsObject.getInt("Code");
                            if (jsonObject.containsKey("Status"))
                                this.cts_status = jsObject.getString("Status");
                            if (jsObject.containsKey("Name"))
                                this.cts_name = jsObject.getString("Name");
                        }
                    }
                }
            }
        }
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public JSONArray getCts() {
        return cts;
    }

    public int getCts_code() {
        return cts_code;
    }

    public String getCts_status() {
        return cts_status;
    }

    public int getCts_id() {
        return cts_id;
    }

    public String getCts_bin() {
        return cts_bin;
    }

    public String getCts_name() {
        return cts_name;
    }

    public String getCts_abi() {
        return cts_abi;
    }

    public boolean isCts_ok() {
        return cts_ok;
    }
}

