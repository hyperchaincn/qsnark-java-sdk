package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午2:05
 */
public class StatusConParams {
    private String token;
    private String _token;
    private String appkey;
    private String address;


    public StatusConParams(String token, String _token, String appkey, String address) {
        this.token = token;
        this._token = _token;
        this.appkey = appkey;
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public String get_token() {
        return _token;
    }

    public String getAppkey() {
        return appkey;
    }

    public String getAddress() {
        return address;
    }
}