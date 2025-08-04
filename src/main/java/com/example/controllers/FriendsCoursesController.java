
package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.example.ToControlDatabase.Finder;
import com.example.ToControlDatabase.Manager;
import com.example.classes.StudyRequest;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.example.firebase.FirestoreUtils;

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

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class FriendsCoursesController {

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
        loadCoursesFromFirestore(s);
    }
     void loadCoursesFromFirestore(String s) throws IOException {

        try {
            Firestore db = FirestoreHelper.getFirestore();
            DocumentSnapshot  userCollection= db.collection("users").document(currFriend.getUsername()).get().get();
            ArrayList<Map<String,Object>> desiredCourses = (ArrayList<Map<String,Object>>) userCollection.get("desiredCourses");

            for (Map<String,Object> courses: desiredCourses) {
                if(courses.get("courseCode").toString().contains(s)){
                    showUsers(courses.get("courseCode").toString());
                }
        }
    }

            catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading users: " + e.getMessage());
        }
    }
    void loadCoursesFromFirestore() throws IOException {

        try {
            Firestore db = FirestoreHelper.getFirestore();
            DocumentSnapshot  userCollection= db.collection("users").document(currFriend.getUsername()).get().get();
            ArrayList<Map<String,Object>> desiredCourses = (ArrayList<Map<String,Object>>) userCollection.get("desiredCourses");

            for (Map<String,Object> courses: desiredCourses) {
                    showUsers(courses.get("courseCode").toString());
                }
        }

            catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading users: " + e.getMessage());
        }
    }


    @FXML
    void initialize() throws IOException {
        assert VBoxSearch != null : "fx:id=\"VBoxSearch\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert backLabel != null : "fx:id=\"backLabel\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert searchAnchor != null : "fx:id=\"searchAnchor\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'FriendsFriends.fxml'.";
        loadCoursesFromFirestore();

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

    void showUsers(String username) throws IOException {

        VBoxSearch.setSpacing(5);

        HBox requestSection = new HBox(15); // Daha fazla boşluk
        requestSection.setPadding(new Insets(10));
        requestSection.setAlignment(Pos.CENTER_LEFT);
        requestSection.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #d0d0d0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Firestore db= FirestoreHelper.getFirestore();
        Label avatar= new Label("📚");
        avatar.setStyle("-fx-font-size: 20px;");
        Label usernameLabel = new Label(username);
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Cancel butonu
        Button sendRequest = new Button("Send Request");
        sendRequest.setStyle("-fx-background-color:rgb(103, 135, 230); -fx-text-fill: white; -fx-background-radius: 5px;");
        sendRequest.setCursor(Cursor.HAND);

        if(Finder.StudyReqFinder(currUser.getUsername(),currFriend.getUsername(), username)!=null){
            sendRequest.setText("Already Sent");
            sendRequest.setDisable(true);
            
        }

        sendRequest.setOnAction(event->{
            FirestoreUtils.addStudyRequesttoTheFirestore(db, currUser.getUsername(), currFriend.getUsername(), username);
            StudyRequest sr=new StudyRequest(currUser.getUsername(), currFriend.getUsername(), username);

            currUser.getSentStudyRequests().add(sr);
            currFriend.getReceivedStudyRequests().add(sr);

            db.collection("users").document(currFriend.getUsername()).update("receivedStudyRequests", currFriend.getReceivedStudyRequests());
            db.collection("users").document(currUser.getUsername()).update("sentStudyRequests", currUser.getSentStudyRequests());

            sendRequest.setText("Already Sent");
            sendRequest.setDisable(true);

        });

        if(username.equals(Manager.getCurrentUser().getUsername())){
            sendRequest.setDisable(true);}

        // Aradaki boşluğu otomatik yaymak için
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        requestSection.getChildren().addAll(avatar, usernameLabel,spacer,sendRequest);
        
        // Add the friend's section to the main VBox container
        VBoxSearch.getChildren().add(requestSection);
    }
}


