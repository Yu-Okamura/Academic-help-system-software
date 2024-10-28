package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene14CreateGroup {
	
	@FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    @FXML
    private void handleCreateArticle(ActionEvent event) {
        switchScene(event, "scene14CreateArticle.fxml");
    }

    @FXML
    private void handleUpdateArticle(ActionEvent event) {
        switchScene(event, "scene14UpdateArticle.fxml");
    }

    @FXML
    private void handleListArticles(ActionEvent event) {
        switchScene(event, "scene14ListArticle.fxml");
    }

    @FXML
    private void handleDeleteArticle(ActionEvent event) {
        switchScene(event, "scene14DeleteArticle.fxml");
    }

    @FXML
    private void handleFullBackup(ActionEvent event) {
        switchScene(event, "scene14BackupFull.fxml");
    }

    @FXML
    private void handleGroupBackup(ActionEvent event) {
        switchScene(event, "scene14BackupGroup.fxml");
    }

    @FXML
    private void handleFullRestore(ActionEvent event) {
        switchScene(event, "scene14RestoreFull.fxml");
    }
    
    @FXML
    private void handleGroupRestore(ActionEvent event) {
        switchScene(event, "scene14RestoreGroup.fxml");
    }

    @FXML
    private void handleListGroups(ActionEvent event) {
        switchScene(event, "scene14ListGroup.fxml");
    }

    @FXML
    private void handleCreateGroup(ActionEvent event) {
        switchScene(event, "scene14CreateGroup.fxml");
    }

    @FXML
    private TextField groupNameField;

    @FXML
    private Button submitButton;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    public void initialize() {
        // Add listener to enable submit button only when the field is not empty
        groupNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        // Enable the submit button only if the field is not empty
        boolean isFieldFilled = !groupNameField.getText().trim().isEmpty();
        submitButton.setDisable(!isFieldFilled);
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        // Get the group name from the field
        String groupName = groupNameField.getText().trim();

        // Print the group name to the console
        System.out.println("New group: " + groupName + " is being added. make sure to assign a group ID automatically by the server.");

        // Switch back to the main scene (scene14.fxml) after submission
        switchScene(event, "scene14.fxml");
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
