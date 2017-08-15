package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 上午10:25
 */
public class GetTxReceiptParams implements Params {

    private String token;
    private String txhash;

    public GetTxReceiptParams(String token, String txhash) {
        this.token = token;
        this.txhash = txhash;
    }

    public String getToken() {
        return token;
    }

    public String getTxhash() {
        return txhash;
    }

    @Override
    public String serlize() {
        String str = "{" +
                "\"token\":\"" + this.token + "\"," +
                "\"txhash\":\"" + this.txhash + "\"" + "}";
        return str;
    }
}
