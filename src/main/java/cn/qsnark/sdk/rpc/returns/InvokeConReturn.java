package cn.qsnark.sdk.rpc.returns;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 上午9:43
 */
public class InvokeConReturn {

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    private String status;
    private String data;
    private String txHash;


    public InvokeConReturn(String jsonString) {
//        System.out.println(jsonString);
        logger.debug("[RESPONSE] " + jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        if (jsonObject.has("Status")) {
            this.status = jsonObject.getString("Status");
        }
        if (jsonObject.has("Data")) {
            this.data = jsonObject.getString("Data");
        }
        if (jsonObject.has("TxHash")) {
            this.txHash = jsonObject.getString("TxHash");
        }
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public String getTxHash() {
        return txHash;
    }

}
