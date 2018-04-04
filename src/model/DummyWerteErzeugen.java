package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DummyWerteErzeugen {
		static int id = 0;
		static int zustand = 1;
		static Random number = new Random();
		static Date date = java.util.Calendar.getInstance().getTime();
		static SimpleDateFormat dateFormatter = 
	              new SimpleDateFormat("dd.MM.yyyy");
		static String dateString = dateFormatter.format(date);
		static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		static String uhrzeit = sdf.format(new Date());
		
		private String host = "";
		private String user = "";
		private String passwort = "";
		private String database = "";
		
		public static void getConnection(String url, String passwort, String username){
			try(Connection con = DriverManager.getConnection(url, username, passwort)){
				if(con != null){
					System.out.println("sdf");
				}
			} catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
		
		public static void main(String [] args){
			werteErzeugen(id, zustand, dateString, uhrzeit);
		}
		

	public static void werteErzeugen(int id, int zustand, String dateString, String uhrzeit){
		for(int i = 0; i < 100; i++){
		zustand = number.nextInt(1);
		System.out.println("ID: "+ id +" Zustand: "+ zustand + " Datum:" + dateString + "Uhrzeit: " + uhrzeit);
		id++;
		
		if(i == 0){
			insertValues(id, zustand, dateString, uhrzeit);
		}
		}
	}
	
	
	public static void insertValues(int id, int zustand, String dateString, String uhrzeit){
		
		String url = "";
		String table = "Insert into oeffnenundschliessen (id, zeitpunkt, zustand) values(?,?,?);";
		try(Connection con = DriverManager.getConnection(url);
				PreparedStatement pstm = con.prepareStatement(table)){
			pstm.setInt(1, id);
			pstm.setString(2, uhrzeit);
			pstm.setInt(3, zustand);
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
}
