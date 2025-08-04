package com.example.classes;

public class Comment {
    String senderName;
    String receiverName;
    String commentS;
    Comment(){

    }

    public Comment(String senderName2, String receiverName2, String c) {
        senderName= senderName2;
        receiverName= receiverName2;
        this.commentS=c;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getCommentS() {
        return commentS;
    }

    public void setCommentS(String commentS) {
        this.commentS = commentS;
    }

    @Override
    public boolean equals(Object obj) {
        Comment c= (Comment)obj;
        return this.receiverName.equals(c.receiverName)&& this.senderName.equals(c.senderName);
    }
}
