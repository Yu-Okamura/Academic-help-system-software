package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene14UpdateArticle {
	
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
    private TextField articleIDField;

    @FXML
    private Button proceedButton;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    public void initialize() {
        // Add listener to the articleIDField to enable the proceed button when not empty
        articleIDField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        // Enable the proceed button only if the articleIDField is not empty
        boolean isFieldFilled = !articleIDField.getText().trim().isEmpty();
        proceedButton.setDisable(!isFieldFilled);
    }

    @FXML
    private void handleProceed(ActionEvent event) {
        // Retrieve the article ID entered by the user
        String articleID = articleIDField.getText().trim();

        // For now, simply print the article ID to the console
        System.out.println("Selected Article ID: " + articleID + " is being updated.");

        // Switch to scene14UpdateArticle2.fxml
        switchScene(event, "scene14UpdateArticle2.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Adjust size as needed
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
