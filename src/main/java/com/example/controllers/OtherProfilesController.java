package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.example.ToControlDatabase.Finder;
import com.example.classes.Comment;

import com.example.classes.User;
import com.example.firebase.FirestoreHelper;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class OtherProfilesController {

    @FXML
    private Label StudyMatesLabel;

    @FXML
    private Label courseNumberLabel;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea aboutMeTextArea;

    @FXML
    private Label courseNo;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane commentsAnchor;

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
    private VBox vBoxComments;

    @FXML
    private Label username1;

    static User friend;

    @FXML
    private Label buttonLabelRank;

    void addCommentBox(String commenterUsername, String commentText) {
        // Ana HBox (arka plan kutusu gibi davranacak)
        HBox commentContainer = new HBox();
        commentContainer.setPadding(new Insets(5));
        commentContainer.setSpacing(10);
        commentContainer.setAlignment(Pos.CENTER_LEFT);
        commentContainer.setStyle(
                "-fx-background-color: #eef5ff; -fx-border-color: #cfcfcfff; " +
                        "-fx-border-radius: 8px; -fx-background-radius: 8px;");

        HBox commentHBox = new HBox(10);
        commentHBox.setAlignment(Pos.TOP_LEFT);
        String s1;
        ImageView iView = null;

        s1 = Finder.UserFinder(commenterUsername).getUrl();
        File file = new File(s1);
        Image image = new Image(file.toURI().toString());

        iView = new ImageView();
        iView.setImage(image);

        Circle clip = new Circle(15, 15, 15); // X, Y, Radius → küçültüldü
        iView.setClip(clip);
        iView.setFitWidth(30); // Görsel boyutu küçük
        iView.setFitHeight(30);

        VBox commentVBox = new VBox();
        commentVBox.setSpacing(0);

        Label usernameLabel = new Label(commenterUsername);
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label commentLabel = new Label(commentText);
        commentLabel.setWrapText(true);
        commentLabel.setStyle("-fx-font-size: 13px;");

        commentVBox.getChildren().addAll(usernameLabel, commentLabel);
        commentHBox.getChildren().addAll(iView, commentVBox);
        commentContainer.getChildren().add(commentHBox);

        // En dıştaki yorum kutusunu ana yorumlar VBox'una ekle
        vBoxComments.getChildren().add(commentContainer);
    }

    void loadComments() {
        try {
            Firestore db = FirestoreHelper.getFirestore();
            QuerySnapshot commentCollection = db.collection("comments").get().get();

            for (int i = 0; i < commentCollection.size(); i++) {
                Comment c = commentCollection.getDocuments().get(i).toObject(Comment.class);
                if (c.getReceiverName().equals(friend.getUsername())) {
                    addCommentBox(c.getSenderName(), c.getCommentS());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading users: " + e.getMessage());
        }
    }

    public AnchorPane getCommentsAnchor() {
        return commentsAnchor;
    }

    public void setCommentsAnchor(AnchorPane commentsAnchor) {
        this.commentsAnchor = commentsAnchor;
    }

    public VBox getvBoxComments() {
        return vBoxComments;
    }

    public void setvBoxComments(VBox vBoxComments) {
        this.vBoxComments = vBoxComments;
    }

    public Label getUsername1() {
        return username1;
    }

    public void setUsername1(Label username1) {
        this.username1 = username1;
    }

    @FXML
    void initialize() throws IOException, InterruptedException, ExecutionException {
        assert aboutMeTextArea != null
                : "fx:id=\"aboutMeTextArea\" was not injected: check your FXML file 'other.fxml'.";
        assert courseNo != null : "fx:id=\"courseNo\" was not injected: check your FXML file 'other.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'other.fxml'.";
        assert commentsAnchor != null
                : "fx:id=\"interestAnchorPane\" was not injected: check your FXML file 'other.fxml'.";
        assert locationLabel != null : "fx:id=\"locationLabel\" was not injected: check your FXML file 'other.fxml'.";
        assert mateNo != null : "fx:id=\"mateNo\" was not injected: check your FXML file 'other.fxml'.";
        assert nameAndSurname != null : "fx:id=\"nameAndSurname\" was not injected: check your FXML file 'other.fxml'.";
        assert ranking != null : "fx:id=\"ranking\" was not injected: check your FXML file 'other.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'other.fxml'.";
        assert vBoxComments != null : "fx:id=\"vBoxLecture\" was not injected: check your FXML file 'other.fxml'.";
        load();
        loadComments();

        StudyMatesLabel.setStyle("-fx-cursor: hand;");

        StudyMatesLabel.setOnMouseClicked(event -> {
            MainPageController.getSecondAnchorPane().getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/FriendsFriends.fxml"));
            FriendsFriendsController.currFriend = friend;
            Node node = null;
            try {
                node = loader.load();
            } catch (IOException e) {

                e.printStackTrace();
            }
            MainPageController.getSecondAnchorPane().getChildren().setAll(node);
        });

        courseNumberLabel.setStyle("-fx-cursor: hand;");

        courseNumberLabel.setOnMouseClicked(event -> {
            MainPageController.getSecondAnchorPane().getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/FriendsCourses.fxml"));
            FriendsCoursesController.currFriend = friend;
            Node node = null;
            try {
                node = loader.load();
            } catch (IOException e) {

                e.printStackTrace();
            }
            MainPageController.getSecondAnchorPane().getChildren().setAll(node);
        });
    }

    void load() {
       
        String s = friend.getAboutMe();
        aboutMeTextArea.setEditable(false);
        aboutMeTextArea.setText(s);
        aboutMeTextArea.setWrapText(true);
        aboutMeTextArea.setFont(new Font("Arial", 15));
        aboutMeTextArea.setEditable(false);
        courseNo.setText(String.valueOf(friend.getDesiredCourses().size()));

        String s1 = friend.getUrl();

        File file = new File(s1);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        Circle clip = new Circle(50, 50, 50); // X, Y, Radius
        imageView.setClip(clip);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        locationLabel.setText(friend.getCity());
        mateNo.setText(String.valueOf(friend.getFriends().size()));
        nameAndSurname.setText(friend.getMainname() + " " + friend.getSurname());
        ranking.setText(String.valueOf(friend.getRankings()));
        username.setText("@" + friend.getUsername());
        username1.setText(friend.getUsername());

    }

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }

    public URL getLocation() {
        return location;
    }

    public void setLocation(URL location) {
        this.location = location;
    }

    public TextArea getAboutMeTextArea() {
        return aboutMeTextArea;
    }

    public void setAboutMeTextArea(TextArea aboutMeTextArea) {
        this.aboutMeTextArea = aboutMeTextArea;
    }

    public Label getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(Label courseNo) {
        this.courseNo = courseNo;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Label getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(Label locationLabel) {
        this.locationLabel = locationLabel;
    }

    public Label getMateNo() {
        return mateNo;
    }

    public void setMateNo(Label mateNo) {
        this.mateNo = mateNo;
    }

    public Label getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(Label nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public Label getRanking() {
        return ranking;
    }

    public void setRanking(Label ranking) {
        this.ranking = ranking;
    }

    public Label getUsername() {
        return username;
    }

    public void setUsername(Label username) {
        this.username = username;
    }

    public static User getFriend() {
        return friend;
    }

    public static void setFriend(User friend) {
        OtherProfilesController.friend = friend;
    }

}
