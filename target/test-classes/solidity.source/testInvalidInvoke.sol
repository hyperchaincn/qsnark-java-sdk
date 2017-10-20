contract TestContractor{
    int length = 0;
    modifier justForTest(){
        length = 2; throw; _;
    }
    function TestContractor(){

    }
    function getLength()justForTest returns(int){
        return length;
    }
}