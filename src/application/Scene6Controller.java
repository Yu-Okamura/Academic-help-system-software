package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.regex.Pattern;

public class Scene6Controller {

    @FXML
    private TextField emailField;

    @FXML
    private Button requestPassButton;

    @FXML
    private Hyperlink loginLink;
    
    private Manager manager = new Manager();

    @FXML
    public void initialize() {
        // Add a listener to the email field to validate input in real-time
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            validateEmail(newValue);
        });
        manager.connect();
    }

    @FXML
    private void handleRequestPass(ActionEvent event) {
    	
    	long unixTime = (System.currentTimeMillis() + 900000 / 1000L);
        String unixString = Long.toString(unixTime);

        String newPassword = manager.generateOTP(unixString);
        String username = manager.getUserNameFromEmail(emailField.getText());

        User requester = new User(username, "", "", "");

        manager.setPassword(requester, newPassword);
        
        try {
            // Load the FXML for Scene 7
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene7.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginLink(ActionEvent event) {
        try {
            // Load the FXML for Scene 1
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateEmail(String email) {
        // Regular expression for validating email
        String emailPattern = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        boolean isValid = Pattern.matches(emailPattern, email);

        // Enable or disable the button based on email validity
        requestPassButton.setDisable(!isValid);
    }
}
