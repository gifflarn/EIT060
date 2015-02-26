package logs;

import people.Doctor;
import people.Nurse;

public class RecordEntry {
	// private String date;
	private String patient;
	private String doctor;
	private String nurse;
	private String division;
	private String data;

	public RecordEntry(String patient, String doctor, String nurse,
			String division, String data) {
		this.patient = patient;
		this.doctor = doctor;
		this.nurse = nurse;
		this.division = division;
		this.data = data;
	}

	public String getPatient() {
		return patient;
	}

	public String getDoctor() {
		return doctor;
	}

	public String getNurse() {
		return nurse;
	}

	public String getDivision() {
		return division;
	}

	public String getData() {
		return data;
	}

	// public String toString(){
	// return d + ":" + n + ":" + division +":" + data;
	// }
	
}
