package logs;

import people.Doctor;
import people.Nurse;

public class RecordEntry {
	private Doctor d;
	private Nurse n;
	private String division;
	private String data;
	
	
	public RecordEntry(Doctor d, Nurse n, String division, String data) {
		this.d = d;
		this.n = n;
		this.division = division;
		this.data = data;	
	}
	
	public Doctor getDoctor() {
		return d;
	}
	
	public Nurse getNurse() {
		return n;
	}
	
	public String getDivision() {
		return division;
	}
	
	public String getData() {
		return data;
	}
	
	public String toString(){
		return d + ":" + n + ":" + division +":" + data;
	}
}
