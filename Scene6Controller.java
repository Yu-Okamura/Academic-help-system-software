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
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.Pattern;

public class Scene6Controller {

    @FXML
    private TextField emailField;

    @FXML
    private Button requestPassButton;

    @FXML
    private Hyperlink loginLink;
    
    private Manager manager = new Manager(); // Instance of Manager to handle database operations

    @FXML
    public void initialize() {
        // Add a listener to the email field to validate input in real-time as the user types
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateEmail(newValue);
        });
        manager.connect(); // Connect to the database upon initialization
    }

    // Handles the action when the "Request Password" button is clicked
    @FXML
    private void handleRequestPass(ActionEvent event) {
        // Generate Unix time 15 minutes in the future and convert to string
        long unixTime = (System.currentTimeMillis() + 900000 / 1000L);
        String unixString = Long.toString(unixTime);

        // Generate a new OTP based on the Unix time
        String newPassword = manager.generateOTP(unixString);
        String username = manager.getUserNameFromEmail(emailField.getText());

        // Create a User object for the requester
        User requester = new User(username, "", "", "");

        // Set the new password for the user
        manager.setPassword(requester, newPassword);
        
        try {
            // Load the FXML for Scene 7 (Password Reset Confirmation)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene7.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
    }

    // Handles the action of navigating to the login screen when the login link is clicked
    @FXML
    private void handleLoginLink(ActionEvent event) {
        try {
            // Load the FXML for Scene 1 (Login Screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
    }

    // Validates the email field based on the provided email pattern
    private void validateEmail(String email) {
        // Regular expression for validating email format
        String emailPattern = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        boolean isValid = Pattern.matches(emailPattern, email);

        // Enable or disable the button based on email validity
        requestPassButton.setDisable(!isValid);
    }
}
