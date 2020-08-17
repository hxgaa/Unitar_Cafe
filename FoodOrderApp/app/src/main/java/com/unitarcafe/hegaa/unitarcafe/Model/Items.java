package com.unitarcafe.hegaa.unitarcafe.Model;

public class Items {
    private String name;
    private String description;
    private String price;
    private String discount;
    private String image;

    public Items() {

    }

    public Items(String name, String description, String price, String discount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        image = "gs://unitarcafe.appspot.com/itemImages/"+name.replace(" ", "_").toLowerCase()+".jpg";
    }
    public Items(String name, String description, String price, String discount, String img) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
