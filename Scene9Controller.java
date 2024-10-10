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

    // Handles the action when the login button is clicked
    @FXML
    private void handleLoginButton(ActionEvent event) {
        switchScene(event, "scene1.fxml"); // Navigate to the login screen
    }

    // Handles the action when the forgot password link is clicked
    @FXML
    private void handleForgotPasswordLink(ActionEvent event) {
        switchScene(event, "scene6.fxml"); // Navigate to the forgot password screen
    }

    // Handles the action when the sign-up link is clicked
    @FXML
    private void handleSignUpLink(ActionEvent event) {
        switchScene(event, "scene2.fxml"); // Navigate to the sign-up screen
    }

    // Switches the scene based on the provided FXML file
    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            // Load the FXML for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set the scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
    }
}
