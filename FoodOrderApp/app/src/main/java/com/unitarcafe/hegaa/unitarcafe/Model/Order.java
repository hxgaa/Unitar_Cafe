package com.unitarcafe.hegaa.unitarcafe.Model;

public class Order {


    private String ProduName;
    private String Quantity;
    private double Price;
    private int Discount;

    public Order() {

    }

    public Order(String produName, String quantity, double price, int discount) {
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

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }
}
