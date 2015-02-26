package ActionEvents;

import people.Patient;
import people.Person;

public class Remove extends Action{

	public Remove(Person p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CharSequence data(Patient p) {
		return "REMOVED_ENTRY:" + p.toString();
	}

	@Override
	public void execute(Patient p) {
		p.getRecords(this.p);
	}

}
