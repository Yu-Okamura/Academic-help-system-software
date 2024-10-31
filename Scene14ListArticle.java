package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene14ListArticle {
	
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
    private TextField groupIDField;

    @FXML
    private Button proceedButton;

    @FXML
    private Hyperlink signOutLink;
    
    private String groupID;

    @FXML
    public void initialize() {
        // Add listener to the groupIDField to enable the proceed button when not empty
        groupIDField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        // Enable the proceed button only if the groupIDField is not empty
        boolean isFieldFilled = !groupIDField.getText().trim().isEmpty();
        proceedButton.setDisable(!isFieldFilled);
    }

    @FXML
    private void handleProceed(ActionEvent event) {
        // Retrieve the group ID entered by the user
        this.groupID = groupIDField.getText().trim();

        if (groupID.equals("0")) {
            System.out.println("Listing all articles");
        } else {
            System.out.println("Listing group " + groupID);
        }

        // Switch to scene14ListArticle2.fxml
        switchScene(event, "scene14ListArticle2.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            // Load the FXML for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            
            if (fxmlFile.equals("scene14ListArticle2.fxml")) {
            	Scene14ListArticle2 controller = (Scene14ListArticle2) loader.getController();
            	controller.loadArticleData(Integer.valueOf(this.groupID));
            }

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
