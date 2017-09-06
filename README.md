#Qsnarksdk使用说明文档

##sdk开发说明

* 接口使用参照`/doc/Qsnarksdk使用说明文档说明文档.doc`
* Qsnark SDK 是qsnark对外提供的API接口的Java封装。

##1 使用方法

简述：

* code  状态码0为成功，-1为失败
* error 成功时为空失败时为失败的类型
* message 成功时为success，失败时为相应的错误信息

每一个接口都有以上返回结果，并封装到返回的对象中以下不在赘述，
开发者可以通过code快速知道自己是否成功使用相应的方法。用户通过
创建QsnarkAPI对象来使用sdk中的接口，下面我们将给出具体案例。


-----

##1.1 getAccess_Token接口

* 通过输入client_id，client_secret，username,password获取accesstoken，即，QsnarkAPI api = new Qsnark(),
然后通过api.getAccesstoken_Token()方法获取accesstoken。
* 返回结果封装成GetTokenReturn对象，用户可以通过对象的属性来获取返回结果。在接下来的示例中需要用到以上返回的token 即token_type+空格+access_token，
* 本例中即为Bearer IEK1DO1ZPAQPBDPPDY7VMQ;refresh_token为后面刷新token使用。

-----

##1.2 refAccess_Token接口

* 通过输入client_id，client_secret,refresh_token（该值由getAccessToken接口返回）刷新token，即，QsnarkAPI api = new Qsnark(),然后通过api.refAccesstoken_Token()方法刷新token。
* 返回结果封装成ReTokenReturn对象，用户可以通过对象的属性来获取返回结果。在接下来的示例中需要用到以上返回的token 即token_type+空格+access_token，本例中即为Bearer CD843SDUOQ61RY3NEXZHLA;refresh_token为继续刷新token使用。

-----


## 1.3 createAccount接口
* 通过输入token,来创建账号，即生成客户账号的地址，通过QsnarkAPI api = new Qsnark(),然后调用createAccount()方法创建地址，该地址为后续的from使用，即调用合约的地址。
* 返回结果封装成CreteAccountReturn对象，用户可以通过对象的属性来获取返回结果。返回生成的账户地址。

-----

## 1.4 queryBlock接口

* 通过输入token，type，value，其中type和value是可选的type有可选两种类型，分别是number，hash，number对应的value为number值或者latest（最后一个），hash对应的是具体的hash值。返回QueryBlockReturn对象，该方法实现查询指定区块数的block，或者指定hash的block，即，QsnarkAPI api = new Qsnark(),然后通过
api.queryBlock()方法获取QueryBlockReturn对象。

* 返回结果封装成QueryBlockReturn对象，用户可以通过对象的属性来获取返回结果。

-----

##1.5 pageBlocks接口

* 通过输入token，index，size获取PageBlocksReturn，该方法实现查询指定页的区块，即，QsnarkAPI api = new Qsnark(),然后通过api.pageBlocks()方法获取PageBlocksReturn对象。
* 返回结果封装成QueryBlocksReturn对象，用户可以通过对象的属性来获取返回结果。

-----

##1.6 rangeBlocks接口

* 通过输入token，from，to获取RangeBlocksReturn，该方法实现查询指定区间内的区块，即，QsnarkAPI api = new Qsnark(),然后通过api.rangeBlocks()方法获取RangeBlocksReturn对象。
* 返回结果封装成RangeBlocksReturn对象，用户可以通过对象的属性来获取返回结果。

-----

##1.7 nodesChain接口

* 通过输入token获取NodesChainReturn 对象，该方法实现查询所有节点信息，即，QsnarkAPI api = new Qsnark(),然后通过api.nodesChain()方法获取NodesChainReturn 对象。
* 返回结果封装成NodesConReturn 对象，用户可以通过对象的属性来获取返回结果。

-----

##1.8 compileContract接口

* 使用时我们要QsnarkAPI api = new QsnarkAPI();创建QsnarkAPI的对象使用其中的compileContract()方法，该方法实现编译合约生成合约的bin，abi，等合约信息。
* 在sdk中我们将返回值进行封装为CompileReturn 类的对象，该对象中包含所有返回结果，通过get方法即可取得返回值，该方法生成的bin，abi，为之后的deploy，invoke，maintain方法使用

-----

## 1.9 deployContract接口

* 使用时我们要QsnarkAPI api = new QsnarkAPI();创建QsnarkAPI的对象使用其中的deployContract()方法，该方法可以将上一步compile之后的合约部署到区块链上。deployContract方法中间我们进行了一个处理，利用了使用deployContract()方法传入的token以及方法返回回来的txhash，去调用getTxReceipt()方法返回合约的信息，最后用户可以得到ContractAddress,然后通过传递进来的自定义方法去使用address进而实现自己的目的。如下例可以看到方法返回两个值。第一个是deployContract()方法的返回值第二个是getTxReceipt()方法的返回值。
* 在sdk中我们将返回值进行封装为DeployReturn类的对象，该对象中包含所有返回结果。

-----

## 1.10 deploysyncContract接口

