package com.unitarcafe.hegaa.unitarcafe.Model;

public class Items {
    private String name;
    private String description;
    private double price;
    private int discount;
    private String image;

    public Items() {

    }

    public Items(String name, String description, double price, int discount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        image = "gs://unitarcafe.appspot.com/itemImages/"+name.replace(" ", "_").toLowerCase()+".jpg";
    }
    public Items(String name, String description, double price, int discount, String img) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        image = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
