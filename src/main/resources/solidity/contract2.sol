contract Bank{

  address owner;
  address[]  applicantConAddrs;
  address[] cashedDraftAddrs;

  modifier onlyOwner(){
       if(msg.sender!=owner)throw;
       _;
  }

  modifier onlyApplicant(){
     bool flag=false;
     for(uint i=0;i<applicantConAddrs.length;i++){
        if(msg.sender==applicantConAddrs[i]){
           flag=true;
           break;
        }
     }
     if(flag!=true) throw;
     _;
  }

  function Bank(){
     owner=msg.sender;
  }

    function addCashedDraftAddr(address draftAddr)onlyApplicant{
       cashedDraftAddrs.push(draftAddr);
    }

  function createApplicant(address companyAddr,bytes32 companyId,bytes companyName,bytes32 applicantId,bytes32 applicantName)onlyOwner{
      Applicant a=new Applicant(owner,companyAddr,companyId,companyName,applicantId,applicantName);
      applicantConAddrs.push(a);
  }

  function createPersonal(address bankConAddr,address personAddr,bytes32 id,bytes32 phoneNum,bytes32 userName,bytes32 idNum,bool isThisBank)onlyOwner{
     Personal p=new Personal(bankConAddr,personAddr,id,phoneNum,userName,idNum,isThisBank);
     applicantConAddrs.push(p);
  }

  function setPersonalIdentity(address personalConAddr,bool b)onlyOwner{
      Personal p=Personal(personalConAddr);
      p.setIsThisBank(b);
  }
}

contract DraftStructs{
     struct DraftInfo{
        bytes32 draftNum;
        Draft.SignType signType;
        uint balance;
        uint signTime;
        uint cashTime;
        uint validDays;
        uint frozenDays;
        uint autoCashDays;
    }
    DraftInfo draftInfo;
    
    function draftInfoMap(address draftAddr){
      Draft draft=Draft(draftAddr);
       var (draftNum,signType,balance,signTime,cashTime,validDays,frozenDays,autoCashDays)=draft.getDraftInfo();
       draftInfo.draftNum=draftNum;
       draftInfo.signType=signType;
       draftInfo.balance=balance;
       draftInfo.signTime=signTime;
       draftInfo.cashTime=cashTime;
       draftInfo.validDays=validDays;
       draftInfo.frozenDays=frozenDays;
       draftInfo.autoCashDays=autoCashDays;
     }
    
    struct DraftOperationInfo{
        bytes32 sequenceNum;
        address fromConAddr;
        address toConAddr;
        bytes32 fromId;
        bytes32 toId;
        address draftAddr;
        address newDraftAddr;
        uint value;
        uint operationTime;
        DraftOperation.OperationType operationType;
    } 
    DraftOperationInfo draftOperationInfo;
    
    function createOperationInfo(bytes32 sequenceNum,address fromConAddr,address toConAddr,bytes32 fromId,bytes32 toId,address draftAddr,address newDraftAddr,uint value,uint operationTime,DraftOperation.OperationType operationType){
        draftOperationInfo.sequenceNum=sequenceNum;
        draftOperationInfo.fromConAddr=fromConAddr;
        draftOperationInfo.toConAddr=toConAddr;
        draftOperationInfo.fromId=fromId;
        draftOperationInfo.toId=toId;
        draftOperationInfo.draftAddr=draftAddr;
        draftOperationInfo.newDraftAddr=newDraftAddr;
        draftOperationInfo.value=value;
        draftOperationInfo.operationTime=operationTime;
        draftOperationInfo.operationType=operationType;
    }
}

contract CommonOperations{

    function deleteAddr(address[] storage a,address draftAddr)internal{
       uint position;
       for(uint i=0;i<a.length;i++){
           if(a[i]==draftAddr){
              position=i;
              break;
           }
       }
       if(position!=a.length){
           a[position]=a[a.length-1];
           a.length=a.length-1;
       }
    }
    
    function copyOperationAddr(address draftAddr,address newDraftAddr,address operation){
        Draft oldDraft=Draft(draftAddr);
        Draft newDraft=Draft(newDraftAddr);
        uint length=oldDraft.getOperationsLen();
        for(uint i=0;i<length;i++){
           address temp=oldDraft.getOperationAddr(i);
           newDraft.addDraftOperation(temp);
        }
        oldDraft.addDraftOperation(operation);
        newDraft.addDraftOperation(operation);
    }

}

