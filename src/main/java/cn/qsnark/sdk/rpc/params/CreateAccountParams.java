package cn.qsnark.sdk.rpc.params;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午2:35
 */


public class CreateAccountParams  {


    private String token;



    public CreateAccountParams(String token) {
        this.token = token;

    }



    public String getToken() {
        return token;
    }
}
