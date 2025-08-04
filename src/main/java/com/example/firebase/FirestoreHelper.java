package com.example.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FirestoreHelper {
    private static Firestore db;

    public static Firestore getFirestore()  {
        if (db == null) {
            FileInputStream serviceAccount = null;
            try {
                serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            FirebaseOptions options = null;
            try {
                options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setProjectId("studymates-16f31") 
                        .build();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        }
        return db;
    }
}
