package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 上午9:53
 */
public class CompileContParams implements Params {

    private String token;

    private String stringParam;

    public CompileContParams(String token, String param) {
        this.token = token;
        this.stringParam = param;
    }



    public String getToken() {
        return token;
    }

    public String getStringParam() {
        return stringParam;
    }

    //构建json格式字符串
    @Override
    public String serlize() {
        return  this.stringParam ;
    }

}
