package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene1Controller {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        // Add listeners to enable/disable the login button based on text input in username and password fields
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> validateInput());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateInput());
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        // Handle login logic when login button is clicked
        System.out.println("Log in button clicked!");
        Manager admin = new Manager();
        admin.connect(); // Connect to the database
        
        // Create a User object with input username and password
        User userToTry = new User(usernameField.getText(), passwordField.getText(), "DEFAULT", "DEFAULT");
        
        // Attempt login and handle based on the result
        int loginSuccess = admin.login(userToTry);
        
        switch (loginSuccess) {
            case 0:
                firstLogin(event, userToTry); // First login scenario
                break;
            case 1:
                regularLogin(event, userToTry); // Regular login scenario
                break;
            case -1:
                loginFailed(event); // Failed login scenario
                break;
            case 2:
                otpLogin(event, userToTry); // OTP login scenario
                break;
        }
    }
    
    // Handles first login scenario
    private void firstLogin(ActionEvent event, User userToPass) {
        try {
            System.out.println("Login first");
            // Load the FXML for Scene 11
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene11.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(userToPass); // Pass user data to the next scene
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handles regular login scenario
    private void regularLogin(ActionEvent event, User user) {
        try {
            Manager admin = new Manager();
            admin.connect();
            
            int userRole = admin.getUserRole(user); // Retrieve the role of the user
            
            // Navigate based on user role
            if (userRole >= 4) {
                System.out.println("Login multi-role");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("scene12.fxml"));
                Parent root = loader.load();
                
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setUserData(user); // Pass user data to the next scene
                stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
                stage.show();
            } else if (userRole == 1) {
                System.out.println("Login admin");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene13.fxml"));
                Parent root = loader.load();

                // Get the current stage (window) and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
                stage.show();
            } else {
                System.out.println("Login normal");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("scene10.fxml"));
                Parent root = loader.load();

                // Get the current stage (window) and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handles OTP login scenario
    private void otpLogin(ActionEvent event, User user) {
        try {
            Manager admin = new Manager();
            admin.connect();
            
            System.out.println("Login OTP");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene8.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(user); // Pass user data to the next scene
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handles login failure scenario
    private void loginFailed(ActionEvent event) {
        try {
            System.out.println("Failed login");
            // Load the FXML for Scene 9
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene9.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUpLink(ActionEvent event) {
        try {
            // Load the FXML for Scene 2 (Sign Up)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleForgotPasswordLink(ActionEvent event) {
        try {
            // Load the FXML for Scene 6 (Forgot Password)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene6.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validates the input fields to enable or disable the login button
    private void validateInput() {
        boolean isUsernameFilled = !usernameField.getText().trim().isEmpty();
        boolean isPasswordFilled = !passwordField.getText().trim().isEmpty();
        loginButton.setDisable(!(isUsernameFilled && isPasswordFilled)); // Enable login button if both fields are filled
    }
}
