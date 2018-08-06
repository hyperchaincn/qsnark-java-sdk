contract MyContract{
function MutiReturns(uint256 a,bytes32 b,address c)returns(uint256 [],bytes32[],address[]){
    uint256[] aa;
    bytes32[] bb;
    address[] cc;
    aa.push(a);
    aa.push(a);
    bb.push(b);
    bb.push(b);
    cc.push(c);
    cc.push(c);
    return (aa,bb,cc);
    }
    }