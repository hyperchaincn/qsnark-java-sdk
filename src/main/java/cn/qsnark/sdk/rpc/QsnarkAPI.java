package cn.qsnark.sdk.rpc;

import cn.qsnark.sdk.HttpRequestManager.*;
import cn.qsnark.sdk.crypto.ECKey;
import cn.qsnark.sdk.crypto.HashUtil;
import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.JsonBean.JsonGetTxRecipt;
import cn.qsnark.sdk.rpc.JsonBean.JsonInvoke;
import cn.qsnark.sdk.rpc.callback.ComCallback;
import cn.qsnark.sdk.rpc.callback.GetDepTxReceiptThread;
import cn.qsnark.sdk.rpc.callback.GetInvTxReceiptThread;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionEncode;
import cn.qsnark.sdk.rpc.params.*;
import cn.qsnark.sdk.rpc.returns.*;
import cn.qsnark.sdk.rpc.utils.ByteUtil;
import cn.qsnark.sdk.rpc.utils.HmUtils;
import cn.qsnark.sdk.rpc.utils.Utils;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private QueryBlocksManager queryBlocksManager = new QueryBlocksManager();
    private NodesManager nodesManager = new NodesManager();
    private CompileContManager compileContManager = new CompileContManager();
    private DeleteConManager deleteConManager = new DeleteConManager();
    private DeployConManager deployConManager = new DeployConManager();
    private DeployArgsConManager deployArgsConManager = new DeployArgsConManager();
    private InvokeConManager invokeConManager = new InvokeConManager();
    private MainTainContManager mainTainContManager = new MainTainContManager();
    private QueryContManage queryContManage = new QueryContManage();
    private StatusManager statusManager = new StatusManager();
    private CountManager countManager = new CountManager();
    private QueryTranManager queryTranManager = new QueryTranManager();
    private SignatureManager signatureManager = new SignatureManager();
    private GetTxReceiptManager getTxReceiptManager = new GetTxReceiptManager();
    private DiscardManager discardManager = new DiscardManager();


    private HmUtils hmUtils = new HmUtils();

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
     * @param client_id 应用唯一标识
     * @param client_secret 应用私钥
     * @param retoken  前一次token
     * @return 标准格式返回值
     */

    public RetokenReturn refAccess_Token(String client_id, String client_secret, String retoken) throws IOException {
        String grant_type = "refresh_token";
        String scope = "all";
        RetokenParams retokenParams = new RetokenParams(grant_type, scope, client_id, client_secret, retoken);
        return new RetokenReturn(this.retokenManager.SyncRequest(retokenParams));
    }

    /**
     * 1.3 创建区块链
     *
     * @param userId        use id
     * @param appId API授权token user api access token
     * @param token      应用唯一标识 request body
     * @return 标准格式返回值
     */
    public CreteAccountReturn createAccount(long userId, long appId,  String token) throws IOException {
        CreateAccountParams createParams = new CreateAccountParams(userId, appId, token);
        return new CreteAccountReturn(this.createAccountManager.SyncRequest(createParams));
    }

    /**
     * 1.4 修改区块链帐号密码
     *
     * @param token        use id
     * @param access_token API授权token
     * @param app_key      应用唯一标识
     * @param NewPwd       用户新密码
     * @param Oldpwd       用户老密码
     * @return 标准格式返回值
     */
    public ChangeAccountReturn ChangeAccountPwd(String token, String access_token, String app_key, String NewPwd, String Oldpwd) throws IOException {
        ChangeAccountParams changeAccountParams = new ChangeAccountParams(token, access_token, app_key, NewPwd, Oldpwd);
        return new ChangeAccountReturn(this.changeAccountManager.SyncRequest(changeAccountParams));
    }

    /**
     * 1.5 查询交易信息
     *
     * @param token user api access token
     * @param hash  transaction hash
     * @return 标准格式返回值
     */
    public QueryTranReturn QueryTransactionByHash(String token, String hash) throws IOException {
        QueryTranParams queryTranParams = new QueryTranParams(token, hash);
        return new QueryTranReturn(this.queryTranManager.SyncRequest(queryTranParams));
    }

    /**
     * 1.6 获取某个应用下的合约列表
     *
     * @param access_token header string true "user api access token"
     * @param token        query string true "user id"
     * @param appkey       query string true "app key"
     * @param pindex       query string true "page index"
     * @param psize        query string true "page size"
     * @return 标准格式返回值
     */
    public QueryContReturn QueryContractList(String access_token, String token, String appkey, String pindex, String psize) throws IOException {
        QueryContParams queryContParams = new QueryContParams(access_token, token, appkey, pindex, psize);
        return new QueryContReturn(this.queryContManage.SyncRequest(queryContParams));
    }

    /**
     * 1.7.1 compileContract编译智能合约
     *
     * @param token      API授权token
     * @param sourceCode 智能合约源码
     * @return 编译结果
     */
    public CompileReturn compileContract(String token, String sourceCode) throws IOException {

        CompileContParams compileContParams = new CompileContParams(token, sourceCode);
        return new CompileReturn(this.compileContManager.SyncRequest(compileContParams));
    }

