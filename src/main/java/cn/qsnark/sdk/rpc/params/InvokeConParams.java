package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午5:46
 */

public class InvokeConParams implements Params {

    private String token;
    private String account_token;
    private boolean _const;
    private String from;
    private String payload;
    private String _private;
    private String to;

    public InvokeConParams(String token, String account_token, boolean _const, String from, String payload, String _private, String to) {
        this.token = token;
        this.account_token = account_token;
        this._const = _const;
        this.from = from;
        this.payload = payload;
        this._private = _private;
        this.to = to;
    }

    public String getToken() {
        return token;
    }

    public String getAccount_token() {
        return account_token;
    }

    public boolean is_const() {
        return _const;
    }

    public String getFrom() {
        return from;
    }

    public String getPayload() {
        return payload;
    }

    public String get_private() {
        return _private;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String serlize() {
        String str = "{" +
                "\"AccountToken\":\"" + this.account_token + "\"," +
                "\"Const\":" + this._const + "," +
                "\"From\":\"" + this.from + "\"," +
                "\"Payload\":\"" + this.payload + "\"," +
                "\"Private\":\"" + this._private + "\"," +
                "\"To\":\"" + this.to + "\"" + "}";
        System.out.println(str);
        return str;
    }
}
