package database;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import people.Doctor;
import people.Person;

@SuppressWarnings("serial")
public class TableFromDatabase extends JFrame {
	public TableFromDatabase() {

	}

	public boolean createTableFromDatabase(Person p, String patientName) {
		Vector<Object> columnNames = new Vector<Object>();
		Vector<Object> data = new Vector<Object>();

		Database db = new Database();
		db.openConnection("db03", "db03", "joel");
		ResultSetMetaData md;
		ResultSet rs;

		try {
			rs = db.getMetaData(patientName);
			md = rs.getMetaData();
			if (p instanceof Doctor
					&& !db.isAssociated(patientName, (Doctor) p)) {
				return false;
			}

			// Connect to an Access Database

			// Read data from a table

			int columns = md.getColumnCount();
			// Get column names

			for (int i = 1; i <= columns; i++) {
				columnNames.addElement(md.getColumnName(i));
			}

			// Get row data

			while (rs.next()) {
				Vector<Object> row = new Vector<Object>(columns);

				for (int i = 1; i <= columns; i++) {
					row.addElement(rs.getObject(i));
				}

				data.addElement(row);
			}

			rs.close();
			// stmt.close();
			db.getConnection().close();
		} catch (Exception e) {
			System.out.println(e);
		}

		// Create table with database data

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column) {
				for (int row = 0; row < getRowCount(); row++) {
					Object o = getValueAt(row, column);

					if (o != null) {
						return o.getClass();
					}
				}

				return Object.class;
			}
		};

		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane);

		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		return true;
	}

}