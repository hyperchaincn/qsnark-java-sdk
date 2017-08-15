package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午4:04
 */
public class QueryTranParams implements Params {

    private String token;

    private String hash;

    public QueryTranParams(String token, String hash) {
        this.token = token;

        this.hash = hash;
    }

    public String getToken() {
        return token;
    }


    public String getHash() {
        return hash;
    }

    @Override
    public String serlize() {
        String str = "{" +
                "\"token\":\"" + this.token + "\"" + "}";
        return str;
    }

}