//    /**
//     * 1.7.2 编译智能合约
//     *
//     * @param jsonString json格式传递参数
//     * @return 编译结果
//     */
//    public CompileReturn compileContract(String jsonString) throws IOException {
//        JsonCompile jsonCompile = new JsonCompile(jsonString);
//        CompileContParams compileContParams = new CompileContParams(jsonCompile.getToken(), jsonCompile.getSourceCode());
//        return new CompileReturn(this.compileContManager.SyncRequest(compileContParams));
//    }


    /**
     * 1.8.1 GetTxReceipt查询交易信息
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
     * 1.8.2 GetTxReceipt查询交易信息
     *
     * @param jsonString jsonString类型参数
     * @return 编译结果
     * @Description get transaction receipt
     */
    public GetTxReciptReturn getTxReceipt(String jsonString) throws IOException {
        JsonGetTxRecipt jsonGetTxRecipt = new JsonGetTxRecipt(jsonString);
        GetTxReceiptParams getTxReceiptParams = new GetTxReceiptParams(jsonGetTxRecipt.getToken(), jsonGetTxRecipt.getTxhash());
        return new GetTxReciptReturn(this.getTxReceiptManager.SyncRequest(getTxReceiptParams));
    }

    /**
     * 1.9.2 DeployContract
     *
     * @param token         header string true "user api access token"
     * @param bin           合约调用者地址
     * @param from          合约调用者地址
     * @return 编译结果
     * @Description compile contract 立即返回交易hash，SDK轮询获取合约地址
     */
    public DeployConReturn deployContract(String token, String bin, String from, ComCallback callback) throws IOException, InterruptedException {

        DeployConParams deployConParams = new DeployConParams(token, bin, from);
        DeployConReturn deployConReturn = new DeployConReturn(this.deployConManager.SyncRequest(deployConParams));

        GetDepTxReceiptThread getDepTxReceiptThread = new GetDepTxReceiptThread(token, deployConReturn, callback);

        Thread thread = new Thread(getDepTxReceiptThread);
        thread.start();

        return deployConReturn;
    }

    /**
     * 1.9.2 DeployContract
     *
     * @param jsonString
     * @return 编译结果
     * @Description compile contract 立即返回交易hash，SDK轮询获取合约地址
     */
