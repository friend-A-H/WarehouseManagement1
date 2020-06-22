package com.example.myapplication.data.model;

import java.io.Serializable;
import java.util.List;

public class ReturnOrder implements Serializable, Order {
    private String id;
    private List<OrderItem> orderItems;

    public ReturnOrder(String id, List<OrderItem> orderItems){
        this.id = id;
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPickState(){
        return "returned";
    }
}