package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene14ViewArticle1 {

    @FXML
    private TextField articleIDField;

    @FXML
    private Button proceedButton;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    private Hyperlink genericMessageLink;

    @FXML
    private Hyperlink specificMessageLink;

    @FXML
    private Hyperlink findArticlesLink;

    @FXML
    private Hyperlink viewArticleLink;

    @FXML
    public void initialize() {
        // Add a listener to enable the proceed button only if the articleIDField is not empty
        articleIDField.textProperty().addListener((observable, oldValue, newValue) -> {
            proceedButton.setDisable(newValue.trim().isEmpty());
        });
    }

    @FXML
    private void handleProceed(ActionEvent event) {
        // Print the article ID to the console
        String articleID = articleIDField.getText().trim();
        System.out.println("Article ID: " + articleID);

        // Proceed to the next scene or handle logic
        switchScene(event, "scene14ViewArticle2.fxml");
        // or 
        //switchScene(event, "scene14ViewArticle3.fxml");
        //if error (no article found or access denied.
    }

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
    private void handleFindArticles(ActionEvent event) {
        // Switch to the scene for searching articles
        switchScene(event, "scene14SearchArticle1.fxml");
    }

    @FXML
    private void handleViewArticle(ActionEvent event) {
        // Switch to the scene for viewing a specific article
        switchScene(event, "scene14ViewArticle1.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
