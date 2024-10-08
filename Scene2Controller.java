package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.Pattern;

public class Scene2Controller {

    @FXML
    private TextField inviteCodeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField confirmEmailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Button signUpButton;

    @FXML
    private Text emailErrorText;

    @FXML
    private Text passwordErrorText;
    
    @FXML
    private Text usernameErrorText;

    @FXML
    public void initialize() {
        // Add listeners to validate inputs
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateEmail());
        confirmEmailField.textProperty().addListener((observable, oldValue, newValue) -> validateEmail());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validatePassword());
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> validateUsername());
        inviteCodeField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        // Handle sign-up logic here
        System.out.println("Sign up button clicked!");
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

    private void validateEmail() {
        String email = emailField.getText();
        String confirmEmail = confirmEmailField.getText();

        if (!confirmEmail.isEmpty() && !confirmEmail.equals(email)) {
            emailErrorText.setText("needs to match");
        } else {
            emailErrorText.setText("");
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
    
    private void validateUsername() {
        String username = usernameField.getText();
        if (username.isEmpty()) {
            usernameErrorText.setText("");
        } else if (!Character.isLetter(username.charAt(0))) {
            usernameErrorText.setText("First character must be an alphabet");
        } else if (username.length() < 3) {
            usernameErrorText.setText("must be at least 3 characters long");
        } else if (username.length() > 12) {
            usernameErrorText.setText("must be less than 13 characters long");
        } else if (!username.matches("[A-Za-z0-9]+")) {
            usernameErrorText.setText("alphabets and numbers only");
        } else {
            usernameErrorText.setText("");
        }
        validateForm();
    }

    private void validateForm() {
        boolean isEmailValid = emailErrorText.getText().isEmpty();
        boolean isPasswordValid = passwordErrorText.getText().isEmpty();
        boolean isUsernameValid = usernameErrorText.getText().isEmpty();
        boolean isAllFieldsFilled = !inviteCodeField.getText().trim().isEmpty() &&
                                    !emailField.getText().trim().isEmpty() &&
                                    !confirmEmailField.getText().trim().isEmpty() &&
                                    !passwordField.getText().trim().isEmpty() &&
                                    !usernameField.getText().trim().isEmpty();

        signUpButton.setDisable(!(isEmailValid && isPasswordValid && isUsernameValid && isAllFieldsFilled));
    }
}