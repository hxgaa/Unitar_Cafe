package com.unitarcafe.hegaa.unitarcafe.Model;

import java.util.List;

public class Request {

    private User to;
    private String orderId;
    private String total;
    private boolean status;
    private List<Order> foods; //list of fod orders

    public Request() {
    }

    public Request(String id, User user, String total, List<Order> foods) {
        this.orderId =id;
        this.to = user;
        this.total = total;
        this.foods = foods;
        this.status = false;
    }
    public Request(String id, User user, String total, List<Order> foods, boolean status) {
        this.orderId =id;
        this.to = user;
        this.total = total;
        this.foods = foods;
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return to;
    }

    public void setUser(User usr) {
        this.to = usr;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String id) {
        this.orderId = id;
    }

//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
