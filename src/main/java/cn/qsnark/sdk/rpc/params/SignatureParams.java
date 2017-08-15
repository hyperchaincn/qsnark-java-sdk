package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 上午10:25
 */
public class SignatureParams implements Params {

    private String token;
    private String access_token;
    private String address;
    private String private_key;

    public SignatureParams(String token, String access_token, String address, String private_key) {
        this.token = token;
        this.access_token = access_token;
        this.address = address;
        this.private_key = private_key;
    }

    public String getToken() {
        return token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getAddress() {
        return address;
    }

    public String getPrivate_key() {
        return private_key;
    }

    @Override
    public String serlize() {
        String str = "{" +
                "\"address\":\"" + this.address + "\"," +
                "\"private_key\":\"" + this.private_key + "\"" + "}";
        return str;
    }
}
