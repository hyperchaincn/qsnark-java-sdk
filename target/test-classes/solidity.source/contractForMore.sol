contract Bank {

    address owner;

    bytes bankName;

    bytes32 bankID;


    address[] applicantConAddrs;

    address[] individualConAddrs;

    address[] cashedDraftAddrs;

    address[] draftOperations;

    function Bank(bytes _bankName,
        bytes32 _bankID) {
        owner = msg.sender;
        bankName = _bankName;
        bankID = _bankID;
    }


    function createApplicant(address bankConAddr,
        address companyAddr,
        bytes32 companyIdType,
        bytes32 companyIdNum,
        bytes companyName) returns(address) {
        Applicant applicant = new Applicant(bankConAddr, companyAddr, companyIdType, companyIdNum, companyName);
        applicantConAddrs.push(applicant);
        return applicant;
    }


    function createIndividual(address bankConAddr,
        address individualAddr,
        bytes32 id,
        bytes32 phoneNum,
        bytes32 userName,
        bytes32 idNum) returns(address) {
        Individual individual = new Individual(bankConAddr, individualAddr, id, phoneNum, userName, idNum);
        individualConAddrs.push(individual);
        return individual;
    }


    function addCashedDraftAddr(address _cashedDraftAddr) {
        cashedDraftAddrs.push(_cashedDraftAddr);
    }


    function addDraftOperation(address _draftOperation) {
        draftOperations.push(_draftOperation);
    }


    function deleteApplicant(address applicantConAddr) {
        uint position;
        for (uint i = 0; i < applicantConAddrs.length; i++) {
            if (applicantConAddrs[i] == applicantConAddr) {
                position = i;
                break;
            }
            position++;
        }
        if (position != applicantConAddrs.length) {
            applicantConAddrs[position] = applicantConAddrs[applicantConAddrs.length - 1];
            applicantConAddrs.length = applicantConAddrs.length - 1;
        }
    }

    function getOwner() constant returns(address) {
        return owner;
    }

    function setOwner(address _owner) {
        owner = _owner;
    }

    function getBankName() constant returns(bytes) {
        return bankName;
    }

    function setBankName(bytes _bankName) {
        bankName = _bankName;
    }

    function getBankID() constant returns(bytes32) {
        return bankID;
    }

    function setBankID(bytes32 _bankID) {
        bankID = _bankID;
    }

    function getApplicantConAddrs() constant returns(address[]) {
        return applicantConAddrs;
    }

    function setApplicantConAddrs(address[] _applicantConAddrs) {
        applicantConAddrs = _applicantConAddrs;
    }

    function getIndividualConAddrs() constant returns(address[]) {
        return individualConAddrs;
    }

    function setIndividualConAddrs(address[] _individualConAddrs) {
        individualConAddrs = _individualConAddrs;
    }

    function getCashedDraftAddrs() constant returns(address[]) {
        return cashedDraftAddrs;
    }

    function setCashedDraftAddrs(address[] _cashedDraftAddrs) {
        cashedDraftAddrs = _cashedDraftAddrs;
    }

    function getDraftOperations() constant returns(address[]) {
        return draftOperations;
    }

    function setDraftOperations(address[] _draftOperations) {
        draftOperations = _draftOperations;
    }
}

contract DraftStructs {
    struct DraftInfo {
        address applicantConAddr;
        address operatorConAddr;
        bytes32 draftNum;
        Draft.SignType signType;
        uint16 currencyType;
        uint faceValue;
        uint signTime;
        uint expiredTime;
        uint presentationStartTime;
        uint frozenDays;
    }
    DraftInfo draftInfo;

    function draftInfoMap(address draftAddr) {
        Draft draft = Draft(draftAddr);
        var (applicantConAddr, operatorConAddr, draftNum, signType, currencyType, faceValue, signTime, expiredTime, presentationStartTime, frozenDays) = draft.getDraftInfo();
        draftInfo.applicantConAddr = applicantConAddr;
        draftInfo.operatorConAddr = operatorConAddr;
        draftInfo.draftNum = draftNum;
        draftInfo.signType = signType;
        draftInfo.currencyType = currencyType;
        draftInfo.faceValue = faceValue;
        draftInfo.signTime = signTime;
        draftInfo.expiredTime = expiredTime;
        draftInfo.presentationStartTime = presentationStartTime;
        draftInfo.frozenDays = frozenDays;
    }
}

contract BasicOperations {

    address newDraftAddr;
    address newDraftOperation;
    bool flag;

    function deleteAddr(address[] storage a, address draftAddr) internal {
        uint position;
        for (uint i = 0; i < a.length; i++) {
            if (a[i] == draftAddr) {
                position = i;
                break;
            }
            position++;
        }
        if (position != a.length) {
            a[position] = a[a.length - 1];
            a.length = a.length - 1;
        }
    }

    function copyOperationAddr(address draftAddr, address newDraftAddr, address operation) {
        Draft oldDraft = Draft(draftAddr);
        Draft newDraft = Draft(newDraftAddr);
        uint length = oldDraft.getOperationsLen();
        for (uint i = 0; i < length; i++) {
            address temp = oldDraft.getOperationAddr(i);
            newDraft.addDraftOperation(temp);
        }
        oldDraft.addDraftOperation(operation);
        newDraft.addDraftOperation(operation);
    }

    function isSigned(address draftAddr) returns(bool) {
        Draft draft = Draft(draftAddr);
        if (draft.getDraftState() == Draft.DraftState.Signed) {
            return true;
        } else {
            return false;
        }
    }

    function isUnIssue(address draftAddr) returns(bool) {
        Draft draft = Draft(draftAddr);
        if (draft.getDraftState() == Draft.DraftState.UnIssue) {
            return true;
        } else {
            return false;
        }
    }


    function isPassExpiredTime(address draftAddr, uint currentTime) returns(bool) {
        Draft draft = Draft(draftAddr);
        if (draft.getExpiredTime() < currentTime) {
            return true;
        } else {
            return false;
        }
    }


    function isPassPresentationStartTime(address draftAddr, uint currentTime) returns(bool) {
        Draft draft = Draft(draftAddr);
        if (draft.getSignType() == Draft.SignType.PayAtSight) {
            return true;
        }
        if (draft.getPresentationStartTime() < currentTime) {
            return true;
        } else {
            return false;
        }
    }

    function setDraftAcceptBankNum(address draftAddr, bytes32 acceptBankNum) {
        Draft draft = Draft(draftAddr);
        draft.setAcceptBankNum(acceptBankNum);
    }


    function setDraftPresentationStartTime(address draftAddr, uint presentationStartTime) {
        Draft draft = Draft(draftAddr);
        draft.setPresentationStartTime(presentationStartTime);
    }
}

