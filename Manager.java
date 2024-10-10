package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import java.util.Arrays;

import application.User;

public class Manager {
	
    static String url = "jdbc:mysql://localhost:3306/CSE360";
    static String root_user = "root";
    static String root_password = "Password321!";
    
    private Connection connection = null;
    
    private String[] inviteCodes = {"CODE001", "CODE002", "CODE003", "CODE004", "CODE005", "CODE007", "CODE008", "CODE009", "CODE010"};
    private int[] inviteCodeRoles = {1, 2, 3, 4, 5, 6, 7, 1, 2, 3};
	
    private Dictionary<String, Integer> inviteDict = new Hashtable<>();
    
    
	public Manager() {
    	this.url = url;
    	this.root_user = root_user;
    	this.root_password = root_password;
    	this.connection = connection;
    	
    	for (int i = 0; i < inviteCodes.length; i++) {
    		this.inviteDict.put(this.inviteCodes[i], this.inviteCodeRoles[i]);
    	}
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
	    	String table1 = "create table invitecode_table(invitecode varchar(100) primary key, role int) ;";
	    	String table2 = "create table userinfo(ID int primary key, Username varchar(25) unique key, Password varchar(25), Name varchar(25), Email varchar(50), role_id int);";
	    	String[] inviteCodes = {"CODE001", "CODE002", "CODE003", "CODE004", "CODE005", "CODE007", "CODE008", "CODE009", "CODE010"};
	    	try(Connection connection = this.connection; Statement statement = connection.createStatement()){
	    		//statement.executeUpdate(database);
	    		//System.out.println("Database Created");
	    		//statement.executeUpdate(use);
	    		//System.out.println("Using CSE360");
	    		statement.executeUpdate(table1);
	    		System.out.println("inviteCode table Created");
	    		statement.executeUpdate(table2);
	    		System.out.println("userinfo table Created");
			/* roles:
   			1-Admin
			2-Student
			3-Teacher
			4-Admin and student
			5-Admin and teacher
			6-Student and teacher
			7-Admin, student, and teacher*/
	
			String insertCODE1 = String.format("Insert into invitecode_table (invitecode, role) VALUES ('%s','%d');", inviteCodes[0], 1);
			statement.executeUpdate(insertCODE1);
			//System.out.println("Invitecode inserted");
			for (int i=1; i<inviteCodes.length; i++) {
				int role = (i%6) +2;
				String insertCODE = String.format("Insert into invitecode_table (invitecode, role) VALUES ('%s','%d');", inviteCodes[i], role);
				statement.executeUpdate(insertCODE);
				//System.out.println("Invitecode inserted");
			}
					
		}
    	catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
    }
	
	
	public void createUser(User user, String invite_code) {
		int num_users = getUserCount();
		int roleID;
		
//		String[] inviteCodes = {"CODE001", "CODE002", "CODE003", "CODE004", "CODE005", "CODE007", "CODE008", "CODE009", "CODE010"};
//		int[] inviteCodeRoles = {1, 2, 3, 4, 5, 6, 7, 1, 2, 3};
//		
//		Dictionary<String, Integer> inviteDict = new Hashtable<>();
//		for (int i = 0; i < inviteCodes.length; i++) {
//			inviteDict.put(inviteCodes[i], inviteCodeRoles[i]);
//		}

		if(num_users == 0) {
			roleID = 1;
		} else {
			roleID = this.inviteDict.get(invite_code);
		}
		
		String query = "INSERT INTO userinfo (id, username, password, name, email, role_id) VALUES(?, ?, ?, ?, ?, ?)";
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setInt(1,  num_users);
			statement.setString(2, user.username);
			statement.setString(3, user.password);
			statement.setString(4, user.name);
			statement.setString(5,  user.email);
			statement.setInt(6,  roleID);
			
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected < 1) {
				System.out.println("Error: user unsuccesfully created.");
			}
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
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
	
	public int validateInviteCode(String invite_code) {
		if (Arrays.asList(this.inviteCodes).contains(invite_code)) {
			return 1;
		}

        return -1;
    }
	
