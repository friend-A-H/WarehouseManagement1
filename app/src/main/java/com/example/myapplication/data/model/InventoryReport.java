package com.example.myapplication.data.model;

import java.util.List;

public interface InventoryReport {
    public void addRecord(String goodShelfID, String goodGridID, String clothID, int difference);
    public List<InventoryRecord> getInventoryRecords();
}
