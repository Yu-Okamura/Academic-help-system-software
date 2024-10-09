package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene11Controller {

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    public void initialize() {
        // Populate the choice box with roles
        roleChoiceBox.getItems().addAll("Administrator", "Instructor", "Student");
        roleChoiceBox.setValue("Choose a role");

        // Add a listener to enable/disable the sign-in button based on the selection
        roleChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            validateChoice();
        });
    }

    @FXML
    private void handleSignIn(ActionEvent event) {
        String selectedRole = roleChoiceBox.getValue();
        if ("Administrator".equals(selectedRole)) {
            switchScene(event, "scene12.fxml");
        } else if ("Instructor".equals(selectedRole) || "Student".equals(selectedRole)) {
            switchScene(event, "scene10.fxml");
        }
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    private void validateChoice() {
        // Enable the sign-in button only if a valid role is selected
        String selectedRole = roleChoiceBox.getValue();
        signInButton.setDisable("Choose a role".equals(selectedRole));
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
