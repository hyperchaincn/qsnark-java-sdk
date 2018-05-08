contract SimpleBank{
	struct User{
		uint256 id;
		bytes32 name;
	}


	mapping (uint256 => User) private accounts;
	uint256[] userlist;

	function newUser(User u) internal{
		userlist.push(u.id);
		accounts[u.id] = u;
    }
	function SimpleBank() {
		User memory u1 = User(1,"name1");
		User memory u2 = User(2,"name2");
		User memory u3 = User(3,"name3");
		newUser(u1);
		newUser(u2);
		newUser(u3);

	}

	function getUsers() returns (uint256[]){
		return userlist;
	}

	function getUserInfo(uint256 id) returns (uint256,bytes32){
		if (accounts[id].id != 0){
			return (accounts[id].id,accounts[id].name);
		}else{
			return (0,0x0);
		}
	}
	function testSub(uint256 a,uint256 b) returns (uint256){
    		return b-a;
    	}
}
