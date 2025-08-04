package com.example.controllers;

import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.example.firebase.MailService;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Random;

// Burası ve buranın fxml indeki controller kısmı değiştirildi.
public class ForgotPasswordController 
{
    @FXML
    private TextField usertextFieldid;

    @FXML
    private Button loginButtonid;

    @FXML
    private Button exitButton;

    @FXML
    private Label secureLoginLabel; // "Forgot Password" başlığı

    @FXML
    private Label usernameLabel; // "Email Address" label'ı

    // Butona basılınca çağrılacak method
    @FXML
    void initialize() {
        loginButtonid.setOnAction(this::handleSendButton);
        exitButton.setOnAction(this::handleExitButton);
    }

    private void handleSendButton(ActionEvent event) {
        
        String email = usertextFieldid.getText().trim();

        if (email.isEmpty()) {
            secureLoginLabel.setText("Please enter your email.");
            return;
        }

        // 1. Yeni şifre oluştur
        String newPassword = generateRandomPassword(); // ya da "Study1234"

        // 2. Firestore/Firebase'de şifre güncelle TO DO

        User userToFind;
        MailService mailService = new MailService();
        try {
            Firestore db = FirestoreHelper.getFirestore();
            QuerySnapshot querySnapshot = db.collection("users").whereEqualTo("email",email).get().get();
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                userToFind = querySnapshot.getDocuments().get(0).toObject(User.class);
                userToFind.setPassword(newPassword);
                db.collection("users").document(userToFind.getUsername()).update("password",userToFind.getPassword());
            } else {
                userToFind = null;
                System.out.println("User not found in Firestore.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        mailService.sendResetPasswordEmail(email, "User", newPassword);
        

        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButtonid.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
            secureLoginLabel.setText("Something went wrong.");
        }
    }

    private void handleExitButton(ActionEvent event) {
        // Ana sayfaya ya da uygulamayı kapat
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
