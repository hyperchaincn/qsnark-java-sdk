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
    private String username;
    private String password;
    private String grant_type;
    private String scope;

    public String getApp_key() {
        return app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getScope() {
        return scope;
    }

    public GetTokenParams(String app_key, String app_secret, String username, String password, String grant_type, String scope) {
        this.app_key = app_key;
        this.app_secret = app_secret;
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
        this.scope = scope;
    }

    @Override
    public String serlize() {
        String str =
                "grant_type=" + this.grant_type +
                "&scope=" + this.scope +
                "&username=" + this.username +
                "&password=" + this.password +
                "&client_id=" + this.app_key +
                "&client_secret=" + this.app_secret;
        return str;
    }
}
