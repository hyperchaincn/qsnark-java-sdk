contract Bank{
 struct Bill{
 bytes32 no;
 int amt;
 bytes32 billType;
 int issueDate;
 int dueDate;
 address drwr;
 address accptr;
 address pyee;
 address hodr;
 bytes32 state;
 address endr;
 address ende;
 }

 struct User{
 bytes32 nm;
 bytes32 cmId;
 bytes32 acct;
 }

 struct BillRecord{
 bytes32 no;
 uint8 busiType;
 bytes32 mark;
 bytes32 nm;
 bytes32 cmId;
 bytes32 acct;
 }



 mapping(bytes32=>BillRecord[]) allbillRecords;
 mapping(address=>bytes32[]) userBills;
 mapping(address=>User) users;
 mapping(address=>bytes32[]) unSignBills;
 mapping(address=>bytes32[]) signBills;
 mapping(bytes32=>Bill) allBills;


 function newUser(bytes32 cmId,bytes32 nm,bytes32 acct)returns(bool){
 if(users[msg.sender].cmId==0x0){
 users[msg.sender].nm = nm;
 users[msg.sender].acct = acct;
 users[msg.sender].cmId = cmId;
 return true;
 }else{
 return false;
 }

 }

 function queryUserInfoByAddress() returns(bytes32,bytes32,bytes32){
 User user = users[msg.sender];
 return (user.nm,user.cmId,user.acct);
 }
 function addBillRecord(bytes32 no,uint8 busiType,bytes32 mark,
 bytes32 nm,bytes32 cmId,bytes32 acct)internal{
 uint len = allbillRecords[no].length;
 allbillRecords[no].length ++;
 allbillRecords[no][len].no = no;
 allbillRecords[no][len].busiType = busiType;
 allbillRecords[no][len].mark = mark;
 allbillRecords[no][len].nm = nm;
 allbillRecords[no][len].cmId = cmId;
 allbillRecords[no][len].acct = acct;
 }

 function pubBillInfo(bytes32 no,int amt,bytes32 billType,int issueDate,int dueDate,address drwr,
 address accptr,address pyee,address hodr )returns(uint8,string){

 if(issueDate>=dueDate){
 return (1,"issueDate big then duedate");
 }else{
 if(billType != "AC01"&&billType!="AC02"){
 return (1,"billtype is not match");
 }
 if(drwr != msg.sender){
 return (1,"drwr is not match");
 }
 if(allBills[no].no!=0x0){
 return (1,"this bill pubed alrea");
 }

 Bill bill = allBills[no];
 userBills[hodr].push(no);
 bill.no = no;
 bill.amt = amt;
 bill.billType = billType;
 bill.issueDate = issueDate;
 bill.dueDate = dueDate;
 bill.drwr = drwr;
 bill.accptr = accptr;
 bill.pyee = pyee;
 bill.hodr = hodr;
 bill.state = "0000001";
 bill.endr = hodr;
 bill.endr = hodr;

 User user = users[msg.sender];
 addBillRecord(no,0,0x0,user.nm,user.cmId,user.acct);
 return (0,"pubBillInfo succeed");
 }

 }

 function queryMyBill()returns(bytes32[]){
 return userBills[msg.sender];
 }

 function endrRequst(bytes32 no,address ende)returns(uint8,bytes32){
 if(allBills[no].state == "0000002"){
 return (1,"has been endr");
 }else{
 if(allBills[no].hodr != msg.sender){
 return (1,"You don't has access");
 }
 allBills[no].state = "0000002";
 allBills[no].ende = ende;
 unSignBills[ende].push(no);

 User user = users[msg.sender];
 addBillRecord(no,1,0x0,user.nm,user.cmId,user.acct);
 return (0,"success");
 }

 }

 function queryMyUnBill()returns(bytes32[]){
 return unSignBills[msg.sender];

 }

 function queryBillInfo(bytes32 no) returns(bytes32,int,bytes32,int,int,bytes32,address,address,address,address){
 Bill bill=allBills[no];
 if(bill.drwr==msg.sender||bill.accptr==msg.sender||bill.pyee==msg.sender||bill.hodr==msg.sender){
 return (bill.no,bill.amt,bill.billType,bill.issueDate,bill.dueDate,bill.state,bill.drwr,bill.accptr,bill.pyee,bill.hodr);
 }
 }

 function queryBillHistInfo(bytes32 no)returns(bytes32,uint8[],bytes32[],bytes32[],bytes32[],bytes32[]){
 Bill bill=allBills[no];

 if(bill.drwr==msg.sender||bill.accptr==msg.sender||bill.pyee==msg.sender||bill.hodr==msg.sender){
 var (number,busiTypes,marks,nms,cmIds,accts)=getBillRecord(no);
 return (number,busiTypes,marks,nms,cmIds,accts);
 }
 }

 function getBillRecord(bytes32 no) internal returns(bytes32,uint8[] memory busiTypes,bytes32[] memory marks,
 bytes32[] memory nms,bytes32[] memory cmIds,bytes32[] memory accts){
 busiTypes = new uint8[](allbillRecords[no].length);
 marks = new bytes32[](allbillRecords[no].length);
 nms = new bytes32[](allbillRecords[no].length);
 cmIds = new bytes32[](allbillRecords[no].length);
 accts = new bytes32[](allbillRecords[no].length);
 for(uint i=0;i<allbillRecords[no].length;i++){
 BillRecord billRecord = allbillRecords[no][i];
 busiTypes[i] = billRecord.busiType;
 marks[i] = billRecord.mark;
 nms[i] = billRecord.nm;
 cmIds[i] = billRecord.cmId;
 accts[i] = billRecord.acct;
 }
 return (no,busiTypes,marks,nms,cmIds,accts);
 }

 function endrResponse(bytes32 no,bytes32 resp)returns(uint8,string){
 if(resp=="SU00"){
 if(allBills[no].ende != msg.sender){
 return (1,"You don't has access");
 }
 var (nm,cmid,acct)=queryUserInfoByAddress();

 deleteBill(unSignBills[msg.sender] ,no);
 deleteBill(userBills[allBills[no].hodr] ,no);

 allBills[no].state = "0000003";
 allBills[no].endr = msg.sender;
 allBills[no].hodr = msg.sender;

 userBills[msg.sender].push(no);
 addBillRecord(no,1,"SU00",nm,cmid,acct);

 return (0,"endrresponse succeed");
 }else if(resp == "SU01"){
 allBills[no].state = "0000002";
 var (nm1,cmid1,acct1) = queryUserInfoByAddress();
 addBillRecord(no,1,"SU01",nm1,cmid1,acct1);
 deleteBill(unSignBills[msg.sender],no);
 return (1,"endresponse failed");
 }
 }

 function deleteBill(bytes32[] storage a,bytes32 draftAddr)internal{
 uint position;
 for(uint i = 0;i<a.length;i++){
 if(a[i] == draftAddr){
 position = i;
 break;
 }
 position++;
 }

 if(position != a.length){
 a[position] = a[a.length-1];
 a.length = a.length-1;
 }
 }

}