package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    private static Stage primaryStage; // Primary window for the application

    @Override
    public void start(Stage stage) {
        primaryStage = stage; // Set the primary stage
        primaryStage.setTitle("Login and Sign Up System"); // Set window title
        loadScene("scene1.fxml"); // Load the initial scene
    }

    // Method to load a new scene using an FXML file
    public static void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Parent root = loader.load(); // Load FXML layout
            Scene scene = new Scene(root, 600, 400); // Set scene size
            primaryStage.setScene(scene); // Display the scene on the primary stage
            primaryStage.show(); // Show the primary stage
        } catch (Exception e) {
            e.printStackTrace(); // Print error if loading fails
        }
    }

    public static void main(String[] args) {
        launch(args); // Launch the application
        
        //Fresh db run ONLY
        Manager admin = new Manager();
		admin.connect();
		
		//admin.createDatabaseAndTables();
		
		User newUser = new User("mwolf", "Abc123!g", "Max Wolf", "mwolf2@cognify.com");				
	
		admin.createUser(newUser, "1234");
		
		admin.addArticleToGroup(1, 5);
		
		admin.addUserToGroup(1, 5, 1, true);
		
		boolean canModify = admin.canUserModifyArticle(1, 1);
		
		System.out.println("CANUSER modify??? " + canModify);
		
		//admin.createGroup("SECRET GROUP");
		
		//admin.addUserToGroup(5, 5, 3, true);
		
		
		
		

		
//		try {
//			admin.create_article(1, "Greate Article", "Description", "bodusier", "1", "Heres a reference", "these are the keys", "1");
//		}catch(Exception e) {
//			System.out.println("FOOK");
//		}
//		try {
//			admin.create_article(1, "Greate Article", "Description", "bodusier", "1,2", "Heres a reference", "these are the keys", "2");
//		}catch(Exception e) {
//			System.out.println("FOOK");
//		}
//		try {
//			admin.create_article(1, "Greate Article", "Description", "bodusier", "1,2", "Heres a reference", "these are the keys", "2");
//		}catch(Exception e) {
//			System.out.println("FOOK");
//		}
		
		//public void create_article(int current_role, String title, String discription, String body, String group_ids, String ref, String keywords, String level) throws Exception {

//		
		//User newUser = new User("mwolf", "Abc123!g", "Max Wolf", "mwolf2@cognify.com");
		
		
		//admin.giveRole(1, 2);
				
//		
		//admin.createUser(newUser, "1234");
		//System.out.println(admin.getUserCount());
    	
    	//Manager admin = new Manager();
    	//admin.connect();
    	//String[] userList = admin.listAllUsers();
    	//for (int i = 0; i < 10; i++) {
    		//System.out.println(userList[i]);
    	//}
    	
    }
}
