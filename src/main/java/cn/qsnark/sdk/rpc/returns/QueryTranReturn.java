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

    private int code;
    private String status;
    private JSONObject transaction;
    private String version;
    private String hash;
    private int blockNumber;
    private String blockHash;
    private String txIndex;
    private String from;
    private String to;
    private int amount;
    private String timestamp;
    private long nonce;
    private int executeTime;
    private String payload;
    private boolean invalid;
    private String invalidMsg;

    public QueryTranReturn(String jsonString) {

        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            this.status = jsonObject.getString("Status");
            this.code = jsonObject.getInt("Code");
            if (code == 0) {
                if (jsonObject.containsKey("Transaction")) {
                    this.transaction = jsonObject.getJSONObject("Transaction");
                    jsonObject = this.transaction;
                    if (jsonObject != null || jsonObject.equals("")) {
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
                        if (jsonObject.containsKey("Nonce")) {
                            this.nonce = jsonObject.getLong("Nonce");
                        }
                        if (jsonObject.containsKey("Payload")) {
                            this.payload = jsonObject.getString("Payload");
                        }
                    }
                }
            }
        }
    }

    public String getVersion() {
        return version;
    }

    public String getStatus() {
        return status;
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

    public long getNonce() {
        return nonce;
    }

    public int getExecuteTime() {
        return executeTime;
    }

    public String getPayload() {
        return payload;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public String getInvalidMsg() {
        return invalidMsg;
    }

    public int getCode() {
        return code;
    }
}
