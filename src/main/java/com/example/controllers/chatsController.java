
package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.OnetoOneChat.ChatWindowView;
import com.example.OnetoOneChat.FirestoreService;
import com.example.ToControlDatabase.Finder;
import com.example.ToControlDatabase.Manager;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class chatsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label label;

    @FXML
    private VBox vBox;

    @FXML
    void initialize() {
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'chats.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'chats.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'chats.fxml'.";
        try {
            loadUsersFromFirestore();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    void loadUsersFromFirestore() throws IOException {

        try {
            Firestore db = FirestoreHelper.getFirestore();
            QuerySnapshot chatCollection = db.collection("chats").get().get();

            for (int i = 0; i < chatCollection.size(); i++) {
                if (chatCollection.getDocuments().get(i).getId().contains(Manager.getCurrentUser().getUsername())) {
                    String[] strArr = chatCollection.getDocuments().get(i).getId().split("_");
                    for (String str : strArr) {
                        if (!str.equals(Manager.getCurrentUser().getUsername())) {
                            showUsers(str);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading users: " + e.getMessage());
        }
    }

    @FXML
    void showUsers(String username) throws IOException {
        vBox.setSpacing(5);
        HBox requestSection = new HBox(15); // Daha fazla boşluk
        requestSection.setPadding(new Insets(10));
        requestSection.setAlignment(Pos.CENTER_LEFT);
        requestSection.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #d0d0d0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        // Avatar (gerçek ImageView ile değiştirilebilir)
        Label avatar = new Label("👤");
        avatar.setStyle("-fx-font-size: 28px;");

        //
        Label usernameLabel = new Label(username);
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Cancel butonu
        Button goChatButton = new Button("Go to Chat");
        goChatButton
                .setStyle("-fx-background-color:rgb(103, 135, 230); -fx-text-fill: white; -fx-background-radius: 5px;");
        goChatButton.setCursor(Cursor.HAND);

       goChatButton.setOnAction(event -> {
    String username1 = Manager.getCurrentUser().getUsername();
    String username2 = username;

    FirestoreService firestoreService = new FirestoreService();
    firestoreService.initializeChatBetweenUsers(username1, username2, chatId -> {
        Platform.runLater(() -> {
            ChatWindowView chatView = new ChatWindowView(chatId, username1);
            
            // Ana sayfadaki ilgili paneli al
            AnchorPane container = MainPageController.getSecondAnchorPane();
            
            container.getChildren().setAll(chatView.getView());

            // Gerekirse yerleştirilen node'u AnchorPane'e yapıştır
            AnchorPane.setTopAnchor(chatView.getView(), 0.0);
            AnchorPane.setBottomAnchor(chatView.getView(), 0.0);
            AnchorPane.setLeftAnchor(chatView.getView(), 0.0);
            AnchorPane.setRightAnchor(chatView.getView(), 0.0);
        });
    });
});
        // Aradaki boşluğu otomatik yaymak için
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        for (int i = 0; i < Manager.getCurrentUser().getFriends().size(); i++) {
        }

        requestSection.getChildren().addAll(avatar, usernameLabel, spacer, goChatButton);

        // Add the friend's section to the main VBox container
        vBox.getChildren().add(requestSection);
    }

}


