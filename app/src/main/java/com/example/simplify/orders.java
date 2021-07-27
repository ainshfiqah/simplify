package com.example.simplify;

public class orders {
    private String name;
    private String phone;
    private String address;
    private String orderDate;
    private String shipmentDate;
    private String trackingNum;
    private String status;

    public orders(String name, String phone, String address, String orderDate, String shipmentDate, String trackingNum) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.shipmentDate = shipmentDate;
        this.trackingNum = trackingNum;
        this.status = status;
    }



    public orders(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
