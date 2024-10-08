package cse360_proj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cse360_proj1.User;

public class Manager {
	
    static String url = "jdbc:mysql://localhost:3306/CSE360";
    static String root_user = "root";
    static String root_password = "Password321!";
    
    private Connection connection = null;
    
    
	public Manager() {
    	this.url = url;
    	this.root_user = root_user;
    	this.root_password = root_password;
    	this.connection = connection;
    }
	
	public void connect() {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(url, root_user, root_password);
			System.out.println("Connection established.");
		}
		catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		this.connection = connection;
	}
	
	public void createDatabaseAndTables() {
    	String database =  "create database CSE360;";
    	String use = " use CSE360;";
    	String table1 = "create table invitecode_table(invitecode varchar(100) primary key) ;";
    	String table2 = "create table userinfo(ID int(50) primary key, Username varchar(25) unique key, Password varchar(25), Name varchar(25), Email varchar(50), role_id int(50));";
    	
    	try(Connection connection = this.connection; Statement statement = connection.createStatement()){
    		//statement.executeUpdate(database);
    		//System.out.println("Database Created");
    		//statement.executeUpdate(use);
    		//System.out.println("Using CSE360");
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
	
	public void createUser(User user, String invite_code) {
		int num_users = getUserCount();
		String query = "INSERT INTO userinfo (username, password, name, email) VALUES(?, ?, ?, ?)";
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, user.username);
			statement.setString(2, user.password);
			statement.setString(3, user.name);
			statement.setString(4,  user.email);
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected < 1) {
				System.out.println("Error: user unsuccesfully created.");
			}
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		//if user is the first to login, give admin credentials
		if(num_users == 0) {
			int userID = getUserID(user);
			
			giveRole(userID, 1);
		}
	}
	
	public void giveRole(int userID, int role) {
		String query = "INSERT INTO user_roles(user_id, role_id) VALUES(?, ?)";
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setInt(1, userID);
			statement.setInt(2, role);
			
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected < 1) {
				System.out.println("Error: unable to add role to user");
			}
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
		}
	}
	
	public int getUserCount() {
		int user_count = 0;
		String query = "SELECT COUNT(*) FROM userinfo";
		
		try (PreparedStatement statement = this.connection.prepareStatement(query);
				ResultSet result = statement.executeQuery()){
			
			if(result.next()) {
				user_count = result.getInt(1);
			}
		}
		catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
		}
		
		return user_count;
	}
	
	public int getUserID(User user) {
		//if no user exists
		int ID = -1;
		
		String query = "SELECT ID FROM userinfo WHERE Username = ?";
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, user.username);
			
			try(ResultSet result = statement.executeQuery()){
				if(result.next()) {
					ID = result.getInt("ID");
				}
				else {
					System.out.println("Error: User not found in database.");
				}
			}
			
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return ID;
	}
	
	
}
