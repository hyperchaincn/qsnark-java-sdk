package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.returns.CompileReturn;
import cn.qsnark.sdk.rpc.returns.CreteAccountReturn;
import cn.qsnark.sdk.rpc.returns.GetTokenReturn;
import cn.qsnark.sdk.rpc.returns.GetTxReciptReturn;

/**
 * Created by Hyperchain on 2017/9/12.
 */
public class MaintainTest {
    public static void main(String[] args) throws Exception {
        QsnarkAPI api = new QsnarkAPI();
        GetTokenReturn getTokenReturn = api.getAccess_Token("123", "123", "17706421110", "123");
        String token = getTokenReturn.getAccess_token();
        String sourcecode = "contract Accumulator{    uint32 sum = 0;   function increment()returns(uint32){        return sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }";

        CompileReturn compileReturn = api.compileContract(token, sourcecode);
        String bin = compileReturn.getCts_bin();
        CreteAccountReturn creteAccountReturn = api.createAccount(token);
        String from = creteAccountReturn.getAddress();
        GetTxReciptReturn txReciptReturn = api.deploysyncContract(token,bin,from);
        String to = txReciptReturn.getContract_address();
        api.maintainContract(token,from,1,bin,to);
    }
}
