package com.example.ToControlDatabase;

import com.example.classes.*;
import com.example.firebase.FirestoreHelper;
import com.google.cloud.firestore.*;


public class Finder {

    private static final Firestore db = FirestoreHelper.getFirestore();

    public static User UserFinder(String username) {
        try {
            QuerySnapshot snapshot = db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .get();

            if (!snapshot.isEmpty()) {
                return snapshot.getDocuments().get(0).toObject(User.class);
            } else {
                System.out.println("User not found: " + username);
            }
        } catch (Exception e) {
            errorMessage("findUserByUsername", e);
        }
        return null;
    }

    public static Course courseCodeFinder(String courseCode) {
        try {
            QuerySnapshot snapshot = db.collection("courses")
                    .whereEqualTo("courseCode", courseCode)
                    .get()
                    .get();

            if (!snapshot.isEmpty()) {
                return snapshot.getDocuments().get(0).toObject(Course.class);
            } else {
                System.out.println("Course not found: " + courseCode);
            }
        } catch (Exception e) {
            errorMessage("findCourseByCode", e);
        }
        return null;
    }

    public static StudyRequest StudyReqFinder(String sender, String receiver, String courseCode) {
        String docId = sender + "_" + receiver + "_" + courseCode;
        try {
            DocumentSnapshot document = db.collection("studyRequests").document(docId).get().get();
            if (document.exists()) {
                return document.toObject(StudyRequest.class);
            } else {
                System.out.println("StudyRequest not found: " + docId);
            }
        } catch (Exception e) {
            errorMessage("findStudyRequest", e);
        }
        return null;
    }

    public static Ranking rankingFinder(String sender, String receiver) {
        String docId = sender + "_" + receiver;
        try {
            DocumentSnapshot document = db.collection("rankings").document(docId).get().get();
            if (document.exists()) {
                return document.toObject(Ranking.class);
            } else {
                System.out.println("Ranking not found: " + docId);
            }
        } catch (Exception e) {
            errorMessage("findRanking", e);
        }
        return null;
    }

    public static Comment commentFinder(String sender, String receiver) {
        String docId = sender + "_" + receiver;
        try {
            DocumentSnapshot document = db.collection("comments").document(docId).get().get();
            if (document.exists()) {
                return document.toObject(Comment.class);
            } else {
                System.out.println("Comment not found: " + docId);
            }
        } catch (Exception e) {
            errorMessage("findComment", e);
        }
        return null;
    }

    private static void errorMessage(String method, Exception e) {
        System.err.println("Error in Finder::" + method + " -> " + e.getMessage());
        e.printStackTrace();
    }
}
