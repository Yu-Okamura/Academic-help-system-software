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

    private int roleid; // Role ID to manage user permissions

    private User passedUser; // User object to carry user data

    @FXML
    public void initialize() {

        // Initialize the choice box with default text and disable the sign-in button initially
        roleChoiceBox.setValue("Select a role");
        signInButton.setDisable(true);

        // Set listener for mouse click to populate roles based on user role
        roleChoiceBox.setOnMouseClicked(this::handleChoiceBoxClick);

        // Add listener to validate the role selection and enable the sign-in button if valid
        roleChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validateChoice());
    }
    
    // Handles the mouse click event on the choice box
    private void handleChoiceBoxClick(MouseEvent event) {
        System.out.println("ChoiceBox clicked!");
        Manager admin = new Manager();
        admin.connect(); // Connect to the database
        
        // Get the current stage and retrieve user data
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        User passedUser = (User) stage.getUserData();
        
        this.passedUser = passedUser;
                
        // Get the role of the user and populate the choice box based on it
        int userRole = admin.getUserRole(passedUser);
        
        populateRoles(userRole);
    }

    // Populates the role options in the choice box based on the user's role ID
    private void populateRoles(int roleNum) {
        this.roleid = roleNum; // Set the role ID
        
        // Populate the choice box with different roles based on roleid
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
                roleChoiceBox.getItems().setAll("Select a role"); // Default text if no valid role ID
                break;
        }
    }

    // Handles the sign-in button click event
    @FXML
    private void handleSignIn(ActionEvent event) {
        System.out.println("Role selected: " + roleChoiceBox.getValue());
        
        // Load a different scene based on the user's role ID
        if (this.roleid == 4 || this.roleid == 5 || this.roleid == 7) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene13.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root;
            
            try {
                root = loader.load();
                stage.setUserData(this.passedUser);
                stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Print stack trace if an exception occurs
            }
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene10.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root;
            
            try {
                root = loader.load();
                stage.setUserData(this.passedUser);
                stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Print stack trace if an exception occurs
            }
        }
    }

    // Handles the sign-out link click event
    @FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml"); // Switch to Scene 1
    }

    // Validates the choice made in the choice box
    private void validateChoice() {
        // Enable the sign-in button only if a valid role is selected
        String selectedRole = roleChoiceBox.getValue();
        signInButton.setDisable("Select a role".equals(selectedRole));
    }

    // Method to switch scenes based on the event and the specified FXML file
    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Display the updated stage
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an I/O error occurs
        }
    }
}
