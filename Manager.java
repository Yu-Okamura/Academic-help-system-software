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
	
    // Database connection details
    static String url = "jdbc:mysql://localhost:3306/CSE360";
    static String root_user = "root";
    static String root_password = "Password321!";
    
    private Connection connection = null;
    
    // Invite codes and corresponding roles
    private String[] inviteCodes = {"CODE001", "CODE002", "CODE003", "CODE004", "CODE005", "CODE007", "CODE008", "CODE009", "CODE010"};
    private int[] inviteCodeRoles = {1, 2, 3, 4, 5, 6, 7, 1, 2, 3};
	
    // Dictionary to map invite codes to roles
    private Dictionary<String, Integer> inviteDict = new Hashtable<>();
    
    // Constructor to initialize the Manager and populate inviteDict
    public Manager() {
        this.url = url;
        this.root_user = root_user;
        this.root_password = root_password;
        this.connection = connection;
    	
        // Populate inviteDict with invite codes and their corresponding roles
        for (int i = 0; i < inviteCodes.length; i++) {
            this.inviteDict.put(this.inviteCodes[i], this.inviteCodeRoles[i]);
        }
    }
	
    // Connects to the database
    public void connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, root_user, root_password);
            System.out.println("Connection established.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        this.connection = connection;
    }
	
    // Creates the database and tables if they don't exist
    public void createDatabaseAndTables() {
        String database = "create database CSE360;";
        String use = "use CSE360;";
        String table1 = "create table invitecode_table(invitecode varchar(100) primary key, role int);";
        String table2 = "create table userinfo(ID int primary key, Username varchar(25) unique key, Password varchar(25), Name varchar(25), Email varchar(50), role_id int);";
        String[] inviteCodes = {"CODE001", "CODE002", "CODE003", "CODE004", "CODE005", "CODE007", "CODE008", "CODE009", "CODE010"};
        
        try (Connection connection = this.connection; Statement statement = connection.createStatement()) {
            // Uncomment the lines below if the database and table need to be created
            // statement.executeUpdate(database);
            // System.out.println("Database Created");
            // statement.executeUpdate(use);
            // System.out.println("Using CSE360");
            
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
            
            // Insert initial invite codes into the database
            String insertCODE1 = String.format("Insert into invitecode_table (invitecode, role) VALUES ('%s','%d');", inviteCodes[0], 1);
            statement.executeUpdate(insertCODE1);
            for (int i = 1; i < inviteCodes.length; i++) {
                int role = (i % 6) + 2;
                String insertCODE = String.format("Insert into invitecode_table (invitecode, role) VALUES ('%s','%d');", inviteCodes[i], role);
                statement.executeUpdate(insertCODE);
            }
					
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
    // Creates a new user in the database
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
	    
        // If this is the first user, make them an admin
        if (num_users == 0) {
            roleID = 1;
        } else {
            roleID = this.inviteDict.get(invite_code);
        }
		
        String query = "INSERT INTO userinfo (id, username, password, name, email, role_id) VALUES(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, num_users);
            statement.setString(2, user.username);
            statement.setString(3, user.password);
            statement.setString(4, user.name);
            statement.setString(5, user.email);
            statement.setInt(6, roleID);
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Error: user unsuccessfully created.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
    // Assigns a role to a user based on their ID
    public void giveRole(int userID, int role) {
        String query = "INSERT INTO user_roles(user_id, role_id) VALUES(?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, userID);
            statement.setInt(2, role);
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Error: unable to add role to user");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
	
    // Retrieves the number of users in the database
    public int getUserCount() {
        int user_count = 0;
        String query = "SELECT COUNT(*) FROM userinfo";
        try (PreparedStatement statement = this.connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                user_count = result.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return user_count;
    }
	
    // Retrieves a user ID based on their username
    public int getUserID(User user) {
        int ID = -1;
        String query = "SELECT ID FROM userinfo WHERE Username = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, user.username);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    ID = result.getInt("ID");
                } else {
                    System.out.println("Error: User not found in database.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return ID;
    }
	
    // Validates the invite code
    public int validateInviteCode(String invite_code) {
        if (Arrays.asList(this.inviteCodes).contains(invite_code)) {
            return 1;
        }
        return -1;
    }
	
    // Updates an existing user in the database
    public void updateUser(int userID, User user) {
        String query = "UPDATE userinfo SET Username = ?, Password = ?, Name = ?, Email = ? WHERE ID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, user.username);
            statement.setString(2, user.password);
            statement.setString(3, user.name);
            statement.setString(4, user.email);
            statement.setInt(5, userID);
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Error: user unsuccessfully updated.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
    // Changes a user's role based on their ID
    public void changeRole(int userID, int roleNum) {
        String query = "UPDATE userinfo SET role_id = ? WHERE ID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, roleNum);
            statement.setInt(2, userID);
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Error: user unsuccessfully updated.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
    // Deletes a user from the database based on their username
    public void deleteUserByID(User user) {
        int ID = -1;
        String selectQuery = "SELECT ID FROM userinfo WHERE Username = ?";
        String deleteQuery = "DELETE FROM userinfo WHERE Username = ?";
        
        try (PreparedStatement selectStatement = this.connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, user.username);
            try (ResultSet result = selectStatement.executeQuery()) {
                if (result.next()) {
                    ID = result.getInt("ID");
                } else {
                    System.out.println("Error: User not found in database.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        try (PreparedStatement deleteStatement = this.connection.prepareStatement(deleteQuery)) {
            deleteStatement.setString(1, user.username);
            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("Error: User could not be deleted.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
    // Retrieves user information based on the username
    public String[] getUserInfo(User user) {
        String query = "SELECT * FROM userinfo WHERE Username = ?";
        String[] infoToReturn = new String[4];
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, user.username);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
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
                    
                    System.out.printf("Username: %s, Password: %s, Name: %s, Email: %s, Role: %d, ID: %d", username, password, name, email, roles, ID);
                }
                if (!(result.next())) {
                    System.out.println("Error: User not found in database.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return infoToReturn;
    }
	
    // Handles login by checking user credentials
    public int login(User user) {
        String query = "SELECT * FROM userinfo WHERE Username = ? AND Password = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, user.username);
            statement.setString(2, user.password);
            
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    String email = result.getString("Email");
                    if (email.equals("DEFAULT")) {
                        return 0; // First-time login
                    } else if (user.password.substring(0, 3).equals("OTP")) {
                        return 2; // OTP login
                    } else {
                        return 1; // Regular login
                    }
                }
                if (!(result.next())) {
                    System.out.println("Error: User not found in database.");
                    return -1; // User not found
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }
	
    // Retrieves the role of a user based on their username
    public int getUserRole(User user) {
        String query = "SELECT role_id FROM userinfo WHERE Username = ?";
        int userRole = -1;
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, user.username);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    userRole = result.getInt("role_id");
                } else {
                    System.out.println("Error: User not found in database.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return userRole;
    }
	
    // Sets a new password for a user
    public void setPassword(User user, String password) {
        String username = user.username;
        String query = "UPDATE userinfo SET Password = ? WHERE Username = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, password);
            statement.setString(2, username);
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected < 1) {
                System.out.println("Error: user unsuccessfully updated.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
    // Generates a one-time password (OTP) based on the given Unix time
    public String generateOTP(String unixTime) {

//Timestamp for password - current time + 15 minutes
//long unixTime = (System.currentTimeMillis() + 900000) / 1000L;
//Characters for password
//Function partially sourced from https://www.baeldung.com/java-random-string
	    
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(4);

        
        // Generate 4 random lowercase characters for OTP
        for (int i = 0; i < 4; i++) {
            int randomLimitedInt = 97 + (int) (random.nextFloat() * (122 - 97 + 1));
            buffer.append((char) randomLimitedInt);
        }
        
        String passApp = buffer.toString();
        String fullOTP = "OTP" + unixTime + passApp;
        
        return fullOTP;
    }
	
    // Checks if a given password has expired based on Unix time
    public boolean checkPasswordExpired(String password) {
        long unixTime = Long.parseLong(password.substring(3, 13));
        long time = 1000L * unixTime;
        
        return System.currentTimeMillis() > time;
    }
	
    // Lists all users in the database
    public String[] listAllUsers() {
        String query = "SELECT Username FROM userinfo";
        String[] infoToReturn = new String[500];
        
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            try (ResultSet result = statement.executeQuery()) {
                int count = 0;
                while (result.next()) {
                    String username = result.getString("Username");
                    infoToReturn[count] = username;
                    count += 1;
                }
                if (!(result.next())) {
                    System.out.println("Error: User not found in database.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return infoToReturn;
    }
	
    // Retrieves the username based on the provided email
    public String getUserNameFromEmail(String email) {
        String username = "nothing";
        String query = "SELECT username FROM userinfo WHERE Email = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    username = result.getString("Username");
                } else {
                    System.out.println("Error: User not found in database.");
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return username;
    }
	
    // Retrieves the invite code based on the index
    public String getInviteCode(int index) {
        return this.inviteCodes[index];
    }
}
