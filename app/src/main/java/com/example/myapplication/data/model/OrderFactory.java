package com.example.myapplication.data.model;

import java.util.List;

public class OrderFactory {
    public static Order produceOrder(String id, List<OrderItem> orderItems, String pickState){
        if(pickState.equals("unpicked")){
            return new UnpickOrder(id, orderItems);
        }
        else if(pickState.equals("shipped")){
            return new ShipOrder(id, orderItems);
        }
        else{
            return new ReturnOrder(id, orderItems);
        }
    }
}
