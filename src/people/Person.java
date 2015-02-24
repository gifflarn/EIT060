package people;

import records.records;


public abstract class Person {

	protected String username;
	protected String hashPw;
	protected static String type;
	
	public Person(String username, String hashPw){
		this.username = username;
		this.hashPw = hashPw;
	}
	
	
	public records getRecords(){
		
		
		return null;
		
	}


	public abstract CharSequence data();
	
}
