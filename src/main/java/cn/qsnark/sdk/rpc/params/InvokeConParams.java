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
    private boolean _const;
    private String from;
    private String payload;
    private String to;

    public InvokeConParams(String token,  boolean _const, String from, String payload, String to) {
        this.token = token;
        this._const = _const;
        this.from = from;
        this.payload = payload;
        this.to = to;
    }

    public String getToken() {
        return token;
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

    public String getTo() {
        return to;
    }

    @Override
    public String serlize() {
        String str = "{" +
                "\"Const\":" + this._const + "," +
                "\"From\":\"" + this.from + "\"," +
                "\"Payload\":\"" + this.payload + "\"," +
                "\"To\":\"" + this.to + "\"" + "}";
        System.out.println(str);
        return str;
    }
}
