package people;

import java.util.ArrayList;

import logs.Record;


public class Patient extends Person{
	private ArrayList<Record> records;
	private ArrayList<Doctor> doctors;
	private ArrayList<Nurse> nurses;
	
	public Patient(String username, String password) {
		super(username, password);
		records = new ArrayList<Record>();
		doctors = new ArrayList<Doctor>();
		nurses = new ArrayList<Nurse>();
	}
	public void addDoctor(Doctor d) {
		doctors.add(d);
	}
	
	public void addNurse(Nurse n){
		nurses.add(n);
	}

	/*public void addTreatment(Doctor d, Nurse n, String division, String data){
		records.add(new Record(d, n, division, data));
	}*/

	public String getRecords(Patient p) {
		if(this != p){
			return null;
		}
		return records.toString();
	}
	
	public String getRecords(Nurse n){
		for(Record r : records) {
			if (r.getNurse() == n || r.getDivision() == n.division)
				return records.toString();
		}
		
		return null;
	}	
	
	public boolean addRecord(Nurse n, Record record) {
		for(Record r : records) {
			if (r.getNurse() == n) {
				records.add(record);
				return true;
			}
		}
		return false;
	}
	
	public String getRecords(Doctor doctor){
		if (doctors.contains(doctor))
			return records.toString();
		
		for(Record r : records) {
			if (r.getDivision().equals(doctor.division))
				return records.toString();
		}
		
		return null;
	}
	
	public boolean addRecord(Doctor doctor, Record record) {
		if (doctors.contains(doctor)) {
			records.add(record);
			return true;
		}
		
		for(Record r : records) {
			if (r.getDivision().equals(doctor.division)) {
				records.add(record);
				return true;
			}
		}
		return false;
	}
	
	public String getRecords(Government g){
		return records.toString();
	}

}
