package records;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import people.Doctor;
import people.Person;

public class AuditLog {

	public AuditLog(){
		//saveToFile();
	}
	
	public static void saveToFile(Person p){
		FileWriter writer = null;
		try {
			writer = new FileWriter("log.txt", true);
			writer.append(p.data());
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args){
		Person p = new Doctor("Lukas", "hejdå");
		saveToFile(p);
	}
}
