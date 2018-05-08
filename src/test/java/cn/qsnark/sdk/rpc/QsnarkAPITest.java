package cn.qsnark.sdk.rpc;

import cn.qsnark.sdk.rpc.returns.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 下午7:16
 */
public class QsnarkAPITest {

    // auth info
//    private static final String auth_phone = "13056961943";
//    private static final String auth_password = "123456";
//    private static final String auth_client_id = "4909d978-fb21-45e2-974d-c7b6a9c17067";
//    private static final String auth_client_secret = "868v4oq14w1DvGt6Bft19rQ3091t2589";
    static QsnarkAPI api = new QsnarkAPI();
    static String access_token = "";


    private static final String auth_phone = "17706421110";
    private static final String auth_password = "linxin";
    private static final String auth_client_id = "be7b6f33-a9bd-46b3-82dd-e32dcf5b79c1";
    private static final String auth_client_secret = "9C6Y9uVO78o08s00VG49PkdE10182UKb";
    private static final String token = "AQ1_OVXWN0CMCXFNKBL3LA";

    @Test
    public void getAccess_Token() throws Exception {

        GetTokenReturn getTokenReturn = api.getAccess_Token(auth_client_id,auth_client_secret,auth_phone,auth_password);
        access_token =getTokenReturn.getAccess_token();
        System.out.println(getTokenReturn.getCode());
        System.out.println(getTokenReturn.getMessage());
        System.out.println(getTokenReturn.getError());
        System.out.println(getTokenReturn.getScope());
        System.out.println(getTokenReturn.getAccess_token());
        System.out.println(getTokenReturn.getToken_type());
        System.out.println(getTokenReturn.getExpires_in());
        System.out.println(getTokenReturn.getRefresh_token());

    }

    @Test
    public void refAccess_Token() throws Exception {

        RetokenReturn retokenReturn = api.refAccess_Token("dd7314bb-e48f-43bd-a0cc-11ebcb977d49", "1108M45t16X2F399706f9p12cv10Pq3H", "ULVR7E4MWF6ILHPNB2BU3A");
        System.out.println(retokenReturn.getCode());
        System.out.println(retokenReturn.getMessage());
        System.out.println(retokenReturn.getError());
        System.out.println(retokenReturn.getScope());
        System.out.println(retokenReturn.getToken_type());
        System.out.println(retokenReturn.getExpires_in());
        System.out.println(retokenReturn.getAccess_token());
        System.out.println(retokenReturn.getRefresh_token());

    }

    @Test
    public void createAccount() throws Exception {

        CreteAccountReturn creteAccountReturn = api.createAccount("UUD6PVZCP5YMXDUJAP1N2Q");
        System.out.println(creteAccountReturn.getCode());
        System.out.println(creteAccountReturn.getStatus());
        System.out.println(creteAccountReturn.getId());
        System.out.println(creteAccountReturn.getAddress());
        System.out.println(creteAccountReturn.getTime());
        System.out.println(creteAccountReturn.isDisabled());

    }

    @Test
    public void queryBlock() throws Exception {

        QueryBlockReturn queryBlockReturn = api.queryBlock("BAJU240_PZM3XHEHRLNUUW", "number", 1);
        System.out.println(queryBlockReturn.getCode());
        System.out.println(queryBlockReturn.getStatus());
        System.out.println(queryBlockReturn.getAvgTime());
        System.out.println(queryBlockReturn.getStatus());
        System.out.println(queryBlockReturn.getBlock());
        System.out.println(queryBlockReturn.getWriteTime());
        System.out.println(queryBlockReturn.getTransactions());
    }
    @Test
    public void pageBlocks() throws Exception {

        PageBlocksReturn pageBlocksReturn = api.pageBlocks("IVKEWCXMOAWXG1DTYAGUZA", 1, 12);

        System.out.println(pageBlocksReturn.getCode());
        System.out.println(pageBlocksReturn.getStatus());
        System.out.println(pageBlocksReturn.getList());
        System.out.println(pageBlocksReturn.getCount());



    }
    @Test
    public void rangeBlocks() throws Exception {

        RangeBlocksReturn queryBlocksReturn = api.rangeBlocks("HOFOKHIJNQEQOPI1CE7VHG", 1, 2);
        System.out.println(queryBlocksReturn.getCode());
        System.out.println(queryBlocksReturn.getStatus());
        System.out.println(queryBlocksReturn.getBlocks());
    }

