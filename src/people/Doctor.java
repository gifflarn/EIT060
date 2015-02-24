package people;


public class Doctor extends Employee{
	
	public Doctor(String username, String hashPw) {
		super(username, hashPw);
	}


	@Override
	public String data() {
		return username + ":Doctor:" + System.currentTimeMillis()/1000 + "\n";
	}

}
