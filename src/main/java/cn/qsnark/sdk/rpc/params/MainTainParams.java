package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 上午9:53
 */
public class MainTainParams implements Params {

    private String token;
    private String accountToken;
    private String from;
    private int opration;
    private String paivatekey;
    private String payload;


    private String to;


    public MainTainParams(String token, String accountToken, String from, int opration, String paivatekey, String payload, String to) {
        this.token = token;
        this.accountToken = accountToken;
        this.from = from;
        this.opration = opration;
        this.paivatekey = paivatekey;
        this.payload = payload;
        this.to = to;
    }

    public String getToken() {
        return token;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public String getFrom() {
        return from;
    }

    public int getOpration() {
        return opration;
    }

    public String getPaivatekey() {
        return paivatekey;
    }

    public String getPayload() {
        return payload;
    }

    public String getTo() {
        return to;
    }

    //构建json格式字符串
    @Override
    public String serlize() {
        String str = "{" +
                "\"AccountToken\":\"" + this.accountToken + "\"," +
                "\"from\":\"" + this.from + "\"," +
                "\"operation\":" + this.accountToken + "," +
                "\"paivatekey\":\"" + this.paivatekey + "\"," +
                "\"payload\":\"" + this.payload + "\"," +
                "\"to\":\"" + this.to + "\"" + "}";
        return str;
    }

}
