package application;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene14ListArticle {
	
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
    private void handleFullBackup(ActionEvent event) {
        switchScene(event, "scene14BackupFull.fxml");
    }

    @FXML
    private void handleGroupBackup(ActionEvent event) {
        switchScene(event, "scene14BackupGroup.fxml");
    }

    @FXML
    private void handleFullRestore(ActionEvent event) {
        switchScene(event, "scene14RestoreFull.fxml");
    }
    
    @FXML
    private void handleGroupRestore(ActionEvent event) {
        switchScene(event, "scene14RestoreGroup.fxml");
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
    private TableView<Article> articleTable;

    @FXML
    private TableColumn<Article, String> titleColumn;

    @FXML
    private TableColumn<Article, String> keywordsColumn;

    @FXML
    private TableColumn<Article, String> levelColumn;

    @FXML
    private TableColumn<Article, String> groupIdsColumn;

    @FXML
    private Hyperlink signOutLink;

    // Sample data for articles
    private ObservableList<Article> articleData = FXCollections.observableArrayList(
            new Article("Title1", "CSE", "E", "4, 8"),
            new Article("Title2", "Example, CSE", "I", "2, 8, 10")
    );

    @FXML
    public void initialize() {
        // Call the function that would read data from the server
        loadArticleData();

        // Set up the table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        keywordsColumn.setCellValueFactory(new PropertyValueFactory<>("keywords"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        groupIdsColumn.setCellValueFactory(new PropertyValueFactory<>("groupIds"));

        // Add sample data to the table
        articleTable.setItems(articleData);
    }

    private void loadArticleData() {
        // Empty function for now (placeholder for server logic)
        // In the future, this would fetch data from a server or database
        System.out.println("Fetching articles from the server...");
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

    // A simple Article class to hold article data
    public static class Article {
        private final ReadOnlyStringWrapper title;
        private final ReadOnlyStringWrapper keywords;
        private final ReadOnlyStringWrapper level;
        private final ReadOnlyStringWrapper groupIds;

        public Article(String title, String keywords, String level, String groupIds) {
            this.title = new ReadOnlyStringWrapper(title);
            this.keywords = new ReadOnlyStringWrapper(keywords);
            this.level = new ReadOnlyStringWrapper(level);
            this.groupIds = new ReadOnlyStringWrapper(groupIds);
        }

        public String getTitle() {
            return title.get();
        }

        public String getKeywords() {
            return keywords.get();
        }

        public String getLevel() {
            return level.get();
        }

        public String getGroupIds() {
            return groupIds.get();
        }
    }
}
