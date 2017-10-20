package cn.qsnark.sdk.rpc;

import cn.qsnark.sdk.HttpRequestManager.*;
import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.callback.*;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionEncode;
import cn.qsnark.sdk.rpc.function.FunctionParamException;
import cn.qsnark.sdk.rpc.params.*;
import cn.qsnark.sdk.rpc.returns.*;
import cn.qsnark.sdk.rpc.utils.AnalyzeRet;
import cn.qsnark.sdk.rpc.utils.Utils;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 上午10:04
 */
public class QsnarkAPI {

    private CreateAccountManager createAccountManager = new CreateAccountManager();
    private ChangeAccountManager changeAccountManager = new ChangeAccountManager();
    private RetokenManager retokenManager = new RetokenManager();
    private GetTokenManager getTokenManager = new GetTokenManager();
    private QueryBlockManager queryBlockManager = new QueryBlockManager();
    private PageBlocksManager pageBlocksManager = new PageBlocksManager();
    private RangeBlocksManager rangeBlocksManager = new RangeBlocksManager();
    private NodesManager nodesManager = new NodesManager();
    private CompileContManager compileContManager = new CompileContManager();
    private DeleteConManager deleteConManager = new DeleteConManager();
    private DeployConManager deployConManager = new DeployConManager();
    private DeploysyncConManager deploysycnConManager = new DeploysyncConManager();
    private DeployArgsConManager deployArgsConManager = new DeployArgsConManager();
    private InvokeConManager invokeConManager = new InvokeConManager();
    private InvokesyncConManager invokesyncConManager = new InvokesyncConManager();
    private MainTainContManager mainTainContManager = new MainTainContManager();
    private QueryContManage queryContManage = new QueryContManage();
    private StatusManager statusManager = new StatusManager();
    private CountManager countManager = new CountManager();
    private QueryTranManager queryTranManager = new QueryTranManager();
    private SignatureManager signatureManager = new SignatureManager();
    private GetTxReceiptManager getTxReceiptManager = new GetTxReceiptManager();
    private DiscardManager discardManager = new DiscardManager();


    /**
     * 1.1 获取access_token
     * <p>
     *
     * @param client_id     httpkey
     * @param client_secret appsecret
     * @param phone         用户手机号
     * @param password      密码
     * @return GetTokenReturn对象
     */
    public GetTokenReturn getAccess_Token(String client_id, String client_secret, String phone, String password) throws IOException {
        GetTokenParams getTokenParams = new GetTokenParams(client_id, client_secret, phone, password);
        return new GetTokenReturn(this.getTokenManager.SyncRequest(getTokenParams));
    }

    /**
     * 1.2 刷新access_token
     * <p>
     *
     * @param client_id
     * @param client_secret
     * @param retoken       刷新token
     * @return RetokenReturn对象
     */
    public RetokenReturn refAccess_Token(String client_id, String client_secret, String retoken) throws IOException {
        RetokenParams retokenParams = new RetokenParams(client_id, client_secret, retoken);
        return new RetokenReturn(this.retokenManager.SyncRequest(retokenParams));
    }

    /**
     * 1.3 创建账户地址
     * <p>
     *
     * @param token 授权令牌
     * @return CreteAccountReturn对象
     */
    public CreteAccountReturn createAccount(String token) throws IOException {
        if(token == null)
            token = "";
        CreateAccountParams createParams = new CreateAccountParams(token);
        return new CreteAccountReturn(this.createAccountManager.SyncRequest(createParams));
    }

    /**
     * 1.4 通过区块哈希查询区块
     * <p>
     *
     * @param token user api access token
     * @param type  query type 可以是'number或hash'
     * @param value 'type'为number时可以是int或者'latest' 为hash是为对应的hash值
     * @return QueryBlockReturn
     */
    public QueryBlockReturn queryBlock(String token, String type, Object value) throws IOException {
        if (token == null)
            token = "";
        QueryBlockParams queryBlockParams = new QueryBlockParams(token, type, value);
        return new QueryBlockReturn(this.queryBlockManager.SyncRequest(queryBlockParams));
    }

