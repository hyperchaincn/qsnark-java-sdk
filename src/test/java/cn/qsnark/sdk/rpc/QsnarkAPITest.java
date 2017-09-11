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
    QsnarkAPI api;

    @Before
    public void init() throws Exception {
        api = new QsnarkAPI();
    }

    @Test
    public void getAccess_Token() throws Exception {

        GetTokenReturn getTokenReturn = api.getAccess_Token("123", "123", "17706421110", "123");
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

        RetokenReturn retokenReturn = api.refAccess_Token("123", "123", "EDMFBVVZXIY_VWCV1IUBNG");
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

        CreteAccountReturn creteAccountReturn = api.createAccount("Bearer BPJKDVCJNDOJ6EXLYTW_PQ");
        System.out.println(creteAccountReturn.getCode());
        System.out.println(creteAccountReturn.getMessage());
        System.out.println(creteAccountReturn.getError());
        System.out.println(creteAccountReturn.getStatus());
        System.out.println(creteAccountReturn.getAddress());

    }

    @Test
    public void queryBlock() throws Exception {

        QueryBlockReturn queryBlockReturn = api.queryBlock("Bearer ZWUEW9TLN3Q_EMELYQFVAA", "", 1);

        System.out.println(queryBlockReturn.getCode());
        System.out.println(queryBlockReturn.getMessage());
        System.out.println(queryBlockReturn.getError());
        System.out.println(queryBlockReturn.getStatus());
        System.out.println(queryBlockReturn.getBlock());
        System.out.println(queryBlockReturn.getWriteTime());
        System.out.println(queryBlockReturn.getTransactions());


    }
    @Test
    public void pageBlocks() throws Exception {

        PageBlocksReturn pageBlocksReturn = api.pageBlocks("Bearer IVKEWCXMOAWXG1DTYAGUZA", 1, 12);

        System.out.println(pageBlocksReturn.getCode());
        System.out.println(pageBlocksReturn.getMessage());
        System.out.println(pageBlocksReturn.getError());
        System.out.println(pageBlocksReturn.getStatus());
        System.out.println(pageBlocksReturn.getList());
        System.out.println(pageBlocksReturn.getCount());



    }
    @Test
    public void rangeBlocks() throws Exception {

        RangeBlocksReturn queryBlocksReturn = api.rangeBlocks("Bearer IVKEWCXMOAWXG1DTYAGUZA", 1, 2);

        System.out.println(queryBlocksReturn.getCode());
        System.out.println(queryBlocksReturn.getMessage());
        System.out.println(queryBlocksReturn.getError());
        System.out.println(queryBlocksReturn.getStatus());
        System.out.println(queryBlocksReturn.getBlocks());


    }

    @Test
    public void nodesChain() throws Exception {

        NodesChainReturn nodesConReturn = api.nodesChain("Bearer IVKEWCXMOAWXG1DTYAGUZA");
        System.out.println(nodesConReturn.getCode());
        System.out.println(nodesConReturn.getMessage());
        System.out.println(nodesConReturn.getError());
        System.out.println(nodesConReturn.getStatus());
        System.out.println(nodesConReturn.getNodes());

    }

    @Test
    public void compileContract() throws Exception {

        String s = "contract Accumulator    uint32 sum = 0;   function increment(){         sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }";
//        String s = "";
        s = null;
        CompileReturn compileReturn = api.compileContract("Bearer 21QO6FBLPK2NXVV_HTVYBW", s);

        System.out.println(compileReturn.getCode());
        System.out.println(compileReturn.getMessage());
        System.out.println(compileReturn.getError());
        System.out.println(compileReturn.getStatus());
        System.out.println(compileReturn.getCts());
        System.out.println(compileReturn.getCts_status());
        System.out.println(compileReturn.getCts_bin());
        System.out.println(compileReturn.getCts_abi());
        System.out.println(compileReturn.getCts_id());
    }

    @Test
    public void manitainContract() throws Exception {
        MainTainReturn mainTainReturn = api.maintainContract("Bearer IVKEWCXMOAWXG1DTYAGUZA", "0x885b67411d84f6aa3b1e8e5ee6730c8123423777", 1, "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575fe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029", "0x6a713a318ac303457da2d42e52e7304f33ef310a");
        System.out.println(mainTainReturn.getCode());
        System.out.println(mainTainReturn.getMessage());
        System.out.println(mainTainReturn.getError());
        System.out.println(mainTainReturn.getStatus());
        System.out.println(mainTainReturn.getData());
        System.out.println(mainTainReturn.getTxHash());
    }

    @Test
    public void queryContract() throws Exception {

        QueryContReturn queryContReturn = api.queryContract("Bearer IVKEWCXMOAWXG1DTYAGUZA", "1", "1");
        System.out.println(queryContReturn.getCode());
        System.out.println(queryContReturn.getMessage());
        System.out.println(queryContReturn.getError());
        System.out.println(queryContReturn.getStatus());

    }

    @Test
    public void statusContract() throws Exception {

        StatusConReturn statusConReturn = api.statusContract("Bearer IVKEWCXMOAWXG1DTYAGUZA", "0xd71e6c4910e517b8556fa3d3b7866eb2f6a7025f");
        System.out.println(statusConReturn.getCode());
        System.out.println(statusConReturn.getMessage());
        System.out.println(statusConReturn.getError());
        System.out.println(statusConReturn.getStatus());
    }

    @Test
    public void countTransaction() throws Exception {

        CountTraReturn countTraReturn = api.countTransaction("Bearer IVKEWCXMOAWXG1DTYAGUZA");
        System.out.println(countTraReturn.getCode());
        System.out.println(countTraReturn.getMessage());
        System.out.println(countTraReturn.getError());
        System.out.println(countTraReturn.getStatus());
        System.out.println(countTraReturn.getCount());
        System.out.println(countTraReturn.getTimeStamp());

    }

    @Test
    public void queryTransaction() throws Exception {

        QueryTranReturn qreturn = api.queryTransaction("Bearer UNYNNXZAPHEVYHGMBLYKZQ", "0x4a630908bf78441197c9fc94aa3ebb4f21218cf61dfe82b62184aa1bc7f1dff1");
        System.out.println(qreturn.getCode());
        System.out.println(qreturn.getMessage());
        System.out.println(qreturn.getError());
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

        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt("Bearer IVKEWCXMOAWXG1DTYAGUZA", "0xe99162f667f1cdff50e6bbbe193f885d9fb0d3456c29b02fa15d91c99aad1c36");
        System.out.println(getTxReciptReturn.getCode());
        System.out.println(getTxReciptReturn.getMessage());
        System.out.println(getTxReciptReturn.getError());
        System.out.println(getTxReciptReturn.getStatus());
        System.out.println(getTxReciptReturn.getContract_address());
        System.out.println(getTxReciptReturn.getPoststate());
        System.out.println(getTxReciptReturn.getTxHash());
        System.out.println(getTxReciptReturn.getRet());
    }


    @Test
    public void discardTransaction() throws Exception {

        DiscardConReturn discardConReturn = api.discardTransaction("Bearer B-IAOLCKN42FOT8CLUZYFQ", "1", "1581776001230590326");
        System.out.println(discardConReturn.getCode());
        System.out.println(discardConReturn.getMessage());
        System.out.println(discardConReturn.getError());
        System.out.println(discardConReturn.getStatus());
        System.out.println(discardConReturn.getTransaction());
    }


}

