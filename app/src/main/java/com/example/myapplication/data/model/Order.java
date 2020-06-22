package com.example.myapplication.data.model;

import java.util.List;

public interface Order {
    public String getPickState();
    public List<OrderItem> getOrderItems();
    public String getId();
    public void setId(String id);
}