    /**
     * 1.5 查询指定页的区块
     * <p>
     *
     * @param token 授权令牌
     * @param index 页码
     * @param size  每页区块数量
     * @return PageBlocksReturn
     */
    public PageBlocksReturn pageBlocks(String token, long index, long size) throws IOException {
        if(token == null)
            token = "";
        PageBlocksParams pageBlocksParams = new PageBlocksParams(token, index, size);
        return new PageBlocksReturn(this.pageBlocksManager.SyncRequest(pageBlocksParams));
    }

    /**
     * 1.6 查询指定区间内的区块
     * <p>
     *
     * @param token 授权令牌
     * @param from  起始区块号
     * @param to    终止区块号 可以使int也可以是'latest'
     * @return RangeBlocksReturn
     */
    public RangeBlocksReturn rangeBlocks(String token, long from, Object to) throws IOException {
        if(token == null)
            token = "";
        RangeBlocksParams rangeBlocksParams = new RangeBlocksParams(token, from, to);
        return new RangeBlocksReturn(this.rangeBlocksManager.SyncRequest(rangeBlocksParams));
    }

    /**
     * 1.7 nodes查询所有节点信息
     * <p>
     *
     * @param token 授权令牌
     * @return NodesChainReturn
     */
    public NodesChainReturn nodesChain(String token) throws IOException {
        if(token == null)
            token = "";
        return new NodesChainReturn(this.nodesManager.SyncRequest(token));
    }

    /**
     * 1.8 compileContract编译智能合约
     *
     * @param token      API授权token
     * @param sourceCode 智能合约源码
     * @return CompileReturn对象
     */
    public CompileReturn compileContract(String token, String sourceCode) throws IOException {
        if (sourceCode == null)
            sourceCode = "";

        if (token == null)
            token = "";
        sourceCode = sourceCode.replace("\"","\\\"").replace("\n", "").replace("\t","");
        System.out.println(sourceCode);
        CompileContParams compileContParams = new CompileContParams(token, sourceCode);
        return new CompileReturn(this.compileContManager.SyncRequest(compileContParams));
    }

    /**
     * 1.9 DeployContract部署合约
     *
     * @param token 授权令牌
     * @param bin   合约编码生成的bin
     * @param from  合约调用者地址
     * @return DeployConReturn对象
     * @Description 立即返回交易hash，SDK轮询获取合约地址
     */
    public DeployConReturn deployContract(String token, String bin, String from, DevCallback callback) throws IOException, InterruptedException {

        if (token == null)
            token = "";
        if(bin == null)
            bin = "";
        DeployConParams deployConParams = new DeployConParams(token, bin, from);
        DeployConReturn deployConReturn = new DeployConReturn(this.deployConManager.SyncRequest(deployConParams));

        GetDepTxReceiptThread getDepTxReceiptThread = new GetDepTxReceiptThread(token, deployConReturn, callback);

        Thread thread = new Thread(getDepTxReceiptThread);
        thread.start();

        return deployConReturn;
    }

    /**
     * 1.10 DeploysyncContract部署合约
     *
     * @param token 授权令牌
     * @param bin   合约编码生成的bin
     * @param from  合约调用者地址
     * @return GetTxReciptReturn对象
     */
    public GetTxReciptReturn deploysyncContract(String token, String bin, String from) throws IOException, InterruptedException {
        if(token == null)
            token = "";
        if(bin == null)
            bin = "";
        DeployConParams deployConParams = new DeployConParams(token, bin, from);
        GetTxReciptReturn getTxReciptReturn = new GetTxReciptReturn(this.deploysycnConManager.SyncRequest(deployConParams));

        return getTxReciptReturn;
    }

