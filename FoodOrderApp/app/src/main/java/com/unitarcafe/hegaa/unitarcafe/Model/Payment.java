package com.unitarcafe.hegaa.unitarcafe.Model;

public class Payment {
    private String custEmail, custName, orderId, datetime, total;
    private boolean status;

    public Payment() {

    }

    public Payment(String custEmail,String custName,String orderId,String datetime,String total, boolean status) {
        this.custEmail = custEmail;
        this.custName = custName;
        this.orderId = orderId;
        this.datetime = datetime;
        this.status = status;
        this.total = total;
    }

    public Payment(String custEmail,String custName,String orderId,String datetime,String total) {
        this.custEmail = custEmail;
        this.custName = custName;
        this.orderId = orderId;
        this.datetime = datetime;
        this.status = true;
        this.total = total;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public String getOrderID() {
        return orderId;
    }

    public void setOrderID(String orderId) {
        this.orderId = orderId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
