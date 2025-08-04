
    package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.example.ToControlDatabase.Finder;
import com.example.ToControlDatabase.Manager;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class FriendsFriendsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox VBoxSearch;

    @FXML
    private Label backLabel;

    @FXML
    private AnchorPane searchAnchor;

    @FXML
    private Button searchButton;

    @FXML
    private TextField textField;

    @FXML
    private Label username;

    static User currFriend;

    User currUser= Manager.getCurrentUser();

    @FXML
    void searchButtonPressed(ActionEvent event) throws IOException {

        VBoxSearch.getChildren().clear();
        String s= textField.getText();
        loadUsersFromFirestore(s);
    }
     void loadUsersFromFirestore(String s) throws IOException {

        try {
            Firestore db = FirestoreHelper.getFirestore();
            DocumentSnapshot  userCollection= db.collection("users").document(currFriend.getUsername()).get().get();
            ArrayList<String> friendsList = (ArrayList<String>) userCollection.get("friends");

            for (String string: friendsList) {
                if(string.contains(s)){
                    showUsers(string);
                }
            }
        }

            catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading users: " + e.getMessage());
        }
    }


    @FXML
    void initialize() {
        assert VBoxSearch != null : "fx:id=\"VBoxSearch\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert backLabel != null : "fx:id=\"backLabel\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert searchAnchor != null : "fx:id=\"searchAnchor\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        loadFriends();

        username.setText(currFriend.getUsername());

        backLabel.setStyle("-fx-cursor: hand;");
        backLabel.setOnMouseClicked(event -> {
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader;
        if(currUser.getFriends().contains(currFriend.getUsername())){
        loader = new FXMLLoader(getClass().getResource("/fxmls/OtherifFriend.fxml"));
        }
        else{
            loader = new FXMLLoader(getClass().getResource("/fxmls/other.fxml")); 
        }
         Node node = null;
         try {
             node = loader.load();
         } catch (IOException e) {
      
             e.printStackTrace();
        }       
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);
        });
    }

    void loadFriends(){
          
        try {
            Firestore db = FirestoreHelper.getFirestore();
            DocumentReference userDocRef = db.collection("users").document(currFriend.getUsername());
            DocumentSnapshot doc = userDocRef.get().get();

            if (doc.exists()) {
                    List<String> friends = (List<String>) doc.get("friends");
                    for (String f : friends) {
                        if(f!=null){
                            showUsers(f);
                        }
                       
                    }

                }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading users: " + e.getMessage());
        }
    }
    void showUsers(String username) throws IOException {
        VBoxSearch.setSpacing(5);
        User searchedUser= Finder.UserFinder(username);
        HBox requestSection = new HBox(15); // Daha fazla boşluk
        requestSection.setPadding(new Insets(10));
        requestSection.setAlignment(Pos.CENTER_LEFT);
        requestSection.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #d0d0d0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Firestore db= FirestoreHelper.getFirestore();
        ImageView iView = null;
        try {
            String s1 = db.collection("users").document(username).get().get().get("url").toString();
            File file = new File(s1);
            Image image = new Image(file.toURI().toString());

            iView= new ImageView();
            iView.setImage(image);

            Circle clip = new Circle(15, 15, 15); // X, Y, Radius → küçültüldü
            iView.setClip(clip);
            iView.setFitWidth(30);   // Görsel boyutu küçük
            iView.setFitHeight(30);

        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

        if(username.equals(Manager.getCurrentUser().getUsername())){
            seeProfileButton.setDisable(true);
        }
        
        // Aradaki boşluğu otomatik yaymak için
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        requestSection.getChildren().addAll(iView, usernameLabel,spacer,seeProfileButton);
        
        // Add the friend's section to the main VBox container
        VBoxSearch.getChildren().add(requestSection);
    }
}


