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
        // Add listeners to validate inputs when text fields change
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> validateUsername());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validatePassword());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateConfirmPassword());
        inviteCodeField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        // Handle sign-up logic when the sign-up button is clicked
        System.out.println("Sign up button clicked!");

        // Create Manager object to handle sign-up operations
        Manager admin = new Manager();
        admin.connect(); // Connect to the database
        
        int numUsersBefore = admin.getUserCount(); // Get the user count before adding the new user
		
        System.out.print("Before adding user: ");
        System.out.println(numUsersBefore);
		
        // Validate the invite code and check its correctness
        int inviteValidation = admin.validateInviteCode(inviteCodeField.getText());
        boolean inviteValid = inviteValidation != -1;
        
        // Create a new User object with default values
        User newUser = new User(usernameField.getText(), passwordField.getText(), "DEFAULT", "DEFAULT");
        boolean usernameUnique = (admin.getUserID(newUser)) == -1; // Check if the username is unique
    	
        // Check if the username is unique and the invite code is valid before creating the user
        if (usernameUnique) {
            if (inviteValid) {
                admin.createUser(newUser, inviteCodeField.getText()); // Create the user in the database
                handleLoginLink(event); // Navigate to the login screen
            } else {
                handleInvalidCode(event); // Handle invalid invite code
            }
        } else {
            handleNonUniqueUser(event); // Handle non-unique username
        }
    	
        System.out.println(admin.getUserCount()); // Print the user count after adding the user
    }

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
            e.printStackTrace();
        }
    }
    
    private void handleNonUniqueUser(ActionEvent event) {
        try {
            // Load the FXML for Scene 5 (Username Not Unique Screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene5.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleInvalidCode(ActionEvent event) {
        try {
            // Load the FXML for Scene 3 (Invalid Code Screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validates the username input field
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
        validateForm(); // Validate the form after updating the username
    }

    // Validates the password input field
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
        validateForm(); // Validate the form after updating the password
    }

    // Validates the confirm password field to ensure it matches the password
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
        validateForm(); // Validate the form after updating the confirm password field
    }

    // Validates the entire form to enable or disable the sign-up button
    private void validateForm() {
        boolean isUsernameValid = usernameErrorText.getText().isEmpty();
        boolean isPasswordValid = passwordErrorText.getText().isEmpty();
        boolean isConfirmPasswordValid = confirmPasswordErrorText.getText().isEmpty();
        boolean isAllFieldsFilled = !inviteCodeField.getText().trim().isEmpty() &&
                                    !usernameField.getText().trim().isEmpty() &&
                                    !passwordField.getText().trim().isEmpty() &&
                                    !confirmPasswordField.getText().trim().isEmpty();

        // Enable the sign-up button if all fields are valid and filled
        signUpButton.setDisable(!(isUsernameValid && isPasswordValid && isConfirmPasswordValid && isAllFieldsFilled));
    }
}
