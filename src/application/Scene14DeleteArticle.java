package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene14DeleteArticle {
	
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
    private void handleBackup(ActionEvent event) {
        switchScene(event, "scene14Backup.fxml");
    }

    @FXML
    private void handleRestore(ActionEvent event) {
        switchScene(event, "scene14Restore.fxml");
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
    private TextField articleIDsField;

    @FXML
    private Button submitButton;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    public void initialize() {
        // Add listener to enable submit button only when the field is not empty
        articleIDsField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        // Enable the submit button only if the field is not empty
        boolean isFieldFilled = !articleIDsField.getText().trim().isEmpty();
        submitButton.setDisable(!isFieldFilled);
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        User passedUser = (User) stage.getUserData();
    	
        // Get the article IDs from the field
        String articleIDs = articleIDsField.getText().trim();
        
        // Print the article IDs to the console
        System.out.println("Article IDs: " + articleIDs + " are being deleted.");
        Manager admin = new Manager();
        admin.connect();
        for (String id : articleIDs.split(",")) {
            String articleID = id.trim(); // Trim to remove any extra spaces
            try {
				admin.delete_article(passedUser.current_role, Integer.parseInt(articleID));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Call delete_article for each ID
        }

        // Switch back to the main scene (scene14.fxml) after deletion
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