contract CashOperation is BasicOperations, DraftStructs {
    address bankConAddr;

    address[] tmpDraftAddrs;
    address[] signedDraftAddrs;
    address[] cashOperationAddrs;


    function createDraftAndOperation(bytes32 newDraftNum,
        bytes32 sequenceNum,
        address draftAddr,
        uint value,
        address fromConAddr,
        address toConAddr,
        uint operation1Time) returns(address, address) {
        newDraftAddr = new Draft(newDraftNum, draftInfo.signType, Draft.DraftState.Cashing, draftInfo.expiredTime, draftInfo.frozenDays, draftInfo.signTime, value, draftInfo.applicantConAddr, draftInfo.operatorConAddr, draftInfo.currencyType);
        tmpDraftAddrs.push(newDraftAddr);
        address draftOperation = createOperation(sequenceNum, draftAddr, newDraftAddr, value, fromConAddr, toConAddr, operation1Time);
        return (newDraftAddr, draftOperation);
    }


    function createOperation(bytes32 sequenceNum,
        address draftAddr,
        address newDraftAddr,
        uint value,
        address fromConAddr,
        address toConAddr,
        uint operation1Time) returns(address) {
        DraftOperation draftOperation = new DraftOperation(sequenceNum, fromConAddr, toConAddr, draftAddr, newDraftAddr, value, operation1Time, DraftOperation.OperationType.Cash);
        draftOperation.setTxState(DraftOperation.TxState.ToCharge);
        cashOperationAddrs.push(draftOperation);
        return draftOperation;
    }


    function cash(bytes32 newDraftNum,
        address draftAddr,
        uint value,
        address bankConAddr,
        bytes32 sequenceNum,
        uint operation1Time) returns(uint, address, address) {
        if (!isSigned(draftAddr)) {
            return (3, 0x0, 0x0);
        }

        if (!isPassPresentationStartTime(draftAddr, operation1Time)) {
            return (8, 0x0, 0x0);
        }

        Draft draft = Draft(draftAddr);
        draftInfoMap(draftAddr);
        address draftOperation;
        if (draftInfo.faceValue < value) {
            return (1, 0x0, 0x0);
        } else if (draftInfo.faceValue == value) {

            draft.setDraftState(Draft.DraftState.Cashing);
            draftOperation = createOperation(sequenceNum, draftAddr, draftAddr, value, draftInfo.operatorConAddr, bankConAddr, operation1Time);
        } else {

            draft.subFaceValue(value);
            (draftAddr, draftOperation) = createDraftAndOperation(newDraftNum, sequenceNum, draftAddr, value, draftInfo.operatorConAddr, bankConAddr, operation1Time);
            setDraftPresentationStartTime(draftAddr, draftInfo.presentationStartTime);
        }
        Bank bank = Bank(bankConAddr);
        bank.addDraftOperation(draftOperation);
        return (0, draftAddr, draftOperation);
    }


    function cashSuccess(address draftAddr,
        address newDraftAddr,
        address bankConAddr,
        uint operation3Time,
        address draftOperationAddr) returns(uint) {
        Draft newDraft = Draft(newDraftAddr);
        if (newDraft.getDraftState() != Draft.DraftState.Cashing) {
            return 4;
        }

        newDraft.changeOwner(bankConAddr, bankConAddr);

        newDraft.setDraftState(Draft.DraftState.Cashed);

        DraftOperation draftOperation = DraftOperation(draftOperationAddr);
        draftOperation.setOperation3Time(operation3Time);

        draftOperation.setTxState(DraftOperation.TxState.Success);

        if (draftAddr != newDraftAddr) {

            deleteAddr(tmpDraftAddrs, newDraftAddr);
            copyOperationAddr(draftAddr, newDraftAddr, draftOperation);
        } else {

            deleteAddr(signedDraftAddrs, newDraftAddr);
        }

        Bank bank = Bank(bankConAddr);
        bank.addCashedDraftAddr(newDraft);
        return 0;
    }

    function cashFail(address oldDraftAddr,
        address newDraftAddr,
        address draftOperationAddr,
        uint operation3Time) returns(uint) {
        Draft newDraft = Draft(newDraftAddr);
        if (newDraft.getDraftState() != Draft.DraftState.Cashing) {
            return 4;
        }
        Draft oldDraft = Draft(oldDraftAddr);
        if (oldDraftAddr == newDraftAddr) {

            oldDraft.setDraftState(Draft.DraftState.Signed);
        } else {

            deleteAddr(tmpDraftAddrs, newDraftAddr);
            newDraft.setDraftState(Draft.DraftState.Signed);
            signedDraftAddrs.push(newDraftAddr);
        }

        address operatorConAddr = oldDraft.getOperatorConAddr();
        address applicantConAddr = oldDraft.getApplicantConAddr();

        DraftOperation draftOperation = DraftOperation(draftOperationAddr);
        draftOperation.setOperation3Time(operation3Time);
        draftOperation.setTxState(DraftOperation.TxState.ChargeFail);
        return 0;
    }

    function addSignedDraft(address signedDraftAddr) {
        signedDraftAddrs.push(signedDraftAddr);
    }

    function deleteSignedDraft(address signedDraftAddr) {
        deleteAddr(signedDraftAddrs, signedDraftAddr);
    }

    function getBankConAddr() constant returns(address) {
        return bankConAddr;
    }

    function setBankConAddr(address _bankConAddr) {
        bankConAddr = _bankConAddr;
    }

    function getSignedDraftAddrs() constant returns(address[]) {
        return signedDraftAddrs;
    }

    function setSignedDraftAddrs(address[] _signedDraftAddrs) {
        signedDraftAddrs = _signedDraftAddrs;
    }

    function getCashOperationAddrs() constant returns(address[]) {
        return cashOperationAddrs;
    }

    function setCashOperationAddrs(address[] _cashOperationAddrs) {
        cashOperationAddrs = _cashOperationAddrs;
    }
}

