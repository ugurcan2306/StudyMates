package com.example.firebase;
import com.example.classes.*;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;



public class FirestoreUtils {

    public static void addUserToFirestore(Firestore db, String mainname, String surname, String username, String password, String email, String City, ArrayList<Course> desiredCourses, ArrayList<String> Friends,ArrayList<StudyRequest> receivedTradeRequests,ArrayList<StudyRequest> sentTradeRequests) {
        Map<String, Object> user = new HashMap<>();
        user.put("mainname", mainname);
        user.put("surname", surname);
        user.put("username", username);
        user.put("password", password);
        user.put("email", email);
        user.put("city", City);
        user.put("friends", Friends);
        user.put("desiredCourses", desiredCourses);
        user.put("receivedStudyRequests", receivedTradeRequests);
        user.put("sentStudyRequests", sentTradeRequests);
        user.put("rankings", 0.0);
        user.put("url","C:/Users/ugurd/OneDrive/Masaüstü/StudyMates/src/main/resources/pictures/defaultP.png");
        user.put("aboutMe","");

        try {
            ApiFuture<WriteResult> result1 = db.collection("users").document(username).set(user);
            System.out.println("Update time: " + result1.get().getUpdateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCoursestoTheFirestore(Firestore db, String courseName, String courseCode, String description) {
    Map<String, Object> courseData = new HashMap<>();
    courseData.put("courseName", courseName);
    courseData.put("courseCode", courseCode);
    courseData.put("Description", description);
    courseData.put("enrolledUsers",new ArrayList<>());

    try {
        ApiFuture<WriteResult> result = db
            .collection("courses")             
            .document(courseCode)                             
            .set(courseData);
        System.out.println("Update time: " + result.get().getUpdateTime());
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static void addStudyRequesttoTheFirestore(Firestore db, String sender, String receiver, String code){
        Map<String, Object> studyRequest= new HashMap<>();
        studyRequest.put("senderName",sender);
        studyRequest.put("receiverName",receiver);
        studyRequest.put("courseCode", code);
         try {
        ApiFuture<WriteResult> result = db
            .collection("studyRequests")             
            .document(sender+"_"+receiver+"_"+code)                             
            .set(studyRequest);
        System.out.println("Update time: " + result.get().getUpdateTime());
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    public static void addRankingToTheFirestore(Firestore db, String sender, String receiver, double ranking){
        Map<String, Object> rankingMap= new HashMap<>();
        rankingMap.put("senderName", sender);
        rankingMap.put("receiverName", receiver);
        rankingMap.put("rank", ranking);
         try {
        ApiFuture<WriteResult> result = db
            .collection("rankings")             
            .document(sender+"_"+receiver)                             
            .set(rankingMap);
        System.out.println("Update time: " + result.get().getUpdateTime());
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    public static void addCommentsToTheFirestore(Firestore db, String sender, String receiver, String commentS){
        Map<String, Object> commentMap= new HashMap<>();
        commentMap.put("senderName", sender);
        commentMap.put("receiverName", receiver);
        commentMap.put("commentS", commentS);
         try {
        ApiFuture<WriteResult> result = db
            .collection("comments")             
            .document(sender+"_"+receiver)                             
            .set(commentMap);
        System.out.println("Update time: " + result.get().getUpdateTime());
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
}