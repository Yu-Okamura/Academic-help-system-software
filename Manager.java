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
    	String table2 = "create table userinfo(ID int primary key, Username varchar(25) unique key, Password varchar(25), Name varchar(25), Email varchar(50), role_id int);";
    	String table3 = "create table articles(ID bigint AUTO_INCREMENT primary key, Title varchar(255),Discription text, Body text, Group_ids varchar(255), Reference_Articles varchar(255), Keywords varchar(255), Level varchar(30));";
    	String table4 = "create table group_table(EntryID BIGINT AUTO_INCREMENT PRIMARY KEY, ID bigint DEFAULT NULL, Name varchar(255), ArticleID bigint NULL);";
    	String table5 = "create table user_roles(user_id int, role_id int, PRIMARY KEY(user_id, role_id));";
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
	
	public int validateInviteCode(String invite_code) {
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
	
	public String [][] getArticleArray(){
		ArrayList<String[]> articles = new ArrayList<>();
		
		String query = "SELECT ID, Title, Group_ids, Keywords, Body, Reference_Articles, Level FROM articles";
		
		try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(query)){
			while(rs.next()) {
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
		catch (SQLException e) {
	        System.out.println("Error retrieving articles: " + e.getMessage());
	        e.printStackTrace();
		}
		
		return articles.toArray(new String[0][]);
	}
	
	public String[] getArticleByID(long identifier) {
	    String[] article = new String[6];
	    String query = "SELECT ID, Title, Keywords, Body, Reference_Articles, Level FROM articles WHERE ID = ?";

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	    	statement.setLong(1, identifier);
	    	try (ResultSet rs = statement.executeQuery()) {
	    		 if (rs.next()) {
	                 article[0] = String.valueOf(rs.getLong("ID"));
	                 article[1] = rs.getString("Title");
	                 //article[2] = rs.getString("Authors");
	                 article[2] = rs.getString("Keywords");
	                 article[3] = rs.getString("Body");
	                 article[4] = rs.getString("Reference_Articles");
	                 article[5] = rs.getString("Level");
	    		 }
	    		 else {
	                 System.out.println("Article with ID " + identifier + " not found.");
	                 return null;
	             }
	    	}
	    }
	    catch (SQLException e) {
	        System.out.println("Error retrieving article: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return article;
	}
	
    public String[][] searchArticles(String searchTerm) {
        ArrayList<String[]> matchingArticles = new ArrayList<>();
        String[] terms = searchTerm.split(" ");
        
        StringBuilder queryBuilder = new StringBuilder("SELECT ID, Title, Authors, Keywords, Body, Reference_Articles, Level FROM articles WHERE ");
        
        for (int i = 0; i < terms.length; i++) {
        	
            if (i > 0) {
            	queryBuilder.append(" AND ");
            }
            
            queryBuilder.append("(")
            .append("LOWER(Title) LIKE ? OR ")
            .append("LOWER(Authors) LIKE ? OR ")
            .append("LOWER(Keywords) LIKE ? OR ")
            .append("LOWER(Body) LIKE ?")
            .append(")");
        }
        try (PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
        	int paramIndex = 1;
            for (String term : terms) {
                String searchPattern = "%" + term.toLowerCase() + "%";
                for (int j = 0; j < 4; j++) {
                    statement.setString(paramIndex++, searchPattern);
                }
            }     

	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                String[] article = new String[7];
	                article[0] = rs.getString("ID");
	                article[1] = rs.getString("Title");
	                article[2] = rs.getString("Authors");
	                article[3] = rs.getString("Keywords");
	                article[4] = rs.getString("Body");
	                article[5] = rs.getString("Reference_Articles");
	                article[6] = rs.getString("Level");
	                matchingArticles.add(article);
	            }
	        }
        }
        
    	catch (SQLException e) {
    		System.out.println("Error searching articles: " + e.getMessage());
    		e.printStackTrace();
    	}
        
        return matchingArticles.toArray(new String[0][]);
    }


    public void update_article(long identifier, String title, String discription, String body, String group_ids, String
    		ref, String keywords, String level) throws Exception {
    		
        	String update_article = "UPDATE articles SET Title = ?, Discription = ?, Body = ?, Group_ids = ?, Reference_Articles = ?, Keywords = ?, Level = ? WHERE ID = ?";
        	
        	try (PreparedStatement pstmt = connection. prepareStatement(update_article)) {
	    		pstmt.setString(1, title);
	    		pstmt.setString(2, discription);
	    		pstmt.setString(3, body);
	    		pstmt.setString(4, group_ids);
	    		pstmt.setString(5, ref);
	    		pstmt.setString(6, keywords);
	    		pstmt.setString(7, level);
	    		pstmt.setLong(8, identifier);
	    		pstmt.executeUpdate();
	    		System.out.println("Article updated successfully");
    		}
    		catch(SQLException e) {
    			System.out.println("Error Restoring" +e.getMessage());
    		}
    }
    //if the group name is a level [Beginner-Expert] then ID is 1-4 otherwise it finds the highest ID value + 1
    public void createGroup(String group_name) {
        Long id = null;
        String query;

        // set specific ID if the group name is predefined
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

        // If id is null (not predefined group) start at 5 or find the next ID
        if (id == null) {
            String query_max = "SELECT COALESCE(MAX(ID), 0) FROM group_table";
            long max_ID = 0;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query_max)) {
                
                if (resultSet.next()) {
                    max_ID = resultSet.getLong(1);
                }
                if(max_ID == 0) {
                	max_ID = 5;
                }
                else {
                	max_ID += 1;
                }
                
                id = max_ID;
            } 
            catch (SQLException e) {
                System.out.println("Error retrieving max ID: " + e.getMessage());
                return;
            }
        }

        // insert the new group with default or calculated ID
        query = "INSERT INTO group_table (ID, Name) VALUES (?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.setString(2, group_name);
            statement.executeUpdate();
            System.out.println("Group '" + group_name + "' created with ID " + id);
        } catch (SQLException e) {
            System.out.println("Error creating group: " + e.getMessage());
        }
    }
    //adds an article to a group: checks if there is a null row and adds it there if not, makes a new entry with same
    // group name and ID and inserts article there
    public void addArticleToGroup(long article_id, long group_id) {
        // check if there is an existing null row for the specified group
        String queryUpdateNull = "UPDATE group_table SET ArticleID = ? WHERE ID = ? AND ArticleID IS NULL LIMIT 1";
        String queryInsert = "INSERT INTO group_table (ID, Name, ArticleID) VALUES (?, ?, ?)";

        // retrieve the group name or use a placeholder if unavailable
        String groupName = getGroupNameById(group_id);

        try (PreparedStatement statementUpdateNull = connection.prepareStatement(queryUpdateNull)) {
            statementUpdateNull.setLong(1, article_id);
            statementUpdateNull.setLong(2, group_id);
            
            int rowsAffected = statementUpdateNull.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Article " + article_id + " added to existing null row in Group ID " + group_id);
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error updating null row: " + e.getMessage());
            return;
        }

        // insert a new row if no null row is available
        try (PreparedStatement statementInsert = connection.prepareStatement(queryInsert)) {
            statementInsert.setLong(1, group_id);
            statementInsert.setString(2, groupName);
            statementInsert.setLong(3, article_id);
            statementInsert.executeUpdate();
            System.out.println("Article " + article_id + " added to Group ID " + group_id);
        } catch (SQLException e) {
            System.out.println("Error adding article to group: " + e.getMessage());
        }
    }

    private String getGroupNameById(long group_id) {
        String query = "SELECT Name FROM group_table WHERE ID = ? LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, group_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Name");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving group name: " + e.getMessage());
        }
        //if no name found
        return "Unknown";
    }
    
	
 public void create_article(String title, String discription, String body, String group_ids, String ref, String keywords, String level) throws Exception {
		
		
		String insertArticle = "INSERT INTO articles (Title ,Discription , Body , Group_ids , Reference_Articles , Keywords , Level ) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertArticle)) {
			pstmt.setString(1, title);
			pstmt.setString(2, discription);
			pstmt.setString(3, body);
			pstmt.setString(4, group_ids.toString());
			pstmt.setString(5, ref);
			pstmt.setString(6, keywords);
			pstmt.setString(7, level);
			pstmt.executeUpdate();
			System.out.println("Article created successfully" );
		}catch(SQLException e) {
			System.out.println("Error Restoring" +e.getMessage());
		}
	}
   public boolean delete_article(int ID) throws Exception {	
	
		String query = "DELETE FROM articles WHERE ID = ? ";
	
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setInt(1, ID);
			
			
			int row = pstmt.executeUpdate();
			return row >0 ;
			
	  }
		catch(SQLException e) {
			System.out.println("Error Restoring" +e.getMessage());
			return false;
		}
	}
   public void listArticles() throws Exception{
		String sql = "SELECT * FROM articles"; 
		try(Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql)){ 

		while(rs.next()) { 
			
			int id = rs.getInt("ID");
			String  title = rs.getString("Title"); 
			String dis = rs.getString("Discription");  
			String body = rs.getString("Body"); 
			String  gid = rs.getString("Group_ids"); 
			String ref = rs.getString("Reference_Articles");  
			String keywords = rs.getString("Keywords");
			String level = rs.getString("Level");
			
			

			 
			System.out.println("ID: " + id); 
			System.out.println(", Title: " + title); 
			System.out.println(", Discription: " + dis); 
			System.out.println(", Body: " + body); 
			System.out.println(", Group_ids: " + gid); 
			System.out.println(", Reference_Articles: " + ref);
			System.out.println(", Keywords: " + keywords); 
			System.out.println(", level: " + level); 
		}
		}
			
		} 
		 public void backup(String path, int groupId) throws SQLException {
				String query = "SELECT * FROM articles";
				
				if (groupId != 0) {
					query += " WHERE JSON_CONTAINS(Group_ids, JSON_QUOTE(?))";
				}
				try (PreparedStatement pstmt = connection.prepareStatement(query);
					 
					 BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
				     if (groupId !=0) {
				    	 pstmt.setString(1, String.valueOf(groupId));
				     }
				     try (ResultSet rs = pstmt.executeQuery()){
						while (rs.next()) {
							long id = rs.getInt("ID");
							String  title = rs.getString("Title"); 
							String discription = rs.getString("Discription"); 
							String body = rs.getString("Body");  
							String group_ids = rs.getString("Group_ids"); 
							String  referenceArticles = rs.getString("Reference_Articles"); 
							String keywords = rs.getString("Keywords");  
							String level = rs.getString("Level");
							
							
							//writing in the file
							writer.write("ID: "+id);
							writer.newLine();
							writer.write("Title: "+title);
							writer.newLine();
							writer.write("Discription: "+discription);
							writer.newLine();
							writer.write("Body: "+body);
							writer.newLine();
							writer.write("Group_ids: "+group_ids);
							writer.newLine();
							writer.write("Reference Articles: "+referenceArticles);
							writer.newLine();
							writer.write("Keywords: "+keywords);
							writer.newLine();
							writer.write("Level: "+level);
							writer.newLine();
							writer.write("-----------------------");
							writer.newLine();
							
							
							
							
						}	
							
							
							
					}
					System.out.println("Backup complete!");
				} catch(IOException e) {
					System.out.println("Error during backup:" + e.getMessage());
				}


			}
		 
		 public void restore(String path, boolean overwrite) throws SQLException {
				String line; 
				int id = 0;
				String title = null;
				String discription= null;
				String body= null;
				String group_ids = null;
				String referenceArticles= null;
				String keywords = null;
				String level = null;
				
				if (overwrite) {
					try (Statement stmt = connection.createStatement()){
						stmt.executeUpdate("DELETE FROM articles");
						
					}catch(SQLException e) {
						System.out.println("Error "+e.getMessage());
						return;
					}
					
				}
				
				
				try (BufferedReader reader = new BufferedReader(new FileReader(path)) ){
					
					while ((line = reader.readLine())!=null) {
						if (line.startsWith("ID: ")) {
							id = Integer.parseInt(line.split(": ")[1]);
						}
						else if(line.startsWith("Title: ")) {
							title = line.split(": " )[1];
						}
						else if(line.startsWith("Discription: ")) {
							discription = line.split(": " )[1];
						}
						else if(line.startsWith("Body: ")) {
							body = line.split(": " )[1];
						}
						else if(line.startsWith("Group_ids: ")) {
							group_ids = line.split(": " )[1];
						}
						else if(line.startsWith("Reference Articles: ")) {
							referenceArticles = line.split(": " )[1];
						}
						else if(line.startsWith("Keywords: ")) {
							body = line.split(": " )[1];
						}
						else if(line.startsWith("Level: ")) {
							level = line.split(": " )[1];
						}
						
						else if(line.startsWith("-----------------------")) {
							
							String query = "INSERT INTO articles (ID , Title, Discription, Body , Group_ids , Reference_Articles , Keywords , Level ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
							}catch(SQLException e) {
								System.out.println("Error "+e.getMessage());
								
							}
						}
						}
					System.out.println("Restore completed!");
				} catch(IOException e) {
					System.out.println("Error Restoring" +e.getMessage());
				}


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
							} else if (user.password.substring(0, 3).equals("OTP")){
								return 2;
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
			
			public String getUserNameFromEmail(String email) {
		        //if no user exists
		        String username = "nothing";

		        String query = "SELECT username FROM userinfo WHERE Email = ?";

		        try(PreparedStatement statement = this.connection.prepareStatement(query)){

		            statement.setString(1, email);

		            try(ResultSet result = statement.executeQuery()){
		                if(result.next()) {
		                    username = result.getString("Username");
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

		        return username;
		    }
			
			public String getInviteCode(int index) {
				return this.inviteCodes[index];
			}
	
}