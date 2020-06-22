package com.example.myapplication.data;

import com.example.myapplication.data.model.GoodGrid;
import com.example.myapplication.data.model.GoodShelf;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class WarehouseSingleton {
    private static WarehouseSingleton warehouse = null;

    private List<GoodShelf> goodShelves;

    private WarehouseSingleton(){
        goodShelves = new ArrayList<GoodShelf>();
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("WarehouseSerializable.txt"));
            goodShelves = (ArrayList<GoodShelf>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(goodShelves.size() == 0){
            initWarehouse();
        }
    }

    public static WarehouseSingleton getInstance(){
        if(warehouse == null){
            warehouse = new WarehouseSingleton();
        }
        return warehouse;
    }

    private void initWarehouse(){
        List<GoodGrid> goodGrids = new ArrayList<GoodGrid>();
        addGridToArray("4a507db7-7c52-2c22-d6a6-77ade48625bd", 8, goodGrids);
        addGridToArray("33783757-e89e-3262-f0a4-e9db956ca1c3", 6, goodGrids);
        addGridToArray("79eb1d76-a37e-a575-7445-1be57fc63842", 2, goodGrids);
        addGridToArray("6aa05e8e-456e-35a5-df0a-fe1ffc2798d4", 6, goodGrids);
        addGridToArray("d3c5f8b7-b5cf-d9ec-91f2-b1db95a82cc9", 7, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray("3a8497ca-a598-ba50-dd0c-4df42d9f0532", 2, goodGrids);
        addGridToArray("fc25a168-bb33-f997-6572-8655f829361c", 5, goodGrids);
        addGridToArray("2f43da55-beb5-fe84-dbdd-0d8d04040b51", 6, goodGrids);
        addGridToArray("eefdc6d2-8dd3-11cb-d7b6-49731edc0e1d", 8, goodGrids);
        GoodShelf goodShelf = new GoodShelf('A', goodGrids);
        goodShelves.add(goodShelf);

        goodGrids = new ArrayList<GoodGrid>();
        addGridToArray("8422024f-cfbc-9309-d884-07db3221fbec", 12, goodGrids);
        addGridToArray("30eef49c-ce14-3305-d813-5dbde54560d1", 5, goodGrids);
        addGridToArray("1e177873-f647-af1c-487e-c126d697e0c6", 12, goodGrids);
        addGridToArray("db332b44-85f3-c428-c131-6c7f69596214", 22, goodGrids);
        addGridToArray("e4ee88ce-fd23-43a7-fccb-fca0bd597f38", 3, goodGrids);
        addGridToArray("202fcfea-e5e7-6051-81cb-77dcbc265e50", 11, goodGrids);
        addGridToArray("9be101d5-e5b2-d805-2eed-3afa6d0e9627", 18, goodGrids);
        addGridToArray("696c347f-c48f-a2b9-855c-6a6f997aa054", 9, goodGrids);
        addGridToArray("cf160a58-8058-b217-a272-2f60091928c9", 2, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        goodShelf = new GoodShelf('B', goodGrids);
        goodShelves.add(goodShelf);

        goodGrids = new ArrayList<GoodGrid>();
        addGridToArray("37fba7ba-20f6-efac-5152-411ef521a383", 12, goodGrids);
        addGridToArray("11c01bad-8319-def3-6409-d086cf5fba4c", 9, goodGrids);
        addGridToArray("3020117f-7d45-32d5-1260-25433e829d49", 6, goodGrids);
        addGridToArray("b233bb40-fbb1-81eb-a676-badb22d54aa9", 3, goodGrids);
        addGridToArray("3f008cd2-d81d-54c5-9d89-5de1254b3c1a", 8, goodGrids);
        addGridToArray("97a7c75e-dab0-6fb5-9b3d-28d1c272cb2d", 4, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        goodShelf = new GoodShelf('C', goodGrids);
        goodShelves.add(goodShelf);

        goodGrids = new ArrayList<GoodGrid>();
        addGridToArray("e980f88b-2116-ffd2-aaac-47a92c092ed8", 2, goodGrids);
        addGridToArray("0594b6c4-d1b1-e2c0-36d0-e2a022b43c0c", 1, goodGrids);
        addGridToArray("ce42b3b2-a96f-a812-9216-494f33d1527c", 2, goodGrids);
        addGridToArray("8393aae1-4cbd-3323-70bf-12d871390abb", 3, goodGrids);
        addGridToArray("8aa95f76-f6b9-576d-66ef-91673817c13a", 6, goodGrids);
        addGridToArray("0df1a700-2904-9b2d-a888-3535958ff3b3", 9, goodGrids);
        addGridToArray("01ad995a-9322-9668-bd47-726377baf99c", 2, goodGrids);
        addGridToArray("fb76b14f-ba23-0825-3609-283ad7fd6001", 4, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        addGridToArray(null, 0, goodGrids);
        goodShelf = new GoodShelf('D', goodGrids);
        goodShelves.add(goodShelf);
    }

    private void addGridToArray(String clothID, int clothNumber, List<GoodGrid> goodGrids){
        GoodGrid goodGrid = new GoodGrid(clothID, clothNumber);
        goodGrids.add(goodGrid);
    }

    public List<GoodShelf> getGoodShelves() {
        return goodShelves;
    }

    public List<String> getAllClothID(){
        List<String> allClothID = new ArrayList<String>();
        for(int i = 0; i < goodShelves.size(); i++){
            for(int j = 0; j < goodShelves.get(i).getGoodGrids().size(); j++){
                if(goodShelves.get(i).getGoodGrids().get(j).getClothID() != null){
                    allClothID.add(goodShelves.get(i).getGoodGrids().get(j).getClothID());
                }
            }
        }
        return allClothID;
    }

    public int searchClothNumByID(String clothID){
        int searchClothNum = -1;
        for(int i = 0; i < goodShelves.size(); i++){
            if(goodShelves.get(i).searchClothNumByID(clothID) != -1){
                searchClothNum = goodShelves.get(i).searchClothNumByID(clothID);
                break;
            }
        }
        return searchClothNum;
    }

    public int[] searchClothPositionByID(String clothID){
        int searchClothShelfPos = -1;
        int searchClothGridPos = -1;
        for(int i = 0; i < goodShelves.size(); i++){
            if(goodShelves.get(i).searchClothPositionByID(clothID) != -1){
                searchClothShelfPos = i;
                searchClothGridPos = goodShelves.get(i).searchClothPositionByID(clothID);
                break;
            }
        }
        int[] searchClothPosition = {searchClothShelfPos, searchClothGridPos};
        return searchClothPosition;
    }
}