contract Draft {
    enum SignType {
        PayAtSight,
        PayAtFixedDate
    }
    SignType signType;
    enum DraftState {
        Saved,
        UnIssue,
        IssueNotSigned,
        Issuing,
        Signed,
        TransferNotSigned,
        Cashing,
        Cashed,
        Invalid
    }
    DraftState draftState;
    bytes32 draftNum;
    bytes32 acceptBankNum;
    address applicantConAddr;
    address operatorConAddr;
    uint16 currencyType;
    uint faceValue;
    uint signTime;
    uint expiredTime;
    uint presentationStartTime;
    uint frozenDays = 0;
    address[] draftOperationAddrs;

    function Draft(bytes32 _draftNum,
        SignType _signType,
        DraftState _draftState,
        uint _expiredTime,
        uint _frozenDays,
        uint _signTime,
        uint _faceValue,
        address _applicanConAddr,
        address _operatorConAddr,
        uint16 _currencyType) {
        draftNum = _draftNum;
        signType = _signType;
        draftState = _draftState;
        expiredTime = _expiredTime;
        frozenDays = _frozenDays;
        signTime = _signTime;
        faceValue = _faceValue;
        applicantConAddr = _applicanConAddr;
        operatorConAddr = _operatorConAddr;
        currencyType = _currencyType;
    }

    function changeOwner(address _applicantConAddr,
        address _operatorConAddr) {
        applicantConAddr = _applicantConAddr;
        operatorConAddr = _operatorConAddr;
    }

    function subFaceValue(uint value) {
        if (faceValue < value) {
            throw;
        } else {
            faceValue = faceValue - value;
        }
    }

    function addFaceValue(uint value) {
        faceValue = faceValue + value;
    }

    function destroyDraft() {
        draftState = DraftState.Invalid;
    }

    function addDraftOperation(address draftOperationAddr) {
        draftOperationAddrs.push(draftOperationAddr);
    }

    function getDraftInfo() constant returns(address, address, bytes32, Draft.SignType, uint16, uint, uint, uint, uint, uint) {
        return (applicantConAddr, operatorConAddr, draftNum, signType, currencyType, faceValue, signTime, expiredTime, presentationStartTime, frozenDays);
    }

    function getOperationsLen() constant returns(uint) {
        return draftOperationAddrs.length;
    }

    function getOperationAddr(uint i) constant returns(address) {
        return draftOperationAddrs[i];
    }

    function getSignType() constant returns(SignType) {
        return signType;
    }

    function setSignType(SignType _signType) {
        signType = _signType;
    }

    function getDraftState() constant returns(DraftState) {
        return draftState;
    }

    function setDraftState(DraftState _draftState) {
        draftState = _draftState;
    }

    function getDraftNum() constant returns(bytes32) {
        return draftNum;
    }

    function setDraftNum(bytes32 _draftNum) {
        draftNum = _draftNum;
    }

    function getAcceptBankNum() constant returns(bytes32) {
        return acceptBankNum;
    }

    function setAcceptBankNum(bytes32 _acceptBankNum) {
        acceptBankNum = _acceptBankNum;
    }

    function getApplicantConAddr() constant returns(address) {
        return applicantConAddr;
    }

    function setApplicantConAddr(address _applicantConAddr) {
        applicantConAddr = _applicantConAddr;
    }

    function getOperatorConAddr() constant returns(address) {
        return operatorConAddr;
    }

    function setOperatorConAddr(address _operatorConAddr) {
        operatorConAddr = _operatorConAddr;
    }

    function getCurrencyType() constant returns(uint16) {
        return currencyType;
    }

    function setCurrencyType(uint16 _currencyType) {
        currencyType = _currencyType;
    }

    function getFaceValue() constant returns(uint) {
        return faceValue;
    }

    function setFaceValue(uint _faceValue) {
        faceValue = _faceValue;
    }

    function getSignTime() constant returns(uint) {
        return signTime;
    }

    function setSignTime(uint _signTime) {
        signTime = _signTime;
    }

    function getExpiredTime() constant returns(uint) {
        return expiredTime;
    }

    function setExpiredTime(uint _expiredTime) {
        expiredTime = _expiredTime;
    }

    function getPresentationStartTime() constant returns(uint) {
        return presentationStartTime;
    }

    function setPresentationStartTime(uint _operation1Time) {
        presentationStartTime = _operation1Time + frozenDays * 86400 * 1000;
    }

    function getFrozenDays() constant returns(uint) {
        return frozenDays;
    }

    function setFrozenDays(uint _frozenDays) {
        frozenDays = _frozenDays;
    }

    function getDraftOperationAddrs() constant returns(address[]) {
        return draftOperationAddrs;
    }

    function setDraftOperationAddrs(address[] _draftOperationAddrs) {
        draftOperationAddrs = _draftOperationAddrs;
    }
}

contract DraftOperation {
    bytes32 sequenceNum;
    address fromConAddr;
    address toConAddr;
    address draftAddr;
    address newDraftAddr;
    uint value;
    uint operation1Time;
    uint operation2Time;
    uint operation3Time;
    enum OperationType {
        Sign,
        Issue,
        Transfer,
        Cash
    }
    OperationType operationType;
    enum TxState {
        Success,
        ToSignature,
        ToCharge,
        SignatureFail,
        ChargeFail
    }
    TxState txState = TxState.ToSignature;

    function DraftOperation(bytes32 _sequenceNum,
        address _fromConAddr,
        address _toConAddr,
        address _draftAddr,
        address _newDraftAddr,
        uint _value,
        uint _operation1Time,
        OperationType _operationType) {
        sequenceNum = _sequenceNum;
        fromConAddr = _fromConAddr;
        toConAddr = _toConAddr;
        draftAddr = _draftAddr;
        newDraftAddr = _newDraftAddr;
        value = _value;
        operation1Time = _operation1Time;
        operationType = _operationType;
    }

    function getOperationInfo() constant returns(bytes32, address, address, address, address, uint, uint, uint, uint, OperationType) {
        return (sequenceNum, fromConAddr, toConAddr, draftAddr, newDraftAddr, value, operation1Time, operation2Time, operation3Time, operationType);
    }

    function getSequenceNum() constant returns(bytes32) {
        return sequenceNum;
    }

    function setSequenceNum(bytes32 _sequenceNum) {
        sequenceNum = _sequenceNum;
    }

    function getFromConAddr() constant returns(address) {
        return fromConAddr;
    }

    function setFromConAddr(address _fromConAddr) {
        fromConAddr = _fromConAddr;
    }

    function getToConAddr() constant returns(address) {
        return toConAddr;
    }

    function setToConAddr(address _toConAddr) {
        toConAddr = _toConAddr;
    }

    function getDraftAddr() constant returns(address) {
        return draftAddr;
    }

    function setDraftAddr(address _draftAddr) {
        draftAddr = _draftAddr;
    }

    function getNewDraftAddr() constant returns(address) {
        return newDraftAddr;
    }

    function setNewDraftAddr(address _newDraftAddr) {
        newDraftAddr = _newDraftAddr;
    }

    function getValue() constant returns(uint) {
        return value;
    }

    function setValue(uint _value) {
        value = _value;
    }

    function getOperation1Time() constant returns(uint) {
        return operation1Time;
    }

    function setOperation1Time(uint _operation1Time) {
        operation1Time = _operation1Time;
    }

    function getOperation2Time() constant returns(uint) {
        return operation2Time;
    }

    function setOperation2Time(uint _operation2Time) {
        operation2Time = _operation2Time;
    }

    function getOperation3Time() constant returns(uint) {
        return operation3Time;
    }

    function setOperation3Time(uint _operation3Time) {
        operation3Time = _operation3Time;
    }

    function getOperationType() constant returns(OperationType) {
        return operationType;
    }

    function setOperationType(OperationType _operationType) {
        operationType = _operationType;
    }

    function getTxState() constant returns(TxState) {
        return txState;
    }

    function setTxState(TxState _txState) {
        txState = _txState;
    }
}

contract UserInfo {
    bytes32 id;
    bytes32 userName;
    bytes32 phoneNum;
    bytes32 idNum;

    function UserInfo(bytes32 _id,
        bytes32 _userName,
        bytes32 _phoneNum, bytes32 _idNum) {
        id = _id;
        userName = _userName;
        phoneNum = _phoneNum;
        idNum = _idNum;
    }

    function getUserInfo() constant returns(bytes32, bytes32, bytes32, bytes32) {
        return (id, userName, phoneNum, idNum);
    }

    function setUserInfo(bytes32 _id,
        bytes32 _userName,
        bytes32 _phoneNum,
        bytes32 _idNum) {
        id = _id;
        userName = _userName;
        phoneNum = _phoneNum;
        idNum = _idNum;
    }
}

