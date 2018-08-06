package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午2:05
 */
public class DeployConParams implements Params {
    private String token;
    private String bin;
    private String from;

    public String getToken() {
        return token;
    }

    public String getBin() {
        return bin;
    }

    public String getFrom() {
        return from;
    }

    public DeployConParams(String token, String bin, String from) {
        this.token = token;
        this.bin = bin;
        this.from = from;

    }

    @Override
    public String serlize() {
        String str = "{" +
                "\"Bin\":\"" + this.bin + "\"," +
                "\"From\":\"" + this.from + "\"" +
                 "}";
        return str;

    }
}
