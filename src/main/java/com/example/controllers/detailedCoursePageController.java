package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;



import com.example.ToControlDatabase.Finder;
import com.example.ToControlDatabase.Manager;
import com.example.classes.Course;
import com.example.classes.StudyRequest;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.example.firebase.FirestoreUtils;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class detailedCoursePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label courseCode;

    @FXML
    private Label courseName;

    @FXML
    private Button lecture1;

    @FXML
    private Button lecture2;

    @FXML
    private Button lecture3;

    @FXML
    private Button lecture4;

    @FXML
    private Button lecture5;

    @FXML
    private Button lecture6;

    @FXML
    private TextFlow textFlow;

    @FXML
    private VBox vBox;

    @FXML
    private Button registerLectureButton;


    @FXML
    void registerLectureButtonPressed(ActionEvent event) throws InterruptedException, ExecutionException {
        Course c= Finder.courseCodeFinder(courseCode.getText());
        User currUser= Manager.getCurrentUser();
        currUser.getDesiredCourses().add(c);

            Firestore db= FirestoreHelper.getFirestore();
            DocumentReference doc =db.collection("users").document(currUser.getUsername());
            doc.update("desiredCourses",currUser.getDesiredCourses()).get();

            registerLectureButton.setText("Registered");
            registerLectureButton.setDisable(true);
            
        
    }
    
    Course openedCourse;

    void checkRegisterButtonAvailabity(){
        User curr= Manager.getCurrentUser();
        if(curr.getDesiredCourses().contains(openedCourse)){
            registerLectureButton.setText("Registered");
            registerLectureButton.setDisable(true);
        }
        else{
            registerLectureButton.setText("Register");
            registerLectureButton.setDisable(false);
        }
    }

    @FXML
    void lecture1Pressed(ActionEvent event) throws IOException {
        switch (coursesPageController.whichButton) {
            case 1:
                openedCourse = Finder.courseCodeFinder("CS101");
                break;
            case 2:
                openedCourse = Finder.courseCodeFinder("EE101");
                break;
            case 3:
                openedCourse = Finder.courseCodeFinder("IE101");
                break;
            case 4:
                openedCourse = Finder.courseCodeFinder("MATH101");
                break;
            case 5:
                openedCourse = Finder.courseCodeFinder("ME101");
                break;
            case 6:
                openedCourse = Finder.courseCodeFinder("PHYS101");
                break;
            default:
                System.err.println("Geçersiz seçim: whichButton = " + coursesPageController.whichButton);
                return; // Erken çıkış yap
        }

        textFlow.getChildren().clear();
        Text text = new Text(openedCourse.getDescription());
        textFlow.getChildren().add(text);
        
        courseCode.setText(openedCourse.getCourseCode());
        loadUsersFromFirestore(openedCourse);
        checkRegisterButtonAvailabity();
    }

    @FXML
    void lecture2Pressed(ActionEvent event) throws IOException {
        switch (coursesPageController.whichButton) {
            case 1:
                openedCourse = Finder.courseCodeFinder("CS102");
                break;
            case 2:
                openedCourse = Finder.courseCodeFinder("EE102");
                break;
            case 3:
                openedCourse = Finder.courseCodeFinder("IE102");
                break;
            case 4:
                openedCourse = Finder.courseCodeFinder("MATH102");
                break;
            case 5:
                openedCourse = Finder.courseCodeFinder("ME102");
                break;
            case 6:
                openedCourse = Finder.courseCodeFinder("PHYS102");
                break;
            default:
                System.err.println("Geçersiz seçim: whichButton = " + coursesPageController.whichButton);
                return; // Erken çıkış yap
        }
        textFlow.getChildren().clear();
        Text text = new Text(openedCourse.getDescription());
        textFlow.getChildren().add(text);
        courseCode.setText(openedCourse.getCourseCode());
        loadUsersFromFirestore(openedCourse);
        checkRegisterButtonAvailabity();
    }

    @FXML
    void lecture3Pressed(ActionEvent event) throws IOException {
        switch (coursesPageController.whichButton) {
            case 1:
                openedCourse = Finder.courseCodeFinder("CS103");
                break;
            case 2:
                openedCourse = Finder.courseCodeFinder("EE103");
                break;
            case 3:
                openedCourse = Finder.courseCodeFinder("IE103");
                break;
            case 4:
                openedCourse = Finder.courseCodeFinder("MATH103");
                break;
            case 5:
                openedCourse = Finder.courseCodeFinder("ME103");
                break;
            case 6:
                openedCourse = Finder.courseCodeFinder("PHYS103");
                break;
            default:
                System.err.println("Geçersiz seçim: whichButton = " + coursesPageController.whichButton);
                return; // Erken çıkış yap
        }
        textFlow.getChildren().clear();
        Text text = new Text(openedCourse.getDescription());
        textFlow.getChildren().add(text);
        courseCode.setText(openedCourse.getCourseCode());
        loadUsersFromFirestore(openedCourse);
        checkRegisterButtonAvailabity();
    }

    @FXML
    void lecture4Pressed(ActionEvent event) throws IOException {
        switch (coursesPageController.whichButton) {
            case 1:
                openedCourse = Finder.courseCodeFinder("CS104");
                break;
            case 2:
                openedCourse = Finder.courseCodeFinder("EE104");
                break;
            case 3:
                openedCourse = Finder.courseCodeFinder("IE104");
                break;
            case 4:
                openedCourse = Finder.courseCodeFinder("MATH104");
                break;
            case 5:
                openedCourse = Finder.courseCodeFinder("ME104");
                break;
            case 6:
                openedCourse = Finder.courseCodeFinder("PHYS104");
                break;
            default:
                System.err.println("Geçersiz seçim: whichButton = " + coursesPageController.whichButton);
                return; // Erken çıkış yap
        }
        textFlow.getChildren().clear();
        Text text = new Text(openedCourse.getDescription());
        textFlow.getChildren().add(text);
        courseCode.setText(openedCourse.getCourseCode());
        loadUsersFromFirestore(openedCourse);
        checkRegisterButtonAvailabity();
    }

    @FXML
    void lecture5Pressed(ActionEvent event) throws IOException {
        switch (coursesPageController.whichButton) {
            case 1:
                openedCourse = Finder.courseCodeFinder("CS105");
                break;
            case 2:
                openedCourse = Finder.courseCodeFinder("EE105");
                break;
            case 3:
                openedCourse = Finder.courseCodeFinder("IE105");
                break;
            case 4:
                openedCourse = Finder.courseCodeFinder("MATH105");
                break;
            case 5:
                openedCourse = Finder.courseCodeFinder("ME105");
                break;
            case 6:
                openedCourse = Finder.courseCodeFinder("PHYS105");
                break;
            default:
                System.err.println("Geçersiz seçim: whichButton = " + coursesPageController.whichButton);
                return; // Erken çıkış yap
        }
        textFlow.getChildren().clear();
        Text text = new Text(openedCourse.getDescription());
        textFlow.getChildren().add(text);
        courseCode.setText(openedCourse.getCourseCode());
        loadUsersFromFirestore(openedCourse);
        checkRegisterButtonAvailabity();
    }

    @FXML
    void lecture6Pressed(ActionEvent event) throws IOException {
        switch (coursesPageController.whichButton) {
            case 1:
                openedCourse = Finder.courseCodeFinder("CS106");
                break;
            case 2:
                openedCourse = Finder.courseCodeFinder("EE106");
                break;
            case 3:
                openedCourse = Finder.courseCodeFinder("IE106");
                break;
            case 4:
                openedCourse = Finder.courseCodeFinder("MATH106");
                break;
            case 5:
                openedCourse = Finder.courseCodeFinder("ME106");
                break;
            case 6:
                openedCourse = Finder.courseCodeFinder("PHYS106");
                break;
            default:
                System.err.println("Geçersiz seçim: whichButton = " + coursesPageController.whichButton);
                return; // Erken çıkış yap
        }
        textFlow.getChildren().clear();
        Text text = new Text(openedCourse.getDescription());
        textFlow.getChildren().add(text);
        courseCode.setText(openedCourse.getCourseCode());
        loadUsersFromFirestore(openedCourse);
        checkRegisterButtonAvailabity();
    }

    @FXML
    void initialize() throws IOException {
        assert courseCode != null
                : "fx:id=\"courseCode\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert courseName != null
                : "fx:id=\"courseName\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert lecture1 != null
                : "fx:id=\"lecture1\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert lecture2 != null
                : "fx:id=\"lecture2\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert lecture3 != null
                : "fx:id=\"lecture3\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert lecture4 != null
                : "fx:id=\"lecture4\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert lecture5 != null
                : "fx:id=\"lecture5\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert lecture6 != null
                : "fx:id=\"lecture6\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert textFlow != null
                : "fx:id=\"textFlow\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'detailedCoursePage.fxml'.";
        switch (coursesPageController.whichButton) {
            case 1:
                openedCourse = Finder.courseCodeFinder("CS101");
                break;
            case 2:
                openedCourse = Finder.courseCodeFinder("EE101");
                break;
            case 3:
                openedCourse = Finder.courseCodeFinder("IE101");
                break;
            case 4:
                openedCourse = Finder.courseCodeFinder("MATH101");
                break;
            case 5:
                openedCourse = Finder.courseCodeFinder("ME101");
                break;
            case 6:
                openedCourse = Finder.courseCodeFinder("PHYS101");
                break;
            default:
                System.err.println("Geçersiz seçim: whichButton = " + coursesPageController.whichButton);
                return; // Erken çıkış yap
        }
        loadUsersFromFirestore(openedCourse);
        checkRegisterButtonAvailabity();
        
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

    public Label getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(Label courseCode) {
        this.courseCode = courseCode;
    }

    public Label getCourseName() {
        return courseName;
    }

    public void setCourseName(Label courseName) {
        this.courseName = courseName;
    }

    public Button getLecture1() {
        return lecture1;
    }

    public void setLecture1(Button lecture1) {
        this.lecture1 = lecture1;
    }

    public Button getLecture2() {
        return lecture2;
    }

    public void setLecture2(Button lecture2) {
        this.lecture2 = lecture2;
    }

    public Button getLecture3() {
        return lecture3;
    }

    public void setLecture3(Button lecture3) {
        this.lecture3 = lecture3;
    }

    public Button getLecture4() {
        return lecture4;
    }

    public void setLecture4(Button lecture4) {
        this.lecture4 = lecture4;
    }

    public Button getLecture5() {
        return lecture5;
    }

    public void setLecture5(Button lecture5) {
        this.lecture5 = lecture5;
    }

    public Button getLecture6() {
        return lecture6;
    }

    public void setLecture6(Button lecture6) {
        this.lecture6 = lecture6;
    }

    public TextFlow getTextFlow() {
        return textFlow;
    }

    public void setTextFlow(TextFlow textFlow) {
        this.textFlow = textFlow;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    void loadUsersFromFirestore(Course c) throws IOException {
        vBox.getChildren().clear();
        try {
            Firestore db = FirestoreHelper.getFirestore();
            QuerySnapshot userCollection = db.collection("users").get().get();

            for (int i = 0; i < userCollection.size(); i++) {
                User user=userCollection.getDocuments().get(i).toObject(User.class);
                if (user.getDesiredCourses().contains(c)
                        && !user.equals(Manager.getCurrentUser())) {
                    showUsers(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while loading users: " + e.getMessage());
        }
    }

    void showUsers(User currFriend) throws IOException {
        vBox.setSpacing(5);
        User currUser= Manager.getCurrentUser();

        HBox requestSection = new HBox(15); // Daha fazla boşluk
        requestSection.setPadding(new Insets(10));
        requestSection.setAlignment(Pos.CENTER_LEFT);
        requestSection.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #d0d0d0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        Firestore db= FirestoreHelper.getFirestore();

             ImageView iView = new ImageView();
            String s1 = currFriend.getUrl();
            File file = new File(s1);
            Image image = new Image(file.toURI().toString());
         
            
            iView.setImage(image);

            Circle clip = new Circle(15, 15, 15); // X, Y, Radius → küçültüldü
            iView.setClip(clip);
            iView.setFitWidth(30);   // Görsel boyutu küçük
            iView.setFitHeight(30);

    

        //
        Label usernameLabel = new Label(currFriend.getUsername());
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        
        usernameLabel.setOnMouseClicked(event ->{
                MainPageController.getSecondAnchorPane().getChildren().clear();
                FXMLLoader loader;

                if(currUser.getFriends().contains(currFriend.getUsername())){
                    loader = new FXMLLoader(getClass().getResource("/fxmls/OtherifFriend.fxml"));
                    OtherifFriendController.setFriend(currFriend);}
                else{
                    loader = new FXMLLoader(getClass().getResource("/fxmls/other.fxml"));
                    OtherProfilesController.setFriend(currFriend);        
                }
                   
                Node node = null;
                try {
                    node = loader.load();
                } catch (IOException e) {
            
                    e.printStackTrace();
                }       
                MainPageController.getSecondAnchorPane().getChildren().setAll(node);
        });

        Button unsendButton = new Button("Unsend");
        unsendButton
                .setStyle("-fx-background-color:rgb(224, 105, 105); -fx-text-fill: white; -fx-background-radius: 5px;");
        unsendButton.setCursor(Cursor.HAND);

        Button sendRequest = new Button("Send Request");
        sendRequest
                .setStyle("-fx-background-color:rgb(103, 135, 230); -fx-text-fill: white; -fx-background-radius: 5px;");
        sendRequest.setCursor(Cursor.HAND);

         // Aradaki boşluğu otomatik yaymak için
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        sendRequest.setOnAction(event -> {
            try {
                FirestoreUtils.addStudyRequesttoTheFirestore(FirestoreHelper.getFirestore(),currUser.getUsername(), currFriend.getUsername(),openedCourse.getCourseCode());
                StudyRequest st = new StudyRequest(currUser.getUsername(),currFriend.getUsername(), openedCourse.getCourseCode());
                Manager.getCurrentUser().getSentStudyRequests().add(st);
                currFriend.getReceivedStudyRequests().add(st);
                DocumentReference studyRef = db.collection("users").document(currUser.getUsername());
                studyRef.update("sentStudyRequests", currUser.getSentStudyRequests()).get();
                DocumentReference studyRef2 = db.collection("users").document(currFriend.getUsername());
                studyRef2.update("receivedStudyRequests", currFriend.getReceivedStudyRequests()).get();
                requestSection.getChildren().clear();
                
                requestSection.getChildren().addAll(iView, usernameLabel, spacer, unsendButton);
            
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        

        unsendButton.setOnAction(event -> {
            String docId = currUser.getUsername() + "_" + currFriend.getUsername()+"_"+openedCourse.getCourseCode();
            try {
                StudyRequest st = Finder.StudyReqFinder(currUser.getUsername(), currFriend.getUsername(),openedCourse.getCourseCode());
                currUser.getSentStudyRequests().remove(st);
                currFriend.getReceivedStudyRequests().remove(st);
                
                
                DocumentReference tradeRef = db.collection("users").document(currUser.getUsername());
                tradeRef.update("sentStudyRequests", currUser.getSentStudyRequests()).get();
                DocumentReference tradeRef2 = db.collection("users").document(currFriend.getUsername());
                tradeRef2.update("receivedStudyRequests", currFriend.getReceivedStudyRequests()).get();
                requestSection.getChildren().clear();
                requestSection.getChildren().addAll(iView, usernameLabel, spacer, sendRequest);
                db.collection("studyRequests").document(docId).delete();
                
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        if (Finder.StudyReqFinder(Manager.getCurrentUser().getUsername(), currFriend.getUsername(),openedCourse.getCourseCode()) == null) {
            requestSection.getChildren().addAll(iView, usernameLabel, spacer, sendRequest);
        } else {
            requestSection.getChildren().addAll(iView, usernameLabel, spacer, unsendButton);
        }
        // Add the friend's section to the main VBox container
        vBox.getChildren().add(requestSection);
    }

}