contract Individual is CashOperation {

    address owner;
    address userInfo;


    address[] toIssueDraftAddrs;

    address[] toSignatureDraftAddrs;


    address[] signOperationAddrs;
    address[] issueOperationAddrs;
    address[] signedOperationAddrs;
    address[] transferOperationAddrs;

    function Individual(address _bankConAddr,
        address individualAddr,
        bytes32 id,
        bytes32 phoneNum,
        bytes32 userName,
        bytes32 idNum) {
        owner = individualAddr;
        bankConAddr = _bankConAddr;
        userInfo = new UserInfo(id, userName, phoneNum, idNum);
    }


    function signDraft(bytes32 _draftNum,
        Draft.SignType _signType,
        uint _expiredTime,
        uint _frozenDays,
        uint _signTime,
        uint _value,
        bytes32 _sequenceNum,
        address _fromIndividualConAddr,
        address _toIndividualConAddr,
        uint16 _currencyType) returns(address, address) {

        Draft draft = new Draft(_draftNum, _signType, Draft.DraftState.UnIssue, _expiredTime, _frozenDays, _signTime, _value, _fromIndividualConAddr, _toIndividualConAddr, _currencyType);

        return createSignOperation(_sequenceNum, _fromIndividualConAddr, _toIndividualConAddr, draft, _value, _signTime);
    }

    function createSignOperation(bytes32 _sequenceNum,
        address _fromIndividualConAddr,
        address _toIndividualConAddr,
        address draftAddr,
        uint _value,
        uint _signTime) returns(address, address) {
        DraftOperation draftOperation = new DraftOperation(_sequenceNum, _fromIndividualConAddr, _toIndividualConAddr, draftAddr, draftAddr, _value, _signTime, DraftOperation.OperationType.Sign);
        draftOperation.setTxState(DraftOperation.TxState.Success);
        Draft draft = Draft(draftAddr);
        Individual toIndividual = Individual(_toIndividualConAddr);

        draft.addDraftOperation(draftOperation);

        signOperationAddrs.push(draftOperation);

        toIndividual.addToIssueDraft(draftAddr);
        return (draft, draftOperation);
    }

    function addToIssueDraft(address draft) {
        toIssueDraftAddrs.push(draft);
    }


    function issue(bytes32 newDraftNum,
        address draftAddr,
        address toAppConAddr,
        uint value,
        bytes32 sequenceNum,
        address fromIndividualConAddr,
        address toConAddr,
        uint operation1Time,
        bytes32 acceptBankNum) returns(uint, address, address) {

        if (isPassExpiredTime(draftAddr, operation1Time)) {
            return (9, 0x0, 0x0);
        }

        if (!isUnIssue(draftAddr)) {
            return (2, 0x0, 0x0);
        }

        (flag, newDraftAddr) = createNewDraft(Draft.DraftState.Issuing, newDraftNum, draftAddr, value);
        if (!flag) {
            return (1, 0x0, 0x0);
        }


        setDraftAcceptBankNum(newDraftAddr, acceptBankNum);
        return createIssueOperation(sequenceNum, fromIndividualConAddr, toConAddr, draftAddr, newDraftAddr, value, operation1Time);
    }

    function createIssueOperation(bytes32 sequenceNum,
        address fromIndividualConAddr,
        address toConAddr,
        address draftAddr,
        address newDraftAddr,
        uint value,
        uint operation1Time) returns(uint, address, address) {

        newDraftOperation = new DraftOperation(sequenceNum, fromIndividualConAddr, toConAddr, draftAddr, newDraftAddr, value, operation1Time, DraftOperation.OperationType.Issue);
        DraftOperation draftOperation = DraftOperation(newDraftOperation);

        draftOperation.setTxState(DraftOperation.TxState.ToCharge);
        issueOperationAddrs.push(newDraftOperation);
        if (draftAddr != newDraftAddr) {

            copyOperationAddr(draftAddr, newDraftAddr, newDraftOperation);
        }

        return (0, newDraftAddr, newDraftOperation);
    }


    function issueSuccess(address oldDraftAddr,
        address newDraftAdrr,
        address draftOperationAddr,
        address toAppConAddr,
        address toOperatorConAddr,
        uint operation3Time) returns(uint) {
        Draft newDraft = Draft(newDraftAdrr);
        if (newDraft.getDraftState() != Draft.DraftState.Issuing) {
            return 5;
        }

        newDraft.setDraftState(Draft.DraftState.Signed);
        newDraft.changeOwner(toAppConAddr, toOperatorConAddr);

        newDraft.setPresentationStartTime(operation3Time);
        if (oldDraftAddr == newDraftAddr) {

            deleteAddr(toIssueDraftAddrs, oldDraftAddr);
        }

        DraftOperation draftOperation = DraftOperation(draftOperationAddr);
        draftOperation.setTxState(DraftOperation.TxState.Success);
        draftOperation.setOperation3Time(operation3Time);

        deleteAddr(tmpDraftAddrs, newDraftAdrr);

        receiveSignedDraft(toAppConAddr, toOperatorConAddr, newDraftAdrr, draftOperation);
        return 0;
    }


    function issueFail(address draftAddr,
        address draftOperationAddr,
        uint operation3Time) returns(uint) {
        Draft draft = Draft(draftAddr);
        if (draft.getDraftState() != Draft.DraftState.Issuing) {
            return 5;
        }

        draft.setDraftState(Draft.DraftState.UnIssue);

        toIssueDraftAddrs.push(draftAddr);

        deleteAddr(tmpDraftAddrs, draftAddr);

        DraftOperation draftOperation = DraftOperation(draftOperationAddr);
        draftOperation.setTxState(DraftOperation.TxState.ChargeFail);
        draftOperation.setOperation3Time(operation3Time);
        return 0;
    }

    function changeDraftOwner(address draftAddr,
        address toAppConAddr,
        address toConAddr) {
        Draft draft = Draft(draftAddr);
        draft.changeOwner(toAppConAddr, toConAddr);
    }


    function transfer(bytes32 newDraftNum,
        address draftAddr,
        uint value,
        address toAppConAddr,
        bytes32 sequenceNum,
        address fromConAddr,
        address toConAddr,
        uint operationTime,
        bytes32 acceptBankNum) returns(uint, address, address) {
        if (!isSigned(draftAddr)) {
            return (3, 0x0, 0x0);
        }
        (flag, newDraftAddr) = createNewDraft(Draft.DraftState.Signed, newDraftNum, draftAddr, value);
        if (!flag) {
            return (1, 0x0, 0x0);
        }

        setDraftAcceptBankNum(newDraftAddr, acceptBankNum);
        changeDraftOwner(newDraftAddr, toAppConAddr, toAppConAddr);

        newDraftOperation = createTransferOperation(sequenceNum, fromConAddr, toConAddr, draftAddr, newDraftAddr, value, operationTime);

        receiveSignedDraft(toAppConAddr, toConAddr, newDraftAddr, newDraftOperation);
        return (0, newDraftAddr, newDraftOperation);
    }


    function receiveSignedDraft(address toAppConAddr,
        address toOperatorAddr,
        address newDraft,
        address newDraftOperation) {
        if (toAppConAddr == toOperatorAddr) {

            Individual individual = Individual(toOperatorAddr);
            individual.signedDraftFromIndividual(newDraft, newDraftOperation);
        } else {

            Operator operator = Operator(toOperatorAddr);
            operator.signedDraft(newDraft, newDraftOperation);
        }
    }



    function createTransferOperation(bytes32 sequenceNum,
        address fromConAddr,
        address toConAddr,
        address draftAddr,
        address newDraftAddr,
        uint value,
        uint operationTime) returns(address) {
        DraftOperation draftOperation = new DraftOperation(sequenceNum, fromConAddr, toConAddr, draftAddr, newDraftAddr, value, operationTime, DraftOperation.OperationType.Transfer);
        draftOperation.setTxState(DraftOperation.TxState.Success);
        transferOperationAddrs.push(draftOperation);
        if (draftAddr != newDraftAddr) {
            copyOperationAddr(draftAddr, newDraftAddr, draftOperation);
        }

        return draftOperation;
    }


    function createNewDraft(Draft.DraftState draftState,
        bytes32 newDraftNum,
        address draftAddr,
        uint value) internal returns(bool, address) {
        Draft draft = Draft(draftAddr);
        uint faceValue = draft.getFaceValue();
        if (faceValue < value) {
            return (false, 0x0);
        }

        if (faceValue == value) {

            if (draftState == Draft.DraftState.Signed) {
                deleteAddr(signedDraftAddrs, draftAddr);
            } else {
                draft.setDraftState(draftState);
            }
            return (true, draftAddr);
        } else {
            draftInfoMap(draftAddr);
            newDraftAddr = new Draft(newDraftNum, draftInfo.signType, draftState, draftInfo.expiredTime, draftInfo.frozenDays, draftInfo.signTime, value, draftInfo.applicantConAddr, draftInfo.operatorConAddr, draftInfo.currencyType);
            draft.subFaceValue(value);

            if (draftState == Draft.DraftState.Signed) {
                setDraftPresentationStartTime(newDraftAddr, draftInfo.presentationStartTime);
            }

            tmpDraftAddrs.push(newDraftAddr);
            return (true, newDraftAddr);
        }
    }


    function signedDraftFromIndividual(address newDraftAddr,
        address draftOperationAddr) {
        signedOperationAddrs.push(draftOperationAddr);
        signedDraftAddrs.push(newDraftAddr);
    }


    function signedDraftFromOperator(address draftAddr,
        address draftOperationAddr) {
        deleteToSignatureDraft(draftAddr);
        signedOperationAddrs.push(draftOperationAddr);
        signedDraftAddrs.push(draftAddr);
    }

    function getBills() constant returns(address[], address[], address[], address[]) {
        return (issueOperationAddrs, signedOperationAddrs, transferOperationAddrs, cashOperationAddrs);
    }

    function getDraftInfo(address draftAddr) constant returns(address, address, bytes32, Draft.SignType, uint16, uint, uint, uint, uint, uint) {
        Draft draft = Draft(draftAddr);
        return draft.getDraftInfo();
    }

    function addToSignatureDraftAddrs(address draftAddr) {
        toSignatureDraftAddrs.push(draftAddr);
    }

    function deleteToSignatureDraft(address draftAddr) {
        deleteAddr(toSignatureDraftAddrs, draftAddr);
    }

    function getOwner() constant returns(address) {
        return owner;
    }

    function setOwner(address _owner) {
        owner = _owner;
    }

    function getUserInfo() constant returns(address) {
        return userInfo;
    }

    function setUserInfo(address _userInfo) {
        userInfo = _userInfo;
    }

    function getToIssueDraftAddrs() constant returns(address[]) {
        return toIssueDraftAddrs;
    }

    function setToIssueDraftAddrs(address[] _toIssueDraftAddrs) {
        toIssueDraftAddrs = _toIssueDraftAddrs;
    }

    function getToSignatureDraftAddrs() constant returns(address[]) {
        return toSignatureDraftAddrs;
    }

    function setToSignatureDraftAddrs(address[] _toSignatureDraftAddrs) {
        toSignatureDraftAddrs = _toSignatureDraftAddrs;
    }

    function getSignOperationAddrs() constant returns(address[]) {
        return signOperationAddrs;
    }

    function setSignOperationAddrs(address[] _signOperationAddrs) {
        signOperationAddrs = _signOperationAddrs;
    }

    function getIssueOperationAddrs() constant returns(address[]) {
        return issueOperationAddrs;
    }

    function setIssueOperationAddrs(address[] _issueOperationAddrs) {
        issueOperationAddrs = _issueOperationAddrs;
    }

    function getSignedOperationAddrs() constant returns(address[]) {
        return signedOperationAddrs;
    }

    function setSignedOperationAddrs(address[] _signedOperationAddrs) {
        signedOperationAddrs = _signedOperationAddrs;
    }

    function getTransferOperationAddrs() constant returns(address[]) {
        return transferOperationAddrs;
    }

    function setTransferOperationAddrs(address[] _transferOperationAddrs) {
        transferOperationAddrs = _transferOperationAddrs;
    }
}

