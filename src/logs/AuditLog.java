package logs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
		
		Doctor joel = new Doctor("Joel Pålsson", "12345", "lth");
		Nurse n = new Nurse("Lukas", "43434", "lth");
		Patient p = new Patient("Harald Nordgren", "54545");

		//System.out.println(db.createRecord(joel, p.getName(), n.getName(), "aids2"));
		ArrayList<RecordEntry> list = db.getRecords(joel, p.getName());
		
		System.out.println();
		for (RecordEntry r : list)
			System.out.println(r.getData());
		System.out.println();
		
		//System.out.println(db.getRecords(joel, p.getName()));
		
		//RecordEntry r = new RecordEntry(joel,n,"lth", "cancer");
		//System.out.println(p.addRecord(joel, r));
		
		System.out.println(joel.data());
		
	//	saveToFile(harald, a, p);
	}
}
