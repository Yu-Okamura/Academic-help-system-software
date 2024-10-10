package application;

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
    
    //creates a User object
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