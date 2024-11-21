package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.io.IOException;

public class Scene14SearchArticle2 {

    @FXML
    private Hyperlink signOutLink1;

    @FXML
    private Hyperlink createArticleLink;

    @FXML
    private Hyperlink updateArticleLink;

    @FXML
    private Hyperlink listArticlesLink;

    @FXML
    private Hyperlink deleteArticleLink;

    @FXML
    private Hyperlink backupLink;

    @FXML
    private Hyperlink listGroupsLink;

    @FXML
    private Hyperlink createGroupLink;

    @FXML
    private Hyperlink restoreLink;

    @FXML
    private Hyperlink findArticlesLink;

    @FXML
    private Hyperlink viewArticleLink;

    @FXML
    private Hyperlink goBackLinkTop;

    @FXML
    private Hyperlink goBackLinkBottom;

    @FXML
    private TextArea summaryTextArea;

    @FXML
    private TextArea articlesTextArea;

    @FXML
    private AnchorPane mainAnchorPane;
    
//    private ArrayList<String[]> beginnerArticles = new ArrayList<>();
//    private ArrayList<String[]> intermediateArticles = new ArrayList<>();
//    private ArrayList<String[]> advancedArticles = new ArrayList<>();
//    private ArrayList<String[]> expertArticles = new ArrayList<>();
    private ArrayList<String[]> articles = new ArrayList<>();
    
    public int levelToPull;

    public void initialize() {
        // Set initial text for the summary section
    	Manager admin = new Manager();
    	admin.connect();
    	System.out.println("get articles");
    	articles = admin.filterArticlesByGroup(levelToPull);
    	System.out.println(articles.size());
    	
        updateSummary(getBeginnerCount(), getIntermediateCount(), getAdvancedCount(), getExpertCount());
        populateArticles();
    }

    private int getBeginnerCount() {
    	if (levelToPull == 1) {
    		return articles.size();
    	} else {
    		return 0;
    	}
    }

    private int getIntermediateCount() {
    	if (levelToPull == 2) {
    		return articles.size();
    	} else {
    		return 0;
    	}
    }

    private int getAdvancedCount() {
    	if (levelToPull == 3) {
    		return articles.size();
    	} else {
    		return 0;
    	}
    }

    private int getExpertCount() {
    	if (levelToPull == 4) {
    		return articles.size();
    	} else {
    		return 0;
    	}
    }

    public void updateSummary(int beginnerCount, int intermediateCount, int advancedCount, int expertCount) {
        String summary = String.format(
                "          Specified group: <name or \"not specified\">\n\n" +
                        "          %d beginner, %d intermediate,\n" +
                        "          %d advanced, %d expert articles found.",
                beginnerCount, intermediateCount, advancedCount, expertCount
        );
        summaryTextArea.setText(summary);
    }

    private void populateArticles() {
        int totalCount = getBeginnerCount() + getIntermediateCount() + getAdvancedCount() + getExpertCount();

        if (totalCount != 0) {
            // Remove the articlesTextArea from the scene
            //mainAnchorPane.getChildren().remove(articlesTextArea);
        	StringBuilder articlestr = new StringBuilder();
        	for (int i = 0; i < articles.size(); i++) {
        		//StringBuilder thisArticleBuilder = new StringBuilder();
        		//thisArticleBuilder.append(articles.get(i)[0]);
        		//thisArticleBuilder.append(articles.get(i)[0]);
        		articlestr.append(articles.get(i)[1]);
        		articlestr.append("\n");
        	}
        	articlesTextArea.setText(articlestr.toString());
        } else {
            // Example articles
            StringBuilder articlestr = new StringBuilder();
            articlestr.append("Example Article 1\n");
            articlestr.append("Example Article 2\n");
            articlestr.append("Example Article 3\n");
            articlesTextArea.setText(articlestr.toString());
        }
    }

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
        switchScene(event, "scene14ViewArticle.fxml");
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        switchScene(event, "scene14.fxml");
    }
    

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
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
}
