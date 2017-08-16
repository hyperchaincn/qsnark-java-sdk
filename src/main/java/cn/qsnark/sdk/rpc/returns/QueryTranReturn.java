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
public class QueryTranReturn {


    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

    private String version;
    private String status;
    private JSONObject transaction;
    private String hash;
    private int blockNumber;
    private String blockHash;
    private String txIndex;
    private String from;
    private String to;
    private int amount;
    private String timestamp;
    private int executeTime;
    private boolean invalid;
    private String invalidMsg;
    private String error;
    private String message;
    private int code;

    public QueryTranReturn(String jsonString) {

        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("invalid access token")) {
            this.error = "invalid access token";
            this.message = "invalid access token";
            this.code = -1;
        } else {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            if (jsonObject.containsKey("status"))
                this.status = jsonObject.getString("Status");
            if (jsonObject.containsKey("Transaction")) {
                this.transaction = jsonObject.getJSONObject("Transaction");
                jsonObject = this.transaction;
                if (jsonObject.containsKey("Version"))
                    this.version = jsonObject.getString("Version");
                if (jsonObject.containsKey("Hash"))
                    this.hash = jsonObject.getString("Hash");
                if (jsonObject.containsKey("BlockNumber"))
                    this.blockNumber = jsonObject.getInt("BlockNumber");
                if (jsonObject.containsKey("BlockHash"))
                    this.blockHash = jsonObject.getString("BlockHash");
                if (jsonObject.containsKey("TxIndex"))
                    this.txIndex = jsonObject.getString("TxIndex");
                if (jsonObject.containsKey("From"))
                    this.from = jsonObject.getString("From");
                if (jsonObject.containsKey("To"))
                    this.to = jsonObject.getString("To");
                if (jsonObject.containsKey("Amount"))
                    this.amount = jsonObject.getInt("Amount");
                if (jsonObject.containsKey("Timestamp"))
                    this.timestamp = jsonObject.getString("Timestamp");
                if (jsonObject.containsKey("ExecuteTime"))
                    this.executeTime = jsonObject.getInt("ExecuteTime");
                if (jsonObject.containsKey("Invalid"))
                    this.invalid = jsonObject.getBoolean("Invalid");
                if (jsonObject.containsKey("InvalidMsg"))
                    this.invalidMsg = jsonObject.getString("InvalidMsg");
            }
            if (this.hash.equals("")) {
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

    public String getVersion() {
        return version;
    }

    public JSONObject getTransaction() {
        return transaction;
    }

    public String getHash() {
        return hash;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public String getTxIndex() {
        return txIndex;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public String getInvalidMsg() {
        return invalidMsg;
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
