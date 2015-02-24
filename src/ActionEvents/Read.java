package ActionEvents;

import people.Patient;

public class Read extends Action{

	public Read(Patient p) {
		super(p);
	}

	@Override
	public CharSequence data(Patient p) {
		return "Read the entry:" + p.toString();
	}

	@Override
	public void execute(Patient p) {
		// TODO Auto-generated method stub
		
	}

}
