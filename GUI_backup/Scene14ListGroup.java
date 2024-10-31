package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scene14ListGroup {
	
	@FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    @FXML
    private void handleCreateArticle(ActionEvent event) {
        switchScene(event, "scene14CreateArticle.fxml");
    }

    @FXML
    private void handleUpdateArticle(ActionEvent event) {
        switchScene(event, "scene14UpdateArticle.fxml");
    }

    @FXML
    private void handleListArticles(ActionEvent event) {
        switchScene(event, "scene14ListArticle.fxml");
    }

    @FXML
    private void handleDeleteArticle(ActionEvent event) {
        switchScene(event, "scene14DeleteArticle.fxml");
    }

    @FXML
    private void handleBackup(ActionEvent event) {
        switchScene(event, "scene14Backup.fxml");
    }

    @FXML
    private void handleRestore(ActionEvent event) {
        switchScene(event, "scene14Restore.fxml");
    }

    @FXML
    private void handleListGroups(ActionEvent event) {
        switchScene(event, "scene14ListGroup.fxml");
    }

    @FXML
    private void handleCreateGroup(ActionEvent event) {
        switchScene(event, "scene14CreateGroup.fxml");
    }

    @FXML
    private TableView<Group> groupTable;

    @FXML
    private TableColumn<Group, String> nameColumn;

    @FXML
    private TableColumn<Group, String> idColumn;

    @FXML
    private TableColumn<Group, String> articlesColumn;

    @FXML
    private Hyperlink signOutLink;

    @FXML
    public void initialize() {
        // Set up the columns in the table
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        articlesColumn.setCellValueFactory(new PropertyValueFactory<>("articleIds"));

        // Load example data into the table
        groupTable.setItems(getGroups());
    }

    private ObservableList<Group> getGroups() {
        // Example data (replace this with server data fetch logic later)
        ObservableList<Group> groups = FXCollections.observableArrayList();
        groups.add(new Group("Beginner", "1", "2, 3"));
        groups.add(new Group("Intermediate", "2", "1"));
        groups.add(new Group("Advanced", "3", ""));
        groups.add(new Group("Expert", "4", ""));
        groups.add(new Group("CSE360", "5", "2"));
        return groups;
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400)); // Adjust size as needed
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper class to represent a group
    public static class Group {
        private String name;
        private String id;
        private String articleIds;

        public Group(String name, String id, String articleIds) {
            this.name = name;
            this.id = id;
            this.articleIds = articleIds;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getArticleIds() {
            return articleIds;
        }
    }
}
