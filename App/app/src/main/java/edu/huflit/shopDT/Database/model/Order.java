package edu.huflit.shopDT.Database.model;

import java.io.Serializable;

public class Order implements Serializable {
    String orderID, orderItemQuantity, orderPrice, orderCreateDate, orderAddress, orderPayment, orderStatus;

    public Order(){}
    public Order(String orderID, String orderItemQuantity, String orderPrice, String orderCreateDate, String orderAddress, String orderPayment, String orderStatus) {
        this.orderID = orderID;
        this.orderItemQuantity = orderItemQuantity;
        this.orderPrice = orderPrice;
        this.orderCreateDate = orderCreateDate;
        this.orderAddress = orderAddress;
        this.orderPayment = orderPayment;
        this.orderStatus = orderStatus;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public void setOrderItemQuantity(String orderItemQuantity) {
        this.orderItemQuantity = orderItemQuantity;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(String orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
