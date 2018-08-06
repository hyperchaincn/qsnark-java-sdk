contract SimpleBank{
	struct User{
		uint256 id;
		bytes32 name;
		int balance;
	}


	mapping (uint256 => User) private accounts;
	uint256[] userlist;

	function newUser(User u) internal{
		userlist.push(u.id);
		accounts[u.id] = u;
	}
	function SimpleBank() {
		User memory u1 = User(1,"name1",100);
		User memory u2 = User(2,"name2",1000);
		User memory u3 = User(3,"name3",1002);
		newUser(u1);
		newUser(u2);
		newUser(u3);

	}

	function getUsers() returns (uint256[]){
		return userlist;
	}

	function getUserInfo(uint256 id) returns (uint256,bytes32,int){
		if (accounts[id].id != 0){
			return (accounts[id].id,accounts[id].name,accounts[id].balance);
		}else{
			return (0,0x0,0);
		}
	}
}