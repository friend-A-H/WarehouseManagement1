package com.example.myapplication.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfitReport implements InventoryReport, Serializable {
    private List<InventoryRecord> inventoryRecords;

    public ProfitReport(){
        inventoryRecords = new ArrayList<InventoryRecord>();
    }

    public void addRecord(String goodShelfID, String goodGridID, String clothID, int difference){
        InventoryRecord inventoryRecord = new InventoryRecord(goodShelfID, goodGridID, clothID, difference);
        inventoryRecords.add(inventoryRecord);
    };

    public List<InventoryRecord> getInventoryRecords() {
        return inventoryRecords;
    }
}
