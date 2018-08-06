package cn.qsnark.sdk.rpc.function;

/**
 * description:
 * <p>
 * date 10/27/16
 *
 * @author chenquan
 * @version 1.0
 */
public class FuncParamReal {
    private String _type;
    private Object _param;

    /**
     * 真值合约参数构造方法,该方法使用的时候要格外注意,需要用参数类型对应的java类型
     * 通常而言:
     * <pre>
     *  uint* &lt;==&gt; BigInteger
     *  bytes &lt;==&gt; byte[]
     * </pre>
     * 除非你知道类型匹配方式,否则请不要直接使用该参数构造形式,请使用 {@link FuncParam}
     *
     * @param type  参数类型
     * @param param 参数值
     */
    public FuncParamReal(String type, Object param) {
        this._type = type;
        this._param = param;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public Object get_param() {
        return _param;
    }

    public void set_param(Object _param) {
        this._param = _param;
    }
}
