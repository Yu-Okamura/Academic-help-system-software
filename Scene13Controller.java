package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene13Controller {

    @FXML
    private ChoiceBox<Integer> generateInviteChoiceBox, editRoleChoiceBox;
    @FXML
    private Hyperlink generateLink, resetLink, setRoleLink, deleteLink, listLink, signOutLink;
    @FXML
    private Text inviteCodeText, otpText;
    @FXML
    private TextField resetEmailField, editRoleEmailField, deleteEmailField, confirmDeleteField, unixTimeField;

    private Manager admin = new Manager(); // Instance of Manager to handle database operations

    @FXML
    public void initialize() {
        // Initialize choice boxes with available role IDs
        generateInviteChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
        editRoleChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7);

        // Disable links initially until inputs are validated
        generateLink.setDisable(true);
        resetLink.setDisable(true);
        setRoleLink.setDisable(true);
        deleteLink.setDisable(true);
        
        admin.connect(); // Connect to the database when the controller initializes

        // Set listeners for the choice boxes and text fields
        generateInviteChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> generateLink.setDisable(newVal == null));
        resetEmailField.textProperty().addListener((obs, oldVal, newVal) -> validateResetFields());
        unixTimeField.textProperty().addListener((obs, oldVal, newVal) -> validateResetFields());
        editRoleChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> validateSetRole());
        editRoleEmailField.textProperty().addListener((obs, oldVal, newVal) -> validateSetRole());
        deleteEmailField.textProperty().addListener((obs, oldVal, newVal) -> deleteLink.setDisable(newVal.trim().isEmpty()));
        confirmDeleteField.textProperty().addListener((obs, oldVal, newVal) -> {
            if ("Yes".equals(newVal.trim())) {
                System.out.println("User deleted"); // Log message when confirmation is "Yes"
            }
        });
    }

    @FXML
    private void handleGenerateInvite(ActionEvent event) {
        // Generate an invite code based on the selected role ID from the choice box
        int selectedRoleId = generateInviteChoiceBox.getValue();
        String inviteCode = this.admin.getInviteCode(selectedRoleId);
        inviteCodeText.setText(inviteCode); // Display the generated invite code in the UI
    }

    @FXML
    private void handleResetPassword(ActionEvent event) {
        // Generate a one-time password based on the input unix time
        String password = admin.generateOTP(unixTimeField.getText());
        User userToChange = new User(resetEmailField.getText(), "", "", "");
        admin.setPassword(userToChange, password); // Update the user's password in the database
        otpText.setText(password); // Display the generated OTP
    }

    @FXML
    private void handleSetRole(ActionEvent event) {
        // Update the role of the user based on the input email and selected role ID
        System.out.println("User role updated"); // Log message for debugging
        User userToChange = new User(editRoleEmailField.getText(), "", "", "");
        int idToChange = admin.getUserID(userToChange); // Retrieve user ID
        admin.changeRole(idToChange, editRoleChoiceBox.getValue()); // Change the user's role in the database
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        // Show confirmation field and delete user if confirmed
        confirmDeleteField.setVisible(true); // Make the confirmation field visible
        User userToChange = new User(deleteEmailField.getText(), "", "", "");
        admin.deleteUserByID(userToChange); // Delete the user from the database
    }

    @FXML
    private void handleListUsers(ActionEvent event) {
        // Handle event to list all users (implementation to be added)
        System.out.println("Listed all users");
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
        // Switch to the sign-out scene
        switchScene(event, "scene1.fxml");
    }

    // Validate fields for password reset
    private void validateResetFields() {
        boolean isEmailFilled = !resetEmailField.getText().trim().isEmpty();
        boolean isUnixTimeFilled = !unixTimeField.getText().trim().isEmpty();
        resetLink.setDisable(!(isEmailFilled && isUnixTimeFilled)); // Enable link only if both fields are filled
    }

    // Validate fields for setting user roles
    private void validateSetRole() {
        boolean isEmailFilled = !editRoleEmailField.getText().trim().isEmpty();
        boolean isRoleSelected = editRoleChoiceBox.getValue() != null;
        setRoleLink.setDisable(!(isEmailFilled && isRoleSelected)); // Enable link only if both conditions are met
    }

    // Method to switch to a different scene based on the provided FXML file
    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            // Load the FXML for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set the scene with specified dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an I/O error occurs
        }
    }
}
