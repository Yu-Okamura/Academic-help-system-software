package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene5Controller {

    // Handles the action of navigating back to the registration screen
    @FXML
    private void handleBackToRegistration(ActionEvent event) {
        try {
            // Load the FXML for Scene 2 (Registration Screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
    }

    // Handles the action of navigating to the login screen
    @FXML
    private void handleLoginLink(ActionEvent event) {
        try {
            // Load the FXML for Scene 1 (Login Screen)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set scene dimensions
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
    }
}
