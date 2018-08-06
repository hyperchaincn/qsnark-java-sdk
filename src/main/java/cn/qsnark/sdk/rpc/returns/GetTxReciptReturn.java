package cn.qsnark.sdk.rpc.returns;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午5:33
 */
public class GetTxReciptReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    private String status;
    private String txHash;
    private String poststate;
    private String contract_address;
    private String ret;
    private String error;
    private String message;
    private int code;


    public GetTxReciptReturn(String jsonString) {

//        System.out.println(jsonString);
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
                if (jsonObject.containsKey("TxHash"))
                    this.txHash = jsonObject.getString("TxHash");
                if (jsonObject.containsKey("PostState"))
                    this.poststate = jsonObject.getString("PostState");
                if (jsonObject.containsKey("ContractAddress"))
                    this.contract_address = jsonObject.getString("ContractAddress");
                if (jsonObject.containsKey("Ret"))
                    this.ret = jsonObject.getString("Ret");
                if (this.txHash == null || this.txHash.equals("")) {
                    this.error = this.status;
                    this.message = this.status;
                    this.code = -1;
                } else {
                    this.message = "success";
                    this.code = 0;
                }
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public String getTxHash() {
        return txHash;
    }

    public String getPoststate() {
        return poststate;
    }

    public String getContract_address() {
        return contract_address;
    }

    public String getRet() {
        return ret;
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
