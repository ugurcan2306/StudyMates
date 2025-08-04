package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.example.OnetoOneChat.ChatWindowView;
import com.example.OnetoOneChat.FirestoreService;
import com.example.ToControlDatabase.Finder;
import com.example.ToControlDatabase.Manager;
import com.example.classes.StudyRequest;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
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

public class allRequestsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox vBoxReceived;

    @FXML
    private VBox vBoxSent;

    @FXML
    private AnchorPane receivedAnchor;

    @FXML
    private AnchorPane sentAnchor;

    User currUser;

    @FXML
    void initialize() {
        assert vBoxReceived != null
                : "fx:id=\"vBoxReceived\" was not injected: check your FXML file 'allRequests.fxml'.";
        assert vBoxSent != null : "fx:id=\"vBoxSent\" was not injected: check your FXML file 'allRequests.fxml'.";
        assert receivedAnchor != null : "fx:id=\"vBoxSent\" was not injected: check your FXML file 'allRequests.fxml'.";
        assert sentAnchor != null : "fx:id=\"vBoxSent\" was not injected: check your FXML file 'allRequests.fxml'.";
        currUser = Manager.getCurrentUser();

        load();

        vBoxSent.heightProperty().addListener((obs, oldVal, newVal) -> {
            sentAnchor.setPrefHeight(newVal.doubleValue() + 20);
        });

        vBoxReceived.heightProperty().addListener((obs, oldVal, newVal) -> {
            receivedAnchor.setPrefHeight(newVal.doubleValue() + 20);
        });
    }

    void load() {
            for (StudyRequest sr : currUser.getSentStudyRequests()) {
                showUsers1(sr.getReceiverName(), sr.getCourseCode(), vBoxSent);
            }
            for (StudyRequest sr : currUser.getReceivedStudyRequests()) {
                showUsers1(sr.getSenderName(), sr.getCourseCode(), vBoxReceived);
            }
        }
    

    void showUsers1(String username, String code, VBox vBoxumuz) {
        Firestore db = FirestoreHelper.getFirestore();
        vBoxumuz.setSpacing(5);
        HBox requestSection = new HBox(15); // Daha fazla boşluk

        User friend = Finder.UserFinder(username);

        requestSection.setPadding(new Insets(10));
        requestSection.setAlignment(Pos.CENTER_LEFT);
        requestSection.setStyle(
                "-fx-background-color:  #eef5ff; -fx-border-color: #eef5ff; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        ImageView iView = null;
        String s1 = friend.getUrl();
        File file = new File(s1);
        Image image = new Image(file.toURI().toString());

        iView = new ImageView();
        iView.setImage(image);

        Circle clip = new Circle(15, 15, 15); // X, Y, Radius → küçültüldü
        iView.setClip(clip);
        iView.setFitWidth(30); // Görsel boyutu küçük
        iView.setFitHeight(30);

        Label friendUsernameLabel = new Label(username);
        friendUsernameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-cursor: hand;");

        friendUsernameLabel.setOnMouseClicked(event -> {
            MainPageController.getSecondAnchorPane().getChildren().clear();
            FXMLLoader loader;

            if (currUser.getFriends().contains(username)) {
                loader = new FXMLLoader(getClass().getResource("/fxmls/OtherifFriend.fxml"));
                OtherifFriendController.setFriend(friend);
            } else {
                loader = new FXMLLoader(getClass().getResource("/fxmls/other.fxml"));
                OtherProfilesController.setFriend(friend);
            }

            Node node = null;
            try {
                node = loader.load();
            } catch (IOException e) {

                e.printStackTrace();
            }
            MainPageController.getSecondAnchorPane().getChildren().setAll(node);
        });

        Label courseCodeLabel = new Label(code);
        courseCodeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Button unsendButton = new Button("Unsend");
        unsendButton
                .setStyle("-fx-background-color:rgb(236, 79, 79); -fx-text-fill: white; -fx-background-radius: 3px;");
        unsendButton.setCursor(Cursor.HAND);

        unsendButton.setOnAction(event -> {
            User currUser = Manager.getCurrentUser();
            String docId = currUser.getUsername() + "_" + username + "_" + code;

            try {
                StudyRequest sr = Finder.StudyReqFinder(currUser.getUsername(), username, code);
                currUser.getSentStudyRequests().remove(sr);
                friend.getReceivedStudyRequests().remove(sr);

                DocumentReference tradeRef = db.collection("users").document(currUser.getUsername());
                tradeRef.update("sentStudyRequests", currUser.getSentStudyRequests()).get();
                DocumentReference tradeRef2 = db.collection("users").document(friend.getUsername());
                tradeRef2.update("receivedStudyRequests", friend.getReceivedStudyRequests()).get();

                vBoxSent.getChildren().remove(requestSection);
                db.collection("studyRequests").document(docId).delete();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        Button accept = new Button("Accept");
        accept.setStyle("-fx-background-color:rgb(5, 208, 56); -fx-text-fill: white; -fx-background-radius: 3px;");
        accept.setCursor(Cursor.HAND);

        accept.setOnAction(event -> {
             StudyRequest sr = new StudyRequest (username, currUser.getUsername(), code);
            if (currUser.getReceivedStudyRequests().contains(sr)) {

                currUser.getReceivedStudyRequests().remove(sr);
                friend.getSentStudyRequests().remove(sr);

                try {
                    DocumentReference studyRef = db.collection("users").document(currUser.getUsername());
                    DocumentReference studyRef2 = db.collection("users").document(friend.getUsername());
                    studyRef.update("receivedStudyRequests", currUser.getReceivedStudyRequests()).get();
                    studyRef2.update("sentStudyRequests", friend.getSentStudyRequests()).get();

                    db.collection("studyRequests").document(username + "_" + currUser.getUsername() + "_" + code)
                            .delete();
                } catch (InterruptedException | ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String username1 = currUser.getUsername();
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
                if (!currUser.getFriends().contains(friend.getUsername())) {
                    currUser.getFriends().add(friend.getUsername());
                    friend.getFriends().add(currUser.getUsername());
                    DocumentReference ref1 = db.collection("users").document(currUser.getUsername());
                    DocumentReference ref2 = db.collection("users").document(friend.getUsername());
                    DocumentReference ref3 = db.collection("studyRequest")
                            .document(friend.getUsername() + "_" + currUser.getUsername() + "_" + code);

                    try {
                        ref1.update("friends", currUser.getFriends()).get();
                        ref2.update("friends", friend.getFriends()).get();
                        ref3.delete();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            }

        });
        Button reject = new Button("Reject");
        reject.setStyle("-fx-background-color:rgb(236, 79, 79); -fx-text-fill: white; -fx-background-radius: 3px;");
        reject.setCursor(Cursor.HAND);

        reject.setOnAction(event -> {

            StudyRequest sr = new StudyRequest (username, currUser.getUsername(), code);
            if ( currUser.getReceivedStudyRequests().contains(sr)) {
                currUser.getReceivedStudyRequests().remove(sr);
                friend.getSentStudyRequests().remove(sr);

                DocumentReference ref1 = db.collection("users").document(currUser.getUsername());
                DocumentReference ref2 = db.collection("users").document(friend.getUsername());

                try {
                    ref1.update("receivedStudyRequests", currUser.getReceivedStudyRequests()).get();
                    ref2.update("sentStudyRequests", friend.getSentStudyRequests()).get();
                    db.collection("studyRequests").document(username + "_" + currUser.getUsername() + "_" + code)
                            .delete();

                } catch (InterruptedException | ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                vBoxReceived.getChildren().remove(requestSection);
            }

        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        if (vBoxumuz == vBoxSent) {
            requestSection.getChildren().addAll(iView, friendUsernameLabel, courseCodeLabel, spacer, unsendButton);
        } else {
            requestSection.getChildren().addAll(iView, friendUsernameLabel, courseCodeLabel, spacer, accept, reject);
        }

        vBoxumuz.getChildren().add(requestSection);
    }

}