* 使用时我们要QsnarkAPI api = new QsnarkAPI();创建QsnarkAPI的对象使用其中的deploysyncContract()方法，该方法可以将上一步compile之后的合约部署到区块链上。
该方法在deploy后直接获取回执getreceipt获得返回的对象，即GetTxReceiptReturn对象。
* 在sdk中我们将返回值进行封装为GetTxReceiptReturn类的对象，该对象中包含所有返回结果。

-----

## 1.11 deployArgsContract接口

* 使用时我们要QsnarkAPI api = new QsnarkAPI();创建QsnarkAPI的对象使用其中的deployArgsContract()方法。该方法使用compile生成的bin以及abi，以及createAccount生成的地址，还有用户自定义方法来实现其功能。
deployArgsContract方法中间我们进行了一个处理，利用了使用deployArgsContract()方法传入的token以及方法返回回来的txhash，去调用getTxReceipt()方法获取调用合约的信息，最后用户可以得到ContractAddress,然后通过传递进来的方法去使用address进而实现自己的目的。如下例可以看到方法返回两个值。第一个是deployArgContract()方法第二个是getTxReceipt()方法。
* 在sdk中我们将返回值进行封装为deployConReturn类的对象，该对象中包含所有返回结果。

-----

##1.12 invokeContract接口

* 使用时我们要QsnarkAPI api = new QsnarkAPI();创建QsnarkAPI的对象使用其中的invokeContract()方法。该方法实现调用合约中的相应方法，要传入对应的方法名以及参数，invokeContract方法中间我们进行了一个处理，利用了使用invokeContract()方法传入的token以及方法返回回来的txhash，去调用getTxReceipt()方法最终返回结果。该方法传依次传入了token,from,to,abi,用户自定义方法,func_name,param1,....。Invoke Contract 直接接受payload参数，payload打包工作交给SDK,立即返回交易hash，在SDK轮询获取调用结果。
方法名后面的由参数与值构成的对象可以有0个到多个即FuncParamReal param1 = new FuncParamReal("uint32", 1)可以有0个到多个，下面的例子中我们给了两个包装的对象;
该方法返回InvokeConReturn对象，对象中封装了所有返回信息。
*

-----

##1.13 invokesyncContract接口

* 使用时我们要QsnarkAPI api = new QsnarkAPI();创建QsnarkAPI的对象使用其中的invokesyncContract()方法。该方法实现调用合约中的相应方法，要传入对应的方法名以及参数，方法名后面的由参数与值构成的对象可以有0个到多个即FuncParamReal param1 = new FuncParamReal("uint32", 1)可以有0个到多个，下面的例子中我们给了两个包装的对象;该方法后面直接利用invoke的hash以及token获取回执getReceipt。返回GetTxReceipt对象，封装了回执信息。
*

-----

##1.14.1调用api中maintainContract()方法

* 此方法主要是对已经部署的合约进行维护，通过输入token，from，opration,payload,to（合约地址）获取MainTainReturn对象，Maintain Contract[合约升级] opcode: 1:升级，2:冻结，3:解冻即，QsnarkAPI api = new Qsnark(),然后通过api.maintainContract()方法获取MainTainReturn对象。该方法要用到compile方法使用源码改动后生成的bin作为payload，to是合约地址，from为账号地址。
* 返回结果封装成MainTainReturn对象，用户可以通过对象的属性来获取返回结果。

-----

##1.15 queryContract接口(未实现待完成)

-----

##1.16 statusContract接口

* 通过输入token，address获取StatusConReturn对象，该方法查询合约状态，即，QsnarkAPI api = new Qsnark(),然后通过api.statusContract()方法获取StatusConReturn对象.
* 返回结果封装成StatusConReturn对象，用户可以通过对象的属性来获取返回结果。

-----

##1.17 countTransaction接口

* 通过输入token获取CountTraReturn对象，该方法获取链上的交易总数，即，QsnarkAPI api = new Qsnark(),然后通过api.countTrasaction()方法获取CountTraReturn对象。
* 返回结果封装成CountTraReturn对象，用户可以通过对象的属性来获取返回结果。

------

##1.18 queryTransaction接口

* 通过输入token,hash获取交易信息，该方法通过hash查询交易，即，QsnarkAPI api = new Qsnark(),然后通过api.queryTransaction()方法获取hash对应的交易。
* 返回QueryTranReturn对象，用户可通过对象属性获取返回值。

-----

##1.9 getTxReceipt接口

* 根据交易hash获取交易回执:使用时我们要QsnarkAPI api = new QsnarkAPI();创建QsnarkAPI的对象使用其中的getTxReceipt()方法。
* 在sdk中我们将返回值进行封装为GetTxReciptReturn类的对象，该对象中包含所有返回结果，通过get方法即可取得返回值。

-----

##1.20 discardTransaction接口

* 通过输入token，start，end获取DiscardConReturn对象，即，QsnarkAPI api = new Qsnark(),然后通过api.discardTransaction()方法获取DiscardConReturn对象。
* 返回结果封装成DiscardConReturn对象，用户可以通过对象的属性来获取返回结果。










