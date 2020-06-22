package com.example.myapplication.data.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String orderClothID;
    private int orderClothNum;

    public OrderItem(String orderClothID, int orderClothNum){
        this.orderClothID = orderClothID;
        this.orderClothNum = orderClothNum;
    }

    public String getOrderClothID() {
        return orderClothID;
    }

    public void setOrderClothID(String orderClothID) {
        this.orderClothID = orderClothID;
    }

    public int getOrderClothNum() {
        return orderClothNum;
    }

    public void setOrderClothNum(int orderClothNum) {
        this.orderClothNum = orderClothNum;
    }
}
