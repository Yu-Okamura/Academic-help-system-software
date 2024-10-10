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
    private TextField resetEmailField, editRoleEmailField, deleteEmailField, confirmDeleteField;

    @FXML
    public void initialize() {
        // Initialize choice boxes with role IDs
        generateInviteChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
        editRoleChoiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7);

        // Disable links initially
        generateLink.setDisable(true);
        resetLink.setDisable(true);
        setRoleLink.setDisable(true);
        deleteLink.setDisable(true);

        // Set listeners
        generateInviteChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> generateLink.setDisable(newVal == null));
        resetEmailField.textProperty().addListener((obs, oldVal, newVal) -> resetLink.setDisable(newVal.trim().isEmpty()));
        editRoleChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> validateSetRole());
        editRoleEmailField.textProperty().addListener((obs, oldVal, newVal) -> validateSetRole());
        deleteEmailField.textProperty().addListener((obs, oldVal, newVal) -> deleteLink.setDisable(newVal.trim().isEmpty()));
        confirmDeleteField.textProperty().addListener((obs, oldVal, newVal) -> {
            if ("Yes".equals(newVal.trim())) {
                System.out.println("User deleted");
            }
        });
    }

    @FXML
    private void handleGenerateInvite(ActionEvent event) {
        inviteCodeText.setText("code: XYZ123"); //example
    }

    @FXML
    private void handleResetPassword(ActionEvent event) {
        otpText.setText("OTP: 123456"); //example
    }

    @FXML
    private void handleSetRole(ActionEvent event) {
        System.out.println("User role updated"); 
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        confirmDeleteField.setVisible(true);
    }

    @FXML
    private void handleListUsers(ActionEvent event) {
        System.out.println("Listed all users");
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    private void validateSetRole() {
        boolean isEmailFilled = !editRoleEmailField.getText().trim().isEmpty();
        boolean isRoleSelected = editRoleChoiceBox.getValue() != null;
        setRoleLink.setDisable(!(isEmailFilled && isRoleSelected));
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            // Load the FXML for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}