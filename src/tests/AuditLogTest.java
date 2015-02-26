package tests;

import logs.AuditLog;
import logs.RecordEntry;

import org.junit.Before;
import org.junit.Test;

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
		
	}
}
