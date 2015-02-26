package ActionEvents;

import people.Patient;
import people.Person;

public class Read extends Action{

	public Read(Person p) {
		super(p);
	}

	@Override
	public CharSequence data(Patient p) {
		return "READ_ENTRY:" + p.toString();
	}

	@Override
	public void execute(Patient p) {
		p.getRecords(this.p); //måste returnera
	}

}
