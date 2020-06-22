package com.example.myapplication.data.model;

public class PickOrderAdapter extends  PickOrderItem{
    private OrderItem orderItem;

    public PickOrderAdapter(OrderItem orderItem){
        this.orderItem = orderItem;
    }

    public String getOrderIncompleteClothID(){
        String orderClothID = orderItem.getOrderClothID();
        String orderIncompleteClothID = orderClothID.substring(0, orderClothID.length()-4);
        return orderIncompleteClothID;
    }
}
