package cn.qsnark.sdk.rpc;

import cn.qsnark.sdk.HttpRequestManager.*;
import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.callback.*;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionEncode;
import cn.qsnark.sdk.rpc.function.FunctionParamException;
import cn.qsnark.sdk.rpc.params.*;
import cn.qsnark.sdk.rpc.returns.*;
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
     *
     * @param app_key
     * @param app_secret
     * @param username
     * @param password
     * @return 标准格式返回值
     */
    public GetTokenReturn getAccess_Token(String app_key, String app_secret, String username, String password) throws IOException {
        String grant_type = "password";
        String scope = "all";
        GetTokenParams getTokenParams = new GetTokenParams(app_key, app_secret, username, password, grant_type, scope);
        return new GetTokenReturn(this.getTokenManager.SyncRequest(getTokenParams));
    }

    /**
     * 1.2 刷新access_token
     *
     * @param client_id
     * @param client_secret
     * @param retoken
     * @return 标准格式返回值
     */
    public RetokenReturn refAccess_Token(String client_id, String client_secret, String retoken) throws IOException {
        String grant_type = "refresh_token";
        String scope = "all";
        RetokenParams retokenParams = new RetokenParams(grant_type, scope, client_id, client_secret, retoken);
        return new RetokenReturn(this.retokenManager.SyncRequest(retokenParams));
    }

    /**
     * 1.3 创建账户地址
     *
     * @param token
     * @return 标准格式返回值
     */
    public CreteAccountReturn createAccount(String token) throws IOException {
        CreateAccountParams createParams = new CreateAccountParams(token);
        return new CreteAccountReturn(this.createAccountManager.SyncRequest(createParams));
    }

    /**
     * 1.4 blockquery
     * <p>
     *
     * @param token user api access token
     * @param type  query type 可以是'number或hash'
     * @param value 'type'为number时可以是int或者'latest'
     * @Description query block by hash 通过区块哈希查询区块
     */
    public QueryBlockReturn queryBlock(String token, String type, Object value) throws IOException {
        QueryBlockParams queryBlockParams = new QueryBlockParams(token, type, value);
        return new QueryBlockReturn(this.queryBlockManager.SyncRequest(queryBlockParams));
    }

    /**
     * 1.5 blocksquery
     * <p>
     *
     * @param token user api access token
     * @param index  from index
     * @param size    to index 可以使int也可以是'latest'
     * @Description query block by hash 通过区块哈希查询区块
     */
    public PageBlocksReturn pageBlocks(String token, long index, long size) throws IOException {
        PageBlocksParams pageBlocksParams = new PageBlocksParams(token, index, size);
        return new PageBlocksReturn(this.pageBlocksManager.SyncRequest(pageBlocksParams));
    }

    /**
     * 1.6 blocksquery
     * <p>
     *
     * @param token user api access token
     * @param from  from index
     * @param to    to index 可以使int也可以是'latest'
     * @Description query block by hash 通过区块哈希查询区块
     */
    public RangeBlocksReturn rangeBlocks(String token, long from, Object to) throws IOException {
        RangeBlocksParams rangeBlocksParams = new RangeBlocksParams(token, from, to);
        return new RangeBlocksReturn(this.rangeBlocksManager.SyncRequest(rangeBlocksParams));
    }

    /**
     * 1.7 nodes
     * <p>
     *
     * @param token user api access token
     * @Description query nodes info 查询所有节点信息
     */
    public NodesChainReturn nodesChain(String token) throws IOException {
        return new NodesChainReturn(this.nodesManager.SyncRequest(token));
    }

    /**
     * 1.8 compileContract编译智能合约
     *
     * @param token      API授权token
     * @param sourceCode 智能合约源码
     * @return 编译结果
     */
    public CompileReturn compileContract(String token, String sourceCode) throws IOException {

        CompileContParams compileContParams = new CompileContParams(token, sourceCode);
        return new CompileReturn(this.compileContManager.SyncRequest(compileContParams));
    }

    /**
     * 1.9 DeployContract
     *
     * @param token header string true "user api access token"
     * @param bin   合约调用者地址
     * @param from  合约调用者地址
     * @return 编译结果
     * @Description compile contract 立即返回交易hash，SDK轮询获取合约地址
     */
    public DeployConReturn deployContract(String token, String bin, String from, DevCallback callback) throws IOException, InterruptedException {

        DeployConParams deployConParams = new DeployConParams(token, bin, from);
        DeployConReturn deployConReturn = new DeployConReturn(this.deployConManager.SyncRequest(deployConParams));

        GetDepTxReceiptThread getDepTxReceiptThread = new GetDepTxReceiptThread(token, deployConReturn, callback);

        Thread thread = new Thread(getDepTxReceiptThread);
        thread.start();

        return deployConReturn;
    }

    /**
     * 1.10 DeployContract
     *
     * @param token header string true "user api access token"
     * @param bin   合约调用者地址
     * @param from  合约调用者地址
     * @return 编译结果
     * @Description compile contract 立即返回交易hash，SDK轮询获取合约地址
     */
    public GetTxReciptReturn deploysyncContract(String token, String bin, String from) throws IOException, InterruptedException {

        DeployConParams deployConParams = new DeployConParams(token, bin, from);
        GetTxReciptReturn getTxReciptReturn = new GetTxReciptReturn(this.deploysycnConManager.SyncRequest(deployConParams));

        return getTxReciptReturn;
    }

    /**
     * 1.11 deployArgs
     * <p>
     *
     * @param token  user api access token
     * @param abiStr Abistr
     * @param bin    Bin
     * @param from   From
     * @return hash
     * @Description compile contract 立即返回交易hash，SDK轮询获取合约地址
     */
    public DeployConReturn deployArgsContract(String token, String bin, String from, DevCallback callback, String abiStr, FuncParamReal... functionParams) throws IOException, FunctionParamException {

        String payload = createPayload(functionParams);
        System.out.println("payload"+payload);
        bin = bin + payload;
        DeployConParams deployConParams = new DeployConParams(token, bin, from);
        DeployConReturn deployConReturn = new DeployConReturn(this.deployConManager.SyncRequest(deployConParams));

        GetDepTxReceiptThread getDepTxReceiptThread = new GetDepTxReceiptThread(token, deployConReturn, callback);

        Thread thread = new Thread(getDepTxReceiptThread);
        thread.start();

        return deployConReturn;
    }

    /**
     * 1.12 Invoke Contract
     * <p>
     * 用户传入交易签名
     *
     * @param func_name      方法名
     * @param functionParams
     * @param from           交易发送方的地址
     * @param to             交易接收方的地址
     * @param callback       回调方法
     * @return 编译结果
     * @Description Invoke Contract
     */
    public InvokeConReturn invokeContract(String token,boolean _const, String from, String to, String abi, InvCallback callback, String func_name, FuncParamReal... functionParams) throws IOException, TxException, InterruptedException {
        String payload = createPayload(func_name, functionParams);
        InvokeConParams invokeConParams = new InvokeConParams(token, _const, from, payload, to);
        InvokeConReturn invokeReturn = new InvokeConReturn(this.invokeConManager.SyncRequest(invokeConParams));

        GetInvTxReceiptThread getInvTxReceiptThread = new GetInvTxReceiptThread(func_name, token, invokeReturn, abi, callback);
        new Thread(getInvTxReceiptThread).start();

        return invokeReturn;
    }

    /**
     * 1.13 Invoke Contract
     * <p>
     * 用户传入交易签名
     *
     * @param func_name      方法名
     * @param functionParams
     * @param from           交易发送方的地址
     * @param to             交易接收方的地址
     * @return 编译结果
     * @Description Invoke Contract
     */
    public GetTxReciptReturn invokesyncContract(String token,boolean _const, String from, String to, String abi,String func_name, FuncParamReal... functionParams) throws IOException, TxException, InterruptedException {
        String payload = createPayload(func_name, functionParams);
        InvokeConParams invokeConParams = new InvokeConParams(token, _const, from, payload, to);
        GetTxReciptReturn getTxReciptReturn = new GetTxReciptReturn(this.invokesyncConManager.SyncRequest(invokeConParams));

        return getTxReciptReturn;
    }

    /**
     * 1.14  maintain
     * <p>
     *
     * @param token user api access token
     * @return 编译结果
     * @Description Maintain Contract[合约升级] opcode: 1:升级，2:冻结，3:解冻
     */
    public MainTainReturn maintainContract(String token, String from, int opration, String payload, String to) throws IOException {

        MainTainParams mainTainParams = new MainTainParams(token, from, opration, payload, to);
        return new MainTainReturn(this.mainTainContManager.SyncRequest(mainTainParams));
    }

    /**
     * 1.15 获取某个应用下的合约列表
     *
     * @param token  query string true "user api access token"
     * @param pindex query string true "page index"
     * @param psize  query string true "page size"
     * @return 标准格式返回值
     */
    public QueryContReturn queryContract(String token, String pindex, String psize) throws IOException {
        QueryContParams queryContParams = new QueryContParams(token, pindex, psize);
        return new QueryContReturn(this.queryContManage.SyncRequest(queryContParams));
    }

    /**
     * 1.16 status
     * <p>
     *
     * @param token   user api access token
     * @param address contract address
     * @Description query contract status 合约状态
     */
    public StatusConReturn statusContract(String token, String address) throws IOException {
        StatusConParams statusConParams = new StatusConParams(token, address);
        return new StatusConReturn(this.statusManager.SyncRequest(statusConParams));
    }

    /**
     * 1.17 count
     * <p>
     *
     * @param token user api access token
     * @Description query transaction by hash 获取链上交易总数
     */
    public CountTraReturn countTransaction(String token) throws IOException {
        return new CountTraReturn(this.countManager.SyncRequest(token));
    }

    /**
     * 1.18  查询交易信息
     *
     * @param token user api access token
     * @param hash  transaction hash
     * @return 标准格式返回值
     */
    public QueryTranReturn queryTransaction(String token, String hash) throws IOException {
        QueryTranParams queryTranParams = new QueryTranParams(token, hash);
        return new QueryTranReturn(this.queryTranManager.SyncRequest(queryTranParams));
    }

    /**
     * 1.19 GetTxReceipt查询交易信息
     *
     * @param token  header string true "user api access token"
     * @param txhash query string true "transaction hash"
     * @return 编译结果
     * @Description get transaction receipt
     */
    public GetTxReciptReturn getTxReceipt(String token, String txhash) throws IOException {
        GetTxReceiptParams getTxReceiptParams = new GetTxReceiptParams(token, txhash);
        return new GetTxReciptReturn(this.getTxReceiptManager.SyncRequest(getTxReceiptParams));
    }

    /**
     * 1.20 discard
     * <p>
     *
     * @param token user api access token
     * @param start start time
     * @param end   end time
     * @Description query discard transaction 查询时间区间内的失败交易
     */
    public DiscardConReturn discardTransaction(String token, String start, String end) throws IOException {
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