contract UserInfo{
  bytes32 id;
  bytes companyName;
  bytes32 userName;
  bytes32 phoneNum;
  bytes32 idNum;

    function UserInfo(bytes32 _id,bytes _companyName,bytes32 _userName,bytes32 _phoneNum,bytes32 _idNum){
       id=_id;
       companyName=_companyName;
       userName=_userName;
       phoneNum=_phoneNum;
       idNum=_idNum;
    }

    function getUserInfo()constant returns(bytes32,bytes,bytes32,bytes32,bytes32){
       return (id,companyName,userName,phoneNum,idNum);
    }
}



contract Applicant is CommonOperations,DraftStructs{

    address companyAddr; 
    address bankAddr; 
    address[] operatorConAddrs;
    address[] unIssueDraftAddrs;
    address[] signinDraftAddrs;
    address[] authorizedDraftAddrs;
    address[] signOperationAddrs;
    address[] authOperationAddrs;
    address[] cashOperationAddrs;
    address[] withdrawOperationAddrs;
    address[] operationAddrs; 
    address[] tempAddrs;
    address companyInfo;
    mapping(bytes32=>address) applicantInfos;

  modifier onlyOwner(){
     if(msg.sender!=companyAddr)throw;
     _;
  }

  modifier onlyOperator(){
     bool flag=false;
     for(uint i=0;i<operatorConAddrs.length;i++){
        if(msg.sender==operatorConAddrs[i]){
           flag=true;
           break;
        }
     }
     if(flag!=true) throw;
     _;
  }

  function Applicant(address _bankAddr,address _companyAddr,bytes32 companyId,bytes companyName,bytes32 applicantId,bytes32 applicantName){
     bankAddr=_bankAddr;
     companyAddr=_companyAddr;
     companyInfo=new UserInfo(companyId,companyName,"","","");
     addApplicant(applicantId,applicantName);
  }

  function deleteUnIssueDraftAddr(address draftAddr)onlyOperator{
     deleteAddr(unIssueDraftAddrs,draftAddr);
  }

  function deleteAutoDraftAddr(address draftAddr)onlyOperator{
     deleteAddr(authorizedDraftAddrs,draftAddr);
  }

  function addApplicant(bytes32 id,bytes32 userName)onlyOwner{
      UserInfo userInfo=new UserInfo(id,"",userName,"","");
      applicantInfos[id]=userInfo;
  }

  function getApplicantInfo(bytes32 id)constant returns(address){
      return applicantInfos[id];
  }
  
    function signinDraft(address draftAddr)onlyOperator{
       signinDraftAddrs.push(draftAddr);
    } 

  function signDraft(bytes32 draftNum,Draft.SignType signType,uint validDays,uint frozenDays,uint signTime,uint value,bytes32 sequenceNum,address applicantConAddr,address operatorConAddr,bytes32 applicantId)onlyOwner{
     Draft draft=new Draft(draftNum,signType,Draft.DraftState.UnIssue,validDays,frozenDays,360,signTime,value,applicantConAddr,operatorConAddr);
     unIssueDraftAddrs.push(draft);
     Operator operator=Operator(operatorConAddr);
     operator.addUnIssuedDraft(draft);
     DraftOperation operation=new DraftOperation(sequenceNum,applicantConAddr,operatorConAddr,applicantId,"",draft,0,value,signTime,DraftOperation.OperationType.Sign);
     signOperationAddrs.push(operation);
     operationAddrs.push(operation);
     draft.addDraftOperation(operation);
  }

  function authorizeTransfer(address applicantConAddr,address operatorConAddr,address draftAddr,bytes32 sequenceNum,bytes32 applicantId,uint operationTime)onlyOwner{
        Draft draft=Draft(draftAddr);
        uint balance=draft.getBalance();
        draft.setOperatorConAddr(operatorConAddr);
        DraftOperation operation=new DraftOperation(sequenceNum,applicantConAddr,operatorConAddr,applicantId,"",draftAddr,draftAddr,balance,operationTime,DraftOperation.OperationType.AuthTransfer);
        authOperationAddrs.push(operation);
        operationAddrs.push(operation);
        draft.addDraftOperation(operation);
        Operator operator=Operator(operatorConAddr);
        operator.addUnIssuedDraft(draftAddr);
        authorizedDraftAddrs.push(draftAddr);
        deleteAddr(signinDraftAddrs,draftAddr);
  }

  function withdrawAuth(address applicantConAddr,address draftAddr,bytes32 sequenceNum,bytes32 applicantId,uint operationTime)onlyOwner{
      Draft draft=Draft(draftAddr);
      address operatorConAddr= draft.getOperatorConAddr();
      draft.setState(Draft.DraftState.Signed);
      deleteAddr(authorizedDraftAddrs,draftAddr);
      signinDraftAddrs.push(draftAddr);
      uint balance=draft.getBalance();
      DraftOperation operation=new DraftOperation(sequenceNum,applicantConAddr,operatorConAddr,applicantId,"",draftAddr,draftAddr,balance,operationTime,DraftOperation.OperationType.WithdrawAuth);
      withdrawOperationAddrs.push(operation);
      operationAddrs.push(operation);
      draft.addDraftOperation(operation);
  }

    function cash(bytes32 newDraftNum,address draftAddr,address applicantConAddr,address operatorConAddr,uint value)onlyOwner returns(address){
       Draft draft=Draft(draftAddr);
       uint balance=draft.getBalance();
       if(balance<value){
          throw;
       }else if(balance==value){
          draft.setState(Draft.DraftState.Cashing);
       }else{
          draftInfoMap(draftAddr);
          draft.partUse(value);
          draftAddr=new Draft(newDraftNum,draftInfo.signType,Draft.DraftState.Cashing,draftInfo.validDays,draftInfo.frozenDays,draftInfo.autoCashDays,draftInfo.signTime,value,applicantConAddr,operatorConAddr);
       }
       return draftAddr;
    }

    function cashSuccess(address draftAddr,address newDraftAddr,uint value,address bankConAddr,bytes32 sequenceNum,address fromConAddr,bytes32 applicantId,uint operationTime){
       Draft draft=Draft(newDraftAddr);
       draft.totalUse(bankConAddr,bankConAddr);
       draft.setState(Draft.DraftState.Signed);
       DraftOperation draftOperation=new DraftOperation(sequenceNum,fromConAddr,bankConAddr,applicantId,"",draftAddr,newDraftAddr,value,operationTime,DraftOperation.OperationType.Cash);
       cashOperationAddrs.push(draftOperation);
       operationAddrs.push(draftOperation);
       if(draftAddr!=newDraftAddr){
           copyOperationAddr(draftAddr,newDraftAddr,draftOperation);
       }
       Bank bank=Bank(bankConAddr);
       bank.addCashedDraftAddr(newDraftAddr);
       deleteAddr(signinDraftAddrs,newDraftAddr);
    }

    function cashFail(address oldDraftAddr,address newDraftAddr){
      Draft oldDraft=Draft(oldDraftAddr);
      if(oldDraftAddr==newDraftAddr){
          oldDraft.setState(Draft.DraftState.Signed);
      }else{
          Draft newDraft=Draft(newDraftAddr);
          uint balance=newDraft.getBalance();
          newDraft.destroyDraft();
          oldDraft.addBalance(balance);
      }
    }

  function addOperator(bytes32 id,bytes32 phoneNum,bytes32 idNum,bytes32 name) onlyOwner{
      Operator operator=new Operator(companyAddr,id,name,phoneNum,idNum);
      operatorConAddrs.push(operator);
  }

  function deleteOperator(address operatorConAddr) onlyOwner returns(bool){
      Operator operator=Operator(operatorConAddr);
      if(operator.isCanBeDelete()==true){
         deleteAddr(operatorConAddrs,operatorConAddr);
         return true;
      }else{
         return false;
      }
      
  }

  function getDrafts()returns(address[]){
    delete tempAddrs;
    for(uint i=0;i<unIssueDraftAddrs.length;i++){
        tempAddrs.push(unIssueDraftAddrs[i]);
    }
    for(uint j=0;j<signinDraftAddrs.length;j++){
        tempAddrs.push(signinDraftAddrs[j]);
    }
    for(uint k=0;k<authorizedDraftAddrs.length;k++){
        tempAddrs.push(authorizedDraftAddrs[k]);
    }
    return tempAddrs;
  }

  function getBills()constant returns(address[]){
   return operationAddrs;
  }
}

