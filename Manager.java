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
	/*
	 * Utility and information gathering functions
	 */
	
	//connects to the databse. Run before accessing database information
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
	//database and tables create function
	public void createDatabaseAndTables() {
	    	String database =  "create database CSE360;";//creating a database
	    	String use = " use CSE360;";//using the new database
	    	String table1 = "create table invitecode_table(invitecode varchar(100) primary key, role int) ;";//creating the invitecode table
	    	String table2 = "create table userinfo(ID int primary key, Username varchar(25) unique key, Password varchar(25), Name varchar(25), Email varchar(50), role_id int);";//creating the userinfo table
		String[] inviteCodes = {"CODE001", "CODE002", "CODE003", "CODE004", "CODE005", "CODE007", "CODE008", "CODE009", "CODE010"};//defining the preexixting invite codes
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
	
			String insertCODE1 = String.format("Insert into invitecode_table (invite code, role) VALUES ('%s','%d');", inviteCodes[0], 1);
			//System.out.println("Invitecode inserted");
			for (int i=1; i<inviteCodes.length; i++) {
				int role = (i%6) +2;
				String insertCODE = String.format("Insert into invitecode_table (invite code, role) VALUES ('%s','%d');", inviteCodes[i], role);
				//System.out.println("Invitecode inserted");
			}
					
		}
    	catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
    }
	//loads user object into database
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
	//gives a user a specific role. 
	//use getUserID to get the ID
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
	//returns the total number of active users
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
	//returns the integer ID associated with a Username in the database
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

	//return the role if the invite code is valid else return -1 for invalid invite code
	public static int validateInviteCode(String invite_code) {
        String query = "SELECT role FROM invitecode_table WHERE invitecode = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, invite_code);

            try(ResultSet result = statement.executeQuery()){
                if(result.next()) {
                    //invite code is valid
                    int role = result.getInt("role");

                    return role;
                }
                else {
                    //invite code is invalid
                    return -1;
                }
            }
        }
        catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }


        return 1;
    }
	
}
