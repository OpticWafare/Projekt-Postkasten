package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DummyWerteErzeugen {
	static int id = 0;
	static int zustand = 0;
	static Timestamp begin = new Timestamp(System.currentTimeMillis());
	private static final String HOST = "localhost";
	private static final String SERVER = "jdbc:mysql://";
	private static final String PORT = "3306";
	private static final String DB_NAME = "postkasten";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	private static Connection con;

	public static Connection getConnection() {

		if (con == null) {
			try {
				con = DriverManager.getConnection(
						SERVER + HOST + ":" + PORT + "/" + DB_NAME + "?user=" + USERNAME + "&password=" + PASSWORD);
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		return con;
	}

	
	public static void werteErzeugen(int id, int zustand, Timestamp begin) {
		for (int i = 0; i < 5; i++) {
			zustand = (int) (Math.random() * 2);
			System.out.println("ID: " + id + " Zustand: " + zustand + " Datum und Uhrzeit: " + begin);
			id++;

			if (i == 0) {
				insertValues(id, zustand, begin);
			}
		}
	}

	public static void insertValues(int id, int zustand, Timestamp begin) {

		String url = SERVER;
		String table = "Insert into oeffnenundschliessen (id, zeitpunkt, zustand) values(?,?,?);";
		try (Connection con = DriverManager.getConnection(url); PreparedStatement pstm = con.prepareStatement(table)) {
			pstm.setInt(1, id);
			pstm.setTimestamp(2, begin);
			pstm.setInt(3, zustand);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		werteErzeugen(id, zustand, begin);
	}

}
