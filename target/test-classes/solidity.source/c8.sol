contract MyContract{
    function MutiReturns(uint256 a,bytes32 b,address c)returns(uint256 [],bytes32,address[]){
        uint256[] aa;
        address[] cc;
        aa.push(a);
        aa.push(a);
        cc.push(c);
        return (aa,b,cc);
    }
}