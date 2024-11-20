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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private TableColumn<Group, String> adminColumn;
    
    @FXML
    private TableColumn<Group, String> instructorColumn;
    
    @FXML
    private TableColumn<Group, String> studentColumn;

    @FXML
    private Hyperlink signOutLink;
    
    private ObservableList<Group> groupData = FXCollections.observableArrayList();
    

    @FXML
    public void initialize() {
        // Set up the columns in the table
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        articlesColumn.setCellValueFactory(new PropertyValueFactory<>("articleIds"));
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("adminIds"));
        instructorColumn.setCellValueFactory(new PropertyValueFactory<>("instructorIds"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentIds"));

        // Load example data into the table
        loadGroupData();
        groupTable.setItems(this.groupData);
    }

    private ObservableList<Group> getGroups() {
        // Example data (replace this with server data fetch logic later)
        groups.add(new Group("Beginner", "1", "2, 3", "1, 2", "1, 2, 3", "5, 6"));
        groups.add(new Group("Intermediate", "2", "1", "4", "4", "7, 8"));
        groups.add(new Group("Advanced", "3", "4, 5", "2", "2, 3", "6, 9"));
        groups.add(new Group("Expert", "4", "", "", "", ""));
        groups.add(new Group("CSE360", "5", "", "", "", ""));
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
    
    private void loadGroupData() {
    	Manager admin = new Manager();
    	admin.connect();
    	String[][] articlesArray = admin.getArticleArray();
	//We use a hashmap to total articles for each group
        Map<String, Integer> groupCountMap = new HashMap<>();


        Map<String, String> groupLevelMap = new HashMap<>();
        for (String[] article : articlesArray) {
            String groupIds = article[2]; // Group_ids
            String level = article[6];    // Level for each article

            String[] groupIdsArray = groupIds.split(",");
            for (String groupId : groupIdsArray) {
                groupId = groupId.trim();
                String groupKey = groupId + ":" + level; // Unique key combining groupId and level
                groupCountMap.put(groupKey, groupCountMap.getOrDefault(groupKey, 0) + 1);
                groupLevelMap.put(groupKey, level); // Store the level for each unique groupId
            }
        }


        for (Map.Entry<String, Integer> entry : groupCountMap.entrySet()) {
            String groupKey = entry.getKey();
            String[] parts = groupKey.split(":");
            String groupId = parts[0];
            String level = groupLevelMap.get(groupKey); // Retrieve the level for this groupId

            String articleIds = String.valueOf(entry.getValue()); // Count of articles as articleIds

            Group group = new Group(level, groupId, articleIds);
            groupData.add(group);
        }
    }

    // Helper class to represent a group
    public static class Group {
        private final String name;
        private final String id;
        private final String articleIds;
        private final String adminIds;
        private final String instructorIds;
        private final String studentIds;

        public Group(String name, String id, String articleIds, String adminIds, String instructorIds, String studentIds) {
            this.name = name;
            this.id = id;
            this.articleIds = articleIds;
            this.adminIds = adminIds;
            this.instructorIds = instructorIds;
            this.studentIds = studentIds;
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

        public String getAdminIds() {
            return adminIds;
        }

        public String getInstructorIds() {
            return instructorIds;
        }

        public String getStudentIds() {
            return studentIds;
        }
    }
}
