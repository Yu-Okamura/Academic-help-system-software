package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * Database Class - Manages database operations for users
 * Methods:
 * getConnection - establishes & returns connection to SQL database
 * createUser - creates a new user in the database
 * updateUser - updates existing user’s information in the database (name, email, roles, etc.)
 * deleteUser - removes a user from the database
 * 
 * Database Table Structure (userinfo):
 * +----------+-------------+------+-----+---------+----------------+
 * | Field    | Type        | Null | Key | Default | Extra          |
 * +----------+-------------+------+-----+---------+----------------+
 * | Username | varchar(25) | NO   | UNI | NULL    |                |
 * | Password | varchar(25) | YES  |     | NULL    |                |
 * | Name     | varchar(25) | YES  |     | NULL    |                |
 * | Email    | varchar(50) | YES  |     | NULL    |                |
 * | ID       | int         | NO   | PRI | NULL    | auto_increment |
 * +----------+-------------+------+-----+---------+----------------+
 * 
 * Method Descriptions:
 * createUser() - creates a user in the database and makes them admin if ID = 1
 * updateUser() - updates user details based on their ID using a user object
 * giveRole() - assigns a role to a user based on their ID
 * connect() - connects to the SQL database
 * getUserCount() - returns the number of active users in the database
 * getUserID() - returns the user ID based on the username
 */

public class User {
    public String username;
    public String password;
    public String name;
    public String email;
    
    // Database connection details
    static String url = "jdbc:mysql://localhost:3306/CSE360";
    static String root_user = "root";
    static String root_password = "Kingfish314!";
    
    // Constructor to create a User object
    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
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

