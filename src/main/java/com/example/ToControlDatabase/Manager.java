package com.example.ToControlDatabase;


import com.example.classes.User;
import com.example.firebase.FirestoreHelper;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

public class Manager {

    // Static field to hold the current Firebase User
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String username) {
        try {
            Firestore db = FirestoreHelper.getFirestore();
            ApiFuture<QuerySnapshot> future = db.collection("users").whereEqualTo("username", username).get();
            QuerySnapshot querySnapshot = future.get(); 
            if (querySnapshot != null && !querySnapshot.isEmpty()) {
               
                for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                    String storedUsername = document.getString("username");
                    if (storedUsername.equals(username)) {
                        currentUser = document.toObject(User.class);
                        break;
                    }
                }
                
            } else {
                System.out.println("User not found in Firestore.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
