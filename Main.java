package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Login and Sign Up System");
        loadScene("scene1.fxml"); // Load Scene 1 initially
    }

    public static void loadScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
        
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
