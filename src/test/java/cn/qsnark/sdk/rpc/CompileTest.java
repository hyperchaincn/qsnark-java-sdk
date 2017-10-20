package cn.qsnark.sdk.rpc;

import java.io.IOException;

public class CompileTest {
    // auth info
    private static final String auth_phone = "13056961943";
    private static final String auth_password = "123456";
    private static final String auth_client_id = "4909d978-fb21-45e2-974d-c7b6a9c17067";
    private static final String auth_client_secret = "868v4oq14w1DvGt6Bft19rQ3091t2589";

    public static void main(String[] args) throws IOException {

        String contract = new GetContractTest().GetContract("c3");
        contract = "contract Accumulator{    uint32 sum = 0;   function increment(){         sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }";        new QsnarkAPI().compileContract("JZYNAR3MMLWBRIH5TIIHQQ",contract);
        new QsnarkAPI().compileContract("BVQTDAECOM-P7QSGFZHVSW",contract);

    }
}
