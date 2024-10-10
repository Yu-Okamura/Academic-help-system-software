package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.Pattern;

public class Scene8Controller {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Text passwordErrorText;

    @FXML
    private Text confirmPasswordErrorText;

    @FXML
    public void initialize() {
        // Add listeners to validate inputs in real-time as the user types
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validatePassword());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateConfirmPassword());
    }

    // Handles the action when the "Change Password" button is clicked
    @FXML
    private void handleChangePassword(ActionEvent event) {
        try {
            Manager admin = new Manager();
            admin.connect(); // Connect to the database
            
            // Load the FXML for Scene 1 (Login Screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and retrieve the user data
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            User thisUser = (User) stage.getUserData(); // Get the user data from the stage
            
            admin.setPassword(thisUser, passwordField.getText()); // Set the new password for the user
            
            stage.setScene(new Scene(root, 600, 400)); // Set the scene with specified dimensions
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

    // Validates the password field based on length and character requirements
    private void validatePassword() {
        String password = passwordField.getText();
        if (password.isEmpty()) {
            passwordErrorText.setText("");
        } else if (password.length() < 8) {
            passwordErrorText.setText("must be at least 8 characters long");
        } else if (password.length() > 12) {
            passwordErrorText.setText("must be less than 13 characters");
        } else if (!Pattern.compile("[a-z]").matcher(password).find()) {
            passwordErrorText.setText("must contain a lowercase");
        } else if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            passwordErrorText.setText("must contain an uppercase");
        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {
            passwordErrorText.setText("must contain a number");
        } else {
            passwordErrorText.setText("");
        }
        validateForm(); // Validate the entire form after updating the password
    }

    // Validates the confirm password field to ensure it matches the password
    private void validateConfirmPassword() {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (!confirmPassword.isEmpty() && !confirmPassword.equals(password)) {
            confirmPasswordErrorText.setText("must match");
        } else {
            confirmPasswordErrorText.setText("");
        }
        validateForm(); // Validate the entire form after updating the confirm password field
    }

    // Validates the form to enable or disable the "Change Password" button
    private void validateForm() {
        boolean isPasswordValid = passwordErrorText.getText().isEmpty();
        boolean isConfirmPasswordValid = confirmPasswordErrorText.getText().isEmpty();
        boolean isAllFieldsFilled = !passwordField.getText().trim().isEmpty() && !confirmPasswordField.getText().trim().isEmpty();

        // Enable the button only if all fields are valid and filled
        changePasswordButton.setDisable(!(isPasswordValid && isConfirmPasswordValid && isAllFieldsFilled));
    }
}
