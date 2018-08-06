package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午2:05
 */
public class SignTransactionParams {
    private String token;
    private String _token;
    private Object body;



    public SignTransactionParams(String token, String _token, Object body) {
        this.token = token;
        this._token = _token;
        this.body = body;

    }

    public String getToken() {
        return token;
    }

    public String get_token() {
        return _token;
    }

    public Object getBody() {
        return body;
    }
}