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
    private String address;


    public StatusConParams(String token, String address) {
        this.token = token;

        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public String getAddress() {
        return address;
    }
}