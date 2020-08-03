package com.unitarcafe.hegaa.unitarcafe.Model;

public class Order {


    private String ProduName;
    private String Quantity;
    private int Price;
    private int Discount;

    public Order() {

    }

    public Order(String produName, String quantity, int price, int discount) {
        ProduName = produName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
    }


    public String getProduName() {
        return ProduName;
    }

    public void setProduName(String produName) {
        ProduName = produName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }
}
