package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class DB_Manager01 {

	private static Connection con;
	private static String server =  "localhost";
	private static String dbname = "postkasten";
	private static String pfad = server+"/"+dbname+"?user=root&password="; 
	//private static String prfadSQLite = "";
	private static PreparedStatement UserErstellen;
	private static PreparedStatement ResultErstellen;
	private static PreparedStatement AusgabeResults;
	
	public static Connection getCon() {
		if(con == null)
		{
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + pfad);
				UserErstellen = getCon().prepareStatement("insert into User(userName) values (?)");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
	}

	public static void createDatabase()
	{
		try {
			Statement stm = getCon().createStatement();
			stm.execute("create if not exists database "+dbname);
			Statement stm1 = getCon().createStatement();
			stm1.execute("use " + dbname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createTables()
	{
		try {
			Statement stm = getCon().createStatement();
			stm.execute("create table if not exists User("
					+ "userID int primary key auto_increment,"
					+ "userName varchar(50) NOT NULL,"
					+ "userRegistrationDate datetime NOT NULL default current_timestamp)");
			stm.execute("create table if not exists Results("
					+ "beginDateTimeOfTest datetime,"
					+ "endDateTimeOfTest datetime,"
					+ "userID int NOT NULL,"
					+ "nrPairs int NOT NULL,"
					+ "nrTripples int NOT NULL,"
					+ "nrFourOfaKind int NOT NULL,"
					+ "nrFullHouse int NOT NULL,"
					+ "nrFlush int NOT NULL,"
					+ "nrStraight int NOT NULL,"
					+ "nrStraightFlush int NOT NULL,"
					+ "nrRoyalFlush int NOT NULL,"
					+ "primary key(beginDateTimeOfTest, endDateTimeOfTest))");
//					+ "foreign key(userID) references User(userID))");
			stm.execute("alter table Results add foreign key (userID) references User(UserID)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	public static void UserErstellen(String userName)
	{
		getCon();
		try {			
			UserErstellen.setString(1, userName);
			UserErstellen.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();;
		}
	}
	
	public static void AddResult(Timestamp beginTimeOfTest, Timestamp endTimeOfTest, int UserID,  int nrPairs, int nrTripples,
			int nrFourOfaKind, int nrFullHouse, int nrFlush, int nrStraight,  int nrStraightFlush, int nrRoyalFlush)
	{
		getCon();
		try {
			ResultErstellen.setTimestamp(1, beginTimeOfTest);
			ResultErstellen.setTimestamp(2, endTimeOfTest);
			ResultErstellen.setInt(3, UserID);
			ResultErstellen.setInt(4, nrPairs);
			ResultErstellen.setInt(5, nrTripples);
			ResultErstellen.setInt(6, nrFourOfaKind);
			ResultErstellen.setInt(7, nrFullHouse);
			ResultErstellen.setInt(8, nrFlush);
			ResultErstellen.setInt(9, nrStraight);
			ResultErstellen.setInt(10, nrStraightFlush);
			ResultErstellen.setInt(11, nrRoyalFlush);
			
			ResultErstellen.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		//createDatabase();
		createTables();
	}
}