contract Operator is CashOperation {

    address companyAddr;

    address applicantConAddr;

    address operatorInfo;


    address[] toIssueDraftAddrs;

    address[] toSignatureDraftAddrs;


    address[] issueOperationAddrs;

    address[] signedOperationAddrs;

    address[] transferOperationAddrs;


    function Operator(address _bankConAddr,
        address _companyAddr,
        address _applicantConAddr,
        bytes32 _id,
        bytes32 _name,
        bytes32 _phoneNum,
        bytes32 _idNum) {
        companyAddr = _companyAddr;
        bankConAddr = _bankConAddr;
        applicantConAddr = _applicantConAddr;
        operatorInfo = new UserInfo(_id, _name, _phoneNum, _idNum);
    }


    function addToIssueDraft(address newDraftAddr) {
        toIssueDraftAddrs.push(newDraftAddr);
    }


    function signedDraft(address draftAddr,
        address draftOperationAddr) {
        DraftOperation draftOperation = DraftOperation(draftOperationAddr);

        signedDraftAddrs.push(draftAddr);
        signedOperationAddrs.push(draftOperationAddr);
    }


    function transfer(bytes32 newDraftNum,
        address draftAddr,
        uint value,
        address toApplicantAddr,
        bytes32 sequenceNum,
        address fromConAddr,
        address toConAddr,
        uint operation1Time,
        bytes32 acceptBankNum) returns(uint, address, address) {
        if (!isSigned(draftAddr)) {
            return (3, 0x0, 0x0);
        }

        (flag, newDraftAddr) = createNewDraft(Draft.DraftState.TransferNotSigned, newDraftNum, draftAddr, value, applicantConAddr, fromConAddr);
        if (!flag) {
            return (1, 0x0, 0x0);
        }

        setDraftAcceptBankNum(newDraftAddr, acceptBankNum);

        newDraftOperation = new DraftOperation(sequenceNum, fromConAddr, toConAddr, draftAddr, newDraftAddr, value, operation1Time, DraftOperation.OperationType.Transfer);

        transferOperationAddrs.push(newDraftOperation);
        tmpDraftAddrs.push(newDraftAddr);

        applicantAddOperation(newDraftOperation, DraftOperation.OperationType.Transfer);

        addToSignatureDraft(toApplicantAddr, toConAddr, newDraftAddr);
        return (0, newDraftAddr, newDraftOperation);
    }

    function applicantAddOperation(address draftOperation,
        DraftOperation.OperationType operationType) {
        Applicant applicant = Applicant(applicantConAddr);
        applicant.addTempOperationAddrs(draftOperation);
    }


    function addToSignatureDraft(address toApplicantAddr,
        address toConAddr,
        address newDraftAddr) {
        if (toApplicantAddr == toConAddr) {
            Individual toIndividual = Individual(toConAddr);
            toIndividual.addToSignatureDraftAddrs(newDraftAddr);
        } else {
            Operator toOperator = Operator(toConAddr);
            toOperator.addToSignatureDraftAddrs(newDraftAddr);
        }
    }


    function transferSuccess(address oldDraftAddr,
        address newDraftAddr) {
        if (oldDraftAddr == newDraftAddr) {

            deleteAddr(signedDraftAddrs, oldDraftAddr);
        }
        deleteAddr(tmpDraftAddrs, newDraftAddr);
    }


    function transferFailed(address oldDraftAddr,
        address newDraftAddr) {

        if (oldDraftAddr != newDraftAddr) {
            signedDraftAddrs.push(newDraftAddr);
        }
        deleteAddr(tmpDraftAddrs, newDraftAddr);
    }


    function issue(bytes32 newDraftNum,
        address draftAddr,
        uint value,
        address toApplicantAddr,
        bytes32 sequenceNum,
        address fromConAddr,
        address toConAddr,
        uint operation1Time,
        bytes32 acceptBankNum) returns(uint, address, address) {
        if (!isUnIssue(draftAddr)) {
            return (2, 0x0, 0x0);
        }

        if (isPassExpiredTime(draftAddr, operation1Time)) {
            return (9, 0x0, 0x0);
        }

        (flag, newDraftAddr) = createNewDraft(Draft.DraftState.IssueNotSigned, newDraftNum, draftAddr, value, applicantConAddr, fromConAddr);
        if (!flag) {
            return (1, 0x0, 0x0);
        }



        setDraftAcceptBankNum(newDraftAddr, acceptBankNum);

        newDraftOperation = new DraftOperation(sequenceNum, fromConAddr, toConAddr, draftAddr, newDraftAddr, value, operation1Time, DraftOperation.OperationType.Issue);
        issueOperationAddrs.push(newDraftOperation);

        tmpDraftAddrs.push(newDraftAddr);

        applicantAddOperation(newDraftOperation, DraftOperation.OperationType.Issue);

        addToSignatureDraft(toApplicantAddr, toConAddr, newDraftAddr);
        return (0, newDraftAddr, newDraftOperation);
    }


    function issueSuccess(address oldDraftAddr,
        address newDraftAddr,
        uint operation3Time) {
        if (oldDraftAddr == newDraftAddr) {

            deleteAddr(toIssueDraftAddrs, oldDraftAddr);
        }
        Draft newDraft = Draft(newDraftAddr);

        newDraft.setPresentationStartTime(operation3Time);
        deleteAddr(tmpDraftAddrs, newDraftAddr);
    }


    function issueFailed(address oldDraftAddr,
        address newDraftAddr) {
        if (oldDraftAddr != newDraftAddr) {

            toIssueDraftAddrs.push(newDraftAddr);
        }
        deleteAddr(tmpDraftAddrs, newDraftAddr);
    }



    function createNewDraft(Draft.DraftState draftState,
        bytes32 newDraftNum,
        address draftAddr,
        uint value,
        address toAppConAddr,
        address toConAddr) returns(bool, address) {
        Draft draft = Draft(draftAddr);
        uint balance = draft.getFaceValue();
        if (balance < value) {
            return (false, 0x0);
        }
        address newDraftAddr;
        if (balance == value) {
            newDraftAddr = draftAddr;
            draft.setDraftState(draftState);
        } else {
            draftInfoMap(draftAddr);
            newDraftAddr = new Draft(newDraftNum, draftInfo.signType, draftState, draftInfo.expiredTime, draftInfo.frozenDays, draftInfo.signTime, value, draftInfo.applicantConAddr, draftInfo.operatorConAddr, draftInfo.currencyType);
            draft.subFaceValue(value);

            if (draftState == Draft.DraftState.TransferNotSigned) {
                setDraftPresentationStartTime(newDraftAddr, draftInfo.presentationStartTime);
            }
        }
        return (true, newDraftAddr);
    }

    function addToSignatureDraftAddrs(address draftAddr) {
        toSignatureDraftAddrs.push(draftAddr);
    }

    function deleteToSignatureDraft(address draftAddr) {
        deleteAddr(toSignatureDraftAddrs, draftAddr);
    }

    function getBills() constant returns(address[], address[], address[], address[]) {
        return (issueOperationAddrs, signedOperationAddrs, transferOperationAddrs, cashOperationAddrs);
    }

    function getTempDrafts() constant returns(address[]) {
        return tmpDraftAddrs;
    }

    function getDrafts() constant returns(address[], address[]) {
        return (signedDraftAddrs, toIssueDraftAddrs);
    }

    function getCompanyAddr() constant returns(address) {
        return companyAddr;
    }

    function setCompanyAddr(address _companyAddr) {
        companyAddr = _companyAddr;
    }

    function getApplicantConAddr() constant returns(address) {
        return applicantConAddr;
    }

    function setApplicantConAddr(address _applicantConAddr) {
        applicantConAddr = _applicantConAddr;
    }

    function getOperatorInfo() constant returns(address) {
        return operatorInfo;
    }

    function setOperatorInfo(address _operatorInfo) {
        operatorInfo = _operatorInfo;
    }

    function getTmpDraftAddrs() constant returns(address[]) {
        return tmpDraftAddrs;
    }

    function setTmpDraftAddrs(address[] _tmpDraftAddrs) {
        tmpDraftAddrs = _tmpDraftAddrs;
    }

    function getToIssueDraftAddrs() constant returns(address[]) {
        return toIssueDraftAddrs;
    }

    function setToIssueDraftAddrs(address[] _toIssueDraftAddrs) {
        toIssueDraftAddrs = _toIssueDraftAddrs;
    }

    function getToSignatureDraftAddrs() constant returns(address[]) {
        return toSignatureDraftAddrs;
    }

    function setToSignatureDraftAddrs(address[] _toSignatureDraftAddrs) {
        toSignatureDraftAddrs = _toSignatureDraftAddrs;
    }

    function getIssueOperationAddrs() constant returns(address[]) {
        return issueOperationAddrs;
    }

    function setIssueOperationAddrs(address[] _issueOperationAddrs) {
        issueOperationAddrs = _issueOperationAddrs;
    }

    function getSignedOperationAddrs() constant returns(address[]) {
        return signedOperationAddrs;
    }

    function setSignedOperationAddrs(address[] _signedOperationAddrs) {
        signedOperationAddrs = _signedOperationAddrs;
    }

    function getTransferOperationAddrs() constant returns(address[]) {
        return transferOperationAddrs;
    }

    function setTransferOperationAddrs(address[] _transferOperationAddrs) {
        transferOperationAddrs = _transferOperationAddrs;
    }
}

