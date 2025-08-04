package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.example.OnetoOneChat.ChatWindowView;
import com.example.OnetoOneChat.FirestoreService;
import com.example.ToControlDatabase.*;

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

public class searchController {
    String searchedPersonUsername;

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
    void initialize() throws IOException {
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'search.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'search.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'search.fxml'.";
       
    }
    public void setSearchedPersonUsername(String searchedPersonUsername) {
        this.searchedPersonUsername = searchedPersonUsername;
         try {
            loadUsersFromFirestore();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

      void loadUsersFromFirestore() throws IOException {

        try {
            Firestore db = FirestoreHelper.getFirestore();
            QuerySnapshot  userCollection= db.collection("users").get().get();

            for (int i = 0; i < userCollection.size(); i++) {
                String username=userCollection.getDocuments().get(i).toObject(User.class).getUsername();
                if(username.contains(searchedPersonUsername)&&!username.equals(Manager.getCurrentUser().getUsername())){
                    showUsers(username);
                }
            }
        }
            catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading users: " + e.getMessage());
        }
    }

    @FXML
    void showUsers(String username) throws IOException {
        
        vBox.setSpacing(5);
        User currUser= Manager.getCurrentUser();
        User searchedUser= Finder.UserFinder(username);
        HBox requestSection = new HBox(15); // Daha fazla boşluk
        requestSection.setPadding(new Insets(10));
        requestSection.setAlignment(Pos.CENTER_LEFT);
        requestSection.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #d0d0d0; -fx-border-radius: 8px; -fx-background-radius: 8px;");


            String s1 = searchedUser.getUrl();
            File file = new File(s1);
            Image image = new Image(file.toURI().toString());

            ImageView iView= new ImageView();
            iView.setImage(image);

            Circle clip = new Circle(15, 15, 15); // X, Y, Radius → küçültüldü
            iView.setClip(clip);
            iView.setFitWidth(30);   // Görsel boyutu küçük
            iView.setFitHeight(30);

        Label usernameLabel = new Label(username);
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
       
        // Cancel butonu
        Button seeProfileButton = new Button("See Profile");
        seeProfileButton.setStyle("-fx-background-color:rgb(103, 135, 230); -fx-text-fill: white; -fx-background-radius: 5px;");
        seeProfileButton.setCursor(Cursor.HAND);

        seeProfileButton.setOnAction(event ->{
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader;
        if(currUser.getFriends().contains(searchedUser.getUsername())){
            OtherifFriendController.setFriend(searchedUser);
            loader= new FXMLLoader(getClass().getResource("/fxmls/OtherifFriend.fxml"));
        }
        else{   
            OtherProfilesController.setFriend(searchedUser);
            loader = new FXMLLoader(getClass().getResource("/fxmls/other.fxml"));
           
        }
        
         Node node = null;
         try {
             node = loader.load();
         } catch (IOException e) {
      
             e.printStackTrace();
        }       
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);

        } );
        

        Button goChatButton = new Button("Go Chat");
        goChatButton.setStyle("-fx-background-color:rgb(103, 135, 230); -fx-text-fill: white; -fx-background-radius: 5px;");
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

        if(!Manager.getCurrentUser().getFriends().contains(searchedUser.getUsername())){
            requestSection.getChildren().addAll(iView, usernameLabel,spacer,seeProfileButton);

        }
        else{
            requestSection.getChildren().addAll(iView, usernameLabel,spacer, goChatButton,seeProfileButton);
        }
        // Add the friend's section to the main VBox container
        vBox.getChildren().add(requestSection);
    }
}

