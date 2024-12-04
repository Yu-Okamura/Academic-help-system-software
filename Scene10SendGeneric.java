package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene10SendGeneric {

    @FXML
    private TextField articleIdField;

    @FXML
    private TextArea genericMessageField;

    @FXML
    private Button submitButton;

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
        // Add listeners to enable or disable the submit button based on field content
        articleIdField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        genericMessageField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        // Enable the submit button only if both fields are not empty
        boolean formValid = !articleIdField.getText().trim().isEmpty() &&
                            !genericMessageField.getText().trim().isEmpty();
        submitButton.setDisable(!formValid);
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        // Print article ID and message to the console
        String articleId = articleIdField.getText().trim();
        String message = genericMessageField.getText().trim();
        System.out.println("Article ID: " + articleId);
        System.out.println("Message: " + message);

        // Switch to the Scene10Student.fxml
        switchScene(event, "scene10Student.fxml");
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    @FXML
    private void handleSendGenericMessage(ActionEvent event) {
        switchScene(event, "scene10SendGeneric.fxml");
    }

    @FXML
    private void handleSendSpecificMessage(ActionEvent event) {
        switchScene(event, "scene10SendSpecific.fxml");
    }

    @FXML
    private void handleFindArticles(ActionEvent event) {
        switchScene(event, "scene10SearchArticle1.fxml");
    }

    @FXML
    private void handleViewArticle(ActionEvent event) {
        switchScene(event, "scene10ViewArticle1.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