contract Applicant is BasicOperations {

    address bankConAddr;

    address companyAddr;

    address[] operatorConAddrs;

    address[] toIssueDraftAddrs;

    address[] signOperationAddrs;

    address[] tempOperationAddrs;

    bytes32 companyIdType;

    bytes32 companyIdNum;

    bytes companyName;


    function Applicant(address _bankConAddr,
        address _companyAddr,
        bytes32 _companyIdType,
        bytes32 _companyIdNum,
        bytes _companyName) {
        bankConAddr = _bankConAddr;
        companyAddr = _companyAddr;
        companyIdType = _companyIdType;
        companyIdNum = _companyIdNum;
        companyName = _companyName;
    }

    function signDraft(bytes32 _draftNum,
        Draft.SignType _signType,
        uint _expiredTime,
        uint _frozenDays,
        uint _signTime,
        uint _value,
        bytes32 _sequenceNum,
        address _applicantConAddr,
        address _operatorConAddr,
        uint16 _currencyType) returns(address, address) {

        Draft draft = new Draft(_draftNum, _signType, Draft.DraftState.UnIssue, _expiredTime, _frozenDays, _signTime, _value, _applicantConAddr, _operatorConAddr, _currencyType);
        return createSignOperation(_sequenceNum, _applicantConAddr, _operatorConAddr, draft, _value, _signTime);
    }


    function createSignOperation(bytes32 _sequenceNum,
        address _applicantConAddr,
        address _operatorConAddr,
        address draft,
        uint _value,
        uint _signTime) returns(address, address) {
        DraftOperation draftOperation = new DraftOperation(_sequenceNum, _applicantConAddr, _operatorConAddr, draft, draft, _value, _signTime, DraftOperation.OperationType.Sign);
        Draft tempDraft = Draft(draft);
        draftOperation.setTxState(DraftOperation.TxState.Success);
        tempDraft.addDraftOperation(draftOperation);
        signOperationAddrs.push(draftOperation);
        toIssueDraftAddrs.push(draft);

        Operator operator = Operator(_operatorConAddr);
        operator.addToIssueDraft(draft);
        return (draft, draftOperation);
    }


    function changeOperator(address _oldOperatorConAddr,
        address _newOperatorConAddr,
        address _draftAddr) returns(uint) {
        if (!isSigned(_draftAddr)) {
            return 3;
        }
        Draft draft = Draft(_draftAddr);
        Operator newOperator = Operator(_newOperatorConAddr);
        Operator oldOperator = Operator(_oldOperatorConAddr);

        draft.setOperatorConAddr(_newOperatorConAddr);

        newOperator.addSignedDraft(draft);

        oldOperator.deleteSignedDraft(draft);
        return 0;
    }


    function isTransferNotSigned(address draftAddr) returns(bool) {
        Draft draft = Draft(draftAddr);
        if (draft.getDraftState() == Draft.DraftState.TransferNotSigned) {
            return true;
        } else {
            return false;
        }
    }


    function transferSuccess(address _oldDraftAddr,
        address _newDraftAddr,
        address _fromOperatorConAddr,
        address _toApplicantConAddr,
        address _toOperatorConAddr,
        address _draftOperationAddr,
        uint _operation3Time) returns(uint) {
        if (!isTransferNotSigned(_newDraftAddr)) {
            return 6;
        }

        updateDraftAndOperation(_oldDraftAddr, _newDraftAddr, _toApplicantConAddr, _toOperatorConAddr, _draftOperationAddr, _operation3Time, DraftOperation.OperationType.Transfer);

        Operator fromOperator = Operator(_fromOperatorConAddr);
        fromOperator.transferSuccess(_oldDraftAddr, _newDraftAddr);

        if (_toApplicantConAddr == _toOperatorConAddr) {

            Individual individual = Individual(_toOperatorConAddr);
            individual.signedDraftFromOperator(_newDraftAddr, _draftOperationAddr);
        } else {

            Operator toOperator = Operator(_toOperatorConAddr);
            toOperator.signedDraft(_newDraftAddr, _draftOperationAddr);
        }


        deleteToSignatureDraft(_toApplicantConAddr, _toOperatorConAddr, _newDraftAddr);
        return 0;
    }

    function updateDraftAndOperation(address _oldDraftAddr,
        address _newDraftAddr,
        address _newApplicantConAddr,
        address _newOperatorConAddr,
        address _draftOperationAddr,
        uint _operation3Time,
        DraftOperation.OperationType operationType) {
        Draft newDraft = Draft(_newDraftAddr);
        DraftOperation draftOperation = DraftOperation(_draftOperationAddr);

        draftOperation.setOperation3Time(_operation3Time);
        draftOperation.setTxState(DraftOperation.TxState.Success);

        deleteAddr(tempOperationAddrs, _draftOperationAddr);

        newDraft.setDraftState(Draft.DraftState.Signed);

        newDraft.changeOwner(_newApplicantConAddr, _newOperatorConAddr);

        if (_oldDraftAddr != _newDraftAddr) {
            copyOperationAddr(_oldDraftAddr, _newDraftAddr, _draftOperationAddr);
        }
    }


    function transferFail(address _oldDraftAddr,
        address _newDraftAddr,
        address _fromOperatorAddr,
        address _toApplicantConAddr,
        address _toOperatorConAddr,
        address _draftOperationAddr,
        uint _operation3Time) returns(uint) {
        if (!isTransferNotSigned(_newDraftAddr)) {
            return 6;
        }
        DraftOperation draftOperation = DraftOperation(_draftOperationAddr);
        Operator fromOperator = Operator(_fromOperatorAddr);

        draftOperation.setTxState(DraftOperation.TxState.SignatureFail);
        draftOperation.setOperation3Time(_operation3Time);
        if (_oldDraftAddr == _newDraftAddr) {
            Draft oldDraft = Draft(_oldDraftAddr);
            oldDraft.setDraftState(Draft.DraftState.Signed);
        } else {
            Draft newDraft = Draft(_newDraftAddr);
            newDraft.setDraftState(Draft.DraftState.Signed);
        }

        deleteAddr(tempOperationAddrs, _draftOperationAddr);

        deleteToSignatureDraft(_toApplicantConAddr, _toOperatorConAddr, _newDraftAddr);
        fromOperator.transferFailed(_oldDraftAddr, _newDraftAddr);
        return 0;
    }


    function isIssueNotSigned(address draftAddr) returns(bool) {
        Draft draft = Draft(draftAddr);
        if (draft.getDraftState() == Draft.DraftState.IssueNotSigned) {
            return true;
        } else {
            return false;
        }
    }



    function issueSuccess_Signature(address _draftAddr,
        address _draftOperationAddr,
        uint _operation2Time) returns(uint) {
        if (!isIssueNotSigned(_draftAddr)) {
            return 7;
        }
        Draft draft = Draft(_draftAddr);

        draft.setDraftState(Draft.DraftState.Issuing);
        DraftOperation draftOperation = DraftOperation(_draftOperationAddr);
        draftOperation.setOperation2Time(_operation2Time);

        draftOperation.setTxState(DraftOperation.TxState.ToCharge);
        return 0;
    }


    function isIssuing(address draftAddr) returns(bool) {
        Draft draft = Draft(draftAddr);
        if (draft.getDraftState() == Draft.DraftState.Issuing) {
            return true;
        } else {
            return false;
        }
    }


    function issueSuccess_PayMoney(address _oldDraftAddr,
        address _newDraftAddr,
        address _fromOperatorAddr,
        address _toApplicantConAddr,
        address _toOperatorConAddr,
        address _draftOperationAddr,
        uint _operation3Time) returns(uint) {
        if (!isIssuing(_newDraftAddr)) {
            return 5;
        }
        updateDraftAndOperation(_oldDraftAddr, _newDraftAddr, _toApplicantConAddr, _toOperatorConAddr, _draftOperationAddr, _operation3Time, DraftOperation.OperationType.Issue);

        Operator fromOperator = Operator(_fromOperatorAddr);

        fromOperator.issueSuccess(_oldDraftAddr, _newDraftAddr, _operation3Time);

        if (_toApplicantConAddr == _toOperatorConAddr) {

            Individual individual = Individual(_toOperatorConAddr);
            individual.signedDraftFromOperator(_newDraftAddr, _draftOperationAddr);
        } else {

            Operator toOperator = Operator(_toOperatorConAddr);
            toOperator.signedDraft(_newDraftAddr, _draftOperationAddr);
        }


        Draft newDraft = Draft(_newDraftAddr);
        newDraft.changeOwner(_toApplicantConAddr, _toOperatorConAddr);

        deleteToSignatureDraft(_toApplicantConAddr, _toOperatorConAddr, _newDraftAddr);
        return 0;
    }


    function issueFail(address _oldDraftAddr,
        address _newDraftAddr,
        address _fromOperatorAddr,
        address _toApplicantAddr,
        address _toOperatorAddr,
        address _draftOperationAddr,
        uint operationTime,
        DraftOperation.TxState draftOperationState) returns(uint) {
        if (!isIssuing(_newDraftAddr) && draftOperationState == DraftOperation.TxState.ChargeFail) {
            return 5;
        }
        if (!isIssueNotSigned(_newDraftAddr) && draftOperationState == DraftOperation.TxState.SignatureFail) {
            return 7;
        }
        DraftOperation draftOperation = DraftOperation(_draftOperationAddr);
        Operator fromOperator = Operator(_fromOperatorAddr);

        draftOperation.setTxState(draftOperationState);
        draftOperation.setOperation3Time(operationTime);
        if (_oldDraftAddr == _newDraftAddr) {

            Draft oldDraft = Draft(_oldDraftAddr);
            oldDraft.setDraftState(Draft.DraftState.UnIssue);
        } else {

            Draft newDraft = Draft(_newDraftAddr);
            newDraft.setDraftState(Draft.DraftState.UnIssue);
            toIssueDraftAddrs.push(_newDraftAddr);
        }
        deleteAddr(tempOperationAddrs, _draftOperationAddr);
        deleteToSignatureDraft(_toApplicantAddr, _toOperatorAddr, _newDraftAddr);
        fromOperator.issueFailed(_oldDraftAddr, _newDraftAddr);
        return 0;
    }



    function deleteToSignatureDraft(address _toApplicantAddr,
        address _toOperatorAddr,
        address _newDraftAddr) {
        if (_toApplicantAddr == _toOperatorAddr) {
            Individual individual = Individual(_toOperatorAddr);
            individual.deleteToSignatureDraft(_newDraftAddr);
        } else {
            Operator toOperator = Operator(_toOperatorAddr);
            toOperator.deleteToSignatureDraft(_newDraftAddr);
        }
    }


    function addOperator(address applicantConAddr,
        bytes32 id,
        bytes32 phoneNum,
        bytes32 idNum,
        bytes32 name) returns(address) {
        Operator operator = new Operator(bankConAddr, companyAddr, applicantConAddr, id, name, phoneNum, idNum);
        operatorConAddrs.push(operator);
        return operator;
    }


    function deleteOperator(address operatorConAddr) returns(bool) {
        deleteAddr(operatorConAddrs, operatorConAddr);
        return true;
    }

    function addTempOperationAddrs(address tempOperationAddr) {
        tempOperationAddrs.push(tempOperationAddr);
    }

    function deleteTempOperation(address tempOperationAddr) {
        deleteAddr(tempOperationAddrs, tempOperationAddr);
    }

    function getBankConAddr() constant returns(address) {
        return bankConAddr;
    }

    function setBankConAddr(address _bankConAddr) {
        bankConAddr = _bankConAddr;
    }

    function getCompanyAddr() constant returns(address) {
        return companyAddr;
    }

    function setCompanyAddr(address _companyAddr) {
        companyAddr = _companyAddr;
    }

    function getOperatorConAddrs() constant returns(address[]) {
        return operatorConAddrs;
    }

    function setOperatorConAddrs(address[] _operatorConAddrs) {
        operatorConAddrs = _operatorConAddrs;
    }

    function getToIssueDraftAddrs() constant returns(address[]) {
        return toIssueDraftAddrs;
    }

    function setToIssueDraftAddrs(address[] _toIssueDraftAddrs) {
        toIssueDraftAddrs = _toIssueDraftAddrs;
    }

    function getSignOperationAddrs() constant returns(address[]) {
        return signOperationAddrs;
    }

    function setSignOperationAddrs(address[] _signOperationAddrs) {
        signOperationAddrs = _signOperationAddrs;
    }

    function getTempOperationAddrs() constant returns(address[]) {
        return tempOperationAddrs;
    }

    function setTempOperationAddrs(address[] _tempOperationAddrs) {
        tempOperationAddrs = _tempOperationAddrs;
    }

    function getCompanyIdType() constant returns(bytes32) {
        return companyIdType;
    }

    function setCompanyIdType(bytes32 _companyIdType) {
        companyIdType = _companyIdType;
    }

    function getCompanyIdNum() constant returns(bytes32) {
        return companyIdNum;
    }

    function setCompanyIdNum(bytes32 _companyIdNum) {
        companyIdNum = _companyIdNum;
    }

    function getCompanyName() constant returns(bytes) {
        return companyName;
    }

    function setCompanyName(bytes _companyName) {
        companyName = _companyName;
    }
}