contract Operator is CommonOperations,DraftStructs{
    address companyAddr;
    address applicantConAddr;

    address[] unIssuedDraftAddrs; 
    address[] unTransferdAddrs;
    address[] tempAddrs;
    
    address[] issueOperationAddrs;
    address[] signedOperationAddrs;
    address[] transferOperationAddrs;
    address[] operationAddrs;

    address operatorInfo;

    modifier onlyOwner(){
        if(msg.sender!=companyAddr) throw;
        _;
    }

    modifier onlyApplicant(){
       if(msg.sender!=applicantConAddr) throw;
       _;
    }

    function Operator(address _companyAddr,bytes32 id,bytes32 phoneNum,bytes32 idNum,bytes32 name){
        companyAddr=_companyAddr;
        applicantConAddr=msg.sender;
        operatorInfo=new UserInfo(id,"",name,phoneNum,idNum);
    }

    function addUnIssuedDraft(address draftAddr) onlyApplicant{
       unIssuedDraftAddrs.push(draftAddr);
    }

    function addUnTransferdDraft(address draftAddr) onlyApplicant{
       unTransferdAddrs.push(draftAddr);
    }

    function transfer(bytes32 newDraftNum,address draftAddr,uint value,address receiveAppConAddr,bytes32 sequenceNum,address fromConAddr,address toConAddr,uint operationTime)onlyOwner returns(address newDraftAddr){
       newDraftAddr=createDraft(newDraftNum,draftAddr,value,receiveAppConAddr,toConAddr);
       createOperationInfo(sequenceNum,fromConAddr,toConAddr,"","",draftAddr,newDraftAddr,value,operationTime,DraftOperation.OperationType.Transfer);
       createOperation();
       return newDraftAddr;
    }
    
    function createOperation()internal{
         DraftOperation draftOperation=new DraftOperation(draftOperationInfo.sequenceNum,draftOperationInfo.fromConAddr,draftOperationInfo.toConAddr,
         draftOperationInfo.fromId,draftOperationInfo.toId,draftOperationInfo.draftAddr,draftOperationInfo.newDraftAddr,draftOperationInfo.value,
         draftOperationInfo.operationTime,draftOperationInfo.operationType);
          transferOperationAddrs.push(draftOperation);
          operationAddrs.push(draftOperation);
          if(draftOperationInfo.draftAddr!=draftOperationInfo.newDraftAddr){
              copyOperationAddr(draftOperationInfo.draftAddr,draftOperationInfo.newDraftAddr,draftOperation);
          }
    }
    
    function createDraft(bytes32 newDraftNum,address draftAddr,uint value,address receiveAppConAddr,address toConAddr)internal returns(address){
        Draft  draft=Draft(draftAddr);
        uint balance=draft.getBalance();
        if(balance<value){
          throw;
        }
        address newDraftAddr;
        if(balance==value){
           Applicant applicant=Applicant(applicantConAddr);
           applicant.deleteAutoDraftAddr(draftAddr);
           draft.totalUse(receiveAppConAddr,toConAddr);
           deleteAddr(unTransferdAddrs,draftAddr);
           newDraftAddr=draftAddr;
        }else{
           draftInfoMap(draftAddr);
           newDraftAddr=new Draft(newDraftNum,draftInfo.signType,Draft.DraftState.Signed,draftInfo.validDays,draftInfo.frozenDays,draftInfo.autoCashDays,draftInfo.signTime,value,receiveAppConAddr,toConAddr);
           draft.partUse(value);
       }
       return newDraftAddr;
    }

    function issue(bytes32 newDraftNum,address draftAddr,address operatorConAddr,uint value)onlyOwner returns(address){
       Draft draft=Draft(draftAddr);
       uint balance=draft.getBalance();
       if(balance<value){
          throw;
       }else if(balance==value){
          draft.setState(Draft.DraftState.Issuing);
       }else{
          draftInfoMap(draftAddr);
          draft.partUse(value);
          draftAddr=new Draft(newDraftNum,draftInfo.signType,Draft.DraftState.Issuing,draftInfo.validDays,draftInfo.frozenDays,draftInfo.autoCashDays,draftInfo.signTime,value,applicantConAddr,operatorConAddr);
       }
       return draftAddr;
    }

    function issueSuccess(address draftAddr,address newDraftAddr,uint value,address receiveAppConAddr,bytes32 sequenceNum,address fromConAddr,address toConAddr,uint operationTime)onlyOwner{
       Draft draft=Draft(newDraftAddr);
       draft.totalUse(receiveAppConAddr,toConAddr);
       draft.setState(Draft.DraftState.Signed);
       DraftOperation draftOperation=new DraftOperation(sequenceNum,fromConAddr,toConAddr,"","",draftAddr,newDraftAddr,value,operationTime,DraftOperation.OperationType.Issue);
       issueOperationAddrs.push(draftOperation);
       operationAddrs.push(draftOperation);
       if(draftAddr!=newDraftAddr){
           copyOperationAddr(draftAddr,newDraftAddr,draftOperation);
       }else{
           Applicant applicant=Applicant(applicantConAddr);
           applicant.deleteUnIssueDraftAddr(draftAddr);
       }
       deleteAddr(unIssuedDraftAddrs,newDraftAddr);
    }

    function issueFail(address oldDraftAddr,address newDraftAddr)onlyOwner{
      Draft oldDraft=Draft(oldDraftAddr);
      if(oldDraftAddr==newDraftAddr){
          oldDraft.setState(Draft.DraftState.UnIssue);
      }else{
          Draft newDraft=Draft(newDraftAddr);
          uint balance=newDraft.getBalance();
          newDraft.destroyDraft();
          oldDraft.addBalance(balance);
      }
    }
    
    function addDraft(address draftAddr,address newDraftAddr,uint value,bytes32 sequenceNum,address fromConAddr,address toConAddr,uint operationTime)onlyOwner{
        DraftOperation draftOperation=new DraftOperation(sequenceNum,fromConAddr,toConAddr,"","",draftAddr,newDraftAddr,value,operationTime,DraftOperation.OperationType.Signed);
        signedOperationAddrs.push(draftOperation);
        operationAddrs.push(draftOperation);
        Applicant applicant=Applicant(applicantConAddr);
        applicant.signinDraft(newDraftAddr);
        Draft newDraft=Draft(newDraftAddr);
        newDraft.setSignedTime(operationTime);
    }

    function getDrafts()onlyOwner returns (address[]){
        delete tempAddrs;
        for(uint i=0;i<unIssuedDraftAddrs.length;i++){
           tempAddrs.push(unIssuedDraftAddrs[i]);
        }
        for(uint j=0;j<unTransferdAddrs.length;j++){
           tempAddrs.push(unTransferdAddrs[j]);
        }
        return tempAddrs;
    }

    function getBills()onlyOwner constant returns(address[]){
       return operationAddrs;
    }

    function getOperatorInfo()onlyOwner constant returns(address){
       return operatorInfo;
    }

    function isCanBeDelete()onlyOwner constant returns(bool){
        if(unIssuedDraftAddrs.length!=0 || unTransferdAddrs.length!=0){
           return false;
        }else{
           return true;
        }
    }
}

