package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Manager {

	private static final String HOST = "localhost";
	private static final String SERVER = "jdbc:mysql://";
	private static final String PORT = "3306";
	private static final String DB_NAME = "postkasten";
	private static final String USERNAME = "Martin";
	private static final String PASSWORD = "post";

	private static Connection con;

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
	
}
