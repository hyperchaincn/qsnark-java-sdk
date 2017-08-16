package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.returns.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-29
 * Time: 下午4:01
 */
public class SDKTest {

    QsnarkAPI api;

    @Before
    public void init() throws Exception {
        api = new QsnarkAPI();
    }


    //编译
    @Test
    public void compileContract() throws Exception {

        String s = "contract SimpleBank{ struct User{ uint256 id; bytes32 name; } mapping (uint256 => User) private accounts; uint256[] userlist; function newUser(User u) internal{ userlist.push(u.id); accounts[u.id] = u; } function SimpleBank() { User memory u1 = User(1,\"name1\"); User memory u2 = User(2,\"name2\"); User memory u3 = User(3,\"name3\"); newUser(u1); newUser(u2); newUser(u3); } function getUsers() returns (uint256[]){ return userlist; } function getUserInfo(uint256 id) returns (uint256,bytes32){ if (accounts[id].id != 0){ return (accounts[id].id,accounts[id].name); }else{ return (0,0x0); } } }";

        CompileReturn compileReturn = api.compileContract("Bearer CLVJBORDO9W7H4WMUXF0XG", s);
    }

//
//    //json格式参数
//    @Test
//    public void jsonCompileContract() throws Exception {
//
//        String s = "[" + "\n" +
//                "{" + "\"CTCode\": \"contract Accumulator { function add(uint32 num1,uint32 num2) returns(uint32) { return num1+num2; } }\"" + "}" +
//                "\n" + "]";
//        System.out.println(s);
//        String jsonString = "{" + "\n" + "\"token\":\"Bearer LVAD9HP1O3CEXXHBY0LOOA\"," + "\n" + "\"sourceCode\":" + s + "" + "\n" + "}";
//        CompileReturn compileReturn = api.compileContract(jsonString);
//
//    }



    //获取回执 查询指定交易回执信息

    @Test
    public void getTxReceipt() throws Exception {

        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt("Bearer YPNLR5IIOWMP-TKIJQJVIA", "0x4d57b6db62be45568421b69fc7fe376946069ac1caa20b53712aae5f39dbab55");
        System.out.println(getTxReciptReturn.getContract_address());
    }


    //传递json字符串参数获取回执 查询指定交易回执信息
    @Test
    public void getJsonTxReceipt() throws Exception {

        String s = "0xa1f24dd2218118a5b72c9f4938e4f9447555af1844b69d23bfd1a020cb1467f5";
        String jsonString = "{" + "\n" + "\"token\":\"Bearer YPNLR5IIOWMP-TKIJQJVIA\"," + "\n" + "\"txhash\":\"" + s + "\"" + "\n" + "}";
        System.out.println(jsonString);
        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt(jsonString);
        System.out.println(getTxReciptReturn.getContract_address());
    }


    //私钥 地址
    @Test
    public void address() throws IOException {

        String s = api.getAddress("0xcf66f23e76f08c452ef21872b592d0ef8f9331d158f8bca49ac36a0bdb052f21");
        System.out.println(s);
        System.out.println(s.length());

    }


    //创建payload
    @Test
    public void createPayload() {
        String jsonContract = "{\"uint33\":\"1\",\"uint32\":\"1\"}";
        String s = api.createPayload("add", jsonContract);
        System.out.println(s);
        System.out.println(s.length());

    }


    //直接传递signature字符串
    @Test
    public void signature() throws Exception {
        String s = "00a05301bf18402910e86e269dc6a3499fa9a13aa4b1375258b80d969c29e7fa7d535a9918c0ba13a1007eb2379dfc29c8df028473703cb3f2ec2646c8b74d2edb00";
        System.out.println(s.length());
        String signature = api.signature("00a05301bf18402910e86e269dc6a3499fa9a13aa4b1375258b80d969c29e7fa7d535a9918c0ba13a1007eb2379dfc29c8df028473703cb3f2ec2646c8b74d2edb00");
        System.out.println(signature);
        System.out.println(signature.length());
    }



