package cn.qsnark.sdk;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangrui on 2018/3/27.
 */
public class DecodeReturn {
    private String fromAddress; //交易发起者
    private String toAddress;   //交易接收者
    private String funcName;    //交易方法名
    private Map<String, Object> paramsMap;  //<参数名，参数值> 映射
    private List<Object> returnList;        //交易返回值
    private String txHash;      //交易哈希
    private String blockHash;   //交易所在的区块的哈希

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public List<Object> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<Object> returnList) {
        this.returnList = returnList;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
}
