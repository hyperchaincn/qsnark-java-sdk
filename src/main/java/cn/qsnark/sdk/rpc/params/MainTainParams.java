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
    private String from;
    private int opration;
    private String payload;
    private String to;


    public MainTainParams(String token, String from, int opration, String payload, String to) {
        this.token = token;
        this.from = from;
        this.opration = opration;
        this.payload = payload;
        this.to = to;
    }

    public String getToken() {
        return token;
    }

    public String getFrom() {
        return from;
    }

    public int getOpration() {
        return opration;
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
        String str = "{\n" +
                "\"from\":\"" + this.from + "\",\n" +
                "\"opration\":" + this.opration + ",\n" +
                "\"payload\":\"" + this.payload + "\",\n" +
                "\"to\":\"" + this.to + "\"" + "\n}";
        return str;
    }

}