   /* @Test
    public void nodesChain() throws Exception {

        NodesChainReturn nodesConReturn = api.nodesChain("HOFOKHIJNQEQOPI1CE7VHG");
        System.out.println(nodesConReturn.getCode());
        System.out.println(nodesConReturn.getStatus());
        System.out.println(nodesConReturn.getNodes());
    }*/

    @Test
    public void compileContract() throws Exception {

        String s = "contract Receipt {\n" +
                "\n" +
                "    uint[] ids;\n" +
                "    string[] receipts;\n" +
                "    mapping(uint => string) receiptsIdMap;\n" +
                "\n" +
                "    function addReceipt(string receipt) public returns(uint) {\n" +
                "        return receipts.push(receipt);\n" +
                "    }\n" +
                "\n" +
                "    function addReceiptById(uint id, string receipt) public returns(uint) {\n" +
                "        receiptsIdMap[id] = receipt;\n" +
                "        ids.push(id);\n" +
                "        return receipts.push(receipt);\n" +
                "    }\n" +
                "\n" +
                "    function deleteReceiptByIndex(uint index) public {\n" +
                "        uint len = receipts.length;\n" +
                "        uint id = ids[index];\n" +
                "        if (index >= len) return;\n" +
                "        for (uint i = index; i < len - 1; i++) {\n" +
                "            receipts[i] = receipts[i + 1];\n" +
                "            ids[i] = ids[i + 1];\n" +
                "        }\n" +
                "\n" +
                "        delete receiptsIdMap[id];\n" +
                "        delete receipts[len - 1];\n" +
                "        delete ids[len - 1];\n" +
                "        ids.length--;\n" +
                "        receipts.length--;\n" +
                "    }\n" +
                "\n" +
                "    function deleteReceiptById(uint id) public {\n" +
                "        uint len = receipts.length;\n" +
                "        uint index = findIdIndex(id);\n" +
                "        if (index >= len) return;\n" +
                "        for (uint i = index; i < len - 1; i++) {\n" +
                "            receipts[i] = receipts[i + 1];\n" +
                "            ids[i] = ids[i + 1];\n" +
                "        }\n" +
                "\n" +
                "        delete receiptsIdMap[id];\n" +
                "        delete receipts[len - 1];\n" +
                "        delete ids[len - 1];\n" +
                "        ids.length--;\n" +
                "        receipts.length--;\n" +
                "    }\n" +
                "\n" +
                "    function updateReceiptByIndex(uint index, string receipt) public {\n" +
                "        receiptsIdMap[ids[index]] = receipt;\n" +
                "        receipts[index] = receipt;\n" +
                "    }\n" +
                "\n" +
                "    function updateReceiptById(uint id, string receipt) public {\n" +
                "        receiptsIdMap[id] = receipt;\n" +
                "        receipts[findIdIndex(id)] = receipt;\n" +
                "    }\n" +
                "\n" +
                "    function findIdIndex(uint id) private returns(uint index) {\n" +
                "        uint len = receipts.length;\n" +
                "        for (index = 0; index < len; index++) {\n" +
                "            if (ids[index] == id) {\n" +
                "                break;\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    function getReceiptByIndex(uint index) public constant returns(string receipt) {\n" +
                "        receipt = receipts[index];\n" +
                "    }\n" +
                "\n" +
                "    function getReceiptById(uint id) public constant returns(string receipt) {\n" +
                "        receipt = receiptsIdMap[id];\n" +
                "    }\n" +
                "\n" +
                "    function getSize() public constant returns(uint size) {\n" +
                "        size = receipts.length;\n" +
                "    }\n" +
                "}";
        //        s = "contract Token {}";
//        s = "contract Digitalpoint {    enum Actor{ Aviation, Bank, Market, Petroleum, Client }        struct Aviation{        bytes32 ID;        bytes32 Name;        uint pointbalance;    }        struct Bank{        bytes32 ID;        bytes32 Name;        uint pointbalance;    }        struct Market{        bytes32 ID;        bytes32 Name;        uint pointbalance;    }        struct Petroleum{        bytes32 ID;        bytes32 Name;        uint pointbalance;    }        struct Client{        bytes32 ID;        bytes32 Name;        uint[] pointbalances;        uint unionpaybalance;    }        struct Commodity{        bytes32 ID;        bytes32 Name;        uint value;    }        mapping(bytes32 => Aviation) aviationMap;    mapping(bytes32 => Bank) bankMap;    mapping(bytes32 => Market) marketMap;    mapping(bytes32 => Petroleum) petroleumMap;    mapping(bytes32 => Client) clientMap;    mapping(bytes32 => Commodity) commodityMap;        function newAviation(bytes32 ID, bytes32 Name, uint pointbalance) returns (bool, bytes32){        Aviation aviation = aviationMap[ID];        if(aviation.ID != 0x0){            return (false,\"this ID has been occupied!\");        }        aviation.ID = ID;        aviation.Name = Name;        aviation.pointbalance = pointbalance;        return (true,\"success\");    }        function newBank(bytes32 ID, bytes32 Name, uint pointbalance) returns (bool, bytes32){        Bank bank = bankMap[ID];        if(bank.ID != 0x0){            return (false,\"this ID has been occupied!\");        }        bank.ID = ID;        bank.Name = Name;        bank.pointbalance = pointbalance;        return (true,\"success\");    }        function newMarket(bytes32 ID, bytes32 Name, uint pointbalance) returns (bool, bytes32){        Market market = marketMap[ID];        if(market.ID != 0x0){            return (false,\"this ID has been occupied!\");        }        market.ID = ID;        market.Name = Name;        market.pointbalance = pointbalance;        return (true,\"success\");    }        function newPetroleum(bytes32 ID, bytes32 Name, uint pointbalance) returns (bool, bytes32){        Petroleum petroleum = petroleumMap[ID];        if(petroleum.ID != 0x0){            return (false,\"this ID has been occupied!\");        }        petroleum.ID = ID;        petroleum.Name = Name;        petroleum.pointbalance = pointbalance;        return (true,\"success\");    }        function newCommodity(bytes32 ID, bytes32 Name, uint value) returns (bool, bytes32){        Commodity commodity = commodityMap[ID];        if(commodity.ID != 0x0){            return (false,\"this ID has been occupied!\");        }        commodity.ID = ID;        commodity.Name = Name;        commodity.value = value;        return (true,\"success\");    }        function newClient(bytes32 ID, bytes32 Name, uint[] pointbalances, uint unionpaybalance) returns (bool, bytes32){        Client client = clientMap[ID];        if(client.ID != 0x0){            return (false,\"this ID has been occupied!\");        }        client.ID = ID;        client.Name = Name;        client.pointbalances = pointbalances;        client.unionpaybalance = unionpaybalance;        return (true,\"success\");    }        function Queryclientbalance(bytes32 ID) returns(bool,bytes32,bytes32,uint[],uint){        Client client = clientMap[ID];        return (true,\"Success\",client.Name,client.pointbalances,client.unionpaybalance);    }        function exchangeMoneyToPoints(bytes32 ID,uint amount,uint n) returns(bool,bytes32){        Client client = clientMap[ID];        client.unionpaybalance -= amount;        client.pointbalances[n-1] += amount;        return (true,\"success\");    }        function exchangepoints(bytes32 ID1, uint amount1, uint n, bytes32 ID2, uint amount2, uint m) returns(bool, bytes32){        Client client1 = clientMap[ID1];        Client client2 = clientMap[ID2];        client1.pointbalances[n-1] -= amount1;        client2.pointbalances[n-1] += amount1;        client1.pointbalances[m-1] += amount2;        client2.pointbalances[m-1] -= amount2;        return (true,\"success\");    }    function pointstransaction(bytes32 ID1, uint n, bytes32 ID2) returns(bool, bytes32){        Client client1 = clientMap[ID1];        Commodity commodity = commodityMap[ID2];        client1.pointbalances[n-1] -= commodity.value;        return (true,\"Purchase succeeded\");    }}}";
        CompileReturn compileReturn = api.compileContract(token, s);
        System.out.println(compileReturn.getCode());
        System.out.println(compileReturn.getCts());
        System.out.println(compileReturn.getCts_abi());
        System.out.println(compileReturn.getCts_bin());
        System.out.println(compileReturn.getCts_id());
        System.out.println(compileReturn.getCts_status());
        System.out.println(compileReturn.isCts_ok());
        System.out.println(compileReturn.getStatus());
        System.out.println(compileReturn.getCts_name());

    }