contract Personal is CommonOperations,DraftStructs{
    address owner;
    address bankConAddr;
    address[] signedDraftAddrs;

    address[] signedOperationAddrs;
    address[] transferOperationAddrs;
    address[] issueOperationAddrs;
    address[] cashOperationAddrs;
    address[] operationAddrs;

    bool isThisBank;
    address userInfo;

    modifier onlyOwner(){
       if(msg.sender!=owner)throw;
       _;
    }

    modifier onlyThisBank(){
       if(isThisBank!=true) throw;
       _;
    }

    modifier onlyBank(){
      if(msg.sender!=bankConAddr)throw;
      _;
    }

    function Personal(address _bankConAddr,address _owner,bytes32 id,bytes32 phoneNum,bytes32 userName,bytes32 idNum,bool _isThisBank){
       bankConAddr=_bankConAddr;
       owner=_owner;
       userInfo=new UserInfo(id,"",userName,phoneNum,idNum);
       isThisBank=_isThisBank;
    }

    function setIsThisBank(bool b)onlyBank{
       isThisBank=b;
    }

    function addDraft(address draftAddr,address newDraftAddr,uint value,bytes32 sequenceNum,address fromConAddr,address toConAddr,uint operationTime)onlyOwner{
         DraftOperation draftOperation=new DraftOperation(sequenceNum,fromConAddr,toConAddr,"","",draftAddr,newDraftAddr,value,operationTime,DraftOperation.OperationType.Signed);
         signedDraftAddrs.push(newDraftAddr);
         Draft newDraft=Draft(newDraftAddr);
         newDraft.setSignedTime(operationTime);
         signedOperationAddrs.push(draftOperation);
         operationAddrs.push(draftOperation);
    }

    function transfer(bytes32 newDraftNum,address draftAddr,uint value,address receiveAppConAddr,bytes32 sequenceNum,address fromConAddr,address toConAddr,uint operationTime)onlyOwner returns(address newDraftAddr){
       newDraftAddr=createDraft(newDraftNum,draftAddr,value,receiveAppConAddr,toConAddr);
       createOperationInfo(sequenceNum,fromConAddr,toConAddr,"","",draftAddr,newDraftAddr,value,operationTime,DraftOperation.OperationType.Transfer);
       createOperation();
       return newDraftAddr;
    }
    
    function createOperation()internal{
         DraftOperation draftOperation=new DraftOperation(draftOperationInfo.sequenceNum,draftOperationInfo.fromConAddr,draftOperationInfo.toConAddr,
         draftOperationInfo.fromId,draftOperationInfo.toId,draftOperationInfo.draftAddr,draftOperationInfo.newDraftAddr,draftOperationInfo.value,
         draftOperationInfo.operationTime,draftOperationInfo.operationType);
          transferOperationAddrs.push(draftOperation);
          operationAddrs.push(draftOperation);
          if(draftOperationInfo.draftAddr!=draftOperationInfo.newDraftAddr){
              copyOperationAddr(draftOperationInfo.draftAddr,draftOperationInfo.newDraftAddr,draftOperation);
          }
    }
    
    function createDraft(bytes32 newDraftNum,address draftAddr,uint value,address receiveAppConAddr,address toConAddr)internal returns(address){
        Draft  draft=Draft(draftAddr);
        draftInfoMap(draftAddr);
        uint balance=draft.getBalance();
        if(balance<value){
          throw;
        }
        address newDraftAddr;
        if(balance==value){
           draft.totalUse(receiveAppConAddr,toConAddr);
           deleteAddr(signedDraftAddrs,draftAddr);
           newDraftAddr=draftAddr;
        }else{
           newDraftAddr=new Draft(newDraftNum,draftInfo.signType,Draft.DraftState.Signed,draftInfo.validDays,draftInfo.frozenDays,draftInfo.autoCashDays,draftInfo.signTime,value,receiveAppConAddr,toConAddr);
           draft.partUse(value);
       }
       return newDraftAddr;
    }
     
     
    function issue(bytes32 draftNum,Draft.SignType signType,uint value,uint frozenDays,uint signTime,address personalConAddr)onlyOwner onlyThisBank returns(address){
        Draft draft=new Draft(draftNum,signType,Draft.DraftState.Issuing,0,frozenDays,360,signTime,value,personalConAddr,personalConAddr);
        return draft;
    }

    function issueSuccess(address draftAddr,uint value,address receiveAppConAddr,bytes32 sequenceNum,address fromConAddr,address toConAddr,uint operationTime)onlyOwner onlyThisBank{
       Draft draft=Draft(draftAddr);
       draft.setState(Draft.DraftState.Signed);
       DraftOperation draftOperation=new DraftOperation(sequenceNum,fromConAddr,toConAddr,"","",draftAddr,draftAddr,value,operationTime,DraftOperation.OperationType.Issue);
       issueOperationAddrs.push(draftOperation);
       operationAddrs.push(draftOperation);
       draft.addDraftOperation(draftOperation);
       draft.totalUse(receiveAppConAddr,toConAddr);
    }

    function issueFail(address draftAddr)onlyOwner onlyThisBank{
       Draft draft=Draft(draftAddr);
       draft.destroyDraft();
    }

    function cash(bytes32 newDraftNum,address draftAddr,uint value,address personalConAddr)onlyOwner onlyThisBank returns(address){
       Draft draft=Draft(draftAddr);
       uint balance=draft.getBalance();
       if(balance<value){
          throw;
       }else if(balance==value){
          draft.setState(Draft.DraftState.Cashing);
       }else{
          draftInfoMap(draftAddr);
          draft.partUse(value);
          draftAddr=new Draft(newDraftNum,draftInfo.signType,Draft.DraftState.Cashing,draftInfo.validDays,draftInfo.frozenDays,draftInfo.autoCashDays,draftInfo.signTime,value,personalConAddr,personalConAddr);
       }
       return draftAddr;
    }

    function cashSuccess(address draftAddr,address newDraftAddr,uint value,address bankConAddr,bytes32 sequenceNum,address fromConAddr,uint operationTime)onlyOwner onlyThisBank{
       Draft draft=Draft(newDraftAddr);
       draft.totalUse(bankConAddr,bankConAddr);
       draft.setState(Draft.DraftState.Signed);
       DraftOperation draftOperation=new DraftOperation(sequenceNum,fromConAddr,bankConAddr,"","",draftAddr,newDraftAddr,value,operationTime,DraftOperation.OperationType.Cash);
       cashOperationAddrs.push(draftOperation);
       operationAddrs.push(draftOperation);
       if(draftAddr!=newDraftAddr){
           copyOperationAddr(draftAddr,newDraftAddr,draftOperation);
       }
       deleteAddr(signedDraftAddrs,newDraftAddr);
       Bank bank=Bank(bankConAddr);
       bank.addCashedDraftAddr(newDraftAddr);
    }

    function cashFail(address oldDraftAddr,address newDraftAddr){
      Draft oldDraft=Draft(oldDraftAddr);
      if(oldDraftAddr==newDraftAddr){
          oldDraft.setState(Draft.DraftState.Signed);
      }else{
          Draft newDraft=Draft(newDraftAddr);
          uint balance=newDraft.getBalance();
          newDraft.destroyDraft();
          oldDraft.addBalance(balance);
      }
    }

    function getSignedDraftAddrs()onlyOwner constant returns(address[]){
       return signedDraftAddrs;
    }

    function getBills()onlyOwner constant returns(address[]){
       return operationAddrs;
    }

    function getUserInfo()onlyOwner constant returns(address){
      return userInfo;
    }

}

