package ActionEvents;

import logs.Record;
import people.Patient;
import people.Person;

public class Add extends Action{
	public Add(Person p) {
		super(p);
	}
	
	@Override
	public CharSequence data(Patient p) {
		return "Added new entry:" + p.toString();
	}

	@Override
	public void execute(Patient p) {
		p.addRecord(this.p, new Record(null, null, null, null));
		
	}

}
