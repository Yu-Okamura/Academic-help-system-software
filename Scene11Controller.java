package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene11Controller {

    // FXML fields for user input
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField preferredNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField reenterEmailField;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Text reenterEmailErrorText;

    // Initialize method to set up listeners for input validation
    @FXML
    public void initialize() {
        // Listeners to validate inputs when the text fields are updated
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateEmail());
        reenterEmailField.textProperty().addListener((observable, oldValue, newValue) -> validateReenterEmail());
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    // Method to handle the Save Changes button click event
    @FXML
    private void handleSaveChanges(ActionEvent event) {
        System.out.println("Save changes button clicked!");
        Manager admin = new Manager();
        admin.connect(); // Connect to the database
        
        // Get the current stage and retrieve user data
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        User passedUser = (User) stage.getUserData();
        
        // Get user role, ID, and information from the database
        int userRole = admin.getUserRole(passedUser);
        int userID = admin.getUserID(passedUser);
        String[] userInfo = admin.getUserInfo(passedUser);
        
        String fullName;
        
        // Build full name based on the presence of a middle name
        if (!middleNameField.getText().equals(null)) {
            fullName = firstNameField.getText() + " " + middleNameField.getText() + " " + lastNameField.getText();
        } else {
            fullName = firstNameField.getText() + " " + lastNameField.getText();
        }
        
        // Create a new user configuration with updated information
        User newUserConfig = new User(userInfo[0], userInfo[1], fullName, emailField.getText());
        
        // Update the user in the database
        admin.updateUser(userID, newUserConfig);
        
        Parent root;
        
        // Load a new scene based on the user's role
        if (userRole == 2 || userRole == 3) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene10.fxml"));
            try {
                root = loader.load();
                stage.setUserData(passedUser);
                stage.setScene(new Scene(root, 600, 400)); // Set new scene dimensions
                stage.show();
            } catch (IOException e) {
                // Print stack trace if an exception occurs
                e.printStackTrace();
            }
        } else if (userRole >= 4) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene12.fxml"));
            try {
                root = loader.load();
                stage.setUserData(passedUser);
                stage.setScene(new Scene(root, 600, 400)); // Set new scene dimensions
                stage.show();
            } catch (IOException e) {
                // Print stack trace if an exception occurs
                e.printStackTrace();
            }
        }
    }

    // Method to handle the Sign Out button click event
    @FXML
    private void handleSignOut(ActionEvent event) {
        try {
            // Load the FXML file for Scene 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            // Print stack trace if an I/O error occurs
            e.printStackTrace();
        }
    }

    // Method to validate the email field
    private void validateEmail() {
        // No additional validation needed for the email field at this time
        validateForm(); // Validate the overall form
    }

    // Method to validate the re-entered email field
    private void validateReenterEmail() {
        String email = emailField.getText();
        String reenterEmail = reenterEmailField.getText();
        if (reenterEmail.isEmpty()) {
            reenterEmailErrorText.setText(""); // Clear error text if empty
        } else if (!reenterEmail.equals(email)) {
            reenterEmailErrorText.setText("must match"); // Display error if emails don't match
        } else {
            reenterEmailErrorText.setText(""); // Clear error text if they match
        }
        validateForm(); // Validate the overall form
    }
    
    // Method to validate the form based on fields being filled and valid
    private void validateForm() {
        boolean isReenterEmailValid = reenterEmailErrorText.getText().isEmpty(); // Check if re-enter email field is valid
        boolean isAllFieldsFilled = !firstNameField.getText().trim().isEmpty() &&
                                    !lastNameField.getText().trim().isEmpty() &&
                                    !emailField.getText().trim().isEmpty() &&
                                    !reenterEmailField.getText().trim().isEmpty();

        // Enable or disable the save button based on validation
        saveChangesButton.setDisable(!(isReenterEmailValid && isAllFieldsFilled));
    }
}
