contract Test{
uint aa;
function  test(uint a,uint b)  returns(uint){
    a=a*b;
    a=a+b;
    a=a-b;
    a=a/b;
    a=a%b;
    a=a**2;
    a+=b;
    a-=b;
    a*=b;
    a/=b;
    a%=b;
    a|=b;
    a&=b;
    a^=b;
    a=a^b;
    a++;
    a--;
    aa=4;
    a=a+aa;
    return a;
    }
}