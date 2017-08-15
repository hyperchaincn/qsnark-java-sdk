package cn.qsnark.sdk.rpc;

import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionEncode;
import cn.qsnark.sdk.rpc.returns.*;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 下午7:16
 */
public class QsnarkAPITest {
    QsnarkAPI api;

    @Before
    public void init() throws Exception {
        api = new QsnarkAPI();
    }

    @Test
    public void getAccess_Token() throws Exception {

        GetTokenReturn getTokenReturn = api.getAccess_Token("123", "123", "yeyc", "hello");
        System.out.println(getTokenReturn.getError());
    }

    @Test
    public void refAccess_Token() throws Exception {

        RetokenReturn retokenReturn = api.refAccess_Token("123", "123",  "7SRASIG9WL-TWUIVKEAU1A");
        System.out.println(retokenReturn.getAccess_token());
        System.out.println(retokenReturn.getToken_type());
        System.out.println(retokenReturn.getMessage());
        System.out.println(retokenReturn.getCode());
    }

    @Test
    public void createAccount() throws Exception {

        api.createAccount(6, 2, "Bearer XUSICYOHONK_AJA1LMQQLQ");
    }

//    @Test
//    public void changeAccount() throws Exception {
//
//        api.ChangeAccountPwd("Bearer YPNLR5IIOWMP-TKIJQJVIA", "1", "1", "1", "1");
//    }


    @Test
    public void queryContractList() throws Exception {

        QueryContReturn singleValueReturn = api.QueryContractList("Bearer F-Q1MMX4OWKHGERYDSEBBQ", "1", "1", "1", "1");
    }

    @Test
    public void compileContract() throws Exception {

        String s = "contract Accumulator{    uint32 sum = 0;   function increment(){         sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }";
        System.out.println(s);
        System.out.println(s);
        CompileReturn compileReturn = api.compileContract("Bearer _G2BS9FTNUE2SC5SO-NB3Q", s);
    }

//    @Test
//    public void jsonCompileContract() throws Exception {
//
//        String s = "[" + "\n" +
//                "{" + "\"CTCode\": \"contract Accumulator{    uint32 sum = 0;   function increment(){         sum = sum + 1;   " +
//                "  }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }\"" + "}" +
//                "\n" + "]";
//        String jsonString = "{" + "\n" + "\"token\":\"Bearer YPNLR5IIOWMP-TKIJQJVIA\"," + "\n" + "\"sourceCode\":" + s + "" + "\n" + "}";
//        System.out.println(jsonString);
//        api.compileContract(jsonString);
//
//    }


    @Test
    public void getTxReceipt() throws Exception {

        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt("Bearer XUSICYOHONK_AJA1LMQQLQ", "0x42d3b2e3dd3f63b933c178e8ff9d6084a3440a79f592ac4bdbe0d1503d045248");
        System.out.println(getTxReciptReturn.getContract_address());
    }

    @Test
    public void getJsonTxReceipt() throws Exception {

        String s = "0x42d3b2e3dd3f63b933c178e8ff9d6084a3440a79f592ac4bdbe0d1503d045248";
        String jsonString = "{" + "\n" + "\"token\":\"Bearer YPNLR5IIOWMP-TKIJQJVIA\"," + "\n" + "\"txhash\":\"" + s + "\"" + "\n" + "}";
        System.out.println(jsonString);
        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt(jsonString);
        System.out.println(getTxReciptReturn.getContract_address());
    }




