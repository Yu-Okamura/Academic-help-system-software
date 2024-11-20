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

    // FXML elements
    @FXML private ChoiceBox<Integer> generateInviteChoiceBox, editRoleChoiceBox;
    @FXML private Hyperlink generateLink, resetLink, setRoleLink, deleteLink, listLink, signOutLink;
    @FXML private Text inviteCodeText, otpText;
    @FXML private TextField resetEmailField, editRoleEmailField, deleteEmailField, confirmDeleteField, unixTimeField;

    private Manager admin = new Manager(); // Manager instance for admin actions

    @FXML
    public void initialize() {
        // Initialize choice boxes with possible role IDs
        generateInviteChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
        editRoleChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7);

        // Disable links initially until valid input is provided
        generateLink.setDisable(true);
        resetLink.setDisable(true);
        setRoleLink.setDisable(true);
        deleteLink.setDisable(true);
        
        admin.connect(); // Establish connection to the database through the Manager

        // Set listeners to enable or disable links based on field values
        generateInviteChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> generateLink.setDisable(newVal == null));
        resetEmailField.textProperty().addListener((obs, oldVal, newVal) -> validateResetFields());
        unixTimeField.textProperty().addListener((obs, oldVal, newVal) -> validateResetFields());
        editRoleChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> validateSetRole());
        editRoleEmailField.textProperty().addListener((obs, oldVal, newVal) -> validateSetRole());
        deleteEmailField.textProperty().addListener((obs, oldVal, newVal) -> deleteLink.setDisable(newVal.trim().isEmpty()));
        confirmDeleteField.textProperty().addListener((obs, oldVal, newVal) -> {
            // Confirm deletion if user types "Yes"
            if ("Yes".equals(newVal.trim())) {
                System.out.println("User deleted");
            }
        });
    }

    // Handle generating an invite code based on selected role ID
    @FXML
    private void handleGenerateInvite(ActionEvent event) {
        int selectedRoleId = generateInviteChoiceBox.getValue();
        String inviteCode = this.admin.getInviteCode(selectedRoleId); // Generate invite code
        inviteCodeText.setText(inviteCode); // Display the generated code
    }

    // Handle resetting the password and displaying OTP
    @FXML
    private void handleResetPassword(ActionEvent event) {
        String password = admin.generateOTP(unixTimeField.getText()); // Generate OTP
        User userToChange = new User(resetEmailField.getText(), "", "", ""); // Create user with provided email
        admin.setPassword(userToChange, password); // Set new password
        otpText.setText(password); // Display the OTP
    }

    // Handle setting a role for a specific user
    @FXML
    private void handleSetRole(ActionEvent event) {
        System.out.println("User role updated"); 
        User userToChange = new User(editRoleEmailField.getText(), "", "", ""); // Create user with email
        int idToChange = admin.getUserID(userToChange); // Get user ID by email
        admin.changeRole(idToChange, editRoleChoiceBox.getValue()); // Update role based on selection
    }

    // Handle user deletion
    @FXML
    private void handleDeleteUser(ActionEvent event) {
        confirmDeleteField.setVisible(true); // Show confirmation field
        User userToChange = new User(deleteEmailField.getText(), "", "", ""); // Create user with email
        admin.deleteUserByID(userToChange); // Delete user by ID
    }

    // Handle listing all users (stubbed for example)
    @FXML
    private void handleListUsers(ActionEvent event) {
        System.out.println("Listed all users"); // Placeholder action
    }

    // Handle signing out and switching scenes
    @FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml"); // Switch to login scene
    }

    // Validate fields for resetting password
    private void validateResetFields() {
        boolean isEmailFilled = !resetEmailField.getText().trim().isEmpty();
        boolean isUnixTimeFilled = !unixTimeField.getText().trim().isEmpty();
        resetLink.setDisable(!(isEmailFilled && isUnixTimeFilled)); // Enable reset link if fields are filled
    }

    // Validate fields for setting user role
    private void validateSetRole() {
        boolean isEmailFilled = !editRoleEmailField.getText().trim().isEmpty();
        boolean isRoleSelected = editRoleChoiceBox.getValue() != null;
        setRoleLink.setDisable(!(isEmailFilled && isRoleSelected)); // Enable role link if fields are valid
    }

    // Switch to a new scene specified by fxmlFile
    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile)); // Load new scene
            Parent root = loader.load();

            // Get current stage and set new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle any loading errors
        }
    }
}
