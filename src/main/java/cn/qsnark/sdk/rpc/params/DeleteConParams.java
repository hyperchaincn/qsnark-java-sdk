package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午3:26
 */
public class DeleteConParams implements Params {

    private String token;

    public DeleteConParams(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String serlize() {
        return null;
    }
}
