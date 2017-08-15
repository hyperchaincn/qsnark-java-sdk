package cn.qsnark.sdk.rpc.types;

/**
 * Created by xieyijun on 16-12-30.
 */
public class AccountContent {
    private  String accountJson;
    private  String encodePub;

    public AccountContent(String accountJson,String encodePub){
        this.accountJson = accountJson;
        this.encodePub = encodePub;
    }

    public String getAccountJson() {
        return accountJson;
    }

    public String getEncodePub() {
        return encodePub;
    }
}
