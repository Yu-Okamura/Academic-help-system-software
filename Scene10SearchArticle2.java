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

import java.io.IOException;

public class Scene10SearchArticle2 {

    @FXML
    private Hyperlink signOutLink1;

    @FXML
    private Hyperlink genericMessageLink;

    @FXML
    private Hyperlink specificMessageLink;

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

    private String groupName = "not specified";

    public void initialize() {
        // Update the summary section with article data and group name
        updateSummary(getBeginnerCount(), getIntermediateCount(), getAdvancedCount(), getExpertCount());
        populateArticles();
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null || groupName.trim().isEmpty() ? "not specified" : groupName;
        System.out.println("Group name set to: " + this.groupName); // Debugging
    }

    private int getBeginnerCount() {
        return 3; // Example data
    }

    private int getIntermediateCount() {
        return 0; // Example data
    }

    private int getAdvancedCount() {
        return 0; // Example data
    }

    private int getExpertCount() {
        return 5; // Example data
    }

    public void updateSummary(int beginnerCount, int intermediateCount, int advancedCount, int expertCount) {
        String summary = String.format(
                "          Specified group: %s\n\n" +
                        "          %d beginner, %d intermediate,\n" +
                        "          %d advanced, %d expert articles found.",
                groupName, beginnerCount, intermediateCount, advancedCount, expertCount
        );
        summaryTextArea.setText(summary);
    }

    private void populateArticles() {
        int totalCount = getBeginnerCount() + getIntermediateCount() + getAdvancedCount() + getExpertCount();

        if (totalCount == 0) {
            mainAnchorPane.getChildren().remove(articlesTextArea); // Remove the articlesTextArea
        } else {
            StringBuilder articles = new StringBuilder();
            articles.append("Example Article 1\n");
            articles.append("Example Article 2\n");
            articles.append("Example Article 3\n");
            articlesTextArea.setText(articles.toString());
        }
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
        switchScene(event, "scene1.fxml");
    }

    @FXML
    private void handleSendGenericMessage(ActionEvent event) {
        switchScene(event, "scene10SendGeneric.fxml");
    }

    @FXML
    private void handleSendSpecificMessage(ActionEvent event) {
        switchScene(event, "scene10SendSpecific.fxml");
    }

    @FXML
    private void handleFindArticles(ActionEvent event) {
        switchScene(event, "scene10SearchArticle1.fxml");
    }

    @FXML
    private void handleViewArticle(ActionEvent event) {
        switchScene(event, "scene10ViewArticle1.fxml");
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        switchScene(event, "scene10.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            if ("scene10SearchArticle2.fxml".equals(fxmlFile)) {
                Scene10SearchArticle2 controller = loader.getController();
                controller.setGroupName(groupName);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
