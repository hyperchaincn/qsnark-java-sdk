package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午2:05
 */
public class QueryBlockParams {
    private String token;
    private String type;
    private Object value;



    public QueryBlockParams(String token, String type, Object value) {
        this.token = token;
        this.type = type;
        this.value = value;

    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}