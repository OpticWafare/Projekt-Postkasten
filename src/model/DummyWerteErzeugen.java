package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class DummyWerteErzeugen{
	static int id = 0;
	static int zustand = 0;
	static Timestamp begin = new Timestamp(System.currentTimeMillis());


	

	public static void main(String[] args) throws IOException {
		werteErzeugen(id, zustand, begin);
		
	}
	public static void werteErzeugen(int id, int zustand, Timestamp begin) throws IOException {
		
		for (int i = 0; i < 5; i++) {
			try {
				TimeUnit.SECONDS.sleep((int) (Math.random() * 10));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			zustand = (int) (Math.random() * 2);
			System.out.println("ID: " + id + " Zustand: " + zustand + " Datum und Uhrzeit: " + begin);
			

			if (zustand == 0) {
				FileWriter writer;
				File datei = new File("PostB.txt");
				
				try{
					writer = new FileWriter(datei, true);
					writer.write("ID: " + id + " Zustand: " + zustand + " Datum und Uhrzeit: " + begin);
					writer.write(System.getProperty("line.separator"));
					
					writer.flush();
					writer.close();
					
				}catch(IOException e){
					e.printStackTrace();
				}	
			}
			id++;
		}
	}

	
}