    @Test
    public void manitainContract() throws Exception {
        MainTainReturn mainTainReturn = api.maintainContract("HOFOKHIJNQEQOPI1CE7VHG", "0x885b67411d84f6aa3b1e8e5ee6730c8123423777", 1, "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575fe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029", "0x6a713a318ac303457da2d42e52e7304f33ef310a");
        System.out.println(mainTainReturn.getCode());
        System.out.println(mainTainReturn.getStatus());
        System.out.println(mainTainReturn.getTxHash());
    }

/*    @Test
    public void queryContract() throws Exception {

        QueryContReturn queryContReturn = api.queryContract("IVKEWCXMOAWXG1DTYAGUZA", "1", "1");
        System.out.println(queryContReturn.getCode());
        System.out.println(queryContReturn.getMessage());
        System.out.println(queryContReturn.getError());
        System.out.println(queryContReturn.getStatus());

    }*/

    @Test
    public void statusContract() throws Exception {

        StatusConReturn statusConReturn = api.statusContract("HOFOKHIJNQEQOI1CE7VHG", "0x69ffb5f1a76ea6ab4dfd58cceea0bcfabdb0f98e");
        System.out.println(statusConReturn.getCode());
        System.out.println(statusConReturn.getStatus());
    }

    @Test
    public void countTransaction() throws Exception {

        CountTraReturn countTraReturn = api.countTransaction("Bearr HOFOKHIJNQEQOPI1CE7VHG");
        System.out.println(countTraReturn.getCode());
        System.out.println(countTraReturn.getStatus());
        System.out.println(countTraReturn.getCount());
        System.out.println(countTraReturn.getTimeStamp());

    }