    /**
     * 1.11 deployArgsContract部署合约
     * <p>
     *
     * @param token          授权令牌
     * @param bin            合约编码生成的bin。
     * @param from           From
     * @param callback       用户自定义方法
     * @param abiStr         合约源码对应的abi数组
     * @param functionParams <FuncParamReal对象>0个或1个或多个
     * @return DeployConReturn对象
     * @Description 立即返回交易hash，SDK轮询获取合约地址
     */
    public DeployConReturn deployArgsContract(String token, String bin, String from, DevCallback callback, String abiStr, FuncParamReal... functionParams) throws IOException, FunctionParamException {
        if(token == null)
            token = "";
        if(bin == null)
            bin = "";
        String payload = createPayload(functionParams);
        System.out.println("payload" + payload);
        bin = bin + payload;
        DeployConParams deployConParams = new DeployConParams(token, bin, from);
        DeployConReturn deployConReturn = new DeployConReturn(this.deployConManager.SyncRequest(deployConParams));

        GetDepTxReceiptThread getDepTxReceiptThread = new GetDepTxReceiptThread(token, deployConReturn, callback);

        Thread thread = new Thread(getDepTxReceiptThread);
        thread.start();

        return deployConReturn;
    }

    /**
     * 1.12 InvokeContract调用合约
     * <p>
     *
     * @param token          授权令牌
     * @param _const
     * @param from           From
     * @param to             合约地址
     * @param abi            合约地址
     * @param callback       用户自定义方法
     * @param func_name      调用合约中的方法名
     * @param functionParams <FuncParamReal对象>0个或1个或多个
     * @return InvokeConReturn对象
     * @Description 立即返回交易hash，SDK轮询获取合约地址
     */
    public InvokeConReturn invokeContract(String token, boolean _const, String from, String to, String abi, InvCallback callback, String func_name, FuncParamReal... functionParams) throws IOException, TxException, InterruptedException {
        if(token == null)
            token = "";
        String payload = createPayload(func_name, functionParams);
        InvokeConParams invokeConParams = new InvokeConParams(token, _const, from, payload, to);
        InvokeConReturn invokeReturn = new InvokeConReturn(this.invokeConManager.SyncRequest(invokeConParams));

        GetInvTxReceiptThread getInvTxReceiptThread = new GetInvTxReceiptThread(func_name, token, invokeReturn, abi, callback);
        new Thread(getInvTxReceiptThread).start();

        return invokeReturn;
    }

    /**
     * 1.13 Invoke Contract调用合约
     * <p>
     *
     * @param token          授权令牌
     * @param _const
     * @param from           From
     * @param to             合约地址
     * @param abi            合约地址
     * @param func_name      调用合约中的方法名
     * @param functionParams <FuncParamReal对象>0个或1个或多个
     * @return GetTxReciptReturn对象
     * @Description 立即返回交易hash，SDK轮询获取合约地址
     */
    public GetTxReciptReturn invokesyncContract(String token, boolean _const, String from, String to, String abi, String func_name, FuncParamReal... functionParams) throws IOException, TxException, InterruptedException {
        if (token == null)
            token = "";
        String payload = createPayload(func_name, functionParams);
        InvokeConParams invokeConParams = new InvokeConParams(token, _const, from, payload, to);
        GetTxReciptReturn getTxReciptReturn = new GetTxReciptReturn(this.invokesyncConManager.SyncRequest(invokeConParams));
        String ret = getTxReciptReturn.getRet();
        System.out.println(new AnalyzeRet().getRes(ret,abi,func_name));
        return getTxReciptReturn;
    }

