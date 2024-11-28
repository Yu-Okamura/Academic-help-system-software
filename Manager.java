package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import application.User;

import java.util.Base64;

import application.EncryptionHelper;
import application.EncryptionUtils;

import org.json.JSONArray;

import application.User;

public class Manager {
	
    static String url = "jdbc:mysql://localhost:3306/CSE360";
    static String root_user = "root";
    static String root_password = "Password321!";
    
    private Connection connection = null;
    
    private String[] inviteCodes = {"CODE001", "CODE002", "CODE003", "CODE004", "CODE005", "CODE007", "CODE008", "CODE009", "CODE010"};
    private int[] inviteCodeRoles = {1, 2, 3, 4, 5, 6, 7, 1, 2, 3};
    
    
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
	
//	public void createDatabaseAndTables() {
//    	//String database =  "create database CSE360;";
//    	//String use = " use CSE360;";
//    	String table1 = "create table invitecode_table(invitecode varchar(100) primary key, role int) ;";
//    	String table2 = "create table userinfo(ID int primary key, Username varchar(25) unique key, Password varchar(25), Name varchar(25), Email varchar(50), role_id int);";
//    	String table3 = "create table articles(ID bigint AUTO_INCREMENT primary key, Title varchar(255),Discription text, Body text, Group_ids varchar(255), Reference_Articles varchar(255), Keywords varchar(255), Level varchar(30));";
//    	String table4 = "create table group_table(EntryID BIGINT AUTO_INCREMENT PRIMARY KEY, ID bigint DEFAULT NULL, Name varchar(255), ArticleID bigint NULL);";
//    	
//    	//starts group increment at 5; 1-4 is Beginner-Expert
//    	String[] inviteCodes = {"CODE001", "CODE002", "CODE003", "CODE004", "CODE005", "CODE007", "CODE008", "CODE009", "CODE010"};
//    	try(Connection connection = this.connection; Statement statement = connection.createStatement()){
//	    		//statement.executeUpdate(database);
//	    		//System.out.println("Database Created");
//	    		//statement.executeUpdate(use);
//	    		//System.out.println("Using CSE360");
//	    		statement.executeUpdate(table1);
//	    		System.out.println("inviteCode table Created");
//	    		statement.executeUpdate(table2);
//	    		System.out.println("userinfo table Created");
//	    		statement.executeUpdate(table3);
//	    		System.out.println("articles table created");
//	    		statement.executeUpdate(table4);
//	    		System.out.println("groups table created");
//			/* roles:
//				1-Admin
//			2-Student
//			3-Teacher
//			4-Admin and student
//			5-Admin and teacher
//			6-Student and teacher
//			7-Admin, student, and teacher*/
//	
//			String insertCODE1 = String.format("Insert into invitecode_table (invitecode, role) VALUES ('%s','%d');", inviteCodes[0], 1);
//			statement.executeUpdate(insertCODE1);
//			//System.out.println("Invitecode inserted");
//			for (int i=1; i<inviteCodes.length; i++) {
//				int role = (i%6) +2;
//				String insertCODE = String.format("Insert into invitecode_table (invitecode, role) VALUES ('%s','%d');", inviteCodes[i], role);
//				statement.executeUpdate(insertCODE);
//				//System.out.println("Invitecode inserted");
//			}
//					
//		}
//		catch(SQLException e) {
//			System.out.println("SQL Error: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}
	public void createDatabaseAndTables() {
    	String database =  "create database CSE360;";
    	String use = " use CSE360;";
    	String table1 = "create table invitecode_table(invitecode varchar(100) primary key, role int) ;";
    	String table2 = "create table userinfo(ID int auto_increment primary key, Username varchar(25) unique key, Password varchar(25), Name varchar(25), Email varchar(50), role_id int);";
    	String table3 = "create table articles(ID bigint AUTO_INCREMENT primary key, Title varchar(255),Discription text, Body text, Group_ids varchar(255), Reference_Articles varchar(255), Keywords varchar(255), Level varchar(30));";
    	String table4 = "create table group_table(EntryID BIGINT AUTO_INCREMENT PRIMARY KEY, ID bigint DEFAULT NULL, Name varchar(255), ArticleID bigint NULL);";
    	String table5 = "create table user_roles(user_id int, role_id int, PRIMARY KEY(user_id, role_id));";
    	String table6 = "create table user_groups(user_id INT, group_id BIGINT, role_id INT, can_decrypt BOOL, PRIMARY KEY(user_id, group_id));";
    	
    	//starts group increment at 5; 1-4 is Beginner-Expert
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
    		statement.executeUpdate(table3);
    		System.out.println("articles table created");
    		statement.executeUpdate(table4);
    		System.out.println("groups table created");
    		statement.executeUpdate(table5);
    		System.out.println("roles table created");
    		statement.executeUpdate(table6);
    		System.out.println("user_groups table created");
    		
    		createGroup("Beginner");
    		createGroup("Intermediate");
    		createGroup("Advanced");
    		createGroup("Expert");
    		
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
	    int num_users = getUserCount(); // Get current user count

	    String query = "INSERT INTO userinfo (username, password, name, email) VALUES(?, ?, ?, ?)";
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setString(1, user.username); // Set username
	        statement.setString(2, user.password); // Set password
	        statement.setString(3, user.name);     // Set name
	        statement.setString(4, user.email);    // Set email
	        int rowsAffected = statement.executeUpdate(); // Execute insert
	
	        if (rowsAffected < 1) {
	            System.out.println("Error: user unsuccessfully created.");
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
        int userID = getUserID(user);

	
	    // If this is the first user, assign admin role
	    if (num_users == 0) {
	        giveRole(userID, 1);
	    }
	    	    	    
	    addUserToGroup(userID, 1, 2, true);
	    addUserToGroup(userID, 2, 2, true);
	    addUserToGroup(userID, 3, 2, true);
	    addUserToGroup(userID, 4, 2, true);
	    
	}
	
	public void giveRole(int userID, int role) {
		//adding role to user_roles table
	    String query = "INSERT INTO user_roles(user_id, role_id) VALUES(?, ?)";
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setInt(1, userID); // Set user ID
	        statement.setInt(2, role);   // Set role
	        int rowsAffected = statement.executeUpdate();
	
	        if (rowsAffected < 1) {
	            System.out.println("Error: unable to add role to user");
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	    }
	    
	    //adding role to userinfo role_id column
	    ArrayList<Integer> roles = getRoleList(userID);
	    
	    //default
	    int role_id = -1;
	    
	    if(roles.contains(1)) {
	    	role_id = 1;
	    	if(roles.contains(2)) {
	    		role_id = 4;
	    		
	    		if(roles.contains(3)) {
	    			role_id = 7;
	    		}
	    	}
	    	else if(roles.contains(3)) {
	    		role_id = 5;
	    	}
	    }
	    else if(roles.contains(2)) {
	    	role_id = 2;
	    	
	    	if(roles.contains(3)) {
	    		role_id = 6;
	    	}
	    }
	    else if(roles.contains(3)) {
	    	role_id = 3;
	    }
	    
	    String query3 = "UPDATE userinfo SET role_id = ? WHERE ID = ?";
	    
	    try (PreparedStatement statement = this.connection.prepareStatement(query3)) {

	        statement.setInt(1, role_id); 
	        statement.setInt(2, userID);    


	        int rowsAffected = statement.executeUpdate();

	        if (rowsAffected > 0) {
	            System.out.println("Role updated successfully to " + role_id + " for user ID: " + userID);
	        } else {
	            System.out.println("Failed updating role for user ID: " + userID);
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	}
	
	public ArrayList<Integer> getRoleList(int userID){
		
	    ArrayList<Integer> roles = new ArrayList<>(); 
	    String query2 = "SELECT role_id FROM user_roles WHERE user_id = ?";
	    
	    try (PreparedStatement statement = this.connection.prepareStatement(query2)) {
	        statement.setInt(1, userID);

	        try (ResultSet result = statement.executeQuery()) {
	            while (result.next()) {
	                roles.add(result.getInt("role_id"));
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return roles;
	}
	
	public int getUserCount() {
	    int user_count = 0;
	    String query = "SELECT COUNT(*) FROM userinfo";
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query);
	         ResultSet result = statement.executeQuery()) {
	        if (result.next()) {
	            user_count = result.getInt(1); // Get user count
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return user_count;
	}
	
	public int getUserID(User user) {
	    int ID = -1;
	    String query = "SELECT ID FROM userinfo WHERE Username = ?";
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setString(1, user.username); // Set username to find ID
	
	        try (ResultSet result = statement.executeQuery()) {
	            if (result.next()) {
	                ID = result.getInt("ID"); // Retrieve user ID
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
	
	public int validateInviteCode(String invite_code) {
	    String query = "SELECT role FROM invitecode_table WHERE invitecode = ?";
	
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, invite_code); // Set invite code
	
	        try (ResultSet result = statement.executeQuery()) {
	            if (result.next()) {
	                int role = result.getInt("role"); // Get associated role
	                return role;
	            } else {
	                return -1; // Invalid invite code
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return 1; // Default return if no role found
	}
	
	public String[][] getArticleArray() {
	    ArrayList<String[]> articles = new ArrayList<>();
	    String query = "SELECT ID, Title, Group_ids, Keywords, Body, Reference_Articles, Level FROM articles";
	
	    try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(query)) {
	        while (rs.next()) {
	            String[] article = new String[7]; // Array to hold article data
	            article[0] = String.valueOf(rs.getLong("ID"));
	            article[1] = rs.getString("Title");
	            article[2] = rs.getString("Group_ids");
	            article[3] = rs.getString("Keywords");
	            article[4] = rs.getString("Body");
	            article[5] = rs.getString("Reference_Articles");
	            article[6] = rs.getString("Level");
	            articles.add(article); // Add article to list
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving articles: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return articles.toArray(new String[0][]); // Convert list to array
	}
	
	public String[] getArticleByID(long identifier) {
	    String[] article = new String[6]; // Array to hold article data
	    String query = "SELECT ID, Title, Keywords, Body, Reference_Articles, Level FROM articles WHERE ID = ?";
	
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setLong(1, identifier); // Set article ID to retrieve
	
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                article[0] = String.valueOf(rs.getLong("ID"));
	                article[1] = rs.getString("Title");
	                article[2] = rs.getString("Keywords");
	                article[3] = rs.getString("Body");
	                article[4] = rs.getString("Reference_Articles");
	                article[5] = rs.getString("Level");
	            } else {
	                System.out.println("Article with ID " + identifier + " not found.");
	                return null; // Return null if no article found
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving article: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return article; // Return article data array
	}
	
	public String[][] searchArticles(String searchTerm) {
	    ArrayList<String[]> matchingArticles = new ArrayList<>();
	    String[] terms = searchTerm.split(" "); // Split search terms by spaces

	    // Build SQL query dynamically for multiple terms
	    StringBuilder queryBuilder = new StringBuilder("SELECT ID, Title, Keywords, Body, Reference_Articles, Discription, Level FROM articles WHERE ");
	    for (int i = 0; i < terms.length; i++) {
	        if (i > 0) queryBuilder.append(" AND ");
	        queryBuilder.append("(LOWER(Title) LIKE ? OR LOWER(Keywords) LIKE ? OR LOWER(Body) LIKE ? OR LOWER(Reference_Articles) LIKE ? OR LOWER(Discription) LIKE ? OR LOWER(Level) LIKE ?)");
	    }

	    try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
	        int paramIndex = 1;
	        for (String term : terms) {
	            String searchPattern = "%" + term.toLowerCase() + "%"; // Create search pattern
	            for (int j = 0; j < 6; j++) { // Update loop count to 6 for each column
	                statement.setString(paramIndex++, searchPattern);
	            }
	        }

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                String[] article = new String[7]; // Array to hold matched article data
	                article[0] = rs.getString("ID");
	                article[1] = rs.getString("Title");
	                article[2] = rs.getString("Keywords");
	                article[3] = rs.getString("Body");
	                article[4] = rs.getString("Reference_Articles");
	                article[5] = rs.getString("Discription");
	                article[6] = rs.getString("Level");
	                matchingArticles.add(article); // Add to results list
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error searching articles: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return matchingArticles.toArray(new String[0][]); // Return matched articles as array
	}
	
	ArrayList<String[]> filterArticlesByGroup(int group_id) {
		ArrayList<String[]> articles = new ArrayList<>();
		String query = "SELECT * FROM group_table WHERE ID = ? AND ArticleID IS NOT NULL";
		
		ArrayList<Integer> article_ids = new ArrayList<>();
		
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, group_id);
			
			try(ResultSet result = statement.executeQuery()){
				while(result.next()) {
					article_ids.add(result.getInt("ArticleID"));
				}
			}
			
			
		} catch(SQLException e) {
			System.out.println("Error filtering articles by group: " + e.getMessage());
		}
		
	    String query2 = "SELECT ID, Title, Group_ids, Keywords, Body, Reference_Articles, Level FROM articles WHERE ID = ?";
	    for (int article_id : article_ids) {
	        try (PreparedStatement stmt = connection.prepareStatement(query2)) {
	            stmt.setInt(1, article_id);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    String[] article = new String[7];
	                    article[0] = String.valueOf(rs.getLong("ID"));
	                    article[1] = rs.getString("Title");
	                    article[2] = rs.getString("Group_ids");
	                    article[3] = rs.getString("Keywords");
	                    article[4] = rs.getString("Body");
	                    article[5] = rs.getString("Reference_Articles");
	                    article[6] = rs.getString("Level");
	                    articles.add(article);
	                }
	            }
	        } catch (SQLException e) {
	            System.out.println("Error fetching article details: " + e.getMessage());
	        }
	    }

	    return articles;
	}

	
	public void update_article(long identifier, String title, String discription, String body, String group_ids, String ref, String keywords, String level) throws Exception {
	    String update_article = "UPDATE articles SET Title = ?, Discription = ?, Body = ?, Group_ids = ?, Reference_Articles = ?, Keywords = ?, Level = ? WHERE ID = ?";
	
	    try (PreparedStatement pstmt = connection.prepareStatement(update_article)) {
	        pstmt.setString(1, title); // Update title
	        pstmt.setString(2, discription); // Update description
	        pstmt.setString(3, body); // Update body
	        pstmt.setString(4, group_ids); // Update group IDs
	        pstmt.setString(5, ref); // Update references
	        pstmt.setString(6, keywords); // Update keywords
	        pstmt.setString(7, level); // Update level
	        pstmt.setLong(8, identifier); // Set article ID
	        pstmt.executeUpdate();
	        System.out.println("Article updated successfully");
	    } catch (SQLException e) {
	        System.out.println("Error updating article: " + e.getMessage());
	    }
	}

	public void createGroup(String group_name) {
	    Long id = null; // ID for the group
	    String query;
	
	    // Set ID for predefined groups based on name
	    switch (group_name) {
	        case "Beginner":
	            id = 1L;
	            break;
	        case "Intermediate":
	            id = 2L;
	            break;
	        case "Advanced":
	            id = 3L;
	            break;
	        case "Expert":
	            id = 4L;
	            break;
	    }
	
	    // If group is not predefined, calculate the next available ID
	    if (id == null) {
	        String query_max = "SELECT COALESCE(MAX(ID), 0) FROM group_table";
	        long max_ID = 0;
	
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query_max)) {
	
	            if (resultSet.next()) {
	                max_ID = resultSet.getLong(1);
	            }
	            id = (max_ID == 0) ? 5 : max_ID + 1; // Set ID to 5 if no groups exist, else increment max ID
	        } catch (SQLException e) {
	            System.out.println("Error retrieving max ID: " + e.getMessage());
	            return;
	        }
	    }
	
	    // Insert the new group with assigned or calculated ID
	    query = "INSERT INTO group_table (ID, Name) VALUES (?, ?)";
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setLong(1, id);       // Set group ID
	        statement.setString(2, group_name); // Set group name
	        statement.executeUpdate();      // Execute insertion
	        System.out.println("Group '" + group_name + "' created with ID " + id);
	    } catch (SQLException e) {
	        System.out.println("Error creating group: " + e.getMessage());
	    }
	}
	
	public void addArticleToGroup(long article_id, long group_id) {
	    String queryUpdateNull = "UPDATE group_table SET ArticleID = ? WHERE ID = ? AND ArticleID IS NULL LIMIT 1";
	    String queryInsert = "INSERT INTO group_table (ID, Name, ArticleID) VALUES (?, ?, ?)";
	
	    // Retrieve the group name for the specified ID
	    String groupName = getGroupNameById(group_id);
	
	    try (PreparedStatement statementUpdateNull = connection.prepareStatement(queryUpdateNull)) {
	        statementUpdateNull.setLong(1, article_id); // Set article ID
	        statementUpdateNull.setLong(2, group_id);   // Set group ID
	        int rowsAffected = statementUpdateNull.executeUpdate(); // Try to update an empty slot
	
	        if (rowsAffected > 0) {
	            System.out.println("Article " + article_id + " added to existing null row in Group ID " + group_id);
	            return; // Return if update successful
	        }
	    } catch (SQLException e) {
	        System.out.println("Error updating null row: " + e.getMessage());
	        return;
	    }
	
	    // Insert a new row if no null slot was available
	    try (PreparedStatement statementInsert = connection.prepareStatement(queryInsert)) {
	        statementInsert.setLong(1, group_id);      // Set group ID
	        statementInsert.setString(2, groupName);    // Set group name
	        statementInsert.setLong(3, article_id);     // Set article ID
	        statementInsert.executeUpdate();            // Execute insertion
	        System.out.println("Article " + article_id + " added to Group ID " + group_id);
	    } catch (SQLException e) {
	        System.out.println("Error adding article to group: " + e.getMessage());
	    }
	}
	
	public void addUserToGroup(int user_id, long group_id, int role_id, boolean can_decrypt){
		//if first instructor in group
		ArrayList<Integer> roles = getRoleList(user_id);
		
		if(getUsersByRoleInGroup(group_id, 3).size() == 0 && roles.contains(2)) {
			role_id = 1;
			can_decrypt = true;
		}
		String query = "INSERT INTO user_groups(user_id, group_id, role_id, can_decrypt) VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement statementInsert = connection.prepareStatement(query)) {
			statementInsert.setInt(1, user_id);
			statementInsert.setLong(2, group_id);
			statementInsert.setInt(3, role_id);
			statementInsert.setBoolean(4, can_decrypt);
			
			statementInsert.executeUpdate();
			System.out.println("User ID: " + user_id + " added to group ID: " + group_id);
			
		} catch (SQLException e){
			System.out.println("Error adding user to group: " + e.getMessage());
		}
	}
	
	public ArrayList<Integer> getUsersByRoleInGroup(long group_id, int role_id){
		ArrayList<Integer> user_ids = new ArrayList<>();
		String query;
		if(role_id == 1) {
			query = "SELECT user_id FROM user_groups WHERE group_id = ? AND role_id = ?";
			
			try(PreparedStatement statement = connection.prepareStatement(query)){
				statement.setLong(1, group_id);
				statement.setInt(2, role_id);
				
		        try (ResultSet result = statement.executeQuery()) {
		            while (result.next()) {
		                user_ids.add(result.getInt("user_id"));
		            }
		        }
				
			} catch(SQLException e) {
				System.out.println("Error getting users from group: " + e.getMessage());
			}
		}
		return user_ids;
	}
	

	public int validateUserGroup(int user_id, int group_id) {
		//assumee user is not in group
		int in_group = -1;
		
		String query = "SELECT * FROM user_groups WHERE user_id = ? AND group_id = ?";
		
		try(PreparedStatement statementSelect = connection.prepareStatement(query)){
			statementSelect.setInt(1, user_id);
			statementSelect.setInt(2, group_id);
			
			try(ResultSet resultSet = statementSelect.executeQuery()){
				if(resultSet.next()) {
					in_group = resultSet.getInt("role_id");
				}
			}
		} catch(SQLException e) {
			System.out.println("Error verifying user group: " + e.getMessage());
		}
		
		return in_group;
	}
	
	private String getGroupNameById(long group_id) {
	    String query = "SELECT Name FROM group_table WHERE ID = ? LIMIT 1";
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setLong(1, group_id); // Set group ID to find name
	        ResultSet resultSet = statement.executeQuery();
	
	        if (resultSet.next()) {
	            return resultSet.getString("Name"); // Return the name if found
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving group name: " + e.getMessage());
	    }
	    return "Unknown"; // Return "Unknown" if no group name found
	}
	
	public boolean canUserModifyArticle(int user_id, int article_id) {
		String article_groups_query = "SELECT ID FROM group_table WHERE ArticleID = ?";
		ArrayList <Integer> article_groups = new ArrayList<>();
		
		try(PreparedStatement statement = connection.prepareStatement(article_groups_query)){
			statement.setInt(1, article_id);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				article_groups.add(resultSet.getInt("ID"));
			}
		} catch(SQLException e) {
			System.out.println("Error getting groups that can modify article");
		}
		
		for(int group_id : article_groups) {

			if(validateUserGroup(user_id, group_id) == 1) {
				return true;
			}
		}
		
		return false;
	}
	
	public void create_article(int current_role, String title, String discription, String body, String group_ids, String ref, String keywords, String level) throws Exception {
        if (current_role == 3) {
            System.out.println("Error: Students cannot create articles.");
            return;
        }

        String insertArticle = "INSERT INTO articles (Title, Discription, Body, Group_ids, Reference_Articles, Keywords, Level) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertArticle, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, title);         // Set title
            pstmt.setString(2, discription);   // Set description
            pstmt.setString(3, body);          // Set body content
            pstmt.setString(4, group_ids);     // Set group IDs
            pstmt.setString(5, ref);           // Set references
            pstmt.setString(6, keywords);      // Set keywords
            pstmt.setString(7, level);         // Set level
            pstmt.executeUpdate();             // Execute article insertion
            System.out.println("Article created successfully");

            // Retrieve the auto-generated article ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long articleId = generatedKeys.getLong(1);
                    System.out.println("Generated Article ID: " + articleId);

                    // Add article to each group in group_ids
                    String[] groupIdList = group_ids.split(",");
                    for (String groupId : groupIdList) {
                        addArticleToGroup(articleId, Long.parseLong(groupId.trim()));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating article: " + e.getMessage());
        }
    }
	
	public boolean delete_article(int current_role, int ID) throws Exception {
		if(current_role == 3) {
			System.out.println("Error: Student cannot delete articles.");
		}
		
	    String query = "DELETE FROM articles WHERE ID = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, ID); // Set article ID to delete
	        int row = pstmt.executeUpdate(); // Execute deletion
	        return row > 0; // Return true if successful
	    } catch (SQLException e) {
	        System.out.println("Error deleting article: " + e.getMessage());
	        return false;
	    }
	}

	public void listArticles() throws Exception {
	    String sql = "SELECT * FROM articles"; // SQL query to fetch all articles
	    try (Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) { // Execute the query
	
	        // Loop through each article and print its details
	        while (rs.next()) {
	            int id = rs.getInt("ID");
	            String title = rs.getString("Title");
	            String dis = rs.getString("Discription");
	            String body = rs.getString("Body");
	            String gid = rs.getString("Group_ids");
	            String ref = rs.getString("Reference_Articles");
	            String keywords = rs.getString("Keywords");
	            String level = rs.getString("Level");
	
	            // Display each attribute of the article
	            System.out.println("ID: " + id);
	            System.out.println(", Title: " + title);
	            System.out.println(", Description: " + dis);
	            System.out.println(", Body: " + body);
	            System.out.println(", Group IDs: " + gid);
	            System.out.println(", Reference Articles: " + ref);
	            System.out.println(", Keywords: " + keywords);
	            System.out.println(", Level: " + level);
	        }
	    }
	}
	
	public void backup(String path, int groupId) throws SQLException {
	    String query = "SELECT * FROM articles";
	    if (groupId != 0) {
	        query += " WHERE JSON_CONTAINS(Group_ids, JSON_QUOTE(?))"; // Filter by group ID if specified
	    }
	    try (PreparedStatement pstmt = connection.prepareStatement(query);
	         BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
	        if (groupId != 0) {
	            pstmt.setString(1, String.valueOf(groupId)); // Set group ID parameter
	        }
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                // Retrieve and write each article's attributes to the file
	                writer.write("ID: " + rs.getInt("ID"));
	                writer.newLine();
	                writer.write("Title: " + rs.getString("Title"));
	                writer.newLine();
	                writer.write("Description: " + rs.getString("Discription"));
	                writer.newLine();
	                writer.write("Body: " + rs.getString("Body"));
	                writer.newLine();
	                writer.write("Group IDs: " + rs.getString("Group_ids"));
	                writer.newLine();
	                writer.write("Reference Articles: " + rs.getString("Reference_Articles"));
	                writer.newLine();
	                writer.write("Keywords: " + rs.getString("Keywords"));
	                writer.newLine();
	                writer.write("Level: " + rs.getString("Level"));
	                writer.newLine();
	                writer.write("-----------------------");
	                writer.newLine();
	            }
	        }
	        System.out.println("Backup complete!");
	    } catch (IOException e) {
	        System.out.println("Error during backup: " + e.getMessage());
	    }
	}
	
	public void restore(String path, boolean overwrite) throws SQLException {
	    if (overwrite) {
	        try (Statement stmt = connection.createStatement()) {
	            stmt.executeUpdate("DELETE FROM articles"); // Clear existing articles if overwrite is true
	        } catch (SQLException e) {
	            System.out.println("Error clearing articles: " + e.getMessage());
	            return;
	        }
	    }
	
	    // Read from the backup file and insert each article back into the database
	    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
	        String line;
	        int id = 0;
	        String title = null, discription = null, body = null, group_ids = null, referenceArticles = null, keywords = null, level = null;
	
	        while ((line = reader.readLine()) != null) {
	            if (line.startsWith("ID: ")) {
	                id = Integer.parseInt(line.split(": ")[1]);
	            } else if (line.startsWith("Title: ")) {
	                title = line.split(": ")[1];
	            } else if (line.startsWith("Description: ")) {
	                discription = line.split(": ")[1];
	            } else if (line.startsWith("Body: ")) {
	                body = line.split(": ")[1];
	            } else if (line.startsWith("Group IDs: ")) {
	                group_ids = line.split(": ")[1];
	            } else if (line.startsWith("Reference Articles: ")) {
	                referenceArticles = line.split(": ")[1];
	            } else if (line.startsWith("Keywords: ")) {
	                keywords = line.split(": ")[1];
	            } else if (line.startsWith("Level: ")) {
	                level = line.split(": ")[1];
	            } else if (line.startsWith("-----------------------")) {
	                // Insert the article data once all fields are read
	                String query = "INSERT INTO articles (ID, Title, Discription, Body, Group_ids, Reference_Articles, Keywords, Level) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	                    pstmt.setLong(1, id);
	                    pstmt.setString(2, title);
	                    pstmt.setString(3, discription);
	                    pstmt.setString(4, body);
	                    pstmt.setString(5, group_ids);
	                    pstmt.setString(6, referenceArticles);
	                    pstmt.setString(7, keywords);
	                    pstmt.setString(8, level);
	                    pstmt.executeUpdate();
	                } catch (SQLException e) {
	                    System.out.println("Error inserting article: " + e.getMessage());
	                }
	            }
	        }
	        System.out.println("Restore completed!");
	    } catch (IOException e) {
	        System.out.println("Error restoring from file: " + e.getMessage());
	    }
	}
	
	public void updateUser(int userID, User user) {
	    String query = "UPDATE userinfo SET Username = ?, Password = ?, Name = ?, Email = ? WHERE ID = ?";
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setString(1, user.username); // Set username
	        statement.setString(2, user.password); // Set password
	        statement.setString(3, user.name);     // Set name
	        statement.setString(4, user.email);    // Set email
	        statement.setInt(5, userID);           // Set user ID to identify the row
	        int rowsAffected = statement.executeUpdate();
	
	        if (rowsAffected < 1) {
	            System.out.println("Error: user unsuccessfully updated.");
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void changeRole(int userID, int roleNum) {
	    String query = "UPDATE userinfo SET role_id = ? WHERE ID = ?";
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setInt(1, roleNum); // Set new role ID
	        statement.setInt(2, userID);  // Set user ID to identify the row
	        int rowsAffected = statement.executeUpdate();
	
	        if (rowsAffected < 1) {
	            System.out.println("Error: user role unsuccessfully updated.");
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public void deleteUserByID(User user) {
	    int ID = -1; // Variable to store user ID
	
	    String selectQuery = "SELECT ID FROM userinfo WHERE Username = ?";
	    String deleteQuery = "DELETE FROM userinfo WHERE Username = ?";
	
	    try (PreparedStatement selectStatement = this.connection.prepareStatement(selectQuery)) {
	        selectStatement.setString(1, user.username); // Set username to find ID
	
	        try (ResultSet result = selectStatement.executeQuery()) {
	            if (result.next()) {
	                ID = result.getInt("ID"); // Retrieve user ID if exists
	            } else {
	                System.out.println("Error: User not found in database.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    try (PreparedStatement deleteStatement = this.connection.prepareStatement(deleteQuery)) {
	        deleteStatement.setString(1, user.username); // Set username to delete user
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
	
	public String[] getUserInfo(User user) {
	    String query = "SELECT * FROM userinfo WHERE Username = ?";
	    String[] infoToReturn = new String[4]; // Array to store user info
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setString(1, user.username); // Set username to find user info
	
	        try (ResultSet result = statement.executeQuery()) {
	            if (result.next()) {
	                // Retrieve user info and store in array
	                infoToReturn[0] = result.getString("Username");
	                infoToReturn[1] = result.getString("Password");
	                infoToReturn[2] = result.getString("Name");
	                infoToReturn[3] = result.getString("Email");
	                int roles = result.getInt("role_id");
	                int ID = result.getInt("ID");
	
	                // Print user info to console
	                System.out.printf("Username: %s, Password: %s, Name: %s, Email: %s, Role: %d, ID: %d",
	                        infoToReturn[0], infoToReturn[1], infoToReturn[2], infoToReturn[3], roles, ID);
	            } else {
	                System.out.println("Error: User not found in database.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return infoToReturn; // Return user info array
	}
	
	public int login(User user) {
	    String query = "SELECT * FROM userinfo WHERE Username = ? AND Password = ?";
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setString(1, user.username); // Set username
	        statement.setString(2, user.password); // Set password
	
	        try (ResultSet result = statement.executeQuery()) {
	            if (result.next()) {
	                // Check email status and password prefix for OTP
	                String email = result.getString("Email");
	                if (email.equals("DEFAULT")) {
	                    return 0; // Default email
	                } else if (user.password.startsWith("OTP")) {
	                    return 2; // OTP-based login
	                } else {
	                    return 1; // Standard login
	                }
	            } else {
	                System.out.println("Error: User not found in database.");
	                return -1; // User not found
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return -1; // Default return for errors
	}
	
	public int getUserRole(User user) {
	    String query = "SELECT role_id FROM userinfo WHERE Username = ?";
	    int userRole = -1; // Default value for user role
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setString(1, user.username); // Set username to find role
	
	        try (ResultSet result = statement.executeQuery()) {
	            if (result.next()) {
	                userRole = result.getInt("role_id"); // Retrieve user role
	            } else {
	                System.out.println("Error: User not found in database.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return userRole; // Return user role
	}
	
	public void setPassword(User user, String password) {
	    String query = "UPDATE userinfo SET Password = ? WHERE Username = ?";
	    
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setString(1, password); // Set new password
	        statement.setString(2, user.username); // Set username to update password
	
	        int rowsAffected = statement.executeUpdate();
	        if (rowsAffected < 1) {
	            System.out.println("Error: user password unsuccessfully updated.");
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public String generateOTP(String unixTime) {
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(4);
	
	    // Generate random lowercase letters for OTP
	    for (int i = 0; i < 4; i++) {
	        int randomLimitedInt = 97 + (int) (random.nextFloat() * (122 - 97 + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	
	    // Concatenate "OTP" with unixTime and random string
	    String fullOTP = "OTP" + unixTime + buffer.toString();
	    return fullOTP; // Return generated OTP
	}
	
	public boolean checkPasswordExpired(String password) {
	    // Extract timestamp from password, convert to milliseconds
	    long unixTime = Long.parseLong(password.substring(3, 13));
	    long time = 1000L * unixTime;
	
	    // Check if current time has exceeded the timestamp
	    return System.currentTimeMillis() > time;
	}

	public String[] listAllUsers() {
	    String query = "SELECT Username FROM userinfo"; // SQL query to retrieve all usernames
	    String[] infoToReturn = new String[500]; // Array to hold up to 500 usernames
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        try (ResultSet result = statement.executeQuery()) {
	            int count = 0; // Counter for usernames
	
	            // Loop through each result row and add username to array
	            while (result.next()) {
	                String username = result.getString("Username");
	                infoToReturn[count] = username; // Store username
	                count += 1; // Increment count
	            }
	
	            if (count == 0) { // Check if no users were found
	                System.out.println("Error: User not found in database.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return infoToReturn; // Return array of usernames
	}
	
	public String getUserNameFromEmail(String email) {
	    String username = "nothing"; // Default return value if user not found
	    String query = "SELECT username FROM userinfo WHERE Email = ?";
	
	    try (PreparedStatement statement = this.connection.prepareStatement(query)) {
	        statement.setString(1, email); // Set email parameter
	
	        try (ResultSet result = statement.executeQuery()) {
	            if (result.next()) {
	                username = result.getString("Username"); // Retrieve username if found
	            } else {
	                System.out.println("Error: User not found in database.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	
	    return username; // Return found username or default value
	}
	
	public String getInviteCode(int index) {
	    return this.inviteCodes[index]; // Return invite code at specified index
	}
	 public String[] encryptArticleTable(String title, String discription, String body, String group_ids, String ref, String keywords, String level) throws Exception {
			//encrypting
					String etitle = EncryptionHelper.encrypt(title);	
					String ediscription = EncryptionHelper.encrypt(discription);	
					String ebody =EncryptionHelper.encrypt(body);	
					String egroup_ids = EncryptionHelper.encrypt(group_ids);	
					String eref = EncryptionHelper.encrypt(ref);	
					String ekeywords = EncryptionHelper.encrypt(keywords);	
					String elevel = EncryptionHelper.encrypt(level);	
					
				
				String insertArticle = "INSERT INTO articles (Title ,Discription , Body , Group_ids , Reference_Articles , Keywords , Level , IV ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				try /*(PreparedStatement pstmt = connection.prepareStatement(insertArticle)) */{
					/*pstmt.setString(1, etitle);
					pstmt.setString(2, ediscription);
					pstmt.setString(3, ebody);
					pstmt.setString(4, egroup_ids);
					pstmt.setString(5, eref);
					pstmt.setString(6, ekeywords);
					pstmt.setString(7, elevel);
					pstmt.executeUpdate();*/
					System.out.println("Article created successfully" );
					
					System.out.println("Titile:"+etitle );
					System.out.println("Discription:"+ediscription );
					System.out.println("Body:"+ebody );
					System.out.println("Group_ids:"+egroup_ids );
					System.out.println("References:" );
					System.out.println("Keywords:" +ekeywords);
					System.out.println("Level:"+elevel );
					
					return new String[] {etitle,ediscription,ebody,egroup_ids,eref,ekeywords,elevel};
					
					
				}catch(Exception e) {
					System.out.println("Error Restoring" +e.getMessage());
					return null;
				}
			}

   
	
    public void decryptArticleTable(String title, String discription, String body, String group_ids, String ref, String keywords, String level) throws Exception {
		//encrypting
				String dtitle = EncryptionHelper.decrypt(title);	
				String ddiscription = EncryptionHelper.decrypt(discription);	
				String dbody =EncryptionHelper.decrypt(body);	
				String dgroup_ids = EncryptionHelper.decrypt(group_ids);	
				String dref = EncryptionHelper.decrypt(ref);	
				String dkeywords = EncryptionHelper.decrypt(keywords);	
				String dlevel = EncryptionHelper.decrypt(level);	
				
				System.out.println("Article created successfully" );
				
				System.out.println("Titile:"+dtitle );
				System.out.println("Discription:"+ddiscription );
				System.out.println("Body:"+dbody );
				System.out.println("Group_ids:"+dgroup_ids );
				System.out.println("References:" );
				System.out.println("Keywords:" +dkeywords);
				System.out.println("Level:"+dlevel );
	
				
				
				
			
		}
	
	
}
