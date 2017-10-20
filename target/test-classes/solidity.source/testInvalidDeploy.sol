contract TestContractor{
    int length = 0;
    modifier justForTest(){
        length = 2; throw; _;
    }
    function TestContractor()justForTest{

    }
    function getLength() returns(int){
        return length;
    }
}