	public void updateUser(int userID, User user) {
		String query = "UPDATE userinfo SET Username = ?, Password = ?, Name = ?, Email = ? WHERE ID = ?";
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, user.username);
			statement.setString(2, user.password);
			statement.setString(3, user.name);
			statement.setString(4, user.email);
			statement.setInt(5, userID);
			
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected < 1) {
				System.out.println("Error: user unsuccesfully updated.");
			}
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void changeRole(int userID, int roleNum) {
		String query = "UPDATE userinfo SET role_id = ? WHERE ID = ?";
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setInt(1, roleNum);
			statement.setInt(2, userID);
			
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected < 1) {
				System.out.println("Error: user unsuccesfully updated.");
			}
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteUserByID(User user) {
		//if no user exists
		int ID = -1;
		
		String selectQuery = "SELECT ID FROM userinfo WHERE Username = ?";
		String deleteQuery = "DELETE FROM userinfo WHERE Username = ?";
		
		try(PreparedStatement selectStatement = this.connection.prepareStatement(selectQuery)){
			
			selectStatement.setString(1, user.username);
			
			try(ResultSet result = selectStatement.executeQuery()){
				if(result.next()) {
					ID = result.getInt("ID");
				}
				else {
					System.out.println("Error: User not found in database.");
				}
			}
		}
		catch(SQLException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		try(PreparedStatement deleteStatement = this.connection.prepareStatement(deleteQuery)){
			
			deleteStatement.setString(1, user.username);
			int rowsAffected = deleteStatement.executeUpdate();
			
			if(rowsAffected > 0){
				System.out.println("User deleted successfully.");
			}
			else{
				System.out.println("Error: User could not be deleted.");
			}
			
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public String[] getUserInfo(User user) {
			
			String query = "SELECT * FROM userinfo WHERE Username = ?";
			
			String[] infoToReturn = new String[4];
			
			try(PreparedStatement statement = this.connection.prepareStatement(query)){
				
				statement.setString(1, user.username);
				
				try(ResultSet result = statement.executeQuery()){
					while(result.next()) {
						String username = result.getString("Username");
						String password = result.getString("Password");
						String name = result.getString("Name");
						String email = result.getString("Email");
						int roles = result.getInt("role_id");
						int ID = result.getInt("ID");
						
						infoToReturn[0] = username;
						infoToReturn[1] = password;
						infoToReturn[2] = name;
						infoToReturn[3] = email;
						
						System.out.printf("Username: %s, Password: %s, Name: %s, Email: %s, Role: %d, ID: %d",username, password, name, email, roles, ID);
						
					}
					if(!(result.next())){
						System.out.println("Error: User not found in database.");
					}
				}
				
			}
			catch(SQLException e) {
				System.out.println("SQL Error: " + e.getMessage());
				e.printStackTrace();
			}
			
			return infoToReturn;
			
		}
	
	public int login(User user) {
		
		String query = "SELECT * FROM userinfo WHERE Username = ? AND Password = ?";
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, user.username);
			statement.setString(2, user.password);
			
			try(ResultSet result = statement.executeQuery()){
				while(result.next()) {
					String email = result.getString("Email");
					if (email.equals("DEFAULT")) {
						return 0;
					} else {
						return 1;
					}
					
				}
				if(!(result.next())){
					System.out.println("Error: User not found in database.");
					return -1;
				}
			
			}
			catch(SQLException e) {
				System.out.println("SQL Error: " + e.getMessage());
				e.printStackTrace();
			}
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int getUserRole(User user) {
		//if no user exists
		
		String query = "SELECT role_id FROM userinfo WHERE Username = ?";
		int userRole = -1;
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, user.username);
			
			try(ResultSet result = statement.executeQuery()){
				if(result.next()) {
					userRole = result.getInt("role_id");
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
		return userRole;
	}
	
	public void setPassword(User user, String password) {
		String username = user.username;
		String query = "UPDATE userinfo SET Password = ? WHERE Username = ?";
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			statement.setString(1, password);
			statement.setString(2, username);
			
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected < 1) {
				System.out.println("Error: user unsuccesfully updated.");
			}
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public String generateOTP(String unixTime) {
		
		//Timestamp for password - current time + 15 minutes
		//long unixTime = (System.currentTimeMillis() + 900000) / 1000L;
		
		//Characters for password
		//Function partially sourced from https://www.baeldung.com/java-random-string
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(4);
		
		for (int i = 0; i < 4; i++) {
	        int randomLimitedInt = 97 + (int) 
	          (random.nextFloat() * (122 - 97 + 1));
	        buffer.append((char) randomLimitedInt);
	    }
		
		String passApp = buffer.toString();
		
		String fullOTP = "OTP" + unixTime + passApp;
		
		return fullOTP;
	}
	
	public boolean checkPasswordExpired(String password) {
		long unixTime = Long.parseLong(password.substring(3, 13));
		long time = 1000L * unixTime;
		
		if (System.currentTimeMillis() > time) {
			return true;
		} else {
			return false;
		}
	}
	
	public String[] listAllUsers() {
		String query = "SELECT Username FROM userinfo";
		
		String[] infoToReturn = new String[500];
		
		try(PreparedStatement statement = this.connection.prepareStatement(query)){
			
			try(ResultSet result = statement.executeQuery()){
				int count = 0;
				while(result.next()) {
					String username = result.getString("Username");
					
					infoToReturn[count] = username;
					
					count += 1;
					
				}
				if(!(result.next())){
					System.out.println("Error: User not found in database.");
				}
			}
			
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return infoToReturn;
		
	}
	
	public String getInviteCode(int index) {
		return this.inviteCodes[index];
	}
}
