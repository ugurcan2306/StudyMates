package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import com.example.ToControlDatabase.Finder;
import com.example.ToControlDatabase.Manager;
import com.example.classes.Comment;
import com.example.classes.Course;

import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;

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


public class MyProfileController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView;

    @FXML
    private Label courseNo;

    @FXML
    private Label locationLabel;

    @FXML
    private Label mateNo;

    @FXML
    private Label nameAndSurname;

    @FXML
    private Label ranking;

    @FXML
    private Label username;

    @FXML
    private AnchorPane commentsAnchor;
  

    @FXML
    private AnchorPane interestAnchorPane;

    private User currUser;

    @FXML
    private VBox vBoxLecture;


    private static ImageView staticImageView;


    @FXML
    private Button plusButton;

    @FXML
    private VBox vBoxComments;

    void addCommentBox(String commenterUsername, String commentText, String imageUrl) {
    HBox commentContainer = new HBox(10);
    commentContainer.setPadding(new Insets(5));
    commentContainer.setStyle("-fx-background-color: #eef5ff; -fx-border-color: #cfcfcfff; -fx-border-radius: 8px; -fx-background-radius: 8px;");

    ImageView avatar = new ImageView();
    avatar.setFitWidth(30);
    avatar.setFitHeight(30);
    Circle clip = new Circle(15, 15, 15);
    avatar.setClip(clip);

    if (imageUrl != null && !imageUrl.isEmpty()) {
        File file = new File(imageUrl);
        avatar.setImage(new Image(file.toURI().toString()));
    }

    VBox commentVBox = new VBox();
    Label l =new Label(commenterUsername);
    l.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
    Label l1= new Label(commentText);
    l1.setStyle("-fx-font-size: 13px;");
    commentVBox.getChildren().addAll(
            l,
            l1
    );

    commentContainer.getChildren().addAll(avatar, commentVBox);
    vBoxComments.getChildren().add(commentContainer);
    }

    void loadComments() {
    new Thread(() -> {
        try {
            Firestore db = FirestoreHelper.getFirestore();
            QuerySnapshot snapshot = db.collection("comments")
                    .whereEqualTo("receiverName", currUser.getUsername())
                    .limit(20)
                    .get()
                    .get();

            Platform.runLater(() -> {
                for (DocumentSnapshot doc : snapshot.getDocuments()) {
                    Comment comment = doc.toObject(Comment.class);
                    if (comment != null) {
                        String s1=null;
                        try {
                            s1 = db.collection("users").document(comment.getSenderName()).get().get().get("url").toString();
                        } catch (InterruptedException | ExecutionException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        addCommentBox(comment.getSenderName(), comment.getCommentS(),s1); // resim URL'si varsa
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
}
    @FXML
    void initialize() throws Exception {
        currUser= Manager.getCurrentUser();
        assert courseNo != null : "fx:id=\"courseNo\" was not injected: check your FXML file 'MyProfile.fxml'.";
        assert locationLabel != null
                : "fx:id=\"locationLabel\" was not injected: check your FXML file 'MyProfile.fxml'.";
        assert mateNo != null : "fx:id=\"mateNo\" was not injected: check your FXML file 'MyProfile.fxml'.";
        assert nameAndSurname != null
                : "fx:id=\"nameAndSurname\" was not injected: check your FXML file 'MyProfile.fxml'.";
        assert ranking != null : "fx:id=\"ranking\" was not injected: check your FXML file 'MyProfile.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'MyProfile.fxml'.";
        initializeMyProfile();
        loadComments();
    }

    public static ImageView getStaticImageView() {
        return staticImageView;
    }

    @FXML
    void plusButtonPressed(ActionEvent event) {
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/coursesPage.fxml"));
        AnchorPane node = null;
        try {
            node = loader.load();
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);
    }

    void initializeMyProfile() throws Exception {
        courseNo.setText(String.valueOf(currUser.getDesiredCourses().size()));
        locationLabel.setText(currUser.getCity());
        mateNo.setText(String.valueOf(currUser.getFriends().size()));
        String name = currUser.getMainname();
        String surname = currUser.getSurname();
        nameAndSurname.setText(name + " " + surname);
        ranking.setText(String.valueOf(currUser.getRankings()));
        username.setText("@" + currUser.getUsername());  
        String s = currUser.getUrl();

        File file = new File(s);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        Circle clip = new Circle(50, 50, 50); // X, Y, Radius
        imageView.setClip(clip);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        staticImageView = imageView;
        load(vBoxLecture);
    }

    void load(VBox container) throws IOException {
        for (Course lecture : currUser.getDesiredCourses()) {
            showUsers(lecture);
        }
}
    @FXML
    void showUsers(Course lecture) throws IOException {

        vBoxLecture.setSpacing(5);
        HBox requestSection = new HBox(15); // Daha fazla boşluk

        requestSection.setPadding(new Insets(10));
        requestSection.setAlignment(Pos.CENTER_LEFT);
        requestSection.setStyle(
                "-fx-background-color:  #eef5ff; -fx-border-color: #eef5ff; -fx-border-radius: 8px; -fx-background-radius: 8px;");
        Label avatar = new Label("📚");
        avatar.setStyle("-fx-font-size: 28px;");

        Label usernameLabel = new Label(lecture.getCourseCode());
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Button removeButton = new Button("Remove");
        removeButton
                .setStyle("-fx-background-color:rgb(236, 79, 79); -fx-text-fill: white; -fx-background-radius: 5px;");
        removeButton.setCursor(Cursor.HAND);

        Course courseToRemove =lecture;
       

        // Aradaki boşluğu otomatik yaymak için
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        requestSection.getChildren().addAll(avatar, usernameLabel, spacer, removeButton);

        // Add the friend's section to the main VBox container
        vBoxLecture.getChildren().add(requestSection);

        Firestore db = FirestoreHelper.getFirestore();

        removeButton.setOnAction(event -> {
            currUser.getDesiredCourses().remove(courseToRemove);

            DocumentReference studyRef = db.collection("users").document(currUser.getUsername());
            try {
                studyRef.update("desiredCourses", currUser.getDesiredCourses()).get();
                courseNo.setText(String.valueOf(currUser.getDesiredCourses().size()));
                vBoxLecture.getChildren().remove(requestSection);
                
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

}
