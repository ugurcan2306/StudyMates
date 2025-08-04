package com.example.classes;

import java.util.Objects;

public class StudyRequest {
    private String senderName;
    private String receiverName;
    private String courseCode;

    // Constructors
    public StudyRequest(String sendername, String receivername,String courseCode) {
        this.senderName = sendername;
        this.receiverName = receivername;
        this.courseCode=courseCode;

    }

    public StudyRequest() {
    }
    

  public String getReceiverName() {
      return receiverName;
  }
  public String getSenderName() {
      return senderName;
  }
  public void setReceiverName(String receivername) {
      this.receiverName = receivername;
  }
  public void setSenderName(String sendername) {
      this.senderName = sendername;
  }


    // Override Methods
    @Override
    public String toString() {
        return "Sender: " + senderName + " Receiver: " + receiverName;
    }


   @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    StudyRequest sr = (StudyRequest) obj;

    return Objects.equals(senderName, sr.senderName)
        && Objects.equals(receiverName, sr.receiverName)
        && Objects.equals(courseCode, sr.courseCode);
}
    @Override
    public int hashCode() {
    return Objects.hash(senderName, receiverName, courseCode);
    }


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}

