package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene10ViewArticle3 {

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
    private Button proceedButton;

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

    @FXML
    private void handleProceed(ActionEvent event) {
        switchScene(event, "scene10ViewArticle1.fxml");
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
