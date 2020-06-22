package com.example.myapplication.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LossReport implements InventoryReport, Serializable {
    private List<InventoryRecord> inventoryRecords;

    public LossReport(){
        inventoryRecords = new ArrayList<InventoryRecord>();
    }

    public void addRecord(String goodShelfID, String goodGridID, String clothID, int difference){
        InventoryRecord inventoryRecord = new InventoryRecord(goodShelfID, goodGridID, clothID, difference * -1);
        inventoryRecords.add(inventoryRecord);
    };

    public List<InventoryRecord> getInventoryRecords() {
        return inventoryRecords;
    }
}
