package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.ToControlDatabase.Finder;
import com.example.classes.Course;
import com.example.firebase.FirestoreHelper;
import com.google.cloud.firestore.Firestore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class coursesPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button csButton;

    @FXML
    private Button eeButton;

    @FXML
    private Button ieButton;

    @FXML
    private Button mathButton;

    @FXML
    private Button meButton;

    @FXML
    private Button physButton;

    static int whichButton=0;

    @FXML
    void csButtonPressed(ActionEvent event) {
        whichButton=1;
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/detailedCoursePage.fxml"));
        Course c= Finder.courseCodeFinder("CS101");
        detailedCoursePageController control=null;
        Node node = null;
        try {
            node = loader.load();
            control = loader.getController(); 
        } catch (IOException e){
            e.printStackTrace();
        }
        control.getCourseName().setText("Computer Science");
        control.getLecture1().setText("CS101");
        control.getLecture2().setText("CS102");
        control.getLecture3().setText("CS103");
        control.getLecture4().setText("CS104");
        control.getLecture5().setText("CS105");
        control.getLecture6().setText("CS106");

        control.getCourseCode().setText("CS101");
        
        Text text = new Text(c.getDescription());
        control.getTextFlow().getChildren().clear();    
        control.getTextFlow().getChildren().add(text);
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);
    }
    @FXML
    void eeButtonPressed(ActionEvent event) {
        whichButton=2;
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/detailedCoursePage.fxml"));
        Course c= Finder.courseCodeFinder("EE101");
        detailedCoursePageController control=null;
        Node node = null;
        try {
            node = loader.load();
            control = loader.getController(); 
        } catch (IOException e){
            e.printStackTrace();
        }
        control.getCourseName().setText("Electric-Electronics");
        control.getLecture1().setText("EE101");
        control.getLecture2().setText("EE102");
        control.getLecture3().setText("EE103");
        control.getLecture4().setText("EE104");
        control.getLecture5().setText("EE105");
        control.getLecture6().setText("EE106");

        control.getCourseCode().setText("EE101");
        Text text = new Text(c.getDescription());
        control.getTextFlow().getChildren().clear();    
        control.getTextFlow().getChildren().add(text);
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);
    }

    @FXML
    void ieButtonPressed(ActionEvent event) {
        whichButton=3;
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/detailedCoursePage.fxml"));
        Course c= Finder.courseCodeFinder("IE101");
        detailedCoursePageController control=null;
        Node node = null;
        try {
            node = loader.load();
            control = loader.getController(); 
        } catch (IOException e){
            e.printStackTrace();
        }
        control.getCourseName().setText("Industrial Engineering");
        control.getLecture1().setText("IE101");
        control.getLecture2().setText("IE102");
        control.getLecture3().setText("IE103");
        control.getLecture4().setText("IE104");
        control.getLecture5().setText("IE105");
        control.getLecture6().setText("IE106");

        control.getCourseCode().setText("IE101");
        
        Text text = new Text(c.getDescription());
        control.getTextFlow().getChildren().clear();    
        control.getTextFlow().getChildren().add(text);
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);
    }

    @FXML
    void mathButtonPressed(ActionEvent event) {
        whichButton=4;
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/detailedCoursePage.fxml"));
        Course c= Finder.courseCodeFinder("MATH101");
        detailedCoursePageController control=null;
        Node node = null;
        try {
            node = loader.load();
            control = loader.getController(); 
        } catch (IOException e){
            e.printStackTrace();
        }
        control.getCourseName().setText("Mathematics");
        control.getLecture1().setText("MATH101");
        control.getLecture2().setText("MATH102");
        control.getLecture3().setText("MATH103");
        control.getLecture4().setText( "MATH104");
        control.getLecture5().setText("MATH105");
        control.getLecture6().setText("MATH106");

        control.getCourseCode().setText("MATH101");
        
        Text text = new Text(c.getDescription());
        control.getTextFlow().getChildren().clear();    
        control.getTextFlow().getChildren().add(text);;
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);
    }

    @FXML
    void meButtonPressed(ActionEvent event) {
        whichButton=5;
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/detailedCoursePage.fxml"));
        Course c= Finder.courseCodeFinder("ME101");
        detailedCoursePageController control=null;
        Node node = null;
        try {
            node = loader.load();
            control = loader.getController(); 
        } catch (IOException e){
            e.printStackTrace();
        }
        control.getCourseName().setText("Mechanical Engineering");
        control.getLecture1().setText("ME101");
        control.getLecture2().setText("ME102");
        control.getLecture3().setText("ME103");
        control.getLecture4().setText("ME104");
        control.getLecture5().setText("ME105");
        control.getLecture6().setText("ME106");

        control.getCourseCode().setText("ME101");
        Text text = new Text(c.getDescription());
        control.getTextFlow().getChildren().clear();    
        control.getTextFlow().getChildren().add(text);
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);
    }

    @FXML
    void physButtonPressed(ActionEvent event) {
        whichButton=6;
        MainPageController.getSecondAnchorPane().getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/detailedCoursePage.fxml"));
        Course c= Finder.courseCodeFinder("PHYS101");
        detailedCoursePageController control=null;
        Node node = null;
        try {
            node = loader.load();
            control = loader.getController(); 
        } catch (IOException e){
            e.printStackTrace();
        }
        control.getCourseName().setText("Physics");
        control.getLecture1().setText("PHYS101");
        control.getLecture2().setText("PHYS102");
        control.getLecture3().setText("PHYS103");
        control.getLecture4().setText("PHYS104");
        control.getLecture5().setText("PHYS105");
        control.getLecture6().setText("PHYS106");

        control.getCourseCode().setText("PHYS101");
        Text text = new Text(c.getDescription());
        control.getTextFlow().getChildren().clear();    
        control.getTextFlow().getChildren().add(text);
        MainPageController.getSecondAnchorPane().getChildren().setAll(node);

    }

    @FXML
    void initialize() {
        assert csButton != null : "fx:id=\"csButton\" was not injected: check your FXML file 'coursesPage.fxml'.";
        assert eeButton != null : "fx:id=\"eeButton\" was not injected: check your FXML file 'coursesPage.fxml'.";
        assert ieButton != null : "fx:id=\"ieButton\" was not injected: check your FXML file 'coursesPage.fxml'.";
        assert mathButton != null : "fx:id=\"mathButton\" was not injected: check your FXML file 'coursesPage.fxml'.";
        assert meButton != null : "fx:id=\"meButton\" was not injected: check your FXML file 'coursesPage.fxml'.";
        assert physButton != null : "fx:id=\"physButton\" was not injected: check your FXML file 'coursesPage.fxml'.";

    }

}