    //    @Test
//    public void signature() throws Exception {
//
//        api.signature("1", "1", "1", "1");
//    }
    @Test
    public void signature() throws Exception {
//        String from = "0xeb28073ec2581727731805baab2fcbd13ea83b3f";
//        String to = "0xeb28073ec2581727731805baab2fcbd13ea83b3f";
//        String payload = "0x60606040526000805463ffffffff1916815560ae908190" +
//                "601e90396000f3606060405260e060020a60003504633ad14af38114" +
//                "6030578063569c5f6d14605e578063d09de08a146084575b6002565b" +
//                "346002576000805460e060020a60243560043563ffffffff84160101" +
//                "81020463ffffffff199091161790555b005b3460025760005463ffffffff16" +
//                "6040805163ffffffff9092168252519081900360200190f35b3460025" +
//                "7605c6000805460e060020a63ffffffff821660010181020463ffffffff1990911617905556";
//        System.out.println(payload);
//        String signature = api.signature(from, to, payload);
        String signature = api.signature("00a05301bf18402910e86e269dc6a3499fa9a13aa4b1375258b80d969c29e7fa7d535a9918c0ba13a1007eb2379dfc29c8df028473703cb3f2ec2646c8b74d2edb00");
        System.out.println(signature);
    }

    @Test
    public void jsonSignature() throws Exception {
        String from = "0xeb28073ec2581727731805baab2fcbd13ea83b3f";
        String to = "0xeb28073ec2581727731805baab2fcbd13ea83b3f";
        String payload = "0x60606040526000805463ffffffff1916815560ae908190" +
                "601e90396000f3606060405260e060020a60003504633ad14af38114" +
                "6030578063569c5f6d14605e578063d09de08a146084575b6002565b" +
                "346002576000805460e060020a60243560043563ffffffff84160101" +
                "81020463ffffffff199091161790555b005b3460025760005463ffffffff16" +
                "6040805163ffffffff9092168252519081900360200190f35b3460025" +
                "7605c6000805460e060020a63ffffffff821660010181020463ffffffff1990911617905556";
//        System.out.println(payload);
//        String signature = api.signature(from, to, payload);
        String jsonSignature = "{\n" + "\"from\":\"" + from + "\",\n\"to\":\"" + to + "\",\n\"payload\":\"" + payload + "\"\n}";
        System.out.println(jsonSignature);
//        String signature = api.signature("00a05301bf18402910e86e269dc6a3499fa9a13aa4b1375258b80d969c29e7fa7d535a9918c0ba13a1007eb2379dfc29c8df028473703cb3f2ec2646c8b74d2edb00");
        String signature = api.jsonSignature(jsonSignature);
        System.out.println(signature);
    }

    @Test
    public void deleteContract() throws Exception {

        api.deleteContract("Bearer YPNLR5IIOWMP-TKIJQJVIA");
    }

    private static Logger logger = Logger.getLogger(QsnarkAPI.class);

//    @Test
//    public void invokeContract() throws Exception {
//
//        InvokeConReturn invokeConReturn = api.invokeContract("0xeb28073ec2581727731805baab2fcbd13ea83b3f"
//                , "3ad14af300000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000001",
//                "0xcf66f23e76f08c452ef21872b592d0ef8f9331d158f8bca49ac36a0bdb052f21"
//                , "0x4dffc0eecde676583ebde1c29d39a0319bc8b1c7", "", "", new InvCallback() {
//                    @Override
//                    public void onCompute(List ret) {
//                        System.out.println(ret);
//                    }
//                });
//        logger.info("successed");
//        System.out.println(invokeConReturn.getTxHash());
//    }


//    java.math.BigInteger hmtrsferamount, BigInteger hmremainamount,
//    cn.qsnark.sdk.rpc.types.SplitEcc RecsplitEcc, SplitEcc PaysplitEcc, String to


    @Test
    public void payload() throws Exception {


        String jsonContract = "{\n\"uint33\":\"1\",\n\"uint32\":\"1\"\n}";
        System.out.println(jsonContract);
        String jsonString = jsonContract.substring(1, jsonContract.length() - 1);
        String[] arr = jsonString.split(",");
        List<FuncParamReal> list = new ArrayList<FuncParamReal>();
        for (int i = 0; i < arr.length; i++) {
            String[] array = arr[i].split(":");
            String type = array[0].replace("\"", "").replace("\"", "").trim();
            String value = array[1].replace("\"", "").replace("\"", "").trim();
            System.out.println(type);
            System.out.println(value);
            FuncParamReal param = new FuncParamReal(type, new BigInteger(value));
            list.add(param);
        }
        FuncParamReal[] arrParam = list.toArray(new FuncParamReal[list.size()]);
        String func_name = "add";
        String payload = FunctionEncode.encodeFunction(func_name, arrParam);
        System.out.println(payload);
    }

