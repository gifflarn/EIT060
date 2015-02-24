package people;

import java.util.ArrayList;

import logs.Record;


public class Doctor extends Employee{
	
	public Doctor(String username, String hashPw, String division) {
		super(username, hashPw, division);
	}


	@Override
	public String data() {
		return username + ":Doctor:" + System.currentTimeMillis()/1000 + "\n";
	}



}