contract Draft{
    bytes32 draftNum;
    address applicantConAddr;
    address operatorConAddr;
  enum SignType{PayAtSight,PayAtFixedDate}
  SignType signType;
    uint faceValue;
    uint balance;

    uint signTime;
    uint signedTime;
    uint cashTime;

  enum DraftState{UnIssue,Signed,UnTransfer,Cashed,Issuing,Cashing}
  DraftState draftState;

  uint validDays=60;
  uint frozenDays=0; 
  uint autoCashDays=360;

    address[] draftOperationAddrs;

  modifier isFreezen(){
     if(signType==SignType.PayAtFixedDate){
        if(frozenDays!=0){
              throw;
        }
     }
     _;
  }

  modifier onlyApplicant(){
      if(msg.sender!=applicantConAddr)throw;
      _;
  }

  modifier onlyOperator(){
      if(msg.sender!=operatorConAddr)throw;
      _;
  }

  modifier appOrOpera(){
      if(msg.sender!=applicantConAddr && msg.sender!=operatorConAddr) throw;
      _;
  }

  function Draft(bytes32 _draftNum,SignType _signType,DraftState _draftState,uint _validDays,uint _frozenDays,uint autoCashedDays,uint _signTime,uint _faceValue,address _applicanConAddr,address _operatorConAddr){
      draftNum=_draftNum;
      signType=_signType;
      draftState=_draftState;
      validDays=_validDays;
      frozenDays=_frozenDays;
      signTime=_signTime;
      faceValue=_faceValue;
      balance=_faceValue;
      applicantConAddr=_applicanConAddr;
      operatorConAddr=_operatorConAddr;
  }

  function getOperationsLen()appOrOpera constant returns(uint){
     return draftOperationAddrs.length;
  }

    function getOperationAddr(uint i)appOrOpera constant returns(address){
       return draftOperationAddrs[i];
    }

  function setSignedTime(uint _signedTime) onlyOperator {
      signedTime=_signedTime;
  }

  function totalUse(address _applicantConAddr,address _operatorConAddr) onlyOperator{
      applicantConAddr=_applicantConAddr;
      operatorConAddr=_operatorConAddr;
  }

  function partUse(uint value) onlyOperator{
     if(balance<value){
        throw;
     }else{
        balance=balance-value;
     }
  }

  function setState(DraftState _state) appOrOpera{
     draftState=_state;
  }

  function updateValidAndFrozen() onlyApplicant{
      if(validDays>0){
          validDays=validDays-1;
          if(validDays<=0){
             if(draftState==DraftState.UnIssue){
              selfdestruct(operatorConAddr);
             }
          }
      }
      if(frozenDays>0){
          frozenDays=frozenDays-1;
      }
      if(autoCashDays>0){
          autoCashDays=autoCashDays-1;
      }
  }

  function addBalance(uint value)onlyOperator{
     balance=balance+value;
  }

  function destroyDraft() appOrOpera {
      selfdestruct(operatorConAddr);
  }

  function addDraftOperation(address draftOperationAddr)appOrOpera{
      draftOperationAddrs.push(draftOperationAddr);
  }

    function getBalance()appOrOpera constant returns(uint){
        return balance;
    }

    function getDraftInfo()appOrOpera constant returns(bytes32,Draft.SignType,uint,uint,uint,uint,uint,uint){
       return (draftNum,signType,balance,signTime,cashTime,validDays,frozenDays,autoCashDays);
    }

    function getOperatorConAddr()onlyApplicant constant returns(address){
       return operatorConAddr;
    }

    function setOperatorConAddr(address _operatorConAddr)onlyApplicant{
        operatorConAddr=_operatorConAddr;
    }
}

contract DraftOperation{
    bytes32 sequenceNum;
    address fromConAddr;
    address toConAddr;
    bytes32 fromId;
    bytes32 toId;
    address draftAddr;
    address newDraftAddr;
    uint value;
    uint operationTime;
    enum OperationType{Sign,Issue,AuthTransfer,WithdrawAuth,Transfer,Signed,Cash}
    OperationType operationType;

    function DraftOperation(bytes32 _sequenceNum,address _fromConAddr,address _toConAddr,bytes32 _fromId,bytes32 _toId,address _draftAddr,address _newDraftAddr,uint _value,uint _operationTime,OperationType _operationType){
        sequenceNum=_sequenceNum;
        fromConAddr=_fromConAddr;
        toConAddr=_toConAddr;
        fromId=_fromId;
        toId=_toId;
        draftAddr=_draftAddr;
        newDraftAddr=_newDraftAddr;
        value=_value;
        operationTime=_operationTime;
        operationType=_operationType;
    }

    function getOperationInfo()constant returns(bytes32,address,address,bytes32,bytes32,address,address,uint,uint,OperationType) {
        return (sequenceNum,fromConAddr,toConAddr,fromId,toId,draftAddr,newDraftAddr,value,operationTime,operationType);
    }
}

