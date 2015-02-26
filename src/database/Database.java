package database;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import people.Person;
import people.Government;
import people.Employee;
import people.Doctor;
import people.Nurse;
import people.Patient;
import logs.RecordEntry;

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

	public Connection getConnection() {
		return conn;
	}
	
	public ArrayList<RecordEntry> getRecords(Person person, String patientName) {
		ArrayList<RecordEntry> records = new ArrayList<RecordEntry>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (person instanceof Doctor) {
				Doctor d = (Doctor) person;
				String doctorName = d.getName();
				String hospitalDivision = d.getDivision();
				String sql = "SELECT * FROM Records WHERE patient = ? AND doctor = ? OR division = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, patientName);
				ps.setString(2, doctorName);
				ps.setString(3, hospitalDivision);
				rs = ps.executeQuery();
			} else if (person instanceof Nurse) {
				Nurse n = (Nurse) person;
				String nurseName = n.getName();
				String hospitalDivision = n.getDivision();
				String sql = "SELECT * FROM Records WHERE patient = ? AND nurse = ? OR division = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, patientName);
				ps.setString(2, nurseName);
				ps.setString(3, hospitalDivision);
				rs = ps.executeQuery();
			} else if (person instanceof Patient) {
				Patient p = (Patient) person;
				patientName = p.getName();
				String sql = "SELECT * FROM Records WHERE patient = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, patientName);
				rs = ps.executeQuery();
			}
			while (rs.next()) {
				records.add(new RecordEntry(
						rs.getInt("recordId"), rs.getDate("creationDate"), rs.getDate("lastUpdateDate"), rs.getString("patient"), rs
		.getString("doctor"), rs.getString("nurse"), rs.getString("division"), rs.getString("data")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return records;
	}

	@SuppressWarnings("resource")
	public String createRecord(Person person, String patientName,
			String associatedNurse, String data) {
		String message = null;
		PreparedStatement ps = null;
		try {
			if (!(person instanceof Doctor)) {
				message = "You do not have the required access rights to create a patient record";
			}
			Doctor d = (Doctor) person;
			String doctorName = d.getName();
			String sql = "SELECT * FROM Records WHERE doctor = ? and patient = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, doctorName);
			ps.setString(2, patientName);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				message = "You do not have the required access rights to create a record for the specified patient";
			} else {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date creationDate = new Date(0);
				Date lastUpdateDate = new Date(0);
				String hospitalDivision = d.getDivision();
				sql = "INSERT INTO Records VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, 0);
				ps.setDate(2, creationDate);
				ps.setDate(3, lastUpdateDate);
				ps.setString(4, patientName);
				ps.setString(5, doctorName);
				ps.setString(6, associatedNurse);
				ps.setString(7, hospitalDivision);
				ps.setString(7, data);
				int i = ps.executeUpdate();
				if (i != 1) {
					message = "Unable add the record to the database";
				} else {
					message = "The record was added to the database";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return message;
	}

	@SuppressWarnings("null")
	public String recordsToString(ArrayList<RecordEntry> records) {
		StringBuilder recordContent = null;
		for (RecordEntry r : records) {
			recordContent.append("Patient name: " + r.getPatient() + "\n"
					+ "Doctor: " + r.getDoctor() + "\n" + "Assisting nurse: "
					+ r.getNurse() + "\n" + "Hospital division: "
					+ r.getDivision() + "\n" + "Record data: " + r.getData()
					+ "\n" + "\n");
		}
		return recordContent.toString();
	}

	
//Public String updateRecord(Person person, String patientName, Record r) {
//	String message = null;
//	if (person instanceof Patient) {
//		message = "You do not have the required access rights to write to patient records";
//	} else {
//		getRecords(person, patientName);
//	}


//	@SuppressWarnings("resource")
//	public String deleteRecord(Person person, String patientName, int recordEntryNbr) {
//		String message = null;
//		PreparedStatement ps = null;
//		try {
//			if (!(person instanceof Government)) {
//				message = "You do not have the required access rights to delete patient records";
//			}
//			Government g = (Government)person;
//			String doctorName = g.getName();
//			String sql = "SELECT * FROM Records WHERE doctor = ? and patient = ?";
//			ps = conn.prepareStatement(sql);
//			ps.setString(1, doctorName);
//			ps.setString(2, patientName);
//			ResultSet rs = ps.executeQuery();
//			if (!rs.next()) {
//				message = "You do not have the required access rights to create a record for the specified patient";
//			} else {
//				String hospitalDivision = d.getDivision();
//				sql = "INSERT INTO Records VALUES(?, ?, ?, ?, ?)";
//				ps = conn.prepareStatement(sql);
//				ps.setString(1, patientName);
//				ps.setString(2, doctorName);
//				ps.setString(3, associatedNurse);
//				ps.setString(4, hospitalDivision);
//				ps.setString(5, data);
//				int i = ps.executeUpdate();
//				if (i != 1) {
//					message = "Unable add the record to the database";
//				} else {
//					message = "The record was added to the database";
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				ps.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return message;
//	}
	
}
