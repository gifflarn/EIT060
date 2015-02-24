package logs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import ActionEvents.Action;
import ActionEvents.Add;

import people.Doctor;
import people.Nurse;
import people.Patient;
import people.Person;

public class AuditLog {

	public AuditLog(){
		//saveToFile();
	}
	
	public static void saveToFile(Person p, Action a, Patient pat){
		FileWriter writer = null;
		try {
			writer = new FileWriter("log.txt", true);
			writer.append(p.data() + ":" + a.data(pat));
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
		Action a = new Add(p);
		p.addDoctor(harald);
		Record r = new Record(harald,n,"lth", "cancer");
		System.out.println(p.addRecord(harald, r));
		System.out.println(p.getRecords(harald));
		
		
		
		saveToFile(p, a, p);
	}
}
