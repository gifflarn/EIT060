package ActionEvents;

import people.Patient;
import people.Person;

public class Remove extends Action{

	public Remove(Patient p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CharSequence data(Patient p) {
		return "Read the entry:" + p.toString();
	}

	@Override
	public void execute(Person p) {
	//	if()
	}

}
