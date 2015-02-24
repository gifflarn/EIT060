package ActionEvents;

import people.Patient;

public abstract class Action {
	protected Patient p;
	public Action(Patient p){
		this.p=p;
	}
	public abstract CharSequence data(Patient p);
	
	public abstract void execute(Patient p);
}
