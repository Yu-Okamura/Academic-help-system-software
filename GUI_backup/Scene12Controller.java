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
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.Arrays;

public class Scene12Controller {

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink signOutLink;

    private int roleid;

    @FXML
    public void initialize() {
        // Initialize the choice box with default text
        roleChoiceBox.setValue("Select a role");
        signInButton.setDisable(true);

        // Populate the choice box based on the value of roleid
        populateRoles();

        // Add listener to enable the sign-in button when a valid role is selected
        roleChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> validateChoice());

        // Add a mouse click event to the ChoiceBox
        roleChoiceBox.setOnMouseClicked(this::handleChoiceBoxClick);
    }

    private void populateRoles() {
        roleid = 5; // Example value, set dynamically based on your application logic

        switch (roleid) {
            case 4:
                roleChoiceBox.getItems().setAll(Arrays.asList("Administrator", "Student"));
                break;
            case 5:
                roleChoiceBox.getItems().setAll(Arrays.asList("Administrator", "Instructor"));
                break;
            case 6:
                roleChoiceBox.getItems().setAll(Arrays.asList("Student", "Instructor"));
                break;
            case 7:
                roleChoiceBox.getItems().setAll(Arrays.asList("Administrator", "Student", "Instructor"));
                break;
            default:
                roleChoiceBox.getItems().setAll("Select a role");
                break;
        }
    }

    @FXML
    private void handleSignIn(ActionEvent event) {
        System.out.println("Role selected: " + roleChoiceBox.getValue());
        // Proceed with application logic based on the selected role
        // Save roleid value based on the choice.
        // For example: if Administrator = 1, Student = 2, Instructor = 3. Save this value in the main application class.
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    private void validateChoice() {
        // Enable the proceed button only if a valid role is selected
        String selectedRole = roleChoiceBox.getValue();
        signInButton.setDisable("Select a role".equals(selectedRole));
    }

    // Method to handle when the ChoiceBox is clicked
    private void handleChoiceBoxClick(MouseEvent event) {
        System.out.println("ChoiceBox clicked!");
        // Additional logic for when the ChoiceBox is clicked (if needed)
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
