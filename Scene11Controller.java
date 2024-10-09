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

    @FXML
    public void initialize() {
        // Add listeners to validate inputs
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateEmail());
        reenterEmailField.textProperty().addListener((observable, oldValue, newValue) -> validateReenterEmail());
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        System.out.println("Save changes button clicked!");
        // Save changes logic here
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
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
        // No additional validation needed for email field at this time
        validateForm();
    }

    private void validateReenterEmail() {
        String email = emailField.getText();
        String reenterEmail = reenterEmailField.getText();
        if (reenterEmail.isEmpty()) {
            reenterEmailErrorText.setText("");
        } else if (!reenterEmail.equals(email)) {
            reenterEmailErrorText.setText("must match");
        } else {
            reenterEmailErrorText.setText("");
        }
        validateForm();
    }

    private void validateForm() {
        boolean isReenterEmailValid = reenterEmailErrorText.getText().isEmpty();
        boolean isAllFieldsFilled = !firstNameField.getText().trim().isEmpty() &&
                                    !lastNameField.getText().trim().isEmpty() &&
                                    !emailField.getText().trim().isEmpty() &&
                                    !reenterEmailField.getText().trim().isEmpty();

        saveChangesButton.setDisable(!(isReenterEmailValid && isAllFieldsFilled));
    }
}
