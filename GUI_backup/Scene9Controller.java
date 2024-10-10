package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene9Controller {

    @FXML
    private void handleLoginButton(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    @FXML
    private void handleForgotPasswordLink(ActionEvent event) {
        switchScene(event, "scene6.fxml");
    }

    @FXML
    private void handleSignUpLink(ActionEvent event) {
        switchScene(event, "scene2.fxml");
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
