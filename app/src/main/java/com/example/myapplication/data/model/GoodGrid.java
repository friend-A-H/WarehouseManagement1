package com.example.myapplication.data.model;

import java.io.Serializable;

public class GoodGrid implements Serializable {

    private String clothID;
    private int clothNumber;

    public GoodGrid(String clothID, int clothNumber){
        this.clothID = clothID;
        this.clothNumber = clothNumber;
    }

    public String getClothID() {
        return clothID;
    }

    public void setClothID(String clothID) {
        this.clothID = clothID;
    }

    public int getClothNumber() {
        return clothNumber;
    }

    public void setClothNumber(int clothNumber) {
        this.clothNumber = clothNumber;
    }
}
