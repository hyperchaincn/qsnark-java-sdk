package cn.qsnark.sdk.rpc.params;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午2:35
 */


public class CreateAccountParams  {

    private long userId;
    private long appId;
    private String token;



    public CreateAccountParams(long userId, long appId, String token) {
        this.userId = userId;
        this.appId = appId;
        this.token = token;

    }

    public long getUserId() {
        return userId;
    }

    public long getAppId() {
        return appId;
    }

    public String getToken() {
        return token;
    }
}
