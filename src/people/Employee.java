package people;


public abstract class Employee extends Person{
	
	protected String division;
	
	public Employee(String username, String hashPw, String division) {
		super(username, hashPw);
		this.division = division;
	}
	
	String getDivision() {
		return division;
	}
	
}
