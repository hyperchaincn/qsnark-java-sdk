package cn.qsnark.sdk.rpc.base;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 下午4:40
 */
public enum BaseConfig {
    Content_Type("application/json"),
    Accept("application/json"),
//    URL("http://127.0.0.1:10000"),
    URL("https://api.hyperchain.cn"),
    TOKENURL("127.0.0.1:14000");
    private String type;

    private BaseConfig(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }


}
