package com.example.OnetoOneChat;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;

import com.example.firebase.FirestoreHelper;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FirestoreService {

    private Firestore firestore;

    public FirestoreService() {
        firestore = FirestoreHelper.getFirestore();
    }

    public void initializeChatBetweenUsers(String userOne, String userTwo, ChatIdCallback callback) {
        List<String> orderedUsers = Arrays.asList(userOne, userTwo);
        orderedUsers.sort(String.CASE_INSENSITIVE_ORDER);
        String generatedChatId = orderedUsers.get(0) + "_" + orderedUsers.get(1);

        firestore.collection("chats").document(generatedChatId).get().addListener(() -> {
            try {
                DocumentSnapshot snapshot = firestore.collection("chats").document(generatedChatId).get().get();
                if (snapshot.exists()) {
                    callback.onChatCreated(generatedChatId);
                } else {
                    Map<String, Object> chatInfo = new HashMap<>();
                    chatInfo.put("users", Arrays.asList(userOne, userTwo));
                    chatInfo.put("createdAt", FieldValue.serverTimestamp());

                    firestore.collection("chats").document(generatedChatId).set(chatInfo)
                            .addListener(() -> callback.onChatCreated(generatedChatId), Executors.newSingleThreadExecutor());
                }
            } catch (InterruptedException | ExecutionException ignored) {}
        }, Executors.newSingleThreadExecutor());
    }

    public void registerUserInChat(String chatId, String user) {
        ApiFuture<DocumentSnapshot> future = firestore.collection("chats").document(chatId).get();
        try {
            DocumentSnapshot doc = future.get();
            if (doc.exists()) {
                List<String> currentUsers = (List<String>) doc.get("users");
                if (currentUsers != null && !currentUsers.contains(user)) {
                    currentUsers.add(user);
                    ApiFuture<WriteResult> update = firestore.collection("chats")
                                                              .document(chatId)
                                                              .update("users", currentUsers);
                    update.get();
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void streamMessagesLive(String chatId, VBox uiContainer, String activeUser) {
        firestore.collection("chats").document(chatId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshot, err) -> {
                    if (err != null) {
                        err.printStackTrace();
                        return;
                    }

                    Platform.runLater(() -> {
                        uiContainer.getChildren().clear();
                        for (DocumentSnapshot msg : snapshot.getDocuments()) {
                            String sender = msg.getString("sender");
                            String content = msg.getString("message");

                            Label textLabel = new Label(content);
                            textLabel.setWrapText(true);
                            textLabel.setMaxWidth(300);

                            HBox msgLayout = new HBox(textLabel);
                            msgLayout.setPadding(new Insets(5));

                            if (sender != null && sender.equals(activeUser)) {
                                msgLayout.setAlignment(Pos.CENTER_RIGHT);
                                textLabel.setStyle("-fx-background-color: #a0d2ff; -fx-padding: 8; -fx-background-radius: 10;");
                            } else {
                                msgLayout.setAlignment(Pos.CENTER_LEFT);
                                textLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 8; -fx-background-radius: 10;");
                            }

                            uiContainer.getChildren().add(msgLayout);
                        }
                    });
                });
    }

    public void sendMessageToChat(String chatId, String sender, String messageText) {
        Map<String, Object> messageEntry = new HashMap<>();
        messageEntry.put("sender", sender);
        messageEntry.put("message", messageText);
        messageEntry.put("timestamp", FieldValue.serverTimestamp());

        ApiFuture<DocumentReference> future = firestore.collection("chats")
                .document(chatId)
                .collection("messages")
                .add(messageEntry);

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void retrieveUserCount(String chatId, UserCountCallback callback) {
        ApiFuture<DocumentSnapshot> future = firestore.collection("chats").document(chatId).get();

        try {
            DocumentSnapshot doc = future.get();
            if (doc.exists()) {
                List<String> members = (List<String>) doc.get("users");
                int count = (members != null) ? members.size() : 0;
                callback.onUserCountChecked(count);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public interface ChatIdCallback {
        void onChatCreated(String chatId);
    }

    public interface UserCountCallback {
        void onUserCountChecked(int userCount);
    }
}
