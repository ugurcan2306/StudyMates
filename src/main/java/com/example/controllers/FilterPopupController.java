package com.example.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.example.ToControlDatabase.Manager;
import com.example.classes.User;
import com.example.firebase.FirestoreHelper;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

public class FilterPopupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button applyButton;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private ComboBox<String> locationComboBox;

    @FXML
    private Slider slider;

  @FXML
void pressApplyButton(ActionEvent event) throws IOException, InterruptedException, ExecutionException {
    String course = courseComboBox.getValue();
    String location = locationComboBox.getValue();
    double rank= slider.getValue();

    boolean desireCourse = false;
    boolean correctLocation = false;
    boolean correctRank= false;

    // search.fxml yükleniyor
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/search.fxml"));
    Node node = loader.load(); // ÖNCE load
    searchController sController = loader.getController(); // SONRA controller 

   
    Firestore db = FirestoreHelper.getFirestore();
    QuerySnapshot userCollection = db.collection("users").get().get();

    for (int i = 0; i < userCollection.size(); i++) {
        User user = userCollection.getDocuments().get(i).toObject(User.class);
        desireCourse = false;
        correctLocation = false;
        correctRank= false;
        boolean samePerson= false;

        if(user.equals(Manager.getCurrentUser())){
            samePerson=true;
        }

        if (user.getDesiredCourses() != null) {
            for (int j = 0; j < user.getDesiredCourses().size(); j++) {
                if (user.getDesiredCourses().get(j) != null &&
                    user.getDesiredCourses().get(j).getCourseName() != null &&
                    user.getDesiredCourses().get(j).getCourseName().equalsIgnoreCase(course)) {
                    desireCourse = true;
                }
            }
        }
        if (user.getCity() != null && user.getCity().equalsIgnoreCase(location)) {
            correctLocation = true;
        }
        if(user.getRankings()>=rank){
            correctRank=true;
        }


        if (desireCourse && correctLocation&& correctRank&&!samePerson) {
            sController.showUsers(user.getUsername());
        }
    }

    MainPageController.getMainContent().getChildren().setAll(node); 
}

    

    @FXML
    void initialize() {
        assert applyButton != null : "fx:id=\"applyButton\" was not injected: check your FXML file 'filter.fxml'.";
        assert courseComboBox != null : "fx:id=\"courseComboBox\" was not injected: check your FXML file 'filter.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'filter.fxml'.";
        assert slider != null : "fx:id=\"slider\" was not injected: check your FXML file 'filter.fxml'.";
        List<String> courses = Arrays.asList("CS", "EE", "Math", "IE", "ME", "Physics");
        courseComboBox.getItems().addAll(courses);

        // Şehirleri yükle
        List<String> cities = Arrays.asList(
            "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir",
            "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli",
            "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkâri",
            "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir",
            "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir",
            "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat",
            "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman",
            "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye",
            "Düzce"
        );
        locationComboBox.getItems().addAll(cities);
    }

}
