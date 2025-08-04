package com.example.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import com.example.firebase.FirestoreHelper;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.example.ToControlDatabase.*;
import com.example.classes.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class LoginController {




    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Label forgotPassword;

    @FXML
    private ImageView imageviewid;

    @FXML
    private Button loginButtonid;

    @FXML
    private Button noAccountLabelid;

    @FXML
    private PasswordField passwordtextfieldLabel;

    @FXML
    private TextField usertextFieldid;

    @FXML
    private VBox vbox;



    @FXML
    void initialize() {
        assert exitButton != null : "fx:id=\"exitButton\" was not injected: check your FXML file 'login.fxml'.";
        assert forgotPassword != null : "fx:id=\"forgotPassword\" was not injected: check your FXML file 'login.fxml'.";
        assert imageviewid != null : "fx:id=\"imageviewid\" was not injected: check your FXML file 'login.fxml'.";
        assert loginButtonid != null : "fx:id=\"loginButtonid\" was not injected: check your FXML file 'login.fxml'.";
        assert noAccountLabelid != null : "fx:id=\"noAccountLabelid\" was not injected: check your FXML file 'login.fxml'.";
        assert passwordtextfieldLabel != null : "fx:id=\"passwordtextfieldLabel\" was not injected: check your FXML file 'login.fxml'.";
        assert usertextFieldid != null : "fx:id=\"usertextFieldid\" was not injected: check your FXML file 'login.fxml'.";
        assert vbox != null : "fx:id=\"vbox\" was not injected: check your FXML file 'login.fxml'.";

        Image image= new Image("/pictures/logo.png");
        imageviewid.setImage(image);
        loginButtonid.setOnAction(event -> {
            String username = usertextFieldid.getText();
            String password = passwordtextfieldLabel.getText();

            if (username.isEmpty() || password.isEmpty()) {
               
                showAlert("Please Fill Both Fields", AlertType.WARNING);
            } else {
                try {
                    validateUser(username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        forgotPassword.setStyle("-fx-cursor: hand;");
        forgotPassword.setOnMouseClicked(event->{
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/ForgotPassword.fxml"));
            Scene registerScene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) loginButtonid.getScene().getWindow();
            stage.setScene(registerScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        });

        noAccountLabelid.setOnAction(event -> {
            register();
        });

       
        exitButton.setOnAction(event -> {
            // Close the application when exit button is clicked
            System.exit(0);
        });
        
    }

    private void validateUser(String username, String password) throws InterruptedException, ExecutionException, IOException {
        Firestore db =  FirestoreHelper.getFirestore();
    
        ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("username", username).get(); 
        QuerySnapshot querySnapshot = future.get();
    
        if (querySnapshot.isEmpty()) {
            showAlert("The username does not exist.", AlertType.WARNING);
        } else {
            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                String storedPassword = document.getString("password");
                String storedUsername=document.getString("username");
                if (storedPassword != null && storedPassword.equals(password) &&  storedUsername != null && storedUsername.equals(username)) {
                    Manager.setCurrentUser(username);          
                    enter();
                    return;
                }
            }
            showAlert("The password is incorrect.", AlertType.WARNING);
        }
    }
    
    private void showAlert( String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void enter() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/MainPage.fxml"));
            Scene registerScene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) loginButtonid.getScene().getWindow();
            stage.setScene(registerScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void register() {
        try {          
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/register.fxml")); 
            Scene registerScene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) loginButtonid.getScene().getWindow();
            stage.setScene(registerScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


