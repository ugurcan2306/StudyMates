package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import com.example.classes.Course;
import com.example.classes.StudyRequest;
import com.example.firebase.FirestoreHelper;
import com.example.firebase.FirestoreUtils;
import com.example.firebase.MailService;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Register {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BtnRegister;

    @FXML
    private PasswordField PasswordFieldConfirm;

    @FXML
    private PasswordField PasswordFieldregister;

    @FXML
    private TextField TextFieldCity;

    @FXML
    private TextField TextFieldEmail;

    @FXML
    private TextField TextFieldName;

    @FXML
    private TextField TextFieldSurname;

    @FXML
    private TextField TextFieldUsername;


    @FXML
    void initialize() {
        assert BtnRegister != null : "fx:id=\"BtnRegister\" was not injected: check your FXML file 'register.fxml'.";
        assert PasswordFieldConfirm != null : "fx:id=\"PasswordFieldConfirm\" was not injected: check your FXML file 'register.fxml'.";
        assert PasswordFieldregister != null : "fx:id=\"PasswordFieldregister\" was not injected: check your FXML file 'register.fxml'.";
        assert TextFieldCity != null : "fx:id=\"TextFieldCity\" was not injected: check your FXML file 'register.fxml'.";
        assert TextFieldEmail != null : "fx:id=\"TextFieldEmail\" was not injected: check your FXML file 'register.fxml'.";
        assert TextFieldName != null : "fx:id=\"TextFieldName\" was not injected: check your FXML file 'register.fxml'.";
        assert TextFieldSurname != null : "fx:id=\"TextFieldSurname\" was not injected: check your FXML file 'register.fxml'.";
        assert TextFieldUsername != null : "fx:id=\"TextFieldUsername\" was not injected: check your FXML file 'register.fxml'.";

    }


    @FXML
    void onRegisterClicked(ActionEvent event) throws IOException, InterruptedException, ExecutionException {
        String username = TextFieldUsername.getText();
        String email = TextFieldEmail.getText();
        String city = TextFieldCity.getText();
        String password = PasswordFieldregister.getText();
        String confirmPassword = PasswordFieldConfirm.getText();
        Firestore db = FirestoreHelper.getFirestore();
        QuerySnapshot querySnapshot = db.collection("users").whereEqualTo("email", email).get().get();
        QuerySnapshot querySnapshot2 = db.collection("users").whereEqualTo("username", username).get().get();

        if (TextFieldName.getText().isEmpty()||TextFieldSurname.getText().isEmpty()||username.isEmpty() || email.isEmpty() || city.isEmpty() || password.isEmpty()||confirmPassword.isEmpty()) {
            
        } 
        else if (!password.equals(confirmPassword)) {
            showAlert("All fields are required.", "Missing Information", Alert.AlertType.ERROR);
            return;
        }
        else if(querySnapshot2!=null&&!querySnapshot2.isEmpty()){
            showAlert("This username is already taken.", "Duplicate Username", Alert.AlertType.ERROR);
            return;
        } 
        else if(querySnapshot!=null&&!querySnapshot.isEmpty()){
            showAlert("This email is already taken", "Same email", Alert.AlertType.ERROR);
            return;
        }

        else {
            try {
                // Initialize Firestore
                System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "src/main/resources/serviceAccountKey.json");
                Firestore database = FirestoreHelper.getFirestore();
              
                FirestoreUtils.addUserToFirestore(database,TextFieldName.getText(),TextFieldSurname.getText(), TextFieldUsername.getText(), PasswordFieldConfirm.getText(), TextFieldEmail.getText(),TextFieldCity.getText(), new ArrayList<Course>(), new ArrayList<String>(),new ArrayList<StudyRequest>(),new ArrayList<StudyRequest>());
                MailService mailService = new MailService();
                mailService.sendWelcomeEmail(email, username);

                showAlert("Registration successful!", "Success", Alert.AlertType.INFORMATION);
                // Redirect to the login page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/login.fxml"));
                Pane loginPane = loader.load();
                Scene loginScene = new Scene(loginPane);
                Stage currentStage = (Stage) BtnRegister.getScene().getWindow();
                currentStage.setScene(loginScene);
                currentStage.show();

            } catch (Exception e) {
               showAlert("Could not save your data: " + e.getMessage(), "Database Error", Alert.AlertType.ERROR);
            }
            }
    }
     private void showAlert(String message, String title, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}


