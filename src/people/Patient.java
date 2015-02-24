package people;


public class Patient extends Person{
	
	public Patient(String username, String password) {
		super(username, password);
	}


	@Override
	public CharSequence data() {
		return username + ":Patient:" + System.currentTimeMillis()/1000 + "\n";
	}

}
