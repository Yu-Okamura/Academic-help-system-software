package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;



public class DatabaseManger {
	static String url = "jdbc:mysql://localhost:3306/CSE360";
    static String root_user = "root";
    static String root_password = "password";
    
    public static void createDatabaseAndTables() {
    	String database =  "create database CSE360;";
    	String use = " use CSE360;";
    	String table1 = "create table invitecode_table(invitecode varchar(100) primary key) ;";
    	String table2 = "create table userinfo(Username varchar(25) primary key, Password varchar(25), Name varchar(25), Email varchar(50));";
    	
    	try(Connection connection = DriverManager.getConnection(url,root_user, root_password); Statement statement = connection.createStatement()){
    		statement.executeUpdate(database);
    		System.out.println("Database Created");
    		statement.executeUpdate(use);
    		System.out.println("Using CSE360");
    		statement.executeUpdate(table1);
    		System.out.println("inviteCode table Created");
    		statement.executeUpdate(table2);
    		System.out.println("userinfo table Created");
    		
    	}
    	catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
    }
    
    public static void main() { 
    	createDatabaseAndTables(); 
    }
}
