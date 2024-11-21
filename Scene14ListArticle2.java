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

public class Scene14ListArticle2 {
	
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
    private void handleFindArticles(ActionEvent event) {
        switchScene(event, "scene14SearchArticle1.fxml");
    }

    @FXML
    private void handleViewArticle(ActionEvent event) {
        switchScene(event, "scene14ViewArticle1.fxml");
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
//    private ObservableList<Article> articleData = FXCollections.observableArrayList(
//            new Article("Title1", "CSE", "E", "4, 8"),
//            new Article("Title2", "Example, CSE", "I", "2, 8, 10")
//    );
    private ObservableList<Article> articleData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Call the function that would read data from the server
        //loadArticleData();

        // Set up the table columns
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        keywordsColumn.setCellValueFactory(new PropertyValueFactory<>("keywords"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        groupIdsColumn.setCellValueFactory(new PropertyValueFactory<>("groupIds"));

        // Add sample data to the table
        //articleTable.setItems(articleData);
    }

    public void loadArticleData(int groupID) {
        //Function that can be called from previous scene to populate data in this scene
        System.out.println("Fetching articles from the server...");
        Manager admin = new Manager();
        admin.connect();
	//Array of all articles
        String[][] articleArray = admin.getArticleArray();
	//If we want to load all articles
        if (groupID == 0) {
        	for(String[] articleDataArr : articleArray) {
            	Article article = new Article(
            			articleDataArr[1], // Title
            			articleDataArr[2], // Keywords
            			articleDataArr[5], // Level
            			articleDataArr[2]  // Group_ids
                    );
                    
                    articleData.add(article);
            }
        } else { //If we're only loading one group's articles
        	for(String[] articleDataArr : articleArray) {
        		String[] groupIdsArray = articleDataArr[2].split(",");
        		for (String groupId : groupIdsArray) {
                    groupId = groupId.trim();
                    try {
                    	int thisGroupId = Integer.valueOf(groupId);
                    	System.out.println(thisGroupId);
                    	if (thisGroupId == groupID) {
                			Article article = new Article(
                        			articleDataArr[1], // Title
                        			articleDataArr[3], // Keywords
                        			articleDataArr[6], // Level
                        			articleDataArr[2]  // Group_ids
                                );
                                
                                articleData.add(article);
                		}
                    } catch (NumberFormatException e) {
                    	//System.out.println("Non-numeric id found, skipping...");
                    }
                }
        		
            }
        }
        articleTable.setItems(articleData);
        
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
