package com.example.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.example.ToControlDatabase.Manager;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.scene.text.Text;

public class settingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button applyButton;

    @FXML
    private PasswordField confirmField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField nameFİeld;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField surnameField;

    @FXML
    private Button updatePPButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextArea textArea;

    @FXML
    private ImageView imageView;

    User currUser = Manager.getCurrentUser();

    @FXML
    void applyButtonPressed(ActionEvent event) throws IOException, InterruptedException, ExecutionException {
    

            Firestore db = FirestoreHelper.getFirestore();
            QuerySnapshot querySnapshot = db.collection("users").whereEqualTo("email", emailField.getText()).get().get();
            QuerySnapshot querySnapshot2 = db.collection("users").whereEqualTo("username", usernameField.getText()).get().get();

            if (!newPasswordField.getText().equals(confirmField.getText())) {
                Alert alert = new Alert(AlertType.ERROR, "Passwords do not match. Please try again.",
                        ButtonType.OK);
                alert.setTitle("Error");
                alert.setHeaderText("Password Mismatch");
                alert.showAndWait();
                return;
            }

            else if(querySnapshot2!=null&&!querySnapshot2.isEmpty()&& !usernameField.getText().equals(currUser.getUsername())){
            showAlert("This username is already taken.", "Duplicate Username", Alert.AlertType.ERROR);
            return;
            } 
            else if(querySnapshot!=null&&!querySnapshot.isEmpty()&& !emailField.getText().equals(currUser.getEmail())){
            showAlert("This email is already taken", "Same email", Alert.AlertType.ERROR);
            return;
            }
            
            else {

                currUser.setSurname(surnameField.getText());
                currUser.setUsername(usernameField.getText());
                currUser.setMainname(nameFİeld.getText());
                currUser.setEmail(emailField.getText());
                currUser.setCity(locationField.getText());
                currUser.setAboutMe(textArea.getText());

                db.collection("users").document(currUser.getUsername()).update("username", usernameField.getText());
                db.collection("users").document(currUser.getUsername()).update("mainname", nameFİeld.getText());
                db.collection("users").document(currUser.getUsername()).update("surname", surnameField.getText());
                db.collection("users").document(currUser.getUsername()).update("email", emailField.getText());
                db.collection("users").document(currUser.getUsername()).update("city", locationField.getText());
                db.collection("users").document(currUser.getUsername()).update("aboutMe", textArea.getText());

                if (!newPasswordField.getText().isEmpty()) {
                    db.collection("users").document(currUser.getUsername()).update("password", confirmField.getText());
                }

                Alert alert = new Alert(AlertType.CONFIRMATION, "Changes are applied.",
                        ButtonType.OK);
                alert.setTitle("Successful");
                alert.setHeaderText("Successful Update");
                alert.showAndWait();
            }
            load();
        }

    @FXML
    void updatePPPressed(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Profil Fotoğrafı Seç");
        // Sadece resim dosyalarını filtrele
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.arw"));

        // Dosya seçim penceresini aç
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);

            // Gerekirse dosya yolunu veritabanına veya dosyaya kaydedebilirsin
            System.out.println("Seçilen dosya: " + selectedFile.getAbsolutePath());

        }
        FirestoreHelper.getFirestore().collection("users").document(currUser.getUsername()).update("url",
                selectedFile.getAbsolutePath());
        currUser.setUrl(selectedFile.getAbsolutePath());

    }
    private void showAlert(String message, String title, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    void initialize() throws IOException, InterruptedException, ExecutionException {
        assert applyButton != null : "fx:id=\"applyButton\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert confirmField != null
                : "fx:id=\"confirmField\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert locationField != null
                : "fx:id=\"locationField\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert nameFİeld != null : "fx:id=\"nameFİeld\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert newPasswordField != null
                : "fx:id=\"newPasswordField\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert surnameField != null
                : "fx:id=\"surnameField\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert updatePPButton != null
                : "fx:id=\"updatePPButton\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert usernameField != null
                : "fx:id=\"usernameField\" was not injected: check your FXML file 'newSettings.fxml'.";
        assert textArea != null;

        load();

    }

    void load(){
        
        Font f = new Font("Georgia", 15);

        usernameField.setText(currUser.getUsername());
        usernameField.setFont(f);

        nameFİeld.setText(currUser.getMainname());
        nameFİeld.setFont(f);

        surnameField.setText(currUser.getSurname());
        surnameField.setFont(f);

        locationField.setText(currUser.getCity());
        locationField.setFont(f);

        emailField.setText(currUser.getEmail());
        emailField.setFont(f);
        textArea.setText(currUser.getAboutMe());
        textArea.setEditable(true);

        File file = new File(currUser.getUrl());
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        Circle clip = new Circle(50, 50, 50); // X, Y, Radius
        imageView.setClip(clip);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

    }

}
