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
import java.sql.SQLException;

public class Scene14Backup {
	
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
    private TextField groupIDField, backupPathField;

    @FXML
    private Button proceedButton;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    public void initialize() {
        // Disable proceed button by default
        proceedButton.setDisable(true);

        // Add listeners to both fields to enable the button when both are filled
        groupIDField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());
        backupPathField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());
    }

    private void validateFields() {
        // Enable proceed button only if both fields are not empty
        boolean isGroupIDFilled = !groupIDField.getText().trim().isEmpty();
        boolean isBackupPathFilled = !backupPathField.getText().trim().isEmpty();
        proceedButton.setDisable(!(isGroupIDFilled && isBackupPathFilled));
    }

    @FXML
    private void handleProceed(ActionEvent event) {
        String groupID = groupIDField.getText().trim();
        String backupPath = backupPathField.getText().trim();

        try {
            int id = Integer.parseInt(groupID);
            if (id == 0) {
                System.out.println("All articles are being backed up.");
            } else if (id > 0) {
                System.out.println("Articles in group ID " + id + " are being backed up.");
            } else {
                System.out.println("Invalid group ID.");
            }

            // Print the backup path
            System.out.println("Backup path: " + backupPath);
            
            Manager admin = new Manager();
            admin.connect();
            try {
				admin.backup(backupPath, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // Switch to Scene14 (or previous scene)
            switchScene(event, "scene14.fxml");
        } catch (NumberFormatException e) {
            System.out.println("Invalid group ID.");
        }
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set the new scene dimensions as needed
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
