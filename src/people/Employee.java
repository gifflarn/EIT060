package people;


public abstract class Employee extends Person{
	
	protected String division;
	
	public Employee(String username, String division) {
		super(username);
		this.division = division;
	}
	
	public String getDivision() {
		return division;
	}
	
}
