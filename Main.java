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
        loadScene("scene14.fxml"); // Load the initial scene
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
//        Manager admin = new Manager();
//		admin.connect();
//		admin.createDatabaseAndTables();
		
		//User newUser = new User("mwolf", "Abc123!g", "Max Wolf", "mwolf2@cognify.com");
		
		//admin.createUser(newUser, "1234");
		//System.out.println(admin.getUserCount());
    	
//    	Manager admin = new Manager();
//    	admin.connect();
//    	String[] userList = admin.listAllUsers();
//    	for (int i = 0; i < 10; i++) {
//    		System.out.println(userList[i]);
//    	}
    	
    }
}