    @Test
    public void queryTransaction() throws Exception {

        QueryTranReturn qreturn = api.queryTransaction("HOFOKHIJNQEQOPI1CE7VHG", "0x80b62b41003946f3d26b9e0ff9ba643a8d8ef38b62b3c39aa7110e13509269bb");
        System.out.println(qreturn.getCode());
        System.out.println(qreturn.getStatus());
        System.out.println(qreturn.getTransaction());
        System.out.println(qreturn.getBlockNumber());
        System.out.println(qreturn.getAmount());
        System.out.println(qreturn.getBlockHash());
        System.out.println(qreturn.getTimestamp());
        System.out.println(qreturn.getVersion());
        System.out.println(qreturn.getTxIndex());
        System.out.println(qreturn.getExecuteTime());
        System.out.println(qreturn.getHash());
        System.out.println(qreturn.getTo());
        System.out.println(qreturn.getInvalidMsg());
        System.out.println(qreturn.getTransaction());

    }


    @Test
    public void getTxReceipt() throws Exception {

        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt("HOFOKHIJNQEQOPI1CE7VHG", "0x0be169be301ff5edb5371e6dc7c91aa01e57eac98fd7e335f32acb74093cd027");
        System.out.println(getTxReciptReturn.getCode());
        System.out.println(getTxReciptReturn.getStatus());
        System.out.println(getTxReciptReturn.getContract_address());
        System.out.println(getTxReciptReturn.getPoststate());
        System.out.println(getTxReciptReturn.getTxHash());
        System.out.println(getTxReciptReturn.getRet());
    }


    @Test
    public void discardTransaction() throws Exception {

        DiscardConReturn discardConReturn = api.discardTransaction("HOFOKHIJNQEQOPI1CE7VHG", "1", "1581776001230590326");
        System.out.println(discardConReturn.getCode());
        System.out.println(discardConReturn.getStatus());
        System.out.println(discardConReturn.getTransaction());
    }


}

