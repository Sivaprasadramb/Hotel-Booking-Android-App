package com.example.surface.ramhotelapp;

public class InvoiceHandler {
    //member variables
    private String service;
    public double charges;
    public String date;
    public String Order_Id;

    //getter and setter methods for member variables
    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOrderId() { return Order_Id;}

    public void setOrderId(String orderid) {
        this.Order_Id=orderid;
    }


}
