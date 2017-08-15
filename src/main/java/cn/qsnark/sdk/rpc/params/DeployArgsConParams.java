package cn.qsnark.sdk.rpc.params;

import net.sf.json.JSONArray;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午2:05
 */
public class DeployArgsConParams implements Params {
    private String token;
    private String abiStr;
    private String accountToken;
    private String args;
    private String bin;
    private String from;
    private int id;
    private String _private;


    public String getToken() {
        return token;
    }

    public String getAbiStr() {
        return abiStr;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public String getArgs() {
        return args;
    }

    public String getBin() {
        return bin;
    }

    public String getFrom() {
        return from;
    }

    public int getId() {
        return id;
    }

    public String get_private() {
        return _private;
    }

    JSONArray jsonArray;

    public DeployArgsConParams(String token, String abiStr, String accountToken, String args, String bin, String from, int id, String _private) {
        this.token = token;
        this.abiStr = abiStr;
        this.accountToken = accountToken;
        this.args = args;
        this.bin = bin;
        this.from = from;
        this.id = id;
        this._private = _private;
    }

    @Override
    public String serlize() {
        String str = "{" +"\n"+
                "\"Abistr\":\"" + this.abiStr + "\"," +"\n"+
                "\"AccountToken\":\"" + this.accountToken + "\"," +"\n"+
                "\"Args\":" + this.args + "," +"\n"+
                "\"Bin\":\"" + this.bin + "\"," +"\n"+
                "\"From\":\"" + this.from + "\"," +"\n"+
                "\"Id\":" + this.id + "," +"\n"+
                "\"Private\":\"" + this._private + "\"" + "\n"+"}";
        return str;

    }
}
