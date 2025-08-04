package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


import com.example.OnetoOneChat.ChatWindowView;
import com.example.OnetoOneChat.FirestoreService;
import com.example.ToControlDatabase.Finder;
import com.example.ToControlDatabase.Manager;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.google.cloud.firestore.Firestore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;


public class friendsController {

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
            loadFriendsFromFirestore();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    void loadFriendsFromFirestore() throws IOException {

        List<String> friendsList = Manager.getCurrentUser().getFriends();
        for (String friend : friendsList) {
            showUsers(friend);
        }
    }

    @FXML
    void showUsers(String username) throws IOException {
        Firestore db= FirestoreHelper.getFirestore();
        User currFriend=Finder.UserFinder(username);
        vBox.setSpacing(5);
        HBox requestSection = new HBox(15); // Daha fazla boşluk
        requestSection.setPadding(new Insets(10));
        requestSection.setAlignment(Pos.CENTER_LEFT);
        requestSection.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #d0d0d0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        ImageView iView = null;
            String s1 = currFriend.getUrl();
            File file = new File(s1);
            Image image = new Image(file.toURI().toString());

            iView = new ImageView();
            iView.setImage(image);

            Circle clip = new Circle(15, 15, 15); // X, Y, Radius → küçültüldü
            iView.setClip(clip);
            iView.setFitWidth(30); // Görsel boyutu küçük
            iView.setFitHeight(30);

        Label usernameLabel = new Label(username);
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button seeProfileButton = new Button("See Profile");
        seeProfileButton
                .setStyle("-fx-background-color:rgb(109, 116, 126); -fx-text-fill: white; -fx-background-radius: 5px;");
        seeProfileButton.setCursor(Cursor.HAND);

        seeProfileButton.setOnAction(event -> {
            MainPageController.getSecondAnchorPane().getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/OtherifFriend.fxml"));
            OtherifFriendController.setFriend(currFriend);
            Node node = null;
            try {
                node = loader.load();
            } catch (IOException e) {

                e.printStackTrace();
            }
            MainPageController.getSecondAnchorPane().getChildren().setAll(node);

        });

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
        Button removeFriendButton= new Button("Remove");
        removeFriendButton
                .setStyle("-fx-background-color:rgba(235, 56, 56, 1); -fx-text-fill: white; -fx-background-radius: 5px;");
        removeFriendButton.setCursor(Cursor.HAND);

        removeFriendButton.setOnAction(event->{
            User currUser= Manager.getCurrentUser();
            String username1 =currUser.getUsername();
            String username2= username;

            if(currFriend.getFriends().contains(username1)){
                currUser.getFriends().remove(username2);
                currFriend.getFriends().remove(username1);
                db.collection("users").document(username1).update("friends",currUser.getFriends());
                db.collection("users").document(username2).update("friends",currFriend.getFriends());
                vBox.getChildren().remove(requestSection);
            }
            
        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        requestSection.getChildren().addAll(iView, usernameLabel, spacer, seeProfileButton, goChatButton,removeFriendButton);

        // Add the friend's section to the main VBox container
        vBox.getChildren().add(requestSection);
    }

}
