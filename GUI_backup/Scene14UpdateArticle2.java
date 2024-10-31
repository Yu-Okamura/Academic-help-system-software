package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;

public class Scene14UpdateArticle2 {
	
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
    private ChoiceBox<String> levelChoiceBox;

    @FXML
    private TextField titleField, keywordsField, linksField, groupIDsField;

    @FXML
    private TextArea descriptionField, bodyField;

    @FXML
    private Button submitButton;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    public void initialize() {
        // Populate levelChoiceBox with levels
        levelChoiceBox.getItems().addAll("Select", "Beginner", "Intermediate", "Advanced", "Expert");
        levelChoiceBox.setValue("Select");

        // Simulate loading data from server (mock data for now)
        loadArticleData();
        
        // Print "article data is now displayed" 1 millisecond after initialization
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> displayArticle()));
        timeline.setCycleCount(1);
        timeline.play();

        // Add listener to validate input and enable submit button
        titleField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        descriptionField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        keywordsField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        linksField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        groupIDsField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        bodyField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        levelChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }
    
    private void displayArticle() {
        System.out.println("article data is now displayed");
    }

    private void validateForm() {
        // Enable the submit button only if all fields are filled and level is selected
        boolean allFieldsFilled = !titleField.getText().trim().isEmpty()
                && !descriptionField.getText().trim().isEmpty()
                && !keywordsField.getText().trim().isEmpty()
                && !linksField.getText().trim().isEmpty()
                && !groupIDsField.getText().trim().isEmpty()
                && !bodyField.getText().trim().isEmpty()
                && !"Select".equals(levelChoiceBox.getValue());

        submitButton.setDisable(!allFieldsFilled);
    }

    private void loadArticleData() {
        // Example data loaded into fields (ur welcome)
        titleField.setText("Example Article");
        levelChoiceBox.setValue("Beginner");
        descriptionField.setText("This is an example article made for screencasting.");
        keywordsField.setText("Example, CSE360, Phase2");
        linksField.setText("https://canvas.asu.edu/courses/193728/assignments/5331995?return_to=https%3A%2F%2Fcanvas.asu.edu%2Fcalendar%23view_name%3Dmonth%26view_start%3D2024-10-30");
        groupIDsField.setText("5"); // Dont forget to hide level ids (1-4) from here
        bodyField.setText("During the last three development phases, the team must produce (and update if needed) the requirements, architecture, design, code, test cases, and demonstrations showing how the requirements flow gracefully through all the software development stages.  Unless special arrangements are made, all four phases of the application development must be written with a Graphical User Interface using JavaFX.");
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        String keywords = keywordsField.getText().trim();
        String links = linksField.getText().trim();
        String groupIDs = groupIDsField.getText().trim();
        String body = bodyField.getText().trim();

        // Determine the corresponding level value
        String level = levelChoiceBox.getValue();
        String levelGroupId = "";
        switch (level) {
            case "Beginner":
                levelGroupId = "1";
                break;
            case "Intermediate":
                levelGroupId = "2";
                break;
            case "Advanced":
                levelGroupId = "3";
                break;
            case "Expert":
                levelGroupId = "4";
                break;
        }

        // Append the levelGroupId in front of the group IDs entered by the user
        String updatedGroupIDs = levelGroupId;
        if (!groupIDs.isEmpty()) {
            updatedGroupIDs += ", " + groupIDs;
        }

        // Print all the information to the console
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Level: " + level);
        System.out.println("Keywords: " + keywords);
        System.out.println("Links: " + links);
        System.out.println("Group IDs: " + updatedGroupIDs);
        System.out.println("Body: " + body);

        // After printing the info, switch back to scene14.fxml
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
