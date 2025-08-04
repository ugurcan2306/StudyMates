package com.example.classes;

import java.util.ArrayList;

public class Course {
    String courseName;
    String courseCode;
    String Description;

    ArrayList<User> enrolledUsers;

    Course(){

    }

    Course(String courseName,String courseCode, String courseDescription){
        this.courseName=courseName;
        this.courseCode=courseCode;
        this.Description=courseDescription;
        enrolledUsers= new ArrayList<>();
        
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String courseDescription) {
        this.Description = courseDescription;
    }
    @Override
    public String toString() {
        return courseCode;
    }
    @Override
    public boolean equals(Object obj) {
        Course c= (Course) obj;
        return courseCode.equals(c.getCourseCode());
    }
    public void setEnrolledUsers(ArrayList<User> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }
}
