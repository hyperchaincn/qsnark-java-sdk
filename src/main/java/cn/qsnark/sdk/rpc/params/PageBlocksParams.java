package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午2:05
 */
public class PageBlocksParams {
    private String token;
    private long index;
    private long size;



    public PageBlocksParams(String token, long index, long size) {
        this.token = token;
        this.index = index;
        this.size = size;

    }

    public String getToken() {
        return token;
    }

    public long getIndex() {
        return index;
    }

    public long getSize() {
        return size;
    }
}