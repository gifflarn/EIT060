package ActionEvents;

import people.Patient;
import people.Person;

public abstract class Action {
	protected Person p;
	public Action(Person p){
		this.p=p;
	}
	public abstract CharSequence data(Patient p);
	
	public abstract void execute(Patient p);
}
