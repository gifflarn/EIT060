package logs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import people.Doctor;
import people.Nurse;
import people.Patient;
import people.Person;
import ActionEvents.Action;
import ActionEvents.Add;

public class AuditLog {
	
	static String AUDIT_LOG_PATH = "audit_log";

	public AuditLog(){
		//saveToFile();
	}
	
	public static void saveToFile(Person p, Action a, Patient pat){
		FileWriter writer = null;
		try {
			writer = new FileWriter(AUDIT_LOG_PATH, true);
			writer.append(a.data(pat) + ":" + p.data());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args){
		
		Doctor harald = new Doctor("Harald", "12345", "lth");
		Nurse n = new Nurse("Lukas", "43434", "lth");
		Patient p = new Patient("Joel", "54545");
		p.addDoctor(harald);

		
		Add a = new Add(p);
		a.execute(p,harald,n,"lth","cancer");
		
		//RecordEntry r = new RecordEntry(harald,n,"lth", "cancer");
		//System.out.println(p.addRecord(harald, r));
		
		System.out.println(p.getRecords(harald));
		System.out.println(harald.data());
		
		saveToFile(harald, a, p);
	}
}
