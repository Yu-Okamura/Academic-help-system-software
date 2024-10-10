package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import javafx.scene.input.MouseEvent;

public class Scene12Controller {

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink signOutLink;

    private int roleid; // Max modify here
    
    private User passedUser;

    @FXML
    public void initialize() {

    	// Initialize the choice box with default text
        roleChoiceBox.setValue("Select a role");
        signInButton.setDisable(true);

        // Populate the choice box based on the value of roleid
        roleChoiceBox.setOnMouseClicked(this::handleChoiceBoxClick);

        // Add listener to enable the sign-in button when a valid role is selected
        roleChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validateChoice());
    }
    
    private void handleChoiceBoxClick(MouseEvent event) {
        System.out.println("ChoiceBox clicked!");
        Manager admin = new Manager();
        admin.connect();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        User passedUser = (User) stage.getUserData();
        
        this.passedUser = passedUser;
        		
        int userRole = admin.getUserRole(passedUser);
        
        populateRoles(userRole);
    }

    private void populateRoles(int roleNum) { // role id independent.
      
        this.roleid = roleNum; // its an example. Max modify here.

        switch (this.roleid) {
            case 4:
                roleChoiceBox.getItems().setAll(Arrays.asList("Administrator", "Student"));
                break;
            case 5:
                roleChoiceBox.getItems().setAll(Arrays.asList("Administrator", "Instructor"));
                break;
            case 6:
                roleChoiceBox.getItems().setAll(Arrays.asList("Student", "Instructor"));
                break;
            case 7:
                roleChoiceBox.getItems().setAll(Arrays.asList("Administrator", "Student", "Instructor"));
                break;
            default:
                roleChoiceBox.getItems().setAll("Select a role");
                break;
        }
    }

    @FXML
    private void handleSignIn(ActionEvent event) {
        System.out.println("Role selected: " + roleChoiceBox.getValue());
        // Proceed with application logic based on selected role
        // save roleid value based on the choice. 
        //if admin =1, student =2, inst=3. Keep this value in main.
        //4,5,7
        if (this.roleid == 4 || this.roleid == 5 || this.roleid == 7) {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("scene13.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root;
            
        	try {
    			root = loader.load();
    			stage.setUserData(this.passedUser);
                
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } else {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("scene10.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root;
            
        	try {
    			root = loader.load();
    			stage.setUserData(this.passedUser);
                
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        
        

    }

    @FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    private void validateChoice() {
        // Enable the proceed button only if a valid role is selected
        String selectedRole = roleChoiceBox.getValue();
        signInButton.setDisable("Select a role".equals(selectedRole));
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            // Load the FXML for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       
}
