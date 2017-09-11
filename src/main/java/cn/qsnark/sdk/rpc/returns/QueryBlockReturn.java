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
public class QueryBlockReturn {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private int code;
    private String status;
    private JSONObject block;
    private long number;
    private String hash;
    private String parentHash;
    private long writeTime;
    private long avgTime;
    private long txCounts;
    private String merkleRoot;
    private JSONArray transactions;

    public QueryBlockReturn(String jsonString) {
        logger.debug("[RESPONSE] " + jsonString);
        if (jsonString.contains("Status")) {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            this.status = jsonObject.getString("Status");
            this.code = jsonObject.getInt("Code");
            if (code == 0) {
                if (jsonObject.containsKey("block")) {
                    if (jsonObject.containsKey("block")) {
                        if (jsonObject.getString("block") == null || jsonObject.getString("block").equals("null") || jsonObject.getString("block").equals("")) {
                            this.block = null;
                        } else {
                            this.block = jsonObject.getJSONObject("block");
                            JSONObject blockObject = JSONObject.fromObject(this.block);
                            this.number = blockObject.getLong("Number");
                            this.hash = blockObject.getString("Hash");
                            this.parentHash = blockObject.getString("ParentHash");
                            this.writeTime = blockObject.getLong("WriteTime");
                            this.avgTime = blockObject.getLong("AvgTime");
                            this.txCounts = blockObject.getLong("Txcounts");
                            this.merkleRoot = blockObject.getString("MerkleRoot");
                            this.transactions = blockObject.getJSONArray("Transactions");
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

    public JSONObject getBlock() {
        return block;
    }

    public long getNumber() {
        return number;
    }

    public String getHash() {
        return hash;
    }

    public String getParentHash() {
        return parentHash;
    }

    public long getWriteTime() {
        return writeTime;
    }

    public long getAvgTime() {
        return avgTime;
    }

    public long getTxCounts() {
        return txCounts;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public JSONArray getTransactions() {
        return transactions;
    }
}