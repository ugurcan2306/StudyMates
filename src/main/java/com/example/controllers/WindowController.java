package com.example.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class WindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchpane;

    @FXML
    private Button sendbtn;

    @FXML
    private TextArea textarea;

    @FXML
    private TextField textfield;

    @FXML
    private VBox vbox;

    @FXML
    void sendbutton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert anchpane != null : "fx:id=\"anchpane\" was not injected: check your FXML file 'one2onechatWindow.fxml'.";
        assert sendbtn != null : "fx:id=\"sendbtn\" was not injected: check your FXML file 'one2onechatWindow.fxml'.";
        assert textarea != null : "fx:id=\"textarea\" was not injected: check your FXML file 'one2onechatWindow.fxml'.";
        assert textfield != null : "fx:id=\"textfield\" was not injected: check your FXML file 'one2onechatWindow.fxml'.";
        assert vbox != null : "fx:id=\"vbox\" was not injected: check your FXML file 'one2onechatWindow.fxml'.";

    }

}

