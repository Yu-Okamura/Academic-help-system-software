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
        // Add listeners to validate inputs
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validatePassword());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateConfirmPassword());
    }

    @FXML
    private void handleChangePassword(ActionEvent event) {
        try {
        	Manager admin = new Manager();
        	admin.connect();
            
            // Load the FXML for Scene 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            User thisUser = (User) stage.getUserData();
            
            admin.setPassword(thisUser, passwordField.getText());
            
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        validateForm();
    }

    private void validateConfirmPassword() {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (!confirmPassword.isEmpty() && !confirmPassword.equals(password)) {
            confirmPasswordErrorText.setText("must match");
        } else {
            confirmPasswordErrorText.setText("");
        }
        validateForm();
    }

    private void validateForm() {
        boolean isPasswordValid = passwordErrorText.getText().isEmpty();
        boolean isConfirmPasswordValid = confirmPasswordErrorText.getText().isEmpty();
        boolean isAllFieldsFilled = !passwordField.getText().trim().isEmpty() && !confirmPasswordField.getText().trim().isEmpty();

        changePasswordButton.setDisable(!(isPasswordValid && isConfirmPasswordValid && isAllFieldsFilled));
    }
}
