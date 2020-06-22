package com.example.myapplication.data.model;

import java.io.Serializable;

public class InventoryRecord implements Serializable {
    private String goodShelfID;

    public String getGoodShelfID() {
        return goodShelfID;
    }

    public String getGoodGridID() {
        return goodGridID;
    }

    public String getClothID() {
        return clothID;
    }

    public int getDifference() {
        return difference;
    }

    private String goodGridID;
    private String clothID;
    private int difference;

    public InventoryRecord(String goodShelfID, String goodGridID, String clothID, int difference){
        this.goodShelfID = goodShelfID;
        this.goodGridID = goodGridID;
        this.clothID = clothID;
        this.difference = difference;
    }
}
