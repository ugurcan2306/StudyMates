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
import com.example.classes.Ranking;
import com.example.classes.StudyRequest;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.example.firebase.FirestoreUtils;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class OtherifFriendController {
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

    @FXML
    private Slider slider;

    @FXML
    private Button rankButton;

    @FXML
    private Button addCommentButton;

    static User friend;

    @FXML
    private Label buttonLabelRank;

    public void setStudyMatesLabel(Label studyMatesLabel) {
        StudyMatesLabel = studyMatesLabel;
    }
    

    void addCommentBox(String commenterUsername, String commentText, String imageUrl) {
    HBox container = new HBox(10);
    container.setPadding(new Insets(5));
    container.setAlignment(Pos.CENTER_LEFT);
    container.setStyle("-fx-background-color: #eef5ff; -fx-border-color: #ccc; -fx-border-radius: 8px; -fx-background-radius: 8px;");

    ImageView avatar = new ImageView();
    avatar.setFitWidth(30);
    avatar.setFitHeight(30);
    avatar.setClip(new Circle(15, 15, 15));

    if (imageUrl != null) {
        File file = new File(imageUrl);
        avatar.setImage(new Image(file.toURI().toString()));
    }

    Label nameLabel = new Label(commenterUsername);
    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

    Label commentLabel = new Label(commentText);
    commentLabel.setWrapText(true);
    commentLabel.setStyle("-fx-font-size: 13px;");

    VBox commentBox = new VBox(nameLabel, commentLabel);
    container.getChildren().addAll(avatar, commentBox);

    vBoxComments.getChildren().add(container);
    }

    void loadComments() {
    new Thread(() -> {
        try {
            Firestore db = FirestoreHelper.getFirestore();
            QuerySnapshot snapshot = db.collection("comments")
                    .whereEqualTo("receiverName", friend.getUsername())
                    .get()
                    .get();

            Platform.runLater(() -> {
                for (DocumentSnapshot doc : snapshot.getDocuments()) {
                    Comment comment = doc.toObject(Comment.class);
                    if (comment != null) {
                        String commenterUrl= Finder.UserFinder(comment.getSenderName()).getUrl();
                        addCommentBox(comment.getSenderName(), comment.getCommentS(), commenterUrl); // yeni metod
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }).start();
}


    @FXML
    void rankButtonPressed(ActionEvent event) {

        User currUser = Manager.getCurrentUser();
        
            Firestore db = FirestoreHelper.getFirestore();

            // rerank olarak gelirse
            if (Finder.rankingFinder(Manager.getCurrentUser().getUsername(), friend.getUsername()) != null) {
                Ranking r = Finder.rankingFinder(currUser.getUsername(), friend.getUsername());
                double currentTotal = friend.getRankings() * friend.getHowManyTimesRanked();
                currentTotal = currentTotal + slider.getValue() - r.getRank();
                friend.setRankings(currentTotal / friend.getHowManyTimesRanked());
                db.collection("users").document(friend.getUsername()).update("rankings", friend.getRankings());
                db.collection("rankings").document(currUser.getUsername() + "_" + friend.getUsername()).update("rank",
                        slider.getValue());
                ranking.setText(friend.getRankings() + "");
                buttonLabelRank.setText("Rerank");

            }

            // rank olarak gelirse
            else {

                double rank = slider.getValue();
                double currentTotal = friend.getRankings() * friend.getHowManyTimesRanked();
                friend.setHowManyTimesRanked(friend.getHowManyTimesRanked() + 1);
                currentTotal = currentTotal + rank;

                friend.setRankings((currentTotal / friend.getHowManyTimesRanked()));

                db.collection("users").document(friend.getUsername()).update("rankings", friend.getRankings());
                db.collection("users").document(friend.getUsername()).update("howManyTimesRanked",
                        friend.getHowManyTimesRanked());
                ranking.setText(friend.getRankings() + "");

                FirestoreUtils.addRankingToTheFirestore(db, Manager.getCurrentUser().getUsername(),
                        friend.getUsername(), rank);
                buttonLabelRank.setText("Rerank");

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

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public Button getRankButton() {
        return rankButton;
    }

    public void setRankButton(Button rankButton) {
        this.rankButton = rankButton;
    }

    public Button getAddCommentButton() {
        return addCommentButton;
    }

    public void setAddCommentButton(Button addCommentButton) {
        this.addCommentButton = addCommentButton;
    }

    public Label getButtonLabelRank() {
        return buttonLabelRank;
    }

    public void setButtonLabelRank(Label buttonLabelRank) {
        this.buttonLabelRank = buttonLabelRank;
    }

    @FXML
    void addCommentButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/popUpComment.fxml"));
            Parent root = loader.load();

            popUpCommentController controller = loader.getController();
            controller.setParentController(this);

            Stage newStage = new Stage();
            
            newStage.initModality(Modality.APPLICATION_MODAL); // Ana pencereyi kilitle
            newStage.setTitle("Add Comment");
            newStage.setScene(new Scene(root));
            newStage.showAndWait(); // Modal olarak göster (bekletici)
            addCommentButton.setText("Recomment");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        slider.setSnapToTicks(true);

        StudyMatesLabel.setStyle("-fx-cursor: hand;");

        StudyMatesLabel.setOnMouseClicked(event ->{
             MainPageController.getSecondAnchorPane().getChildren().clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/FriendsFriends.fxml"));
                FriendsFriendsController.currFriend=friend;
                Node node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
            
                    e.printStackTrace();
                }       
                MainPageController.getSecondAnchorPane().getChildren().setAll(node);
        } );

        courseNumberLabel.setStyle("-fx-cursor: hand;");
        
        courseNumberLabel.setOnMouseClicked(event->{
                MainPageController.getSecondAnchorPane().getChildren().clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/FriendsCourses.fxml"));
                FriendsCoursesController.currFriend=friend;
                Node node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
            
                    e.printStackTrace();
                }       
                MainPageController.getSecondAnchorPane().getChildren().setAll(node);

        });
        load();
        loadComments();
    }

   void load() {
            String about = friend.getAboutMe();
            String imageUrl = friend.getUrl();

            
                aboutMeTextArea.setEditable(false);
                aboutMeTextArea.setWrapText(true);
                aboutMeTextArea.setText(about);
                aboutMeTextArea.setFont(new Font("Arial", 15));

                courseNo.setText(String.valueOf(friend.getDesiredCourses().size()));
                locationLabel.setText(friend.getCity());
                mateNo.setText(String.valueOf(friend.getFriends().size()));
                nameAndSurname.setText(friend.getMainname() + " " + friend.getSurname());
                ranking.setText(String.valueOf(friend.getRankings()));
                username.setText("@" + friend.getUsername());
                username1.setText(friend.getUsername());

                if (imageUrl != null) {
                    File file = new File(imageUrl);
                    imageView.setImage(new Image(file.toURI().toString()));
                    Circle clip = new Circle(50, 50, 50);
                    imageView.setClip(clip);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                }

                if (Finder.rankingFinder(Manager.getCurrentUser().getUsername(), friend.getUsername()) != null) {
                    buttonLabelRank.setText("Rerank");
                }
                if (Finder.commentFinder(Manager.getCurrentUser().getUsername(), friend.getUsername()) != null) {
                    addCommentButton.setText("Recomment");
                }

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
        OtherifFriendController.friend = friend;
    }

}
