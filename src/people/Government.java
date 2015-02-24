package people;


public class Government extends Person {
	
	
	public Government(String username, String hashPw) {
		super(username, hashPw);
	}

	@Override
	public CharSequence data() {
		return username + ":Government:" + System.currentTimeMillis()/1000 + "\n";
	}

}
