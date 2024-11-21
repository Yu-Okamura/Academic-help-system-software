package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.SQLException;

public class Scene14Restore {
	
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
        switchScene(event, "scene14SearchArticle1.fxml");
    }

    @FXML
    private void handleViewArticle(ActionEvent event) {
        switchScene(event, "scene14ViewArticle1.fxml");
    }

    @FXML
    private TextField backupPathField;

    @FXML
    private ChoiceBox<String> restoreOptionChoiceBox;

    @FXML
    private Button proceedButton;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    public void initialize() {
        // Set the items for the choice box
        restoreOptionChoiceBox.setItems(FXCollections.observableArrayList("Overwrite restore", "Additive restore"));
        restoreOptionChoiceBox.setValue("Select");

        // Disable proceed button by default
        proceedButton.setDisable(true);

        // Add listeners to the fields to enable the button when both are filled
        backupPathField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());
        restoreOptionChoiceBox.valueProperty().addListener((obs, oldVal, newVal) -> validateFields());
    }

    private void validateFields() {
        // Enable proceed button only if both fields are not empty and a choice is selected
        boolean isBackupPathFilled = !backupPathField.getText().trim().isEmpty();
        boolean isChoiceSelected = restoreOptionChoiceBox.getValue() != null && !restoreOptionChoiceBox.getValue().equals("Select");
        proceedButton.setDisable(!(isBackupPathFilled && isChoiceSelected));
    }

    @FXML
    private void handleProceed(ActionEvent event) {
        String backupPath = backupPathField.getText().trim();
        String restoreOption = restoreOptionChoiceBox.getValue();
        System.out.println(restoreOption);

        // Print the restore option and path
        System.out.println("Restore option selected: " + restoreOption);
        System.out.println("Backup path: " + backupPath);
        
        Manager admin = new Manager();
        admin.connect();
        boolean overwrite = false;
        if (restoreOption.equals("Overwrite restore")) {
        	overwrite = true;
        } else {
        	overwrite = false;
        }
        try {
			admin.restore(backupPath, overwrite);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Switch to Scene14 (or previous scene)
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
