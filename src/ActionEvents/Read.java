package ActionEvents;

import people.Patient;
import people.Person;

public class Read extends Action{

	public Read(Patient p) {
		super(p);
	}

	@Override
	public CharSequence data(Patient p) {
		return "Read the entry:" + p.toString();
	}

	@Override
	public void execute(Person p) {
		// TODO Auto-generated method stub
		
	}

}
