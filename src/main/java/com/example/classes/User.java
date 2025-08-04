package com.example.classes;

import java.util.ArrayList;

public class User {
    private String username;
    private String mainname;
    private String surname;
    private String city;
    private double rankings;
    private String email;
    private String password;
    private ArrayList<Course> desiredCourses;
    private ArrayList<StudyRequest> sentStudyRequests;
    private ArrayList<StudyRequest> receivedStudyRequests;
    private ArrayList<String> friends;
    private String url;
    private String aboutMe;
    private int howManyTimesRanked=0;

    User(){}

    public String getAboutMe() {
        return aboutMe;
    }
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public double getRankings() {
        return rankings;
    }
    public void setRankings(double ranking) {
        this.rankings = ranking;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public ArrayList<Course> getDesiredCourses() {
        return desiredCourses;
    }
    public void setDesiredCourses(ArrayList<Course> desiredCourses) {
        this.desiredCourses = desiredCourses;
    }
    public ArrayList<StudyRequest> getSentStudyRequests() {
        return sentStudyRequests;
    }
    public void setSentStudyRequests(ArrayList<StudyRequest> sentStudyRequests) {
        this.sentStudyRequests = sentStudyRequests;
    }
    public ArrayList<StudyRequest> getReceivedStudyRequests() {
        return receivedStudyRequests;
    }
    public void setReceivedStudyRequests(ArrayList<StudyRequest> receivedStudyRequests) {
        this.receivedStudyRequests = receivedStudyRequests;
    }
    public ArrayList<String> getFriends() {
        return friends;
    }
    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
    public String getUsername() {
        return username;
    }
    public String getMainname() {
        return mainname;
    }
    public String getSurname() {
        return surname;
    }
    public String getCity() {
        return city;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setMainname(String mainName) {
        this.mainname = mainName;
    }
    public void setSurname(String lastName) {
        this.surname = lastName;
    }
    public void setCity(String city) {
        this.city = city;
    }
    @Override
    public boolean equals(Object obj) {
        User u= (User)obj;
        return this.username.equals(u.username); 

    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getHowManyTimesRanked() {
        return howManyTimesRanked;
    }
    public void setHowManyTimesRanked(int howManyTimesRanked) {
        this.howManyTimesRanked = howManyTimesRanked;
    }
    
}
