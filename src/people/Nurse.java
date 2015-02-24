package people;

import java.util.ArrayList;

import logs.Record;


public class Nurse extends Employee {

	public Nurse(String username, String password, String division) {
		super(username, password, division);
	}

	@Override
	public CharSequence data() {
		return username + ":Nurse:" + System.currentTimeMillis()/1000 + "\n";
	}


}
