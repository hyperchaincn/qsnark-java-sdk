package cn.qsnark.sdk.rpc.params;

import cn.qsnark.sdk.crypto.HashUtil;
import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.utils.ByteUtil;
import cn.qsnark.sdk.rpc.utils.Utils;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-06
 * Time: 下午7:49
 */
public class ToHashParams {
    private static Logger logger = Logger.getLogger(QsnarkAPI.class);
    private String from;
    private String to;
    private String payload;
    private String hash;
    private Long timestamp;
    private Long nonce;

    //    = "from=" + this.from + "&to=" + this.to +"&value=" + this.payload + "&timestamp=0x" + Long.toHexString(this.timestamp) + "&nonce=0x" +Long.toHexString(this.nonce);
// 调用合约
    public ToHashParams(String from, String to, String payload) throws TxException {
        this.timestamp = genTimestamp();
        this.nonce = genNonce();
        this.from = chPrefix(from);
        this.to = chPrefix(to);
        this.payload = chPrefix(payload);
        String toHashString = "from=" + this.from + "&to=" + this.to + "&value=" + this.payload + "&timestamp=0x" + Long.toHexString(this.timestamp) + "&nonce=0x" + Long.toHexString(this.nonce);
        logger.debug("need hash " + toHashString);

        byte[] hash = HashUtil.sha3(toHashString.getBytes());
        this.hash = ByteUtil.toHexString(hash);
        if (from == null || from.equals("")) {
            throw new TxException(1, "new Transaction", "from is empty,please check transaction!", null);
        } else if (to == null || to.equals("")) {
            throw new TxException(1, "new Transaction", "to is empty,please check transaction!", null);
        } else if (payload == null || payload.equals("")) {
            throw new TxException(1, "new Transaction", "payload is empty,please check transaction!", null);
        }
    }

    private static Long genTimestamp() {
        return new Date().getTime() + Utils.randInt(1000, 1000000);
    }

    private static Long genNonce() {
        return Utils.genNonce();
    }

    private static String chPrefix(String origin) {
        if (origin.startsWith("0x")) {
            return origin;
        } else {
            return "0x" + origin;
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getPayload() {
        return payload;
    }

    public String getHash() {
        return hash;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getNonce() {
        return nonce;
    }
}
