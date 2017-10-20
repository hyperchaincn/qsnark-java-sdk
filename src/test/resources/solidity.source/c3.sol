contract MyContract{
                    function mreturns(uint a,bytes32 b,address c)returns(uint,bytes32[],address){
                        bytes32[] bb;
                        bb.push(b);
                        bb.push(b);
                        bb.push(bb);
                        return (a,bb,c);
                    }
                }