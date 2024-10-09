package justTesting;

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
+----------+-------------+------+-----+---------+----------------+

createUser() : creates a user in the database, makes them admin if they have ID = 1
updateUser() : updates a user based on their ID to a new a user object
giveRole() : assigns a role to a user ID

connect() : connects to database
getUserCount() : gives the number of active users.
getUserID() : returns an ID based on a username
 */

public class User {
	public String username;
	public String password;
	public String name;
	public String email;
	
    static String url = "jdbc:mysql://localhost:3306/CSE360";
    static String root_user = "root";
    static String root_password = "Kingfish314!";
    
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
		
		if(num_users != 0) {
			int valid_code = validateInviteCode(invite_code);
			
			if(valid_code == -1) {
				System.out.println("Error: Invalid invite code.");
				
				return;
			}
		}
		
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
		else {
			int role = validateInviteCode(invite_code);
			
			int userID = getUserID(user);
			giveRole(userID, role);
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
	
	public static int deleteUserByID(User user) {
		//if no user exists
		int ID = -1;
		
		String selectQuery = "SELECT ID FROM userinfo WHERE Username = ?";
		String deleteQuery = "DELETE FROM userinfo WHERE Username = ?";
		
		try(PreparedStatement selectStatement = connection.prepareStatement(selectQuery)){
			
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
		
		try(PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)){
			
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
		
		return ID;
	}
	
	
	//gives a user a specific role. admin: 1, instructor: 2, student: 3
	//use getUserID to get the ID
	public static void giveRole(int userID, int role) {
		String query = "UPDATE userinfo SET roles = ? WHERE ID = ?";
		
		try(PreparedStatement statement = connection.prepareStatement(query)){
			
			statement.setInt(1, role);
			statement.setInt(2, userID);
			
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
	
	//returns all info associated with a user
	public static void getUserInfo() {
		
		String query = "SELECT * FROM userinfo";
		
		try(PreparedStatement statement = connection.prepareStatement(query)){
			
			try(ResultSet result = statement.executeQuery()){
				while(result.next()) {
					String username = result.getString("Username");
					String password = result.getString("Password");
					String name = result.getString("Name");
					String email = result.getString("Email");
					int roles = result.getInt("roles");
					int ID = result.getInt("ID");
					
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
		
	}
	
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
			}
		}
		catch(SQLException e) {
			System.out.println("SQL Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return ID;
	}
	
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
		
		
		return -1;
	}
	

    public static void main(String[] args) {
    	/*
    	connection = connect();
    	User Billy = new User("BillEEEEEBob", "password", "Bill", "bill@gmail.com");
    	User Bobby = new User("boobob", "password", "boeol", "obbueut@gmail.com");

    	
    	//int userID = getUserID(Billy);
    	//System.out.println(userID);
    	createUser(Billy, "hello");
    	createUser(Bobby, "12345");
    	
    	giveRole(1, 4);
    	
    	
    	getUserInfo();
    	
    	
    	
    	try {
    		connection.close();
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	*/
    }
}

