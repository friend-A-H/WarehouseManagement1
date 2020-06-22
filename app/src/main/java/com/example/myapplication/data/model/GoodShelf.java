package com.example.myapplication.data.model;

import java.io.Serializable;
import java.util.List;

public class GoodShelf implements Serializable {

    private char shelfID;
    private List<GoodGrid> goodGrids;

    public GoodShelf(char shelfID, List<GoodGrid> goodGrids){
        this.shelfID = shelfID;
        this.goodGrids = goodGrids;
    }

    public char getShelfID() {
        return shelfID;
    }

    public void setShelfID(char shelfID) {
        this.shelfID = shelfID;
    }

    public List<GoodGrid> getGoodGrids() {
        return goodGrids;
    }

    public void setGoodGrids(List<GoodGrid> goodGrids) {
        this.goodGrids = goodGrids;
    }

    public int searchClothNumByID(String clothID){
        int searchClothNum = -1;
        for(int i = 0; i < goodGrids.size(); i++){
            if(goodGrids.get(i).getClothID() != null && goodGrids.get(i).getClothID().equals(clothID)){
                searchClothNum = goodGrids.get(i).getClothNumber();
                break;
            }
        }
        return searchClothNum;
    }

    public int searchClothPositionByID(String clothID){
        int searchClothGridPos = -1;
        for(int i = 0; i < goodGrids.size(); i++){
            if(goodGrids.get(i).getClothID() != null && goodGrids.get(i).getClothID().equals(clothID)){
                searchClothGridPos = i;
                break;
            }
        }
        return searchClothGridPos;
    }
}
