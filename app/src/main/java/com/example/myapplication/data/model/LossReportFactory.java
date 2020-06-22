package com.example.myapplication.data.model;

public class LossReportFactory implements InventoryReportFactory{
    public InventoryReport produceInventoryReport(){
        return new LossReport();
    }
}
