package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.ToControlDatabase.Finder;
import com.example.ToControlDatabase.Manager;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.example.firebase.FirestoreUtils;
import com.google.cloud.firestore.Firestore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class popUpCommentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TextField textField;

    @FXML
    private Label usernameLabel;

    private OtherifFriendController parentController;

    public void setParentController(OtherifFriendController controller) {
        this.parentController = controller;
    }


    

    @FXML
    void addButtonPressed(ActionEvent event) throws IOException {
        String sender= Manager.getCurrentUser().getUsername();
        String receiver= OtherifFriendController.getFriend().getUsername();
        Firestore db=null;
        String commentS= textField.getText();

            db = FirestoreHelper.getFirestore();
        

        if(Finder.commentFinder(sender, receiver)!=null){
            db.collection("comments").document(sender+"_"+receiver).update("commentS",commentS);
        }
       else{
        FirestoreUtils.addCommentsToTheFirestore(db,sender,receiver,commentS);
       }
       parentController.getvBoxComments().getChildren().clear();
       parentController.loadComments();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'popUpComment.fxml'.";
        assert textField != null : "fx:id=\"textField\" was not injected: check your FXML file 'popUpComment.fxml'.";
        assert usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'popUpComment.fxml'.";
        usernameLabel.setText( OtherifFriendController.getFriend().getUsername());

    }
    


}

