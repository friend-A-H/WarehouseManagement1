package com.example.myapplication.data.model;

public class ProfitReportFactory implements InventoryReportFactory{
    public InventoryReport produceInventoryReport(){
        return new ProfitReport();
    }
}
