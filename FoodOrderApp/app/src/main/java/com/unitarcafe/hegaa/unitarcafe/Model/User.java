package com.unitarcafe.hegaa.unitarcafe.Model;

public class User {

    private String Name;
    private String PassHash;
    private String Phone;
    private String Email;

    public User() {

    }

    public User(String name, String password, String email, String phone) {
        Name = name;
        PassHash = password;
        Phone = phone;
        Email = email;
    }

    public User(String name, String email, String phone) {
        Name = name;
        Phone = phone;
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassHash() {
        return PassHash;
    }

    public void setPassHash(String password) {
        this.PassHash = password;
    }
}
