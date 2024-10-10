package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    // Static reference to the primary stage of the application
    private static Stage primaryStage;

    // Override the start method, entry point for JavaFX applications
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Login and Sign Up System");
        loadScene("scene1.fxml"); // Load the initial scene (Scene 1)
    }

    // Method to load and display a scene based on the given FXML file
    public static void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile)); // Load FXML file
            Parent root = loader.load(); // Load the scene graph
            Scene scene = new Scene(root, 600, 400); // Create a new scene with specified dimensions
            primaryStage.setScene(scene); // Set the scene to the primary stage
            primaryStage.show(); // Display the stage
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace in case of an error
        }
    }

    // Main method, the application's entry point
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
        
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