    @Test
    public void address() throws IOException {

        String s = api.getAddress("0xcf66f23e76f08c452ef21872b592d0ef8f9331d158f8bca49ac36a0bdb052f21");
        System.out.println(s);
        System.out.println(s.length());

    }

    @Test
    public void createPayload() {
        String jsonContract = "{\"uint33\":\"1\",\"uint32\":\"1\"}";
        String s = api.createPayload("add", jsonContract);
        System.out.println(s);
        System.out.println(s.length());

    }

    @Test
    public void queryTransactionByHash() throws Exception {

        QueryTranReturn qreturn = api.QueryTransactionByHash("Bearer YPNLR5IIOWMP-TKIJQJVIA", "0x17e8747903ea0a929cfda41e38e70d20b4205291b3b59af2f3ee0529b8f62825");
        System.out.println(qreturn.getTimestamp());
    }

    @Test
    public void manitainContract() throws Exception {
        String jsonString = "{\n" +
                "  \"AccountToken\": \"1\",\n" +
                "  \"from\": \"0xeb28073ec2581727731805baab2fcbd13ea83b3f\",\n" +
                "  \"opration\": 1,\n" +
                "  \"paivatekey\": \"0xcf66f23e76f08c452ef21872b592d0ef8f9331d158f8bca49ac36a0bdb052f21\",\n" +
                "  \"payload\": \"0x60606040523415600b57fe5b5b605e8060196000396000f300606060405263ffffffff60e060020a600035041663f954b8e281146020575bfe5b3415602757fe5b602d602f565b005b5b5600a165627a7a723058200e839a57a0a817eea675d3a664f8d9b98b42f00c435b5ab2713e52a755275e430029\",\n" +
                "  \"to\": \"0xca1d2f87624994954e561e1ca116567319609b6a\"\n" +
                "}";
        api.maintainContract("Bearer YPNLR5IIOWMP-TKIJQJVIA", jsonString);
    }

    @Test
    public void countTransaction() throws Exception {

        api.countTransaction("Bearer YPNLR5IIOWMP-TKIJQJVIA");
    }

    @Test
    public void statusContract() throws Exception {

        api.statusContract("Bearer YPNLR5IIOWMP-TKIJQJVIA", "1", "1", "0xbd2d36e631fbe711728ef23dff9c6c6e0928d382");
    }

    @Test
    public void discardTransaction() throws Exception {

        DiscardConReturn discardConReturn = api.discardTransaction("Bearer YPNLR5IIOWMP-TKIJQJVIA", "20161111", "20170606");
        System.out.println(discardConReturn.getTransaction());
    }

    @Test
    public void nodesChain() throws Exception {

        NodesConReturn nodesConReturn = api.nodesChain("Bearer YPNLR5IIOWMP-TKIJQJVIA");
        System.out.println(nodesConReturn.getNodes());
    }

    @Test
    public void queryBlock() throws Exception {

        QueryBlockReturn queryBlockReturn = api.queryBlock("Bearer YPNLR5IIOWMP-TKIJQJVIA", "number", 11);

    }

    @Test
    public void queryBlocks() throws Exception {

        QueryBlocksReturn queryBlocksReturn = api.queryBlocks("Bearer YPNLR5IIOWMP-TKIJQJVIA", 1, 11);

    }

    @Test
    public void signTransaction() throws Exception {
        SignTransactionReturn queryBlocksReturn = api.signTransaction("Bearer YPNLR5IIOWMP-TKIJQJVIA", "1", "{}");

    }


}

