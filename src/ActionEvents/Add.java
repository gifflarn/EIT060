package ActionEvents;

import people.Patient;
import people.Person;

public class Add extends Action{
	public Add(Patient p) {
		super(p);
	}
	
	@Override
	public CharSequence data(Patient p) {
		return "Added new entry:" + p.toString();
	}

	@Override
	public void execute(Person p) {
		// TODO Auto-generated method stub
		
	}

}
