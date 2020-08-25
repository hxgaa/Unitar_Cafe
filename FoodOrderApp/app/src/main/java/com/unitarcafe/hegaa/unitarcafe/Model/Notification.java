package com.unitarcafe.hegaa.unitarcafe.Model;

public class Notification {
    private String id, message, status, title;

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

    public Notification(String id, String title, String status, String message) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.message = message;
    }

    public String getTitle() {return  title;}

    public void setTitle(String title) {this.title = title;}

    public String getNotiID() {return  id;}

    public void setNotiID(String id) {this.id = id;}

    public String getStatus() {return  status;}

    public void setStatus(String status) {this.status = status;}

    public String getMessage() {return  message;}

    public void setMessage(String message) {this.message = message;}

}