//    public DeployConReturn deployContract(String jsonString, ComCallback callback) throws IOException, InterruptedException {
//        JsonDeploy jsonDeploy = new JsonDeploy(jsonString);
//        DeployConParams deployConParams = new DeployConParams(jsonDeploy.getToken(), jsonDeploy.getAccount_token(), jsonDeploy.getBin(),
//                jsonDeploy.getFrom(), jsonDeploy.getId(), jsonDeploy.get_private());
//        DeployConReturn deployConReturn = new DeployConReturn(this.deployConManager.SyncRequest(deployConParams));
//
//        GetDepTxReceiptThread getDepTxReceiptThread = new GetDepTxReceiptThread(jsonDeploy.getToken(), deployConReturn, callback);
//
//        Thread thread = new Thread(getDepTxReceiptThread);
//        thread.start();
//
//        return deployConReturn;
//    }


    /**
     * 1.10.1 Invoke Contract
     * 用户传入交易签名
     *
     * @param func_name 方法名
     * @param from      交易发送方的地址
     * @param payload   部署合约与调用合约的时候才有这个值，可以通过这个值追朔到合约调用的方法以及调用传入的参数
     * @param _private  区块链私钥
     * @param to        交易接收方的地址
     * @param callback  回调方法
     * @return 编译结果
     * @Description Invoke Contract
     */
    //本方法用户需要提供payload 不需要提供contract信息
    public InvokeConReturn invokeContract(String token, String account_token, String func_name, String from, String payload, String _private, String to, String abi, InvCallback callback) throws IOException, TxException, InterruptedException {


        boolean _const = true;

        InvokeConParams invokeConParams = new InvokeConParams(token, account_token, _const, from, payload, _private, to);
        InvokeConReturn invokeReturn = new InvokeConReturn(this.invokeConManager.SyncRequest(invokeConParams));
        GetInvTxReceiptThread getInvTxReceiptThread = new GetInvTxReceiptThread(func_name, token, invokeReturn, abi, callback);
        new Thread(getInvTxReceiptThread).start();

        return invokeReturn;
    }

    /**
     * 1.10.2 Invoke Contract
     * 用户传入交易签名
     *
     * @param jsonString 包含各种信息
     * @param callback   回调方法
     * @return 编译结果
     * @Description Invoke Contract
     */

    //本方法用户需要提供payload 不需要提供contract信息
    public InvokeConReturn invokeContract(String jsonString, InvCallback callback) throws IOException, TxException, InterruptedException {

        boolean _const = true;
        JsonInvoke jsonInvoke = new JsonInvoke(_const, jsonString);
        InvokeConParams invokeConParams = new InvokeConParams(jsonInvoke.getToken(), jsonInvoke.getAccount_token(), _const, jsonInvoke.getFrom(), jsonInvoke.getPayload(), jsonInvoke.get_private(), jsonInvoke.getTo());
        InvokeConReturn invokeReturn = new InvokeConReturn(this.invokeConManager.SyncRequest(invokeConParams));
        GetInvTxReceiptThread getInvTxReceiptThread = new GetInvTxReceiptThread(jsonInvoke.getFunc_name(), jsonInvoke.getToken(), invokeReturn, jsonInvoke.getAbi(), callback);
        new Thread(getInvTxReceiptThread).start();

        return invokeReturn;
    }

    /**
     * 1.10.1 Invoke Contract
     * <p>
     * 用户传入交易签名
     *
     * @param func_name    方法名
     * @param jsonContract json字符串格式的contract
     * @param from         交易发送方的地址
     * @param _private     区块链私钥
     * @param to           交易接收方的地址
     * @param callback     回调方法
     * @return 编译结果
     * @Description Invoke Contract
     */
    //本方法用户需要提供contract信息 不需要提供payload
    public InvokeConReturn invokeContractNopay(String token, String func_name, String jsonContract, String from, String _private, String to, String abi, InvCallback callback) throws IOException, TxException, InterruptedException {
        String account_token = "";
        boolean _const = true;

        String payload = createPayload(func_name, jsonContract);
        System.out.println(payload);
        InvokeConParams invokeConParams = new InvokeConParams(token, account_token, _const, from, payload, _private, to);
        InvokeConReturn invokeReturn = new InvokeConReturn(this.invokeConManager.SyncRequest(invokeConParams));

        GetInvTxReceiptThread getInvTxReceiptThread = new GetInvTxReceiptThread(func_name, token, invokeReturn, abi, callback);
        new Thread(getInvTxReceiptThread).start();

        return invokeReturn;
    }

    /**
     * 1.10.2 Invoke Contract
     * 用户传入交易签名
     *
     * @param jsonString 包含各种信息
     * @param callback   回调方法
     * @return 编译结果
     * @Description Invoke Contract
     */

    //本方法用户需要提供contract信息 不需要提供payload
    public InvokeConReturn invokeContractNopay(String jsonString, InvCallback callback) throws IOException, TxException, InterruptedException {
        String account_token = "";
        boolean _const = true;

        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String func_name = jsonObject.getString("func_name");
        String jsonContract = jsonObject.getString("jsonContract");


        String payload = createPayload(func_name, jsonContract);
        System.out.println(payload);
        JsonInvoke jsonInvoke = new JsonInvoke(account_token, _const, payload, jsonString);
        InvokeConParams invokeConParams = new InvokeConParams(jsonInvoke.getToken(), jsonInvoke.getAccount_token(), jsonInvoke.is_const(), jsonInvoke.getFrom(), jsonInvoke.getPayload(), jsonInvoke.get_private(), jsonInvoke.getTo());
        InvokeConReturn invokeReturn = new InvokeConReturn(this.invokeConManager.SyncRequest(invokeConParams));
        GetInvTxReceiptThread getInvTxReceiptThread = new GetInvTxReceiptThread(jsonInvoke.getFunc_name(), jsonInvoke.getToken(), invokeReturn, jsonInvoke.getAbi(), callback);
        new Thread(getInvTxReceiptThread).start();

        return invokeReturn;
    }

    private static Long genTimestamp() {
        return new Date().getTime() + Utils.randInt(1000, 1000000);
    }

    private static Long genNonce() {
        return Utils.genNonce();
    }

    /**
     * 1.11.1 签名
     *
     * @param from    合约调用者地址
     * @param to      合约的地址
     * @param payload 调用合约的编码
     * @return 编译结果
     * @Description signature
     */
    public String signature(String from, String to, String payload) throws Exception {
        ECKey ecKey = new ECKey();
        Long timestamp = genTimestamp();
        Long nonce = genNonce();
        String toHashString = "from=" + from + "&to=" + to + "&value=" + payload + "&timestamp=0x" + Long.toHexString(timestamp) + "&nonce=0x" + Long.toHexString(nonce);
//        String toHashString = "from=" + from +  "&value=" + payload + "&timestamp=0x" + Long.toHexString(timestamp) + "&nonce=0x" + Long.toHexString(nonce);


        byte[] hash = HashUtil.sha3(toHashString.getBytes());
        String hash2 = ByteUtil.toHexString(hash);

        if (hash2.startsWith("0x")) {
            hash2 = hash2.substring(2);
        }
        ECKey.ECDSASignature ecdsaSignature = ecKey.sign(Utils.hexStringToByteArray(hash2));
        byte[] flag = new byte[1];
        flag[0] = 0;
        String signature = ByteUtil.toHexString(flag) + ecdsaSignature.toHex();
        return signature;
    }

    /**
     * 1.11.2 签名
     *
     * @param jsonString json字符串格式数据
     * @return 编译结果
     * @Description signature
     */
    public String jsonSignature(String jsonString) throws IOException {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String from = jsonObject.getString("from");
        String to = jsonObject.getString("to");
        String payload = jsonObject.getString("payload");
        ECKey ecKey = new ECKey();
        Long timestamp = genTimestamp();
        Long nonce = genNonce();
        String toHashString = "from=" + from + "&to=" + to + "&value=" + payload + "&timestamp=0x" + Long.toHexString(timestamp) + "&nonce=0x" + Long.toHexString(nonce);


        byte[] hash = HashUtil.sha3(toHashString.getBytes());
        String hash2 = ByteUtil.toHexString(hash);

        if (hash2.startsWith("0x")) {
            hash2 = hash2.substring(2);
        }
        ECKey.ECDSASignature ecdsaSignature = ecKey.sign(Utils.hexStringToByteArray(hash2));
        byte[] flag = new byte[1];
        flag[0] = 0;
        String signature = ByteUtil.toHexString(flag) + ecdsaSignature.toHex();
        return signature;
    }

    /**
     * 1.11.3 签名
     *
     * @param signature 签名
     * @return 编译结果
     * @Description signature
     */
    public String signature(String signature) throws IOException {
        if (!signature.startsWith("0x")) {
            signature = "0x" + signature;
        }
        byte[] flag = new byte[1];
        flag[0] = 0;
        System.out.println(ByteUtil.toHexString(flag));
        System.out.println(signature.length());
        signature = ByteUtil.toHexString(flag) + signature;
        return signature;
    }

    /**
     * 1.12 删除智能合约
     *
     * @param token query string true "user id"
     * @return 编译结果
     * @Description delete Contract
     */
    public DeployConReturn deleteContract(String token) throws IOException {
        DeleteConParams deleteConParams = new DeleteConParams(token);
        return new DeployConReturn(this.deleteConManager.SyncRequest(deleteConParams));
    }

    /**
     * 1.13 createPayload
     *
     * @param func_name    方法名
     * @param jsonContract 交易
     * @return 编译结果
     * @Description createPayload Contract
     */

    public String createPayload(String func_name, String jsonContract) {
        //根据方法名还有交易信息生成payload

        String jsonString = jsonContract.substring(1, jsonContract.length() - 1);
        String[] arr = jsonString.split(",");
        List<FuncParamReal> list = new ArrayList<FuncParamReal>();
        for (int i = 0; i < arr.length; i++) {
            String[] array = arr[i].split(":");
            String type = array[0].replace("\"", "").replace("\"", "").trim();
            String value = array[1].replace("\"", "").replace("\"", "").trim();
            FuncParamReal param = new FuncParamReal(type, new BigInteger(value));
            list.add(param);
        }
        FuncParamReal[] arrParam = list.toArray(new FuncParamReal[list.size()]);
        String payload = FunctionEncode.encodeFunction(func_name, arrParam);
        return payload;

    }


    /**
     * 1.14 getPrivateKey
     * <p>
     * 私钥->公钥->地址
     *
     * @param privKey
     * @return 编译结果
     * @Description createPayload Contract
     */
    public String getAddress(String privKey) throws IOException {
        if (privKey.substring(0, 2).equals("0x")) {
            privKey = privKey.substring(2);
        }
        BigInteger priInteger = new BigInteger(privKey, 16);
        boolean compressed = false;
        byte[] publicKey = ECKey.publicKeyFromPrivate(priInteger, compressed);
        byte[] addressArray = ECKey.computeAddress(publicKey);
        BigInteger bigInteger = new BigInteger(1, addressArray);
        String address = "0x" + bigInteger.toString(16);
        return address;
    }

    /**
     * 1.15 maintain
     * <p>
     *
     * @param token      user api access token
     * @param jsonString request body
     * @return 编译结果
     * @Description Maintain Contract[合约升级] opcode: 1:升级，2:冻结，3:解冻
     */
    public MainTainReturn maintainContract(String token, String jsonString) throws IOException {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String accountToken = jsonObject.getString("AccountToken");
        String from = jsonObject.getString("from");
        int opration = 1;
        String paivatekey = jsonObject.getString("paivatekey");
        String payload = jsonObject.getString("payload");
        String to = jsonObject.getString("to");

        MainTainParams mainTainParams = new MainTainParams(token, accountToken, from, opration, paivatekey, payload, to);
        return new MainTainReturn(this.mainTainContManager.SyncRequest(mainTainParams));
    }

    /**
     * 1.16 deployArgs
     * <p>
     *
     * @param token        user api access token
     * @param abiStr       Abistr
     * @param accountToken AccountToken
     * @param args         Args
     * @param bin          Bin
     * @param from         From
     * @param id           Id
     * @param _private     Private
     * @return hash
     * @Description compile contract 立即返回交易hash，SDK轮询获取合约地址
     */
    public DeployArgsConReturn deployArgsContract(String token, String abiStr, String accountToken, String args, String bin, String from, int id, String _private) throws IOException {
        DeployArgsConParams deployArgsConParams = new DeployArgsConParams(token, abiStr, accountToken, args, bin, from, id, _private);
        return new DeployArgsConReturn(this.deployArgsConManager.SyncRequest(deployArgsConParams));
    }

    /**
     * 1.17 count
     * <p>
     *
     * @param token user api access token
     * @Description query transaction by hash 获取链上交易总数
     */
    public CountConReturn countTransaction(String token) throws IOException {
        return new CountConReturn(this.countManager.SyncRequest(token));
    }

    /**
     * 1.18 status
     * <p>
     *
     * @param token   user api access token
     * @param _token  user id
     * @param appkey  app key
     * @param address contract address
     * @Description query contract status 合约状态
     */
    public StatusConReturn statusContract(String token, String _token, String appkey, String address) throws IOException {
        StatusConParams statusConParams = new StatusConParams(token, _token, appkey, address);
        return new StatusConReturn(this.statusManager.SyncRequest(statusConParams));
    }

    /**
     * 1.19 discard
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

    /**
     * 1.20 nodes
     * <p>
     *
     * @param token user api access token
     * @Description query nodes info 查询所有节点信息
     */
    public NodesConReturn nodesChain(String token) throws IOException {
        return new NodesConReturn(this.nodesManager.SyncRequest(token));
    }

    /**
     * 1.20 blockquery
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
     * 1.21 blocksquery
     * <p>
     *
     * @param token user api access token
     * @param from  from index
     * @param to    to index 可以使int也可以是'latest'
     * @Description query block by hash 通过区块哈希查询区块
     */
    public QueryBlocksReturn queryBlocks(String token, long from, Object to) throws IOException {
        QueryBlocksParams queryBlocksParams = new QueryBlocksParams(token, from, to);
        return new QueryBlocksReturn(this.queryBlocksManager.SyncRequest(queryBlocksParams));
    }

    /**
     * 1.22 signTransaction
     * <p>
     *
     * @param token  user api access token
     * @param _token user api access token
     * @param body   query body
     * @Description query block by hash 通过区块哈希查询区块
     */
    public SignTransactionReturn signTransaction(String token, String _token, Object body) throws IOException {
        SignTransactionParams signTransactionParams = new SignTransactionParams(token, _token, body);
        return new SignTransactionReturn(this.signatureManager.SyncRequest(signTransactionParams));
    }

}
