package network;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.security.cert.X509Certificate;
import javax.swing.JFrame;

import logs.AuditLog;
import people.Doctor;
import people.Government;
import people.Nurse;
import people.Patient;
import people.Person;
import database.Database;
import database.TableFromDatabase;

public class ServerConnectionHandler implements Runnable {
	private ServerSocket serverSocket = null;
	private static int numConnectedClients = 0;
	private Database database;
	private Person p;

	public ServerConnectionHandler(ServerSocket ss) throws IOException {
		serverSocket = ss;
		newListener();
	}

	public void run() {
		try {
			SSLSocket socket = (SSLSocket) serverSocket.accept();
			newListener();
			SSLSession session = socket.getSession();
			X509Certificate cert = (X509Certificate) session
					.getPeerCertificateChain()[0];
			String subject = cert.getSubjectDN().getName();
			String[] info = new String[] {
					subject.split("CN=")[1].split(",")[0],
					subject.split("OU=")[1].split(",")[0],
					subject.split("O=")[1].split(",")[0] };
			switch (info[2].toString()) {
			case "Doctor":
				p = new Doctor(info[0], info[1]);
				break;
			case "Nurse":
				p = new Nurse(info[0], info[1]);
				break;
			case "Patient":
				p = new Patient(info[0]);
				break;
			case "Government":
				p = new Government(info[0]);
			}
		//	 p = new Government("Lukas");
			numConnectedClients++;
			System.out.println("client connected");
			System.out.println("client name (cert subject DN field): "
					+ subject);
			System.out.println(numConnectedClients
					+ " concurrent connection(s)\n");

			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			String clientMsg = null;
			while ((clientMsg = in.readLine()) != null) {
				System.out.println("received '" + clientMsg + "' from client");
				if (clientMsg.split(" ").length < 1) {
					continue;
				}
				out.println(handleInput(clientMsg.split(" ")[0], p));
				out.flush();
			}
			in.close();
			out.close();
			socket.close();
			numConnectedClients--;
			System.out.println("client disconnected");
			System.out.println(numConnectedClients
					+ " concurrent connection(s)\n");
		} catch (IOException e) {
			System.out.println("Client died: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	private void newListener() {
		(new Thread(this)).start();
	} // calls run()

	public static void init(String args[]) {
		System.out.println("\nServer Started\n");
		int port = 8888;
		String type = "TLS";
		try {
			ServerSocketFactory ssf = getServerSocketFactory(type);
			ServerSocket ss = ssf.createServerSocket(port);
			((SSLServerSocket) ss).setNeedClientAuth(true); // enables client
															// authentication
			new ServerConnectionHandler(ss);
		} catch (IOException e) {
			System.out.println("Unable to start Server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static ServerSocketFactory getServerSocketFactory(String type) {
		System.out.println("Working Directory = "
				+ System.getProperty("user.dir"));

		if (type.equals("TLS")) {
			SSLServerSocketFactory ssf = null;
			try { // set up key manager to perform server authentication
				SSLContext ctx = SSLContext.getInstance("TLS");
				KeyManagerFactory kmf = KeyManagerFactory
						.getInstance("SunX509");
				TrustManagerFactory tmf = TrustManagerFactory
						.getInstance("SunX509");
				KeyStore ks = KeyStore.getInstance("JKS");
				KeyStore ts = KeyStore.getInstance("JKS");
				char[] password = "password".toCharArray();

				ks.load(new FileInputStream("certificates/serverkeystore"),
						password); // keystore
				// password
				// (storepass)
				ts.load(new FileInputStream("certificates/servertruststore"),
						password); // truststore
				// password
				// //
				// (storepass)
				kmf.init(ks, password); // certificate password (keypass)
				tmf.init(ts); // possible to use keystore as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				ssf = ctx.getServerSocketFactory();
				return ssf;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return ServerSocketFactory.getDefault();
		}
		return null;
	}

	private String handleInput(String clientMsg, Person p) {
		int id;
		database = new Database();
		database.openConnection("db03", "db03", "joel");
		String patientName = new ClientGUI().getText("Enter Patient's Name :");
		String msg = "";

		switch (clientMsg.toLowerCase()) {
		case "add":
			if (!(p instanceof Doctor)) {
				return "You don't have the permission to do that.";
			}
			String associatedNurse = new ClientGUI()
					.getText("Enter Nurse's Name :");
			String data = new ClientGUI().getText("Enter Additional Data:");
			System.out.println(database.createRecord(p, patientName,
					associatedNurse, data));
			msg = "ADDED_ENTRY:" + patientName;

			break;
		case "remove":
			if (!(p instanceof Government)) {
				return "You don't have the permission to do that.";
			}
			TableFromDatabase frame = new TableFromDatabase();
			frame.createTableFromDatabase(p, patientName);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			try {
				id = Integer.valueOf(new ClientGUI()
						.getText("Enter the ID you wish to delete: "));
			} catch (NumberFormatException e) {
				return "Not a valid number";
			}
			frame.dispose();
			database.deleteRecord(p, id);
			msg = "REMOVED_ENTRY:" + id;
			break;
		case "read":
			System.out.println(database.recordsToString(
					database.getRecords(p, patientName), patientName));
			msg = "READ_ENTRY:" + patientName;
			break;
		case "edit":
			if (p instanceof Patient || p instanceof Government) {
				return "You don't have the permission to do that.";
			}
			TableFromDatabase frame2 = new TableFromDatabase();
			if (!frame2.createTableFromDatabase(p, patientName)) {
				return "You don't have permission to do that.";
			}
			frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame2.pack();
			frame2.setVisible(true);
			try {
				id = Integer.valueOf(new ClientGUI()
						.getText("Enter the ID you wish to edit: "));
			} catch (NumberFormatException e) {
				return "Not a valid number";
			}
			frame2.dispose();
			String editData = new ClientGUI().getText("Enter the new data: ");
			database.editRecord(p, patientName, id, editData);
			msg = "EDITED_ENTRY:" + patientName + "WITH_NEW_DATA:" + editData;
			break;
		default:
			return "Invalid Command";
		}
		AuditLog.saveToFile(p, msg);
		return msg;

	}

}
