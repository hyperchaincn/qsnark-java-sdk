contract SimulateBankUpgrade{
     address owner;
     bytes32 bankName;
     uint bankNum;
     bool isInvalid;
     mapping(address => uint) public accounts;
     function SimulateBank( bytes32 _bankName,uint _bankNum,bool _isInvalid){
         bankName = _bankName;
         bankNum = _bankNum;
         isInvalid = _isInvalid;
         owner = msg.sender;
     }
     function issue(address addr,uint number) returns (bool){
         if(msg.sender==owner){
             accounts[addr] = accounts[addr] + number;
             return true;
         }
         return false;
     }
     function reset(address addr) returns (bool){
         if(msg.sender==owner){
             accounts[addr] = 0;
             return true;
         }
         return false;
     }
     function transferValue(address addr1,address addr2,uint amount) returns (bool){
         if(accounts[addr1] >= amount){
             accounts[addr1] = accounts[addr1] - amount;
             accounts[addr2] = accounts[addr2] + amount;
             return true;
         }
         return false;
     }
     function getAccountBalance(address addr) returns(uint){
         return accounts[addr];
     }
 }