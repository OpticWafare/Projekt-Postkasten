package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Datenbankmanager zum Erstellen der Datenbank und
 * zum Auslesen und Speichern von Daten
 *
 * Auf die Methoden wird statisch zugegriffen
 */
public class DB_Manager {

	/** IP Adresse des Servers */
	private static final String HOST = "localhost";
	/** Prefix der URL für die Verbindung zur Datenbank */
	private static final String SERVER = "jdbc:mysql://";
	/** Port des DB-Servers */
	private static final String PORT = "3306";
	/** Name der Datenbank */
	private static final String DB_NAME = "postkasten";
	/** Benutzername des Datenbank-Users */
	private static final String USERNAME = "Martin";
	/** Passwort des Datenbank-Users */
	private static final String PASSWORD = "Post";

	/** Verbindung zur Datenbank */
	private static Connection con;

	/**
	 * Gibt die Verbindung zur Datenbank zurück.
	 * Falls sie nicht existiert, wird sie erstellt
	 * @return Verbindung zur Datenbank
	 */
	public static Connection getConnection() {

		if (con == null) {
			try {
				
				con = DriverManager.getConnection(SERVER + HOST + ":" + PORT
						+ "/" + DB_NAME + "?user=" + USERNAME + "&password="
						+ PASSWORD);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} return con;
	}

	/** Datenbank erstellen, falls sie nicht existiert */
	public static void createDatabase()
	{
		Statement stm = null;
		try {
			stm = getConnection().createStatement();
			stm.execute("CREATE Database IF NOT EXISTS "  + DB_NAME);
			stm.execute("use " + DB_NAME);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/** Erstellt die Datenbanktabelle in der Postkastenänderungen gespeichert werden */
	public static void createTable()
	{
		Statement stm = null;
		try {
			stm = getConnection().createStatement();
			stm.execute("CREATE table IF NOT EXISTS oeffnenundschliessen("
					+ "ID int(10) PRIMARY KEY AUTO_INCREMENT,"
					+ "Zeitpunkt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
					+ "Zustand TINYINT(1) NOT NULL)");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/** Fängt eine Postkastenänderung in die Datenbanktabelle ein */
	public static void insertTable(int i)
	{
		PreparedStatement stm = null;
		try {
			stm = getConnection().prepareStatement("INSERT INTO oeffnenundschliessen (Zustand) VALUES (?)");
			stm.setInt(1, i);
			stm.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
//	public static void main(String[] args) {
//		DB_Manager.getConnection();
//		DB_Manager.createDatabase();
//		DB_Manager.createTable();
//		
//	}
}
