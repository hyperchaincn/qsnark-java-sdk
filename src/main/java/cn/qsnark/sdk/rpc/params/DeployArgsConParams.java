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
    private String args;
    private String bin;
    private String from;


    public String getToken() {
        return token;
    }

    public String getAbiStr() {
        return abiStr;
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


    JSONArray jsonArray;

    public DeployArgsConParams(String token, String abiStr, String args, String bin, String from) {
        this.token = token;
        this.abiStr = abiStr;
        this.args = args;
        this.bin = bin;
        this.from = from;
    }

    @Override
    public String serlize() {
        String str = "{" +"\n"+
                "\"Abistr\":\"" + this.abiStr + "\"," +"\n"+
                "\"Args\":" + this.args + "," +"\n"+
                "\"Bin\":\"" + this.bin + "\"," +"\n"+
                "\"From\":\"" + this.from + "\""
                +"\n"+"}";
        return str;

    }
}
