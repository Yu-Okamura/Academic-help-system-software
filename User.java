package cse360_proj1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * Database
getConnection - establishes & returns connection to SQL database
createUser (params - user object)
updateUser - Updates existing user’s info in the database (any column - name, email, roles, etc)
deleteUser

mysql> describe userinfo;
+----------+-------------+------+-----+---------+----------------+
| Field    | Type        | Null | Key | Default | Extra          |
+----------+-------------+------+-----+---------+----------------+
| Username | varchar(25) | NO   | UNI | NULL    |                |
| Password | varchar(25) | YES  |     | NULL    |                |
| Name     | varchar(25) | YES  |     | NULL    |                |
| Email    | varchar(50) | YES  |     | NULL    |                |
| ID       | int         | NO   | PRI | NULL    | auto_increment |
| roles    | int         | YES  |     | NULL    |
+----------+-------------+------+-----+---------+----------------+


mysql> describe invitecode_tables;
+-----------+-------------+------+-----+---------+----------------+
| Field     | Type        | Null | Key | Default | Extra          |
+-----------+-------------+------+-----+---------+----------------+
| invitcode | varchar(50) | NO   | PRI | NULL    | 		  |
| role      | int	  | YES  |     | NULL    |                |
+-----------+-------------+------+-----+---------+----------------+

 */

public class User {
	public String username;
	public String password;
	public String name;
	public String email;

	
    static String url = "jdbc:mysql://localhost:3306/CSE360";
    static String root_user = "root";
    static String root_password = "Password321!";
    
    static Connection connection = null;
    
    //creates a User object
    public User(String username, String password, String name, String email) {
    	this.username = username;
    	this.password = password;
    	this.name = name;
    	this.email = email;
    }
	
	
	//loads User object into database
	public static void createUser(User user, String invite_code) {
		int num_users = getUserCount(connection);
		String query = "INSERT INTO userinfo (username, password, name, email) VALUES(?, ?, ?, ?)";
		
		try(PreparedStatement statement = connection.prepareStatement(query)){
			
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
	
	//updating userinfo, make a new User object with
	public static void updateUser(int userID, User user) {
		String query = "UPDATE userinfo SET Username = ?, Password = ?, Name = ?, Email = ? WHERE ID = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(query)){
			
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
	
	//gives a user a specific role. admin: 1, instructor: 2, student: 3
	//use getUserID to get the ID
	public static void giveRole(int userID, int role) {
		String query = "INSERT INTO user_roles(user_id, role_id) VALUES(?, ?)";
		
		try(PreparedStatement statement = connection.prepareStatement(query)){
			
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
	
	
	/*
	 * Utility and information gathering functions
	 */
	
	//connects to the databse. Run before accessing database information
	public static Connection connect() {
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(url, root_user, root_password);
			System.out.println("Connection established.");
		}
		catch (SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return connection;
	}
	
	//returns the total number of active users
	public static int getUserCount(Connection connection) {
		int user_count = 0;
		String query = "SELECT COUNT(*) FROM userinfo";
		
		try (PreparedStatement statement = connection.prepareStatement(query);
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
	public static int getUserID(User user) {
		//if no user exists
		int ID = -1;
		
		String query = "SELECT ID FROM userinfo WHERE Username = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(query)){
			
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

    public static void main(String[] args) {
    	connection = connect();
    	User Billy = new User("BillEEEEEBob", "password", "Bill", "bill@gmail.com");
    	
    	//int userID = getUserID(Billy);
    	//System.out.println(userID);
    	updateUser(1, Billy);
    	
    	//giveRole(1, 1);

    	try {
    		connection.close();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
}
