package database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Database is a class that specifies the interface to the movie database. Uses
 * JDBC and the MySQL Connector/J driver.
 */
public class Database {
	/**
	 * The database connection.
	 */
	private Connection conn;

	/**
	 * Create the database interface object. Connection to the database is
	 * performed later.
	 */
	public Database() {
		conn = null;
	}

	/**
	 * Open a connection to the database, using the specified user name and
	 * password.
	 * 
	 * @param userName
	 *            The user name.
	 * @param password
	 *            The user's password.
	 * @return true if the connection succeeded, false if the supplied user name
	 *         and password were not recognized. Returns false also if the JDBC
	 *         driver isn't found.
	 */
	public boolean openConnection(String database, String userName,
			String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://puccini.cs.lth.se/" + database, userName,
					password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Close the connection to the database.
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
		}
		conn = null;
	}

	/**
	 * Check if the connection to the database has been established
	 * 
	 * @return true if the connection has been established
	 */
	public boolean isConnected() {
		return conn != null;
	}

	public ArrayList<Record> readRecords(Person person, String patientName) {
		ArrayList<Record> records = new ArrayList<Record>();
		PreparedStatement ps = null;
		if(persontype = Doctor) {
			doctorName = person.getName();
			hospitalDistrict = person.getDistrict();
			String sql = "SELECT * FROM Records WHERE patient = ? AND doctor = ? OR district = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientName);
			ps.setString(2, doctorName);
			ps.setString(3, hospitalDistrict);
			ResultSet rs = ps.executeQuery();
		}	
		if(persontype == Nurse) {
			nurseName = person.getName();
			hospitalDistrict = person.getDistrict();
			String sql = "SELECT * FROM Records WHERE patient = ? AND nurse = ? OR district = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientName);
			ps.setString(2, nurseName);
			ps.setString(3, hospitalDistrict);
			ResultSet rs = ps.executeQuery();
		}
		if(persontype == Patient) {
			patientName = person.getName();
			String sql = "SELECT * FROM Records WHERE patient = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, patientName);

			ResultSet rs = ps.executeQuery();
		}
		while (rs.next()) {
			records.add(new Record(rs.getString("patient"), rs.getString("doctor"), rs.getString("nurse"), rs.getString("division"), rs.getString("data")));
		}
		return records;
	}
	
	public String createRecord(Person person, String patientName, String associatedNurse, String data) {
		String message = null;
		persontype = person
		if (persontype != Doctor) {
			message = "You do not have the required access rights to create a patient record";
		}
		String doctorName = person.getName();
		String sql = "SELECT * FROM Records WHERE doctor = ? and patient = ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, doctorName);
		ps.setString(2, patientName);
		ResultSet rs = ps.executeQuery();	
		if(!rs.next()) {
			message = "You do not have the required access rights to create a record for the specified patient";
		}
//		String date = getDate();
		String hospitalDivision = person.getDivision();
		sql = "INSERT INTO Records VALUES(?, ?, ?, ?, ?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, date);
		ps.setString(2, patientName);
		ps.setString(1, associatedNurse);
		ps.setString(2, hospitalDivision);
		ps.setString(2, data);
		int i = ps.executeUpdate();
		if (i != 1) {
			message = "Unable add the record to the database";
		}
		message = "The record was added to the database";
		return message;
	}
		
	public String recordsToString(ArrayList<Record> records) {
		StringBuilder recordContent = null;
		for (Record r : records) {
			recordContent.append("Date: " + r.getDate() + "\n" + "Patient name: " + r.getPatient() + "\n" + "Doctor: " + r.getDoctor() + "\n" + "Assisting nurse: " + r.getNurse() + "\n" + "Hospital division: " + r.getDivision() + "\n" + "Record data: " + r.getData() + "\n" + "\n");
		}
		return recordContent.toString();
	}	

	class Record {
		private String date;
		private String patient;
		private String doctor;
		private String nurse;
		private String division;
		private String data;
		
		public Record(String date, String patient, String doctor, String nurse,
				String division, String data) {
			this.patient = patient;
			this.doctor = doctor;
			this.nurse = nurse;
			this.division = division;
			this.data = data;
			this.date = date;
		}
		
		public String getDate() {
			return date;
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
	}

}
