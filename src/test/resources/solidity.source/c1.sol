contract Bank{
 struct Bill{
 bytes32 no;
 int amt;
 bytes4 billType;
 int issueDate;
 int dueDate;
 address drwr;
 address accptr;
 address pyee;
 address hodr;
 bytes8 state;
 address endr;
 address ende;
 }

 struct User{
 bytes32 nm;
 bytes32 cmId;
 bytes32 acct;
 // address userAddress;
 }

 struct BillRecord{
 bytes32 no;
 uint8 busiType;
 bytes4 mark;
 bytes32 nm;
 bytes32 cmId;
 bytes32 acct;
 }



 mapping(bytes32=>BillRecord[])allbillRecords;
 mapping(address=>bytes32[])userBills;
 mapping(address=>User) users;
 mapping(address=>bytes32[]) unSignBills;
 mapping(address=>bytes32[]) signBills;
 mapping(bytes32=>Bill)allBills;


 function newUser(bytes32 cmId,bytes32 nm,bytes32 acct)returns(bool){
 users[msg.sender].nm=nm;
 users[msg.sender].acct=acct;
 users[msg.sender].cmId=cmId;
 return true;
 }

 function queryUserInfoByAddress() returns(bytes32,bytes32,bytes32){
 User user=users[msg.sender];
 return (user.nm,user.cmId,user.acct);
 }
 function addBillRecord(bytes32 no,uint8 busiType,bytes4 mark,
 bytes32 nm,bytes32 cmId,bytes32 acct){
 uint len = allbillRecords[no].length;
 allbillRecords[no].length ++;
 allbillRecords[no][len].no = no;
 allbillRecords[no][len].busiType =busiType;
 allbillRecords[no][len].mark = mark;
 allbillRecords[no][len].nm =nm;
 allbillRecords[no][len].cmId = cmId;
 allbillRecords[no][len].acct = acct;
 }

 function pubBillInfo(bytes32 no,int amt,bytes4 billType,int issueDate,int dueDate,address drwr,
 address accptr,address pyee,address hodr )returns(uint8,string){

 if(issueDate>=dueDate){
 return (1,"issueDate big then duedate");
 }else{
 if(billType!="AC01"&&billType!="AC02"){
 return (1,"billtype is not match");
 }

 Bill bill=allBills[no];
 userBills[msg.sender].push(no);
 bill.no=no;
 bill.amt=amt;
 bill.billType=billType;
 bill.issueDate=issueDate;
 bill.drwr = drwr;
 bill.accptr=accptr;
 bill.pyee=pyee;
 bill.hodr=hodr;
 bill.state = "0000001";


 User user = users[msg.sender];
 addBillRecord(no,0,"",user.nm,user.cmId,user.acct);
 return (0,"pubBillInfo succeed");
 }

 }

 function queryMyBill()returns(bytes32[]){
 return userBills[msg.sender];
 }

 function endrRequst(bytes32 no,address ende)returns(uint8,bytes32){
 if(allBills[no].state=="0000002"){
 return (1,"has been endr");
 }else{
 if(allBills[no].hodr != msg.sender){
 return (1,"You don't has access");
 }
 allBills[no].state = "0000002";
 allBills[no].endr=msg.sender;
 allBills[no].ende=ende;
 unSignBills[ende].push(no);

 User user = users[msg.sender];
 addBillRecord(no,1,"",user.nm,user.cmId,user.acct);
 return (0,"success");
 }

 }

 function queryMyUnBill()returns(bytes32[]){
 return unSignBills[msg.sender];

 }

 function queryBillInfo(bytes32 no) returns(bytes32,int,bytes4,int,int,bytes8,address,address,address,address){
 Bill bill=allBills[no];
 if(bill.drwr==msg.sender){
 return (bill.no,bill.amt,bill.billType,bill.issueDate,bill.dueDate,bill.state,bill.drwr,bill.accptr,bill.pyee,bill.hodr);
 }
 }

 function queryBillHistInfo(bytes32 no)returns(bytes32,uint8[],bytes4[],bytes32[],bytes32[],bytes32[]){
 Bill bill=allBills[no];

 if(bill.drwr==msg.sender||bill.accptr==msg.sender||bill.pyee==msg.sender||bill.hodr==msg.sender){
 var (number,busiTypes,marks,nms,cmIds,accts)=getBillRecord(no);
 return (number,busiTypes,marks,nms,cmIds,accts);
 }
 }

 function getBillRecord(bytes32 no)returns(bytes32,uint8[],bytes4[],bytes32[],bytes32[],bytes32[]){
 uint8[] busiTypes;
 bytes4[] marks;
 bytes32[] nms;
 bytes32[] cmIds;
 bytes32[] accts;
 for(uint i=0;i<allbillRecords[no].length;i++){
 BillRecord billRecord = allbillRecords[no][i];
 busiTypes.push(billRecord.busiType);
 marks.push(billRecord.mark);
 nms.push(billRecord.nm);
 cmIds.push(billRecord.cmId);
 accts.push(billRecord.acct);
 }
 return (no,busiTypes,marks,nms,cmIds,accts);
 }

 function endrResponse(bytes32 no,bytes4 resp)returns(uint8,string){
 if(resp=="SU00"){
 if(allBills[no].ende != msg.sender){
 return (1,"You don't has access");
 }
 var (nm,cmid,acct)=queryUserInfoByAddress();

 allBills[no].state = "0000003";
 allBills[no].endr = msg.sender;

 addBillRecord(no,1,"SU00",nm,cmid,acct);
 deleteAddr(unSignBills[msg.sender] ,no);
 deleteAddr(userBills[allBills[no].endr] ,no);
 userBills[msg.sender].push(no);

 return (0,"endrresponse succeed");
 }else if(resp=="SU01"){
 allBills[no].state="0000002";
 var (nm1,cmid1,acct1)=queryUserInfoByAddress();
 addBillRecord(no,1,"SU01",nm1,cmid1,acct1);
 deleteAddr(unSignBills[msg.sender],no);
 return (1,"endresponse failed");
 }
 }

 function deleteAddr(bytes32[] storage a,bytes32 draftAddr)internal{
 uint position;
 for(uint i=0;i<a.length;i++){
 if(a[i]==draftAddr){
 position=i;
 break;
 }
 position++;
 }

 if(position!=a.length){
 a[position]=a[a.length-1];
 a.length=a.length-1;
 }
 }
}