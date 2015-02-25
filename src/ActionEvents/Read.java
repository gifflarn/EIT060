package ActionEvents;

import people.Patient;
import people.Person;

public class Read extends Action{

	public Read(Person p) {
		super(p);
	}

	@Override
	public CharSequence data(Patient p) {
		return "Read the entry:" + p.toString();
	}

	@Override
	public void execute(Patient p) {
		p.getRecords(this.p);
	}

}
