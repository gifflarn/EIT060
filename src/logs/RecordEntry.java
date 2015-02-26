package logs;

import people.Doctor;
import people.Nurse;

public class RecordEntry {
	private int recordId;
	private String creationDate;
	private String lastUpdateDate;
	private String patient;
	private String doctor;
	private String nurse;
	private String division;
	private String data;

	public RecordEntry(int recordId, String creationDate,
			String lastUpdateDate, String patient, String doctor,
			String nurse, String division, String data) {
		this.recordId = recordId;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
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
