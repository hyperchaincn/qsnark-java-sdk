package cn.qsnark.sdk.rpc.function;

/**
 * Created by chenquan on 11/18/16.
 */
public class FunctionParamException extends Exception {
    public FunctionParamException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
