# 趣链开发者平台  Java SDK
Qsnark SDK 是qsnark对外提供的API接口的Java封装。

# SDK下载列表
|简述|下载地址|
|:---|:---|:---|
|Java SDK|[https://dev.hyperchain.cn/download/qsnarksdk-1.0-SNAPSHOT.jar](https://dev.hyperchain.cn/download/qsnarksdk-1.0-SNAPSHOT.jar)|
|Java SDK依赖包|[https://dev.hyperchain.cn/download/qsnarksdk-1.0-SNAPSHOT-jar-with-dependencies.jar](https://dev.hyperchain.cn/download/qsnarksdk-1.0-SNAPSHOT-jar-with-dependencies.jar)|
|Go SDK|[https://github.com/hyperchaincn/qsnark-go-sdk](https://github.com/hyperchaincn/qsnark-go-sdk)|
|Node.js SDK|[https://github.com/hyperchaincn/qsnark-nodejs-sdk](https://github.com/hyperchaincn/qsnark-nodejs-sdk)|
|Javascript SDK|[https://github.com/hyperchaincn/qsnark-js-sdk](https://github.com/hyperchaincn/qsnark-js-sdk)|
|Php SDK|[https://github.com/hyperchaincn/qsnark-php-sdk](https://github.com/hyperchaincn/qsnark-php-sdk)|

# SDK API概览
|接口名称|接口功能|
|:---|:---|:---|
|[getAccess_Token](getAccess_Token)|获取AccessToken|
|[refAccess_Token](refAccess_Token)|刷新AccessToken|
|[queryTransaction](queryTransaction)|获取交易信息|
|[rangeBlocks](rangeBlocks)|获取区间区块信息|
|[countTransaction](countTransaction)|获取交易总数|
|[discardTransaction](discardTransaction)|获取无效交易|
|[queryBlock](queryBlock)|获取区块信息|
|[pageBlocks](pageBlocks)|获取区块信息（分页）|
|[rangeBlocks](rangeBlocks)|获取区间区块信息|
|[compileContract](compileContract)|编译合约|
|[deployContract](deployContract)|异步部署合约|
|[deploysyncContract](deploysyncContract)|同步部署合约|
|[deployArgsContract](deployArgsContract)|部署合约 （方法名参数）|
|[invokeContract](invokeContract)|异步调用合约方法|
|[invokesyncContract](invokesyncContract)|同步调用合约方法|
|[maintainContract](maintainContract)|维护合约|
|[statusContract](statusContract)|查询合约状态|
|[createAccount](createAccount)|创建区块链账户|


# 实例

## 获取AccessToken

### 接口描述

根据用户手机号码密码等信息，获取授权令牌token。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|client_id|string|httpkey|
|client_secret|string|httpsecret|
|phone|string|用户手机号|
|password|string|密码|


### 调用示例

|参数|值|
|:--|:--|
|client_id|123|
|client_secret|123|
|phone|17706421110|
|password|123|

```java
GetTokenReturn getTokenReturn = api.getAccess_Token("123", "123", "17706421110", "123");
```

### 返回结果说明
返回的GetTokenReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，-1为失败|
|error|成功时为空失败时为失败的类型|
|message|成功时为success，失败时为相应的错误信息|
|access_token|授权令牌|
|expires_in|有效时间(s|
|refresh_token|刷新token|
|scope|范围|
|token_type|token类型|

```json
{
    "code": 0 ,
    "error":"" ,
    "message":"success" ,
    "access_token": "IEK1DO1ZPAQPBDPPDY7VMQ",
    "expires_in": 7200,
    "refresh_token": "JH_-7WRJUIS5EQ4U35SD2G",
    "scope": "all",
    "token_type": "Bearer"
}

```


## 刷新AccessToken

### 接口描述

根据refresh_token等参数，刷新授权令牌token。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|client_id|string|httpkey|
|client_secret|string|httpsecret|
|refresh_token|string|刷新token码|


### 调用示例

|参数|值|
|:--|:--|
|client_id|123|
|client_secret|123|
|refresh_token|JH_-7WRJUIS5EQ4U35SD2G|

```java
RetokenReturn retokenReturn = api.refAccess_Token("123", "123", "YFUMQEGTUT2-VAJ_LHA1QA");
```

### 返回结果说明
返回的RetokenReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，-1为失败|
|error|成功时为空失败时为失败的类型|
|message|成功时为success，失败时为相应的错误信息|
|access_token|授权令牌|
|expires_in|有效时间(s|
|refresh_token|刷新token|
|scope|范围|
|token_type|token类型|

```json
{
    "code": 0 ,
    "error":"" ,
    "message":"success" ,
    "access_token":"CD843SDUOQ61RY3NEXZHLA",
    "expires_in":7200,
    "refresh_token":"LAJDJSVYXG-XQTZUVCNOOG",
    "scope":"all",
    "token_type":"Bearer"
}

```


## 获取交易信息

### 接口描述

根据token,hash获取交易信息。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|transactionHash |string|交易的哈希值,32字节的十六进制字符串|

### 调用示例

|参数|值|
|:--|:--|
|token|3VCA4KTPPLCRIQXEWNIWLW|
|hash|0x17e8747903ea0a929cfda41e38e70d20b4205291b3b59af2f3ee0529b8f62825|

```java
QueryTranReturn qreturn = api.queryTransaction("3VCA4KTPPLCRIQXEWNIWLW", "0x17e8747903ea0a929cfda41e38e70d20b4205291b3b59af2f3ee0529b8f62825");
```

### 返回结果说明
返回的QueryTranReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|Transaction|包含以下全部信息|
|Version|平台版本号|
|Hash|交易的哈希值,32字节的十六进制字符串|
|BlockNumber|交易所在的区块高度|
|BlockHash|交易所在区块哈希值|
|TxIndex|交易在区块中的交易列表的位置|
|From|交易发送方的地址,20字节的十六进制字符串|
|To|交易接收方的地址,20字节的十六进制字符|
|Amount|交易量|
|Timestamp|交易发生时间(单位 ns|
|Nonce| 16位随机数|
|ExecuteTime|交易的处理时间(单位ms|
|Payload|部署合约与调用合约的时候才有这个值，可以通过这个值追朔到合约调用的方法以及调用传入的参数|
|Invalid|交易是否不合法|
|InvalidMsg|交易的不合法信息|

```json
{
  "code": 0 ,
  "status":"" ,
  "Transaction": {
  "Version": "1.2",
  "Hash": "0x4a630908bf78441197c9fc94aa3ebb4f21218cf61dfe82b62184aa1bc7f1dff1",
  "BlockNumber": 1,
  "BlockHash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
  "TxIndex": 0,
  "From": "0x0aed175e1b70c67aa601a0f65946aee6bc4e5534",
  "To": "0x0000000000000000000000000000000000000000",
  "Amount": 0,
  "Timestamp": 1502860798414577195,
  "Nonce": 6651414131918424343,
  "ExecuteTime": 23,
  "Payload": "0x60a0604052600c60608190527f48656c6c6f2051736e61726b0000000000000000000000000000000000000000608090815261003e916000919061004c565b50341561004757fe5b6100ec565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061008d57805160ff19168380011785556100ba565b828001600101855582156100ba579182015b828111156100ba57825182559160200191906001019061009f565b5b506100c79291506100cb565b5090565b6100e991905b808211156100c757600081556001016100d1565b5090565b90565b610188806100fb6000396000f300606060405263ffffffff60e060020a6000350416638da9b7728114610021575bfe5b341561002957fe5b6100316100b1565b604080516020808252835181830152835191928392908301918501908083838215610077575b80518252602083111561007757601f199092019160209182019101610057565b505050905090810190601f1680156100a35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100b961014a565b6000805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561013f5780601f106101145761010080835404028352916020019161013f565b820191906000526020600020905b81548152906001019060200180831161012257829003601f168201915b505050505090505b90565b604080516020810190915260008152905600a165627a7a72305820794559401ace9f00ea9b8bc27902f3396df15cc581cc6545a04292177f71725c0029",
  
  "Invalid": false,
  "InvalidMsg": ""
}

```


-----

## 获取区间区块信息

### 接口描述

根据token，from，to参数，查询指定区间内的区块。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|from|long|起始区块号|
|to|object|终止区块号可以为具体块号或者为latest|

### 调用示例

|参数|值|
|:--|:--|
|token|Y5BFCSV2MCAZUQMZ9LMMSQ|
|from|1|
|to|2|

```java
RangeBlocksReturn queryBlocksReturn = api.rangeBlocks("IVKEWCXMOAWXG1DTYAGUZA", 1, 2);
```

### 返回结果说明
返回的RangeBlocksReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|Blocks|区块数组|

```json
{
    "code": 0 ,
    "status":"" ,
    "Blocks": [ 
    {
      "Number": 2,
      "Hash": "0xaa9d9227d307f87d2c19f3b624f6af32bd90784044d1089c9e457fc90b70794f",
      "ParentHash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
      "WriteTime": 1502860905924476359,
      "AvgTime": 17,
      "Txcounts": 1,
      "MerkleRoot": "0xda854e53569a308034da9714d2a9cf16a174e39b96699b0d0727c27d93896f2f",
      "Transactions": [
        {
          "Version": "1.2",
          "Hash": "0x612b1b6d10cd1ee55ad3084e8734adaae11f83e9a2fcd062c064c0bc382eb8ba",
          "BlockNumber": 2,
          "BlockHash": "0xaa9d9227d307f87d2c19f3b624f6af32bd90784044d1089c9e457fc90b70794f",
          "TxIndex": 0,
          "From": "0x0aed175e1b70c67aa601a0f65946aee6bc4e5534",
          "To": "0xceced635239b1b801d972b93d253f628bf1cf9fa",
          "Amount": 0,
          "Timestamp": 1502860805006583586,
          "Nonce": 5944830206637008055,
          "ExecuteTime": 17,
          "Payload": "0x8da9b772",
          "Invalid": false,
          "InvalidMsg": ""
        }
      ]
    },
    {
      "Number": 1,
      "Hash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
      "ParentHash": "0x0000000000000000000000000000000000000000000000000000000000000000",
      "WriteTime": 1502860899344164151,
      "AvgTime": 23,
      "Txcounts": 1,
      "MerkleRoot": "0xda854e53569a308034da9714d2a9cf16a174e39b96699b0d0727c27d93896f2f",
      "Transactions": [
        {
          "Version": "1.2",
          "Hash": "0x4a630908bf78441197c9fc94aa3ebb4f21218cf61dfe82b62184aa1bc7f1dff1",
          "BlockNumber": 1,
          "BlockHash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
          "TxIndex": 0,
          "From": "0x0aed175e1b70c67aa601a0f65946aee6bc4e5534",
          "To": "0x0000000000000000000000000000000000000000",
          "Amount": 0,
          "Timestamp": 1502860798414577195,
          "Nonce": 6651414131918424343,
          "ExecuteTime": 23,
          "Payload": "0x60a0604052600c60608190527f48656c6c6f2051736e61726b0000000000000000000000000000000000000000608090815261003e916000919061004c565b50341561004757fe5b6100ec565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061008d57805160ff19168380011785556100ba565b828001600101855582156100ba579182015b828111156100ba57825182559160200191906001019061009f565b5b506100c79291506100cb565b5090565b6100e991905b808211156100c757600081556001016100d1565b5090565b90565b610188806100fb6000396000f300606060405263ffffffff60e060020a6000350416638da9b7728114610021575bfe5b341561002957fe5b6100316100b1565b604080516020808252835181830152835191928392908301918501908083838215610077575b80518252602083111561007757601f199092019160209182019101610057565b505050905090810190601f1680156100a35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100b961014a565b6000805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561013f5780601f106101145761010080835404028352916020019161013f565b820191906000526020600020905b81548152906001019060200180831161012257829003601f168201915b505050505090505b90565b604080516020810190915260008152905600a165627a7a72305820794559401ace9f00ea9b8bc27902f3396df15cc581cc6545a04292177f71725c0029",
          "Invalid": false,
          "InvalidMsg": ""
        }
      ]
    }
  ]
}

```


## 获取交易总数

### 接口描述

根据token获取链上的交易总数。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|

### 调用示例

|参数|值|
|:--|:--|
|token|Y5BFCSV2MCAZUQMZ9LMMSQ|

```java
CountTraReturn countTraReturn = api.countTransaction("Y5BFCSV2MCAZUQMZ9LMMSQ");
```
### 返回结果说明
返回的CountTraReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|Count|交易数|
|Timestamp|时间戳|

```json
{
    "code": 0 ,
    "status":"" ,
    "Count": 175390,
    "Timestamp": 1502847160298475207
}

```


-----

## 获取无效交易

### 接口描述

通过输入token，start，end查询无效交易信息。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|start |long|起始timestamp|
|end|long|终止timestamp|

### 调用示例

|参数|值|
|:--|:--|
|token|Y5BFCSV2MCAZUQMZ9LMMSQ|
|start|1509235200000000000|
|end|1509375428000000000|

```java

    DiscardConReturn discardConReturn = api.discardTransaction("B14X4H6SMLMQI_WXEVKJDW", "1509235200000000000", "1509375428000000000");

```

### 返回结果说明
返回的DiscardConReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|Transactions|无效交易集合|
|Version|版本号|
|Hash|交易的哈希值,32字节的十六进制字符串|
|BlockNumber|交易所在的区块高度|
|BlockHash|交易的哈希值,32字节的十六进制字符串|
|TxIndex|交易在区块中的交易列表的位置|
|From|交易发送方的地址,20字节的十六进制字符串|
|To|交易接收方的地址,20字节的十六进制字符|
|Amount|交易量|
|Nonce|16位随机数|
|ExecuteTime|<string> 交易的处理时间(单位ms|
|Payload|部署合约与调用合约的时候才有这个值，可以通过这个值追朔到合约调用的方法以及调用传入的参数。|
|Invalid|交易是否不合法|
|InvalidMsg|交易的不合法信息|

```json
"code": 0 
    "status":"" 
    "Transactions":[ 
    {
        "Version":"", 
        "Hash":"0x08dbbaf176ff51b4501d6757efa5c5b7e28fe6b2febf07fec2d373cef12860a6",
        
        "BlockNumber":0, 
        "BlockHash": "", 
        "TxIndex": 0, 
        "From":"0x885b67411d84f6aa3b1e8e5ee6730c8123423777",
        "To": "0x6a713a318ac303457da2d42e52e7304f33ef310a",
        "Amount": 0, "Timestamp": 1503296874051787500,
        "Nonce": 894385949183117200, 
        "ExecuteTime":0,
        "Payload": "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029",
       
        "Invalid": true,
        "InvalidMsg": "INVOKE_CONTRACT_FAILED"
    },{
        "Version": "",
        "Hash": "0xe12fb78e754f4bde5bf23b1f098d9e75cb977dc5275a6f994f74066c2b52d10c",
        "BlockNumber": 0,
        "BlockHash": "",
        "TxIndex": 0,
        "From": "0xdf54c8ff47e57f9e69f1f24f53ea46bd009724c6",
        "To": "0x0000000000000000000000000000000000000000",
        "Amount": 0,
        "Timestamp": 1509345937588384109,
        "Nonce": 6129484611666145821,
        "ExecuteTime": 0,
        "Payload": "0x606060405260006000553415601057fe5b5b6002600090815580fd5b5b5b60728061002b6000396000f300606060405263ffffffff60e060020a600035041663be1c766b81146020575bfe5b3415602757fe5b602d603f565b60408051918252519081900360200190f35b6000545b905600a165627a7a7230582088586937fdbf571f4028e7f0f731739c0af294a130403c85c75001ab5d2345db0029",
        "Invalid": true,
        "InvalidMsg": "DEPLOY_CONTRACT_FAILED"
    }]
}

```


## 获取区块信息

### 接口描述

根据token，type，value参数，实现查询指定区块数的block。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|type|string|可以是number或者hash|
|value|object|type为number时可以是int(区块的高度)或"latest"(最后一块)type为hash时区块的hash值|



### 调用示例
|参数|值|
|:--|:--|
|token|Y5BFCSV2MCAZUQMZ9LMMSQ|
|type|number|
|value|1|


```java
QueryBlockReturn queryBlockReturn = api.queryBlock("Y5BFCSV2MCAZUQMZ9LMMSQ", "", 1);
```

### 返回结果说明
返回的QueryBlockReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|Block|整个block的信息|
|Number|区块的高度|
|Hash|区块的哈希值,32字节的十六进制字符串|
|ParentHash|父区块哈希值，32字节的十六进制字符串|
|WriteTime|区块的生成时间(单位ns|
|AvgTime|当前区块中，交易的平均处理时间（单位ms|
|Txcounts|当前区块中打包的交易数量|
|MerkleRoot|Merkle树的根哈希|
|0xda854e53569a308034da9714d2a9cf16a174e39b96699b0d0727c27d93896f2f",
    "Transactions|区块中的交易列表|

```json
{
    "code": 0 ,
    "status":"" ,
    "Block":{
    "Number": 1,
    "Hash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
    
    "ParentHash": "0x0000000000000000000000000000000000000000000000000000000000000000",
    
    "WriteTime": 1502860899344164151,
    "AvgTime": 23,
    "Txcounts": 1,
    "MerkleRoot": "0xda854e53569a308034da9714d2a9cf16a174e39b96699b0d0727c27d93896f2f",
    "Transactions": [
      {
        "Version": "1.2",
        "Hash": "0x4a630908bf78441197c9fc94aa3ebb4f21218cf61dfe82b62184aa1bc7f1dff1",
        "BlockNumber": 1,
        "BlockHash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
        "TxIndex": 0,
        "From": "0x0aed175e1b70c67aa601a0f65946aee6bc4e5534",
        "To": "0x0000000000000000000000000000000000000000",
        "Amount": 0,
        "Timestamp": 1502860798414577195,
        "Nonce": 6651414131918424343,
        "ExecuteTime": 23,
        "Payload": "0x60a0604052600c60608190527f48656c6c6f2051736e61726b0000000000000000000000000000000000000000608090815261003e916000919061004c565b50341561004757fe5b6100ec565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061008d57805160ff19168380011785556100ba565b828001600101855582156100ba579182015b828111156100ba57825182559160200191906001019061009f565b5b506100c79291506100cb565b5090565b6100e991905b808211156100c757600081556001016100d1565b5090565b90565b610188806100fb6000396000f300606060405263ffffffff60e060020a6000350416638da9b7728114610021575bfe5b341561002957fe5b6100316100b1565b604080516020808252835181830152835191928392908301918501908083838215610077575b80518252602083111561007757601f199092019160209182019101610057565b505050905090810190601f1680156100a35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100b961014a565b6000805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561013f5780601f106101145761010080835404028352916020019161013f565b820191906000526020600020905b81548152906001019060200180831161012257829003601f168201915b505050505090505b90565b604080516020810190915260008152905600a165627a7a72305820794559401ace9f00ea9b8bc27902f3396df15cc581cc6545a04292177f71725c0029",
        "Invalid": false,
        "InvalidMsg": ""
      }
    ]
  }
}

```


## 获取区块信息（分页）

### 接口描述

根据token，index，size参数，实现查询指定页的区块。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|index|long|page index 页码|
|size|long|page size 每页区块数量|



### 调用示例

|参数|值|
|:--|:--|
|token|Y5BFCSV2MCAZUQMZ9LMMSQ|
|index|1|
|size|1|

```java
PageBlocksReturn pageBlocksReturn = api.pageBlocks("Y5BFCSV2MCAZUQMZ9LMMSQ", 1, 12);
```

### 返回结果说明
返回的PageBlocksReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|List|区块列表|
|Number|区块的高度|

```json
{
    "code": 0 ,
    "status":"" ,
    "List": [ 
    {
      "Number": 144,
      "Hash": "0x0f91c2be510f101e1d06fec1cfe381bcd8b04e9ad69d99f03521498f1f09c7b6",
      "ParentHash": "0x76c785dd4e58762787f39e1e48f57ff0e3d4877f26e8cda53810d31232870d9b",
      "WriteTime": 1503322044366500267,
      "AvgTime": 24,
      "Txcounts": 1,
      "MerkleRoot": "0xa49893299fa4f0e2d39d6f49253d3896c9d1724d103913012f765da6083ca3e5",
      "Transactions": [
        {
          "Version": "1.2",
          "Hash": "0x5b04a3ebeaa9d39c6d9e001ec4eda5985e02f48b39c8fe7a3b47df69a6054070",
          "BlockNumber": 144,
          "BlockHash": "0x0f91c2be510f101e1d06fec1cfe381bcd8b04e9ad69d99f03521498f1f09c7b6",
          "TxIndex": 0,
          "From": "0x76fd78244f6a91d2fbe047a10a9b78e682474e7a",
          "To": "0x0000000000000000000000000000000000000000",
          "Amount": 0,
          "Timestamp": 1503322043820082352,
          "Nonce": 5600924393587988459,
          "ExecuteTime": 24,
          "Payload": "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a72305820ef7bd79289fbeed067c8fea77467f6a922f70007fc6ba26663290ab3469867420029",
          "Invalid": false,
          "InvalidMsg": ""
        }
      ]
    }
  ],
  "Count": 144
}

```


-----

## 获取区间区块信息

### 接口描述

根据token，from，to参数，查询指定区间内的区块。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|from|long|起始区块号|
|to|object|终止区块号可以为具体块号或者为latest|

### 调用示例

|参数|值|
|:--|:--|
|token|Y5BFCSV2MCAZUQMZ9LMMSQ|
|from|1|
|to|2|

```java
RangeBlocksReturn queryBlocksReturn = api.rangeBlocks("IVKEWCXMOAWXG1DTYAGUZA", 1, 2);
```

### 返回结果说明
返回的RangeBlocksReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|Blocks|区块数组|

```json
{
    "code": 0 ,
    "status":"" ,
    "Blocks": [ 
    {
      "Number": 2,
      "Hash": "0xaa9d9227d307f87d2c19f3b624f6af32bd90784044d1089c9e457fc90b70794f",
      "ParentHash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
      "WriteTime": 1502860905924476359,
      "AvgTime": 17,
      "Txcounts": 1,
      "MerkleRoot": "0xda854e53569a308034da9714d2a9cf16a174e39b96699b0d0727c27d93896f2f",
      "Transactions": [
        {
          "Version": "1.2",
          "Hash": "0x612b1b6d10cd1ee55ad3084e8734adaae11f83e9a2fcd062c064c0bc382eb8ba",
          "BlockNumber": 2,
          "BlockHash": "0xaa9d9227d307f87d2c19f3b624f6af32bd90784044d1089c9e457fc90b70794f",
          "TxIndex": 0,
          "From": "0x0aed175e1b70c67aa601a0f65946aee6bc4e5534",
          "To": "0xceced635239b1b801d972b93d253f628bf1cf9fa",
          "Amount": 0,
          "Timestamp": 1502860805006583586,
          "Nonce": 5944830206637008055,
          "ExecuteTime": 17,
          "Payload": "0x8da9b772",
          "Invalid": false,
          "InvalidMsg": ""
        }
      ]
    },
    {
      "Number": 1,
      "Hash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
      "ParentHash": "0x0000000000000000000000000000000000000000000000000000000000000000",
      "WriteTime": 1502860899344164151,
      "AvgTime": 23,
      "Txcounts": 1,
      "MerkleRoot": "0xda854e53569a308034da9714d2a9cf16a174e39b96699b0d0727c27d93896f2f",
      "Transactions": [
        {
          "Version": "1.2",
          "Hash": "0x4a630908bf78441197c9fc94aa3ebb4f21218cf61dfe82b62184aa1bc7f1dff1",
          "BlockNumber": 1,
          "BlockHash": "0x17fb07cb6e6fe9ff00d4de91b82590158eb12ef1faff724abca9ca7ab584daed",
          "TxIndex": 0,
          "From": "0x0aed175e1b70c67aa601a0f65946aee6bc4e5534",
          "To": "0x0000000000000000000000000000000000000000",
          "Amount": 0,
          "Timestamp": 1502860798414577195,
          "Nonce": 6651414131918424343,
          "ExecuteTime": 23,
          "Payload": "0x60a0604052600c60608190527f48656c6c6f2051736e61726b0000000000000000000000000000000000000000608090815261003e916000919061004c565b50341561004757fe5b6100ec565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061008d57805160ff19168380011785556100ba565b828001600101855582156100ba579182015b828111156100ba57825182559160200191906001019061009f565b5b506100c79291506100cb565b5090565b6100e991905b808211156100c757600081556001016100d1565b5090565b90565b610188806100fb6000396000f300606060405263ffffffff60e060020a6000350416638da9b7728114610021575bfe5b341561002957fe5b6100316100b1565b604080516020808252835181830152835191928392908301918501908083838215610077575b80518252602083111561007757601f199092019160209182019101610057565b505050905090810190601f1680156100a35780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100b961014a565b6000805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561013f5780601f106101145761010080835404028352916020019161013f565b820191906000526020600020905b81548152906001019060200180831161012257829003601f168201915b505050505090505b90565b604080516020810190915260008152905600a165627a7a72305820794559401ace9f00ea9b8bc27902f3396df15cc581cc6545a04292177f71725c0029",
          "Invalid": false,
          "InvalidMsg": ""
        }
      ]
    }
  ]
}

```


-----

## 编译合约

### 接口描述

根据token，sourcecode参数，编译合约源码。

### 参数说明


|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|sourcecode|string|合约源码|


### 调用示例
|参数|值|
|:--|:--|
|token|_G2BS9FTNUE2SC5SO-NB3Q|
|sourceCode|contract Accumulator{    uint32 sum = 0;   function increment(){         sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }|


```java
String s = "contract Accumulator{    uint32 sum = 0;   function increment(){         sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }";
CompileReturn compileReturn = api.compileContract("BZTEAKUJOYECQVU7ZIYXLA", s);
```
### 返回结果说明
返回的CompileReturn对象的属性：

```jsoncc
{
  "Code": 0,
  "Status": "ok",
  "Cts": [
    {
      "Code": 0,
      "Status": "",
      "Id": 0,
      "Bin": "0x60606040526000805463ffffffff19169055341561001c57600080fd5b5b61012680610
      02c6000396000f300606060405263ffffffff7c010000000000000000000000000000000000000000
      00000000000000006000350416633ad14af381146050578063569c5f6d146071578063d09de08a146
      09a575b600080fd5b3415605a57600080fd5b606f63ffffffff6004358116906024351660ac565b00
      5b3415607b57600080fd5b608160ce565b60405163ffffffff909116815260200160405180910390f
      35b341560a457600080fd5b606f60db565b005b6000805463ffffffff808216850184011663ffffff
      ff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff80821660010
      11663ffffffff199091161790555b5600a165627a7a72305820bccd1806f9855c781cf23418c61758
      b4da87d5e5cef6503be5aa0d4b4c8db4460029",
      "Abi": "[{\"constant\":false,\"inputs\":[{\"name\":\"num1\",\"type\":\"uint32\"},
      {\"name\":\"num2\",\"type\":\"uint32\"}],\"name\":\"add\",\"outputs\":[],\"payable\":
      false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,
      \"inputs\":[],\"name\":\"getSum\",\"outputs\":[{\"name\":\"\",\"type\":\"uint32\"}],
      \"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},
      {\"constant\":false,\"inputs\":[],\"name\":\"increment\",\"outputs\":[],\"payable\":false,
      \"stateMutability\":\"nonpayable\",\"type\":\"function\"}]",
      "Name": "Accumulator",
      "OK": true
    }
  ]
}
```

-----

## 异步部署合约

### 接口描述

根据token，bin，from，callback参数，部署合约异步获取回执。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|from|string|合约调用者地址|
|bin|string|合约源码编码生成的bin|
|callback|function|用户自定义方法|

### 调用示例

|参数|值|
|:--|:--|
|token|XUSICYOHONK_AJA1LMQQLQ|
|address|0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029|

```java
DeployConReturn deployConReturn = api.deployContract("XUSICYOHONK_AJA1LMQQLQ", "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029","0x91049d6088088bf0c6c9c67d81375a89f648be0",
        new DevCallback() {
            @Override
            public void onCompute(String address) {
                System.out.println(address);
            }
        });
```

### 返回结果说明
返回的DeployConReturn对象的属性：


|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|txHash|交易hash|

```json
{
    "code":0 ,
    "status":"" ,
    "txHash":"0x05a267ed9cb3102c6297ecf1585b495bc529245e7d90fd4414bd880f3b7c12b6"
}

```


|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|txHash|交易hash|
|postState|部署地址|

    异步线程获取回执,此部分的值返回对象获取不到但是可以用传入的自定义方法使用contractAddress
```json
{

    "code":0 ,
    "status":"" ,
    "txHash":"0xe6f62694aa5be2614b24dc3394a79cf1f5923c8dc7dbd81663328ae47aae0613",
    "postState": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "contractAddress": "0x752bc8552696ba53bb7fddb67294c704b4c6f553",
    "ret": "0x606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029"
}

```


-----

## 同步部署合约

### 接口描述

根据token，bin，from，部署合约,并获取回执信息。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|from|string|合约调用者地址|
|bin|string|合约源码编码生成的bin|

### 调用示例

|参数|值|
|:--|:--|
|token|XUSICYOHONK_AJA1LMQQLQ|
|bin|0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029|
|from|0x645aaec26e752d2249a7d3df9f8f6f2c10f71ac5|

```java
GetTxReciptReturn txReciptReturn = api.deploysyncContract("60K-WEVJPMW3BXHSRUYGGA", "0x60606040523415600b57fe5b604051602080605f83398101604052515b5b505b603380602c6000396000f30060606040525bfe00a165627a7a723058206f3d39eab86ddcfb556e1c6f43a60903e1ab88beb7909c0d49d7a25b1b1a15650029", "0x885b67411d84f6aa3b1e8e5ee6730c8123423777");
```

### 返回结果说明
返回的GetTxReciptReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|txHash|交易hash|
|postState|部署合约地址|

```json
{
    "code":0 ,
    "status":"" ,
    "txHash":"0x03ed7e9955bc7aa0a538b7671d4287df7537ce9215319965fcbd451774e3f244",
    "postState": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "contractAddress": "0x41c018c1f9631bb598218b06810a6df74ad5e07f",
    "ret": "0x60606040525bfe00a165627a7a723058206f3d39eab86ddcfb556e1c6f43a60903e1ab88beb7909c0d49d7a25b1b1a15650029"
}

```


## 部署合约 （方法名参数）

### 接口描述

根据token，bin，from，callback,abi,param...部署合约,并异步获取回执信息。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|bin|string|合约源码编码生成的bin|
|from|string|合约调用者地址|
|callback|function|用户自定义方法|
|abi|string|合约源码对应的abi数组|
|param...|FuncParamReal对象|<FuncParamReal对象>0个或1个或多个|

### 调用示例

```java
FuncParamReal param1 = new FuncParamReal("uint32", 1);
FuncParamReal param2 = new FuncParamReal("uint256", 2);

"token":　"JQS1VXOTNEANEOUZ79537A",
"bin":　"0x60606040523415600b57fe5b604051602080605f83398101604052515b5b505b603380602c6000396000f30060606040525bfe00a165627a7a723058206f3d39eab86ddcfb556e1c6f43a60903e1ab88beb7909c0d49d7a25b1b1a15650029",
"from": "0x885b67411d84f6aa3b1e8e5ee6730c8123423777",
new DevCallback() {
    @Override
    public void onCompute(String address) {
        System.out.println(address);
    }
},
"Abistr":　"[{"constant":false,"inputs":[{"name":"num1","type":"uint32"},{"name":"num2","type":"uint32"}],"name":"add","outputs":[],"payable":false,"type":"function"},{"constant":false,},"inputs":[],"name":"getSum","outputs":[{"name":"","type":"uint32"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"increment","outputs":[],"payable":false,"type":"function"}]",
param...(可为空，一个或多个，此处为空)
```

```java
DeployConReturn deployConReturn = api.deployArgsContract("60K-WEVJPMW3BXHSRUYGGA","0x60606040523415600b57fe5b604051602080605f83398101604052515b5b505b603380602c6000396000f30060606040525bfe00a165627a7a723058206f3d39eab86ddcfb556e1c6f43a60903e1ab88beb7909c0d49d7a25b1b1a15650029","0x885b67411d84f6aa3b1e8e5ee6730c8123423777",
    new DevCallback() {
        @Override
        public void onCompute(String address) {
            System.out.println(address);
        }
    }, abi);

```

### 返回结果说明
返回的GetTxReciptReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|txHash|交易hash|

```json
{
    "code": 0 ,
    "status": "" ,
    "txHash": "0x2c0097fdefc0affc27520a35268d2ac36b64dfca76f4347e140e05d9898a8a98"
}

```


|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|txHash|交易hash|
|postState|部署合约地址|

 异步线程获取回执,此部分的值返回对象获取不到但是可以用传入的自定义方法使用contractAddress
```json
{
   
    "code": 0 ,
    "status": "" ,
    "txHash": "0x2c0097fdefc0affc27520a35268d2ac36b64dfca76f4347e140e05d9898a8a98",
    "postState": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "contractAddress": "0x41c018c1f9631bb598218b06810a6df74ad5e07f",
    "ret": "0x60606040525bfe00a165627a7a723058206f3d39eab86ddcfb556e1c6f43a60903e1ab88beb7909c0d49d7a25b1b1a15650029"
}

```


-----

## 异步调用合约方法

### 接口描述

根据token，_const，from，to，abi,callback,func_name,param...调用合约,并异步获取回执信息。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|_const|bool|constant|
|from|string|合约调用者地址|
|to|string|合约地址|
|abi|string|合约源码对应的abi数组|
|callback|function|用户自定义方法|
|param...|FuncParamReal对象|<FuncParamReal对象>0个或1个或多个|



### 调用示例

```
FuncParamReal param1 = new FuncParamReal("uint32", 1);
FuncParamReal param2 = new FuncParamReal("uint32", 2);
"token":"FWYHAQMTWE6BPQWHPSGFXQ" ,
"const":false ,
"from":"0x9e33ffae1477a33233126c6680d418e0fb1ed219",
"to":"0x4dffc0eecde676583ebde1c29d39a0319bc8b1c7",
"abi":"[{"constant":false,"inputs":[{"name":"num1","type":"uint32"},  {"name":"num2","type":"uint32"}],"name":"add","outputs":[],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"getSum","outputs":[{"name":"","type":"uint32"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"increment","outputs":[],"payable":false,"type":"function"}]"，
(用户自定义方法，可以使用address来进行自己想要运行的方法)
new InvCallback() {
    @Override
    public void onCompute(List ret) {
        System.out.println(ret);
    }
},
"func_name":"add"，
param1,
param2
```

```java
InvokeConReturn invokeConReturn = api.invokeContract("ZWUEW9TLN3Q_EMELYQFVAA",false,"0x7c2b111bce226cde5848a6304dc36922b7099491"
        , "0x83665f52cd2d201269da2faa6c877d2d037b991b", abi, new InvCallback() {
            @Override
            public void onCompute(List ret) {
                System.out.println(ret);
            }
        });
```

### 返回结果说明
返回的GetTxReciptReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|

```json
{
    "code": 0,
    "status":"", 
    "TxHash": "0x331c519f6c5c8d33d032e5da33ae55c8ce96d75eefd0f4201f8145ffd584fd1a"
}

```


|参数|说明|
|:--|:--|
|Code|状态码0为成功，非零则为相应错误码|
|Status|成功时为"ok"，失败时为相应的错误信息|

```json
异步线程获取回执,此部分的值返回对象获取不到但是可以用传入的自定义方法使用ret
{
    "Code": 0, 
    "Status": "ok", 
    "TxHash": "0x971643ffe42e25febe46e5e4791d03f6abb13a5acb31bd339062f802362c2c0f",
    "ContractAddress": "0x0000000000000000000000000000000000000000",
    "Ret": "0x0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000100000000000000000000000738fdc2553b5cdcae43952539dcb04b3ae621ee1"
}

```


-----

## 同步调用合约方法

### 接口描述

根据token，_const，from，to，abi,func_name,param...调用合约,获取回执信息。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|_const|bool|constant|
|from|string|合约调用者地址|
|to|string|合约地址|
|abi|string|合约源码对应的abi数组|
|func_name|string|调用合约中的方法名|
|param...|FuncParamReal对象|<FuncParamReal对象>0个或1个或多个|

### 调用示例

```java
FuncParamReal param1 = new FuncParamReal("uint32", 1);
FuncParamReal param2 = new FuncParamReal("uint32", 2);
"token":"FWYHAQMTWE6BPQWHPSGFXQ" ,
"const":false ,
"from":"0x9e33ffae1477a33233126c6680d418e0fb1ed219",
"to":"0x4dffc0eecde676583ebde1c29d39a0319bc8b1c7",
"abi":"[{"constant":false,"inputs":[{"name":"num1","type":"uint32"},    {"name":"num2","type":"uint32"}],"name":"add","outputs":[],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"getSum","outputs":[{"name":"","type":"uint32"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"increment","outputs":[],"payable":false,"type":"function"}]"，
"func_name":"add"，
param1,
param2
```

```java
GetTxReciptReturn getTxReciptReturn = api.invokesyncContract("FFDCMMO4NXSHO29OVURYGW", false,"0x7c2b111bce226cde5848a6304dc36922b7099491"
      , "0x83665f52cd2d201269da2faa6c877d2d037b991b", abi, "getString");

```

### 返回结果说明
返回的GetTxReciptReturn对象的属性：

|参数|说明|
|:--|:--|
|Code|状态码0为成功，非零则为相应错误码|
|Status|成功时为"ok"，失败时为相应的错误信息|

```json
{
    "Code": 0, 
    "Status": "ok", 
    "TxHash": "0x971643ffe42e25febe46e5e4791d03f6abb13a5acb31bd339062f802362c2c0f",
    "ContractAddress": "0x0000000000000000000000000000000000000000",
    "Ret": "0x0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000100000000000000000000000738fdc2553b5cdcae43952539dcb04b3ae621ee1"
}

```


## 维护合约

### 接口描述

根据token，from，opration,payload,to,维护合约。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|from|string|合约调用者地址|
|opration|int|1升级2冻结3解冻|
|payload|string|新的合约的bin|
|to|string|合约地址|

### 调用示例
|参数|值|
|:--|:--|
|token|Y5BFCSV2MCAZUQMZ9LMMSQ|
|from|0xd4621641bfa08b005c704726d7d2082665f34df4|
|opration|1|
|payload|0x60606040523415600b57fe5b5b60338060196000396000f30060606040525bfe00a165627a7a72305820d0b1f6bf73919b56bcf02d5db4871de60bd62a9b4ef2200e69b8ade3e6fcd4b90029|
|to|0xdf9f4f078997b8269f0782bc40cb3d07a6efd069|

```java
MainTainReturn mainTainReturn = api.maintainContract("IVKEWCXMOAWXG1DTYAGUZA", "0x885b67411d84f6aa3b1e8e5ee6730c8123423777", 1, "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575fe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029", "0x6a713a318ac303457da2d42e52e7304f33ef310a");
```

### 返回结果说明
返回的MainTainReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|

```json
{
    "code":0,
    "status":"",
    "TxHash":"0xb07859c9e33218956ae03941e2c3e03d7102057e64677902f5b8ab69950186bb"
}

```


-----

## 查询合约状态

### 接口描述

根据token，address查询合约状态。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|
|address|string|合约地址|

### 调用示例

|参数|值|
|:--|:--|
|token|Y5BFCSV2MCAZUQMZ9LMMSQ|
|contract address|0x9dcaee215a9cbd1207f6d1351a930a804a269892|

```java
StatusConReturn statusConReturn = api.statusContract("IVKEWCXMOAWXG1DTYAGUZA", "0xd71e6c4910e517b8556fa3d3b7866eb2f6a7025f");
```

### 返回结果说明
返回的StatusConReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|Status|成功时为""，失败时为相应的错误信息|
|ctStatus|合约状态|

```json
{
    "code":0,
    "Status":"ok",
    "ctStatus":"normal"
}

```


-----

## 创建区块链账户

### 接口描述

通过传入token，创建账户地址。

### 参数说明

|参数|类型|说明|
|:---|:---|:---|
|token|string|授权令牌|


### 调用示例

|参数|值|
|:--|:--|
|token|BPJKDVCJNDOJ6EXLYTW_PQ|

```java
CreteAccountReturn creteAccountReturn = api.createAccount("BPJKDVCJNDOJ6EXLYTW_PQ");
```

### 返回结果说明
返回的CreteAccountReturn对象的属性：

|参数|说明|
|:--|:--|
|code|状态码0为成功，非零则为相应错误码|
|status|成功时为""，失败时为相应的错误信息|
|id|地址|
|time|创建时间|
|isDisabled|是否可用|
|appName|应用名称|

```json
{
    "code": 0,
    "status":"",
    "id": 3683 ,
    "address": "0x7ecc0ffcdd771be370b7cc748cc320d26cef9a55",
    "time": "2017-09-12 15:00:27",
    "isDisabled": false,
    "appName": "test"
}

```


## 错误码说明

|错误码|含义|
|:---|:---|
|1008|授权未通过|
|1009|非法参数|
|1010|查询异常|
|1011|appkey不存在|
|1012|不支持的查询类型|
|1013|合约编译异常|
|1014|账户私钥异常|
|1015|合约部署异常|
|1016|合约调用异常|
|1017|合约维护操作异常|
