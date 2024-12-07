package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene14SearchArticle1 {

    @FXML
    private CheckBox beginnerCheckBox;

    @FXML
    private CheckBox intermediateCheckBox;

    @FXML
    private CheckBox advancedCheckBox;

    @FXML
    private CheckBox expertCheckBox;

    @FXML
    private TextField groupNameField;

    @FXML
    private TextArea keywordsField;

    @FXML
    private Button submitButton;
    
    private int selectedLevel;

    @FXML
    public void initialize() {
        // Add listeners to enable or disable the submit button based on selections
        beginnerCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> validateSelection());
        intermediateCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> validateSelection());
        advancedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> validateSelection());
        expertCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> validateSelection());
    }

    private void validateSelection() {
        // Enable the submit button if at least one level is selected
        boolean anyLevelSelected = beginnerCheckBox.isSelected() ||
                                   intermediateCheckBox.isSelected() ||
                                   advancedCheckBox.isSelected() ||
                                   expertCheckBox.isSelected();
        submitButton.setDisable(!anyLevelSelected);
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        // Determine selected levels
//        StringBuilder levelIds = new StringBuilder();
//        if (beginnerCheckBox.isSelected()) levelIds.append("1, ");
//        if (intermediateCheckBox.isSelected()) levelIds.append("2, ");
//        if (advancedCheckBox.isSelected()) levelIds.append("3, ");
//        if (expertCheckBox.isSelected()) levelIds.append("4, ");
//
//        // Remove the trailing comma and space, if present
//        if (levelIds.length() > 0) levelIds.setLength(levelIds.length() - 2);
//
//        // Retrieve optional group name
//        String groupName = groupNameField.getText().trim();
//        String groupNameOutput = groupName.isEmpty() ? "" : "Group name: " + groupName;
//
//        // Retrieve optional keywords or phrases
//        String keywords = keywordsField.getText().trim();
//        String keywordsOutput = keywords.isEmpty() ? "" : "Keywords: " + keywords;
//
//        // Print the output
//        System.out.println("Level IDs: " + levelIds);
//        if (!groupNameOutput.isEmpty()) System.out.println(groupNameOutput);
//        if (groupNameOutput.isEmpty()) System.out.println("Group not specified.");
//        if (!keywordsOutput.isEmpty()) System.out.println(keywordsOutput);
//        if (keywordsOutput.isEmpty()) System.out.println("No keywords or phrases specified.");]
      if (beginnerCheckBox.isSelected()) selectedLevel = 1;
      if (intermediateCheckBox.isSelected()) selectedLevel = 2;
      if (advancedCheckBox.isSelected()) selectedLevel = 3;
      if (expertCheckBox.isSelected()) selectedLevel = 4;

        // Switch to the next scene
        switchScene(event, "scene14SearchArticle2.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            //Parent root = loader.load();
            Scene14SearchArticle2 ctrlr = new Scene14SearchArticle2();
            ctrlr.levelToPull = selectedLevel;
            ctrlr.searchKeywords = keywordsField.getText().trim();
            loader.setController(ctrlr);
            // Get the current stage
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            //stage.setUserData(selectedLevel);
            stage.setScene(new Scene(root, 600, 400)); // Adjust size as necessary
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        switchScene(event, "scene14SearchArticle1.fxml");
    }

    @FXML
    private void handleViewArticle(ActionEvent event) {
        switchScene(event, "scene14ViewArticle1.fxml");
    }
}
