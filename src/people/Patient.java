package people;

import java.util.ArrayList;

import logs.RecordEntry;

public class Patient extends Person {
	private ArrayList<RecordEntry> records;
	private ArrayList<Doctor> doctors;
	private ArrayList<Nurse> nurses;

	public Patient(String username, String password) {
		super(username, password);
		records = new ArrayList<RecordEntry>();
		doctors = new ArrayList<Doctor>();
		nurses = new ArrayList<Nurse>();
	}

	public void addDoctor(Doctor d) {
		doctors.add(d);
	}

	public void addNurse(Nurse n) {
		nurses.add(n);
	}

	/*
	 * public void addTreatment(Doctor d, Nurse n, String division, String
	 * data){ records.add(new Record(d, n, division, data)); }
	 */

	// public String getRecords(Patient p) {
	// if (this != p) {
	// return null;
	// }
	// return records.toString();
	// }
	//
	// public String getRecords(Nurse n) {
	// for (Record r : records) {
	// if (r.getNurse() == n || r.getDivision() == n.division)
	// return records.toString();
	// }
	//
	// return null;
	// }

	public boolean addRecord(Nurse n, RecordEntry record) {
		for (RecordEntry r : records) {
			if (r.getNurse() == n) {
				records.add(record);
				return true;
			}
		}
		return false;
	}

	public String getRecords(Person p) {
		switch (p.getClass().getName()) {
		case "Doctor":
			if (doctors.contains(p))
				return records.toString();

			for (RecordEntry r : records) {
				if (r.getDivision().equals(((Doctor) p).division))
					return records.toString();
			}

			break;

		case "Nurse":
			for (RecordEntry r : records) {
				if (r.getNurse() == p
						|| r.getDivision() == ((Nurse) p).division)
					return records.toString();
			}

			break;

		case "Patient":
			if (this != p) {
				return null;
			}
			return records.toString();

		default:
			return records.toString();

		}

		return records.toString();

	}

	// public String getRecords(Doctor doctor) {
	// if (doctors.contains(doctor))
	// return records.toString();
	//
	// for (Record r : records) {
	// if (r.getDivision().equals(doctor.division))
	// return records.toString();
	// }
	//
	// return null;
	// }

	public boolean addRecord(Person p, RecordEntry record) {
		switch (p.getClass().getSimpleName()) {
		case "Doctor":
			if (doctors.contains(p)) {
				records.add(record);
				return true;
			}

			for (RecordEntry r : records) {
				if (r.getDivision().equals(((Doctor) p).division)) {
					records.add(record);
					return true;
				}
			}
			return false;

		case "Nurse":
			for (RecordEntry r : records) {
				if (r.getNurse() == p) {
					records.add(record);
					return true;
				}
			}
			return false;
		}
		return false;

	}

	// public String getRecords(Government g) {
	// return records.toString();
	// }

}
