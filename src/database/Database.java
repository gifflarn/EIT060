package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import logs.RecordEntry;
import people.Doctor;
import people.Government;
import people.Nurse;
import people.Patient;
import people.Person;

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

	@SuppressWarnings({ "finally", "resource" })
	public String createRecord(Person person, String patientName, String associatedNurse, String data) {
		String message = null;
		if (!(person instanceof Doctor)) {
			return message = "You do not have the required access rights to create records";
		} else {
			PreparedStatement ps = null;
			try {
				Doctor d = (Doctor) person;
				String doctorName = d.getName();
				String sql = "SELECT * FROM Records WHERE doctor = ? and patient = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, doctorName);
				ps.setString(2, patientName);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					message = "You do not have the required access rights to create a record for the selected patient";
				} else {
					Date creationDate = new Date(System.currentTimeMillis());
					Date lastUpdateDate = new Date(System.currentTimeMillis());
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
					ps.setString(8, data);
					int n = ps.executeUpdate();
					if (n != 1) {							// hur skulle detta kunna ske utan att det genereras ett exception?
						message = "Unable to add the record to the database";
					} else {
						message = "The record was successfully added to the database";
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Unable add the record to the database";
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return message;
			}
		}
	} 
	
	@SuppressWarnings("finally")
	public String editRecord(Person person, String patientName, int recordId, String data) {
		String message = null;
		if (person instanceof Patient) {
			return message = "You do not have the required access rights to edit records";
		} else {
			String name = person.getName();
			Date lastUpdateDate = new Date(System.currentTimeMillis());
			String hospitalDivision = null;
			String sql = null;
			PreparedStatement ps = null;
			int rs = -1;
			try {
				if (person instanceof Doctor) {
					Doctor d = (Doctor) person;
					hospitalDivision = d.getDivision();
					sql = "UPDATE Records SET data = ?, lastUpdateDate = ? WHERE recordId = ? AND (patient = ? AND doctor = ? OR division = ?)";
				} else if (person instanceof Nurse) {
					Nurse n = (Nurse) person;
					hospitalDivision = n.getDivision();
					sql = "UPDATE Records SET data = ?, lastUpdateDate = ? WHERE recordId = ? AND (patient = ? AND nurse = ? OR division = ?)";
				}	
				ps = conn.prepareStatement(sql);
				ps.setString(1, data);
				ps.setDate(2, lastUpdateDate);
				ps.setInt(3, recordId);
				ps.setString(4, patientName);
				ps.setString(5, name);
				ps.setString(6, hospitalDivision);
				rs = ps.executeUpdate();
				if (rs == -1) {
					message = "You do not have the required access rights to edit the selected record";
				} else {
					message = "The record was successfully updated";
				}
			} catch (SQLException e) {
				e.printStackTrace();	
				message = "Unable add the record to the database";
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return message;	
			}
		}
	}	
	
	@SuppressWarnings("finally")
	public String deleteRecord(Person person, int recordId) {
		String message = null;
		if (!(person instanceof Government)) {
			return message = "You do not have the required access rights to delete records";
		} else {
			PreparedStatement ps = null;
			try {
				String sql = "DELETE FROM Records WHERE recordId = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, recordId);
				int n = ps.executeUpdate();
				if (n != 1) {
					message = "The selected record id does not exist";
				} else {
					message = "The record was successfully deleted from the database";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				message = "Unable delete the record from the database";
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return message;
			}
		}
	}
	
	public ArrayList<RecordEntry> getRecords(Person person, String patientName) {
		ArrayList<RecordEntry> records = new ArrayList<RecordEntry>();
		String name = person.getName();
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (person instanceof Patient) {
				sql = "SELECT * FROM Records WHERE patient = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, name);
			} else {
				String hospitalDivision = null;	
				if (person instanceof Doctor) {
					Doctor d = (Doctor) person;
					hospitalDivision = d.getDivision();
					sql = "SELECT * FROM Records WHERE patient = ? AND doctor = ? OR division = ?";
				} else if (person instanceof Nurse) {
					Nurse n = (Nurse) person;
					hospitalDivision = n.getDivision();
					sql = "SELECT * FROM Records WHERE patient = ? AND nurse = ? OR division = ?";
				}
				ps = conn.prepareStatement(sql);
				ps.setString(1, patientName);
				ps.setString(2, name);
				ps.setString(3, hospitalDivision);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				records.add(new RecordEntry(rs.getInt("recordId"), rs
					.getDate("creationDate"), rs.getDate("lastUpdateDate"),
					rs.getString("patient"), rs.getString("doctor"), rs
					.getString("nurse"), rs.getString("division"),
					rs.getString("data")));
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
	
	@SuppressWarnings("null")
	public String recordsToString(ArrayList<RecordEntry> records, String PatientName) {
		StringBuilder recordContent = new StringBuilder();
		for (RecordEntry r : records) {
			if(!PatientName.equals(r.getPatient())){
				continue;
			}
			recordContent.append("Patient name: " + r.getPatient() + "\n"
					+ "Doctor: " + r.getDoctor() + "\n" + "Assisting nurse: "
					+ r.getNurse() + "\n" + "Hospital division: "
					+ r.getDivision() + "\n" + "Record data: " + r.getData()
					+ "\n" + "\n");
		}
		return recordContent.toString();
	}
	
}
