package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午2:05
 */
public class QueryBlocksParams {
    private String token;
    private long from;
    private Object to;



    public QueryBlocksParams(String token, long from, Object to) {
        this.token = token;
        this.from = from;
        this.to = to;

    }

    public String getToken() {
        return token;
    }

    public long getFrom() {
        return from;
    }

    public Object getTo() {
        return to;
    }
}