package ActionEvents;

import logs.RecordEntry;
import people.Doctor;
import people.Nurse;
import people.Patient;
import people.Person;

public class Add extends Action{
	public Add(Person p) {
		super(p);
	}
	
	@Override
	public CharSequence data(Patient p) {
		return "ADDED_ENTRY:" + p.toString();
	}

	@Override
	public void execute(Patient p) {
		p.addRecord(this.p, new RecordEntry(null, null, null, null));
		
	}
	
	public void execute(Patient p, Doctor d, Nurse n, String division, String data) {
		System.out.println(p.addRecord(d, new RecordEntry(d, n, division, data)));
	}

}
