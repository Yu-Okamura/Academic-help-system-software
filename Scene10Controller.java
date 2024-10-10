package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene10Controller {

    // This method is triggered when the sign-out button is pressed
    @FXML
    private void handleSignOut(ActionEvent event) {
        try {
            // Load the FXML file for Scene 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) where the event occurred and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Set the scene dimensions
            stage.show(); // Show the stage with the new scene
        } catch (IOException e) {
            // Print the stack trace if an I/O error occurs
            e.printStackTrace();
        }
    }
}
