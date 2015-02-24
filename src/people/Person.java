package people;



public abstract class Person {
	protected String username;
	protected String hashPw;
	
	protected Person(String username, String hashPw){
		this.username = username;
		this.hashPw = hashPw;
	}

	public String data() {
		return username + ":" + this.getClass().getSimpleName() + ":" + System.currentTimeMillis()/1000 + "\n";
	}
	
	public String toString(){
		return username;
	}
	
}
