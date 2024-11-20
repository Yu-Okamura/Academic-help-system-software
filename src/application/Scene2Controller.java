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
import java.util.regex.Pattern;

public class Scene2Controller {

    @FXML
    private TextField inviteCodeField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Text usernameErrorText;

    @FXML
    private Text passwordErrorText;

    @FXML
    private Text confirmPasswordErrorText;

    @FXML
    public void initialize() {
        // Add listeners to validate inputs
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> validateUsername());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validatePassword());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateConfirmPassword());
        inviteCodeField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        // Handle sign-up logic here
        System.out.println("Sign up button clicked!");

        //Create manager object to handle signup
        Manager admin = new Manager();
        admin.connect();
        
        int numUsersBefore = admin.getUserCount();
		
		System.out.print("Before adding user: ");
		System.out.println(numUsersBefore);
		
		//Easy check for invite code & role correctness
		int inviteValidation = admin.validateInviteCode(inviteCodeField.getText());
        boolean inviteValid = inviteValidation != -1;
        
    	User newUser = new User(usernameField.getText(), passwordField.getText(), "DEFAULT", "DEFAULT");
    	boolean usernameUnique = (admin.getUserID(newUser)) == -1;
    	
    	
    	if (usernameUnique) {
    		if (inviteValid) {
    			admin.createUser(newUser, inviteCodeField.getText());
    			handleLoginLink(event);
    		} else {
    			handleInvalidCode(event);
    		}
    	} else {
    		handleNonUniqueUser(event);
    	}
    	
    	System.out.println(admin.getUserCount());
    }

    @FXML
    private void handleLoginLink(ActionEvent event) {
        try {
            // Load the FXML for Scene 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleNonUniqueUser(ActionEvent event) {
        try {
            // Load the FXML for Scene 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene5.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleInvalidCode(ActionEvent event) {
        try {
            // Load the FXML for Scene 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateUsername() {
        String username = usernameField.getText();
        if (username.isEmpty()) {
            usernameErrorText.setText("");
        } else if (!Character.isLetter(username.charAt(0))) {
            usernameErrorText.setText("must start with an alphabet");
        } else if (username.length() < 3) {
            usernameErrorText.setText("must be at least 3 characters long");
        } else if (username.length() > 15) {
            usernameErrorText.setText("must be less than 16 characters long");
        } else if (!username.matches("[A-Za-z0-9]+")) {
            usernameErrorText.setText("alphabets and numbers only");
        } else {
            usernameErrorText.setText("");
        }
        validateForm();
    }

    private void validatePassword() {
        String password = passwordField.getText();
        if (password.isEmpty()) {
            passwordErrorText.setText("");
        } else if (password.length() < 8) {
            passwordErrorText.setText("must be at least 8 characters long");
        } else if (password.length() > 12) {
            passwordErrorText.setText("must be less than 13 characters long");
        } else if (!Pattern.compile("[a-z]").matcher(password).find()) {
            passwordErrorText.setText("must contain a lowercase");
        } else if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            passwordErrorText.setText("must contain an uppercase");
        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {
            passwordErrorText.setText("must contain a number");
        } else {
            passwordErrorText.setText("");
        }
        validateForm();
    }

    private void validateConfirmPassword() {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (confirmPassword.isEmpty()) {
            confirmPasswordErrorText.setText("");
        } else if (!confirmPassword.equals(password)) {
            confirmPasswordErrorText.setText("must match");
        } else {
            confirmPasswordErrorText.setText("");
        }
        validateForm();
    }

    private void validateForm() {
        boolean isUsernameValid = usernameErrorText.getText().isEmpty();
        boolean isPasswordValid = passwordErrorText.getText().isEmpty();
        boolean isConfirmPasswordValid = confirmPasswordErrorText.getText().isEmpty();
        boolean isAllFieldsFilled = !inviteCodeField.getText().trim().isEmpty() &&
                                    !usernameField.getText().trim().isEmpty() &&
                                    !passwordField.getText().trim().isEmpty() &&
                                    !confirmPasswordField.getText().trim().isEmpty();

        signUpButton.setDisable(!(isUsernameValid && isPasswordValid && isConfirmPasswordValid && isAllFieldsFilled));
    }
}
