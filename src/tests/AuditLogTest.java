package tests;

import logs.AuditLog;
import logs.RecordEntry;

import org.junit.Before;
import org.junit.Test;

import ActionEvents.Action;
import ActionEvents.Add;

import people.Doctor;
import people.Nurse;
import people.Patient;

public class AuditLogTest {
	AuditLog log;
	
	@Before
	public void setUp() throws Exception {
		log = new AuditLog();
		
	}

	@Test
	public void testAuditLog() {
		Doctor harald = new Doctor("Harald", "12345", "lth");
		Nurse n = new Nurse("Lukas", "43434", "lth");
		Patient p = new Patient("Joel", "54545");
		Action a = new Add(p);
		p.addDoctor(harald);
		RecordEntry r = new RecordEntry(harald,n,"lth", "cancer");
		p.addRecord(harald, r);
		
		
		AuditLog.saveToFile(p, a, p);
	}
}
