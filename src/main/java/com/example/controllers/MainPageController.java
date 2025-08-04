package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.ToControlDatabase.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainPageController {
    private List<Node> initialContent;

    static String searchBarResult;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Author;

    @FXML
    private Button chatButton;

    @FXML
    private Label StudyMatesLabel;

    @FXML
    private TextField TextField;

    @FXML
    private Label Word;

    @FXML
    private Label WordTitle;

    @FXML
    private Button coursesButton;

    @FXML
    private Button logOutButton;

    @FXML
    private AnchorPane mainContent;

    private static AnchorPane mainContentStatic;
    private static AnchorPane secondAnchorPaneStatic;

    @FXML
    private Button profileButton;
    
    @FXML
    private Button mainPageButton;

    @FXML
    private Button searchButton;

    @FXML
    private AnchorPane secondAnchorPane;

    @FXML
    private Button settingsButton;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane popupMainContent;

    @FXML
    private VBox vBox;

    @FXML
    private Font x1;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    void pressFilter(ActionEvent event) throws IOException {
        if (popupMainContent.getChildren().isEmpty()) {
            Node node = FXMLLoader.load(getClass().getResource("/fxmls/filter.fxml"));
            popupMainContent.getChildren().setAll(node);
        } else {
            popupMainContent.getChildren().clear();
        }
    }
    @FXML
    void pressSearchButton(ActionEvent event) throws IOException {
        mainContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/search.fxml"));
        Node node = loader.load();
        searchController control = loader.getController(); // Artık çalışır
        control.setSearchedPersonUsername(TextField.getText()); // Veriyi gönder
        mainContent.getChildren().setAll(node); // AnchorPane üzerine koy
    }

    @FXML
    void chatsButtonPressed(ActionEvent event) {
        secondAnchorPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/allRequests.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        secondAnchorPane.getChildren().setAll(node);

    }

    @FXML
    void coursesButtonPressed(ActionEvent event) {
        secondAnchorPane.getChildren().clear();
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
        secondAnchorPane.getChildren().setAll(node);
    }
    @FXML   
    void mainPageButtonPressed(ActionEvent event) {
    secondAnchorPane.getChildren().setAll(initialContent);
}


    @FXML
    void friendsButtonPressed(ActionEvent event) {
        secondAnchorPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/friends.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        secondAnchorPane.getChildren().setAll(node);
    }

   @FXML
void logoutButtonPressed(ActionEvent event) {
    try {
        // Login ekranını yükle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/login.fxml"));
        Parent root = loader.load();

        // Yeni sahne oluştur
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(root));
        loginStage.setTitle("Login");

        // Mevcut sahneyi kapat
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Yeni sahneyi göster
        loginStage.show();

        // Oturum bilgisini sıfırla
        Manager.setCurrentUser(null);

    } catch (IOException e) {
        e.printStackTrace();
    }
}

    

    @FXML
    void profileButtonPressed(ActionEvent event) {
        secondAnchorPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/MyProfile.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        secondAnchorPane.getChildren().setAll(node);
    }

    @FXML
    void settingsButtonPressed(ActionEvent event) {
        secondAnchorPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/newSettings.fxml"));
        Node node=null;
        try {
            node = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        secondAnchorPane.getChildren().setAll(node); 
    }

    public static AnchorPane getMainContent() {
        return mainContentStatic;
    }
    public static AnchorPane getSecondAnchorPane(){
        return secondAnchorPaneStatic;
    }
    public static void setMainContentStatic(AnchorPane mainContentStatic) {
        MainPageController.mainContentStatic = mainContentStatic;
    }
    public static void setSecondAnchorPaneStatic(AnchorPane secondAnchorPaneStatic) {
        MainPageController.secondAnchorPaneStatic = secondAnchorPaneStatic;
    }

    

    @FXML
    void initialize() {
        mainContentStatic = mainContent;
        secondAnchorPaneStatic= secondAnchorPane;
        assert Author != null : "fx:id=\"Author\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert chatButton != null : "fx:id=\"FilterButton\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert StudyMatesLabel != null
                : "fx:id=\"StudyMatesLabel\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert TextField != null : "fx:id=\"TextField\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert Word != null : "fx:id=\"Word\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert WordTitle != null : "fx:id=\"WordTitle\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert coursesButton != null
                : "fx:id=\"coursesButton\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert logOutButton != null : "fx:id=\"logOutButton\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert mainContent != null : "fx:id=\"mainContent\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert profileButton != null
                : "fx:id=\"profileButton\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert secondAnchorPane != null
                : "fx:id=\"secondAnchorPane\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert settingsButton != null
                : "fx:id=\"settingsButton\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert splitPane != null : "fx:id=\"splitPane\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert x3 != null : "fx:id=\"x3\" was not injected: check your FXML file 'MainPage.fxml'.";
        assert x4 != null : "fx:id=\"x4\" was not injected: check your FXML file 'MainPage.fxml'.";
        initialContent = new ArrayList<>(secondAnchorPane.getChildren());
    }

}
