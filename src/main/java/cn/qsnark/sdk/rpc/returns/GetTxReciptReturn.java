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
    private int code;


    public GetTxReciptReturn(String jsonString) {

        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            this.status = jsonObject.getString("Status");
            this.code = jsonObject.getInt("Code");
            if (this.code == 0) {
                if (jsonObject.containsKey("TxHash"))
                    this.txHash = jsonObject.getString("TxHash");
                if (jsonObject.containsKey("PostState"))
                    this.poststate = jsonObject.getString("PostState");
                if (jsonObject.containsKey("ContractAddress"))
                    this.contract_address = jsonObject.getString("ContractAddress");
                if (jsonObject.containsKey("Ret"))
                    this.ret = jsonObject.getString("Ret");
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

    public int getCode() {
        return code;
    }
}
