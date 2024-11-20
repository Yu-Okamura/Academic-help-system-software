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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene11Controller {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField preferredNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField reenterEmailField;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Text reenterEmailErrorText;

    @FXML
    public void initialize() {
        // Add listeners to validate inputs
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateEmail());
        reenterEmailField.textProperty().addListener((observable, oldValue, newValue) -> validateReenterEmail());
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    @FXML
    private void handleSaveChanges(ActionEvent event) {
        System.out.println("Save changes button clicked!");
        Manager admin = new Manager();
        admin.connect();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        User passedUser = (User) stage.getUserData();
        		
        int userRole = admin.getUserRole(passedUser);
        int userID = admin.getUserID(passedUser);
        String[] userInfo = admin.getUserInfo(passedUser);
        
        String fullName;
        
        if (!middleNameField.getText().equals(null)) {
        	fullName = firstNameField.getText() + " " + middleNameField.getText() + " " + lastNameField.getText();
        } else {
        	fullName = firstNameField.getText() + " " + lastNameField.getText();
        }
        
        User newUserConfig = new User(userInfo[0], userInfo[1], fullName, emailField.getText());
        
        admin.updateUser(userID, newUserConfig);
        
		Parent root;
		
		if (userRole == 2 || userRole == 3) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("scene10.fxml"));
			try {
				root = loader.load();
				stage.setUserData(passedUser);
	            
	            stage.setScene(new Scene(root, 600, 400));
	            stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (userRole >= 4) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("scene12.fxml"));
        	try {
				root = loader.load();
				stage.setUserData(passedUser);
	            
	            stage.setScene(new Scene(root, 600, 400));
	            stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
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

    private void validateEmail() {
        // No additional validation needed for email field at this time
        validateForm();
    }

    private void validateReenterEmail() {
        String email = emailField.getText();
        String reenterEmail = reenterEmailField.getText();
        if (reenterEmail.isEmpty()) {
            reenterEmailErrorText.setText("");
        } else if (!reenterEmail.equals(email)) {
            reenterEmailErrorText.setText("must match");
        } else {
            reenterEmailErrorText.setText("");
        }
        validateForm();
    }
    

    private void validateForm() {
        boolean isReenterEmailValid = reenterEmailErrorText.getText().isEmpty();
        boolean isAllFieldsFilled = !firstNameField.getText().trim().isEmpty() &&
                                    !lastNameField.getText().trim().isEmpty() &&
                                    !emailField.getText().trim().isEmpty() &&
                                    !reenterEmailField.getText().trim().isEmpty();

        saveChangesButton.setDisable(!(isReenterEmailValid && isAllFieldsFilled));
    }
}