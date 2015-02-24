package people;


public class Nurse extends Employee {

	public Nurse(String username, String password) {
		super(username, password);
	}

	@Override
	public CharSequence data() {
		return username + ":Nurse:" + System.currentTimeMillis()/1000 + "\n";
	}

}
