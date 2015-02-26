package logs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import network.ServerConnectionHandler;

import database.Database;
import people.Doctor;
import people.Nurse;
import people.Patient;
import people.Person;

public class AuditLog {
	
	static String AUDIT_LOG_PATH = "audit_log";

	public AuditLog(){
		//saveToFile();
	}
	
	public static void saveToFile(Person p, Patient pat){
		FileWriter writer = null;
		try {
			writer = new FileWriter(AUDIT_LOG_PATH, true);
			writer.append(":" + p.data());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args){
		
		Database db = new Database();
		if(db.openConnection("db03", "db03", "joel")) {
			System.out.println("Connected established");			
		}
		
		Doctor harald = new Doctor("Harald", "12345", "lth");
		Nurse n = new Nurse("Lukas", "43434", "lth");
		Patient p = new Patient("Joel", "54545");

		System.out.println(db.createRecord(harald, p.getName(), n.getName(), "aids"));
		System.out.println(db.getRecords(harald, p.getName()));

	//	a.execute(p,harald,n,"lth","cancer");
		
		//RecordEntry r = new RecordEntry(harald,n,"lth", "cancer");
		//System.out.println(p.addRecord(harald, r));
		
		System.out.println(harald.data());
		
	//	saveToFile(harald, a, p);
	}
}
