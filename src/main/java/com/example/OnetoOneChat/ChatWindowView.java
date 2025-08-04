package com.example.OnetoOneChat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ChatWindowView {

    private final VBox rootLayout;
    private final FirestoreService firestoreService;

    public ChatWindowView(String chatId, String username) {
        this.firestoreService = new FirestoreService();

        String[] strArr = chatId.split("_");
        String chatWith = null;
        for (String s : strArr) {
            if (!s.equals(username)) {
                chatWith = s;
                break;
            }
        }

        Label chatHeader = new Label("💬 Chatting: " + chatWith);
        chatHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10;");

        VBox messageContainer = new VBox(5);
        messageContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(messageContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        TextField inputField = new TextField();
        Button sendButton = new Button("➤");
        sendButton.setStyle("-fx-font-size: 18px; -fx-text-fill: green;");

        HBox inputLayout = new HBox(10, inputField, sendButton);
        inputLayout.setPadding(new Insets(10));
        inputLayout.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(inputField, Priority.ALWAYS);
        inputField.setMaxWidth(Double.MAX_VALUE);

        rootLayout = new VBox(10, chatHeader, scrollPane, inputLayout);
        rootLayout.setPadding(new Insets(10));

        firestoreService.streamMessagesLive(chatId, messageContainer, username);

        sendButton.setOnAction(e -> {
            String message = inputField.getText().trim();
            if (!message.isEmpty()) {
                firestoreService.sendMessageToChat(chatId, username, message);
                inputField.clear();
            }
        });

        inputField.setOnAction(sendButton.getOnAction());
    }

    public VBox getView() {
        return rootLayout;
    }
}
