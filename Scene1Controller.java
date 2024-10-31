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
        // Add listeners to enable/disable the login button based on text input
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> validateInput());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateInput());
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        // Handle login logic here
        System.out.println("Log in button clicked!");
        Manager admin = new Manager();
        admin.connect();
        
        User userToTry = new User(usernameField.getText(), passwordField.getText(), "DEFAULT", "DEFAULT");
        
        int loginSuccess = admin.login(userToTry);
        
        switch (loginSuccess) {
        case 0:
        	firstLogin(event, userToTry);
        	break;
        case 1:
        	regularLogin(event, userToTry);
        	break;
        case -1:
        	loginFailed(event);
        	break;
        case 2:
        	otpLogin(event, userToTry);
        }
        
    }
    
    private void firstLogin(ActionEvent event, User userToPass) {
    	try {
            // Load the FXML for Scene 2
    		System.out.println("Login first");
    		
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene11.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            stage.setUserData(userToPass);
            
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void regularLogin(ActionEvent event, User user) {
    	try {
            // Load the FXML for Scene 2
    		Manager admin = new Manager();
            admin.connect();
            
            int userRole = admin.getUserRole(user);
            
            
            if (userRole >= 4) {
            	System.out.println("Login multi-role");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("scene12.fxml"));
                Parent root = loader.load();
                
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setUserData(user);
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            } else if (userRole == 1) {
            	System.out.println("Login admin");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene13.fxml"));
                Parent root = loader.load();

                // Get the current stage (window) and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            } else {
            	System.out.println("Login normal");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("scene10.fxml"));
                Parent root = loader.load();

                // Get the current stage (window) and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void otpLogin(ActionEvent event, User user) {
    	try {
            // Load the FXML for Scene 2
    		Manager admin = new Manager();
            admin.connect();
            
            System.out.println("Login OTP");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene8.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(user);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loginFailed(ActionEvent event) {
    	try {
    		System.out.println("Failed login");
            // Load the FXML for Scene 2
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene9.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUpLink(ActionEvent event) {
        try {
            // Load the FXML for Scene 2
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleForgotPasswordLink(ActionEvent event) {
        try {
            // Load the FXML for Scene 6
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene6.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateInput() {
        // Enable the login button only if both fields are not empty
        boolean isUsernameFilled = !usernameField.getText().trim().isEmpty();
        boolean isPasswordFilled = !passwordField.getText().trim().isEmpty();
        loginButton.setDisable(!(isUsernameFilled && isPasswordFilled));
    }
    
}
