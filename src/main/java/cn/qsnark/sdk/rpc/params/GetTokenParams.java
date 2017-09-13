package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 上午11:14
 */
public class GetTokenParams implements Params {

    private String app_key;
    private String app_secret;
    private String phone;
    private String password;

    public GetTokenParams(String app_key, String app_secret, String phone, String password) {
        this.app_key = app_key;
        this.app_secret = app_secret;
        this.phone = phone;
        this.password = password;

    }

    @Override
    public String serlize() {
        String str =
                "&phone=" + this.phone +
                "&password=" + this.password +
                "&client_id=" + this.app_key +
                "&client_secret=" + this.app_secret;
        return str;
    }
}
