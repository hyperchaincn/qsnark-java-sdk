package cn.qsnark.sdk.rpc.params;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午3:36
 */
public class ChangeAccountParams implements Params {


    private String token;
    private String access_token;
    private String app_key;
    private String newPwd;
    private String oldpwd;

    public ChangeAccountParams(String token, String access_token, String app_key, String newPwd, String oldpwd) {
        this.token = token;
        this.access_token = access_token;
        this.app_key = app_key;
        this.newPwd = newPwd;
        this.oldpwd = oldpwd;
    }

    public String getToken() {
        return token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getApp_key() {
        return app_key;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public String getOldpwd() {
        return oldpwd;
    }

    @Override
    public String serlize() {
        String str = "{" +
                "\"NewPwd\":\"" + this.newPwd + "\"," +
                "\"Oldpwd\":\"" + this.oldpwd + "\"" + "}";
        return str;
    }

}
