package people;

import java.util.ArrayList;

import logs.Record;


public abstract class Person {
	protected String username;
	protected String hashPw;
	
	public Person(String username, String hashPw){
		this.username = username;
		this.hashPw = hashPw;
	}
	
	
//	public abstract ArrayList<Record> retrieveRecords(Patient p);


	public abstract CharSequence data();
	
	public String toString(){
		return username;
	}
	
}