    //按顺序给出signature对应的参数列表
    @Test
    public void signature2() throws Exception {
        String from = "0xeb28073ec2581727731805baab2fcbd13ea83b3f";
        String to = "0xeb28073ec2581727731805baab2fcbd13ea83b3f";
        String payload = "0x60606040526000805463ffffffff1916815560ae908190" +
                "601e90396000f3606060405260e060020a60003504633ad14af38114" +
                "6030578063569c5f6d14605e578063d09de08a146084575b6002565b" +
                "346002576000805460e060020a60243560043563ffffffff84160101" +
                "81020463ffffffff199091161790555b005b3460025760005463ffffffff16" +
                "6040805163ffffffff9092168252519081900360200190f35b3460025" +
                "7605c6000805460e060020a63ffffffff821660010181020463ffffffff1990911617905556";
        String signature = api.signature(from, to, payload);
        System.out.println(signature);
        System.out.println(signature.length());
    }




    //json格式参数获取signature
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
        String jsonSignature = "{\n" + "\"from\":\"" + from + "\",\n\"to\":\"" + to + "\",\n\"payload\":\"" + payload + "\"\n}";
        System.out.println(jsonSignature);
        String signature = api.jsonSignature(jsonSignature);
        System.out.println(signature);
    }

    @Test
    public void queryTransactionByHash() throws Exception {

        QueryTranReturn qreturn = api.QueryTransactionByHash("Bearer YPNLR5IIOWMP-TKIJQJVIA", "0x17e8747903ea0a929cfda41e38e70d20b4205291b3b59af2f3ee0529b8f62825");
        System.out.println(qreturn.getTimestamp());
    }

    @Test
    public void manitainContract() throws Exception {
        String jsonString = "{\n" +
                "  \"AccountToken\": \"1223\",\n" +
                "  \"from\": \"0xeb28073ec2581727731805baab2fcbd13ea83b3f\",\n" +
                "  \"opration\": 1,\n" +
                "  \"paivatekey\": \"0xcf66f23e76f08c452ef21872b592d0ef8f9331d158f8bca49ac36a0bdb052f21\",\n" +
                "  \"payload\": \"0x60606040523415600b57fe5b5b605e8060196000396000f300606060405263ffffffff60e060020a600035041663f954b8e281146020575bfe5b3415602757fe5b602d602f565b005b5b5600a165627a7a723058200e839a57a0a817eea675d3a664f8d9b98b42f00c435b5ab2713e52a755275e430029\",\n" +
                "  \"to\": \"0xca1d2f87624994954e561e1ca116567319609b6a\"\n" +
                "}";
        api.maintainContract("Bearer YPNLR5IIOWMP-TKIJQJVIA",jsonString);
    }
    @Test
    public void countTransaction() throws Exception {

        api.countTransaction("Bearer YPNLR5IIOWMP-TKIJQJVIA");
    }
    @Test
    public void statusContract() throws Exception {

        api.statusContract("Bearer YPNLR5IIOWMP-TKIJQJVIA","1","1","0xbd2d36e631fbe711728ef23dff9c6c6e0928d382");
    }
    @Test
    public void discardTransaction() throws Exception {

       DiscardConReturn discardConReturn = api.discardTransaction("Bearer YPNLR5IIOWMP-TKIJQJVIA","20161111","20170606");
        System.out.println(discardConReturn.getTransaction());
    }
    @Test
    public void nodesChain() throws Exception {

        NodesConReturn nodesConReturn = api.nodesChain("Bearer YPNLR5IIOWMP-TKIJQJVIA");
        System.out.println(nodesConReturn.getNodes());
    }
    @Test
    public void queryBlock() throws Exception {

        QueryBlockReturn queryBlockReturn = api.queryBlock("Bearer YPNLR5IIOWMP-TKIJQJVIA","number",11);

    }
    @Test
    public void queryBlocks() throws Exception {

        QueryBlocksReturn queryBlocksReturn = api.queryBlocks("Bearer YPNLR5IIOWMP-TKIJQJVIA",1,11);

    }
    @Test
    public void signTransaction() throws Exception {
        SignTransactionReturn queryBlocksReturn = api.signTransaction("Bearer YPNLR5IIOWMP-TKIJQJVIA","1","{}");

    }
}