    /**
     * 1.14  maintain维护合约
     * <p>
     *
     * @param token    授权令牌
     * @param from     合约调用者地址
     * @param opration 1升级2冻结3解冻
     * @param payload  新的合约的bin
     * @param to       合约地址
     * @return MainTainReturn对象
     * @Description Maintain Contract[合约升级] opcode: 1:升级，2:冻结，3:解冻
     */
    public MainTainReturn maintainContract(String token, String from, int opration, String payload, String to) throws IOException {

        if (token == null)
            token = "";
        MainTainParams mainTainParams = new MainTainParams(token, from, opration, payload, to);
        return new MainTainReturn(this.mainTainContManager.SyncRequest(mainTainParams));
    }

   /* *//**
     * 1.15 获取某个应用下的合约列表
     *
     * @param token  query string true "user api access token"
     * @param pindex query string true "page index"
     * @param psize  query string true "page size"
     * @return 标准格式返回值
     *//*
    public QueryContReturn queryContract(String token, String pindex, String psize) throws IOException {
        QueryContParams queryContParams = new QueryContParams(token, pindex, psize);
        return new QueryContReturn(this.queryContManage.SyncRequest(queryContParams));
    }*/

    /**
     * 1.16 status合约状态
     *
     * @param token   登录令牌
     * @param address 合约地址
     * @return StatusConReturn对象
     * @Description query contract status 合约状态
     */
    public StatusConReturn statusContract(String token, String address) throws IOException {
        if (address == null)
            address = "";
        if (token == null) {
            token = "";
        }
        StatusConParams statusConParams = new StatusConParams(token, address);
        return new StatusConReturn(this.statusManager.SyncRequest(statusConParams));
    }

    /**
     * 1.17 count获取链上交易总数
     * <p>
     *
     * @param token 登录令牌
     * @return CountTraReturn对象
     * @Description query transaction by hash 获取链上交易总数
     */
    public CountTraReturn countTransaction(String token) throws IOException {
        if(token == null)
            token = "";
        return new CountTraReturn(this.countManager.SyncRequest(token));
    }

    /**
     * 1.18  查询交易信息
     *
     * @param token 授权的令牌
     * @param hash  交易的哈希值,32字节的十六进制字符串
     * @return QueryTranReturn对象
     */
    public QueryTranReturn queryTransaction(String token, String hash) throws IOException {
        if(token == null)
            token = "";
        QueryTranParams queryTranParams = new QueryTranParams(token, hash);
        return new QueryTranReturn(this.queryTranManager.SyncRequest(queryTranParams));
    }

    /**
     * 1.19 GetTxReceipt查询交易信息
     *
     * @param token  授权令牌
     * @param txhash 交易hash
     * @return GetTxReciptReturn对象
     * @Description get transaction receipt
     */
    public GetTxReciptReturn getTxReceipt(String token, String txhash) throws IOException {
        if(token == null)
            token = "";
        GetTxReceiptParams getTxReceiptParams = new GetTxReceiptParams(token, txhash);
        return new GetTxReciptReturn(this.getTxReceiptManager.SyncRequest(getTxReceiptParams));
    }

    /**
     * 1.20 discard查询时间区间内的失败交易
     * <p>
     *
     * @param token 授权令牌
     * @param start 开始时间戳
     * @param end   结束时间戳
     * @Description query discard transaction 查询时间区间内的失败交易
     */
    public DiscardConReturn discardTransaction(String token, String start, String end) throws IOException {
        if(token == null)
            token = "";
        DiscardConParams discardConParams = new DiscardConParams(token, start, end);
        return new DiscardConReturn(this.discardManager.SyncRequest(discardConParams));
    }


    private static Long genTimestamp() {
        return new Date().getTime() + Utils.randInt(1000, 1000000);
    }

    private static Long genNonce() {
        return Utils.genNonce();
    }

    private static String createPayload(String func_name, FuncParamReal... functionParams) {

        String payload = FunctionEncode.encodeFunction(func_name, functionParams);
        return payload;

    }

    private static String createPayload(FuncParamReal... functionParams) throws FunctionParamException {

        String payload = FunctionEncode.encodeFunctionConstructor(functionParams);
        return payload;

    }
}
