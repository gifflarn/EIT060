package logs;

import java.sql.Date;

public class RecordEntry {
	private int recordId;
	private Date creationDate;
	private Date lastUpdateDate;
	private String patient;
	private String doctor;
	private String nurse;
	private String division;
	private String data;

	public RecordEntry(int recordId, Date creationDate, Date lastUpdateDate,
			String patient, String doctor, String nurse, String division,
			String data) {
		this.recordId = recordId;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
		this.patient = patient;
		this.doctor = doctor;
		this.nurse = nurse;
		this.division = division;
		this.data = data;
	}

	public int recordId() {
		return recordId;
	}

	public Date creationDate() {
		return creationDate;
	}

	public Date lastUpdateDate() {
		return lastUpdateDate;
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
	@Override
	public String toString() {
		return doctor + ":" + nurse + ":" + division + ":" + data;
	}

}
