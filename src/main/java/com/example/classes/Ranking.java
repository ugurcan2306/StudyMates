package com.example.classes;

public class Ranking {
    String senderName;
    String receiverName;
    double rank;

    public Ranking(String senderName2, String receiverName2, double rank) {
        senderName= senderName2;
        receiverName= receiverName2;
        this.rank= rank;
    }
    Ranking(){
        
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

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }
    
    
}
