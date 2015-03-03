package people;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public abstract class Person {
	protected String username;
	protected String fullName;
	
	protected Person(String username){
		this.username = username;
	}
	
	protected Person(String username, String fullName){
		this.username = username;
		this.fullName = fullName;
	}

	public String data() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return username + ":" + this.getClass().getSimpleName() + ":" + dateFormat.format(date) + "\n";
	}
	
	public String getName(){
		return username;
	}

	public String getUsername() {
		return username;
	}

	public String getFullName() {
		return fullName;
	}
}
