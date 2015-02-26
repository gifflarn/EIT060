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

import database.*;
import people.*;

import ActionEvents.Action;
import ActionEvents.Add;
import ActionEvents.Read;
import ActionEvents.Remove;

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
			String[] info = new String[]{subject.split("CN=")[0].split(",")[0], subject.split("OU=")[0].split(",")[0], subject.split("O=")[0].split(",")[0]};
			switch(info[1]){
			case "Doctor":
				p = new Doctor(info[0], "", info[2]);
				break;
			case "Nurse":
				p = new Nurse(info[0], "", info[2]);
				break;
			case "Patient":
				p = new Patient(info[0], "");
				break;
			case "Government":
				p = new Government(info[0], "");
			}
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
				if (clientMsg.split(" ").length < 2) {
					continue;
				}
				Patient p = new Patient(clientMsg.split(" ")[1], "");
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

				ks.load(new FileInputStream("lab1/serverkeystore"), password); // keystore
				// password
				// (storepass)
				ts.load(new FileInputStream("lab1/servertruststore"), password); // truststore
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

	private String handleInput(String clientMsg, Patient p) {
		Action a = null;
		
		
		switch (clientMsg.toLowerCase()) {
		case "add":
			String patientName = null;
			String associatedNurse = null;
			String data = null;
			
			database.createRecord(p, patientName, associatedNurse, data);
			
			a = new Add(p);
			break;
		case "remove":
			a = new Remove(p);
			break;
		case "read":
			a = new Read(p);
			break;
		}

		a.execute(p);
		return null;

	}
}
