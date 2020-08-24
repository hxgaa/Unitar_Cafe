package com.unitarcafe.hegaa.unitarcafe.Model;

public class Notification {
    private String message, status, title;

    public Notification() {

    }

    public Notification(String title, String status, String message) {
        this.title = title;
        this.status = status;
        this.message = message;
    }

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
        this.status = "NEW";
    }

    public String getTitle() {return  title;}

    public void setTitle(String title) {this.title = title;}

    public String getStatus() {return  status;}

    public void setStatus(String title) {this.status = status;}

    public String getMessage() {return  message;}

    public void setMessage(String message) {this.message = message;}

}
