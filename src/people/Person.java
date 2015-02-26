package people;



public abstract class Person {
	protected String username;
	protected String hashPw;
	protected String fullName;
	
	protected Person(String username, String hashPw){
		this.username = username;
		this.hashPw = hashPw;
	}
	
	protected Person(String username, String hashPw, String fullName){
		this.username = username;
		this.hashPw = hashPw;
		this.fullName = fullName;
	}

	public String data() {
		return username + ":" + this.getClass().getSimpleName() + ":" + System.currentTimeMillis()/1000 + "\n";
	}
	
	public String toString(){
		return username;
	}

	public String getUsername() {
		return username;
	}

	public String getHashPw() {
		return hashPw;
	}

	public String getFullName() {
		return fullName;
	}
}
