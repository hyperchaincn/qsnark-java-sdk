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
    private String status;
    private JSONArray cts;
    private String cts_status;
    private int cts_id;
    private String cts_bin;
    private String cts_abi;
    private boolean cts_ok;
    private String error;
    private String message;
    private int code;

    public CompileReturn(String jsonString) {

        logger.debug("[RESPONSE] " + jsonString);
        String ctsJsonString = "";
        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else {
            if (jsonString.contains("Status")) {
                JSONObject jsonObject = JSONObject.fromObject(jsonString);
                if (jsonObject.containsKey("Status"))
                    this.status = jsonObject.getString("Status");
                if (jsonObject.containsKey("Cts")) {
                    ctsJsonString = jsonObject.getString("Cts");
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
                        }

                        if (this.cts_bin == null || this.cts_bin.equals("")) {
                            this.error = this.status;
                            this.message = this.status;
                            this.code = -1;
                        } else {

                            this.message = "success";
                            this.code = 0;
                        }
                    }else{
                        this.error = this.status;
                        this.message = this.status;
                        this.code = -1;
                    }

                }


            }
        }
        if (ctsJsonString == null || ctsJsonString.equals("")) {
            this.error = this.status;
            this.message = this.status;
            this.code = -1;
        }
    }

    public String getStatus() {
        return status;
    }

    public JSONArray getCts() {
        return cts;
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

    public String getCts_abi() {
        return cts_abi;
    }

    public boolean isCts_ok() {
        return cts_ok;
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
