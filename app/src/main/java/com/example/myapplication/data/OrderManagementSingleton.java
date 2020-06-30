package com.example.myapplication.data;

import com.example.myapplication.data.model.Order;
import com.example.myapplication.data.model.OrderFactory;
import com.example.myapplication.data.model.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderManagementSingleton {
    private static OrderManagementSingleton orderManagement = null;

    private List<Order> unpickedOrders = new ArrayList<Order>();
    private List<Order> shippedOrders = new ArrayList<Order>();
    private List<Order> returnedOrders = new ArrayList<Order>();

    private Random random = new Random();

    private OrderManagementSingleton(){
        randomInitUnpickedOrders();
    }

    public static OrderManagementSingleton getInstance(){
        if(orderManagement == null){
            orderManagement = new OrderManagementSingleton();
        }
        return orderManagement;
    }

    private void randomInitUnpickedOrders(){
        WarehouseSingleton warehouse = WarehouseSingleton.getInstance();
        List<String> allClothID = warehouse.getAllClothID();
        List<OrderItem> orderItems;
        int index;
        for(int i = 0; i < 3; i++){
            orderItems = new ArrayList<OrderItem>();
            for(int j = 0; j < random.nextInt(5)+1; j++){
                index = random.nextInt(allClothID.size());
                if(warehouse.searchClothNumByID(allClothID.get(index)) != 0){
                    if(warehouse.searchClothNumByID(allClothID.get(index)) == 1) {
                        OrderItem orderItem = new OrderItem(allClothID.get(index), 1);
                        orderItems.add(orderItem);
                    }
                    else{
                        OrderItem orderItem = new OrderItem(allClothID.get(index), random.nextInt(warehouse.searchClothNumByID(allClothID.get(index)) - 1)+1);
                        orderItems.add(orderItem);
                    }
                }
                else j--;
                allClothID.remove(index);
            }
            Order unpickOrder = OrderFactory.produceOrder(String.valueOf(i), orderItems, "unpicked");
            unpickedOrders.add(unpickOrder);
        }

        OrderItem orderItem = new OrderItem(null, 0);
        Boolean initOrderItem = false;
        for(int i = 0; i < unpickedOrders.get(unpickedOrders.size() - 1).getOrderItems().size(); i++){                //生成带有与最后一个随机订单相同服装ID的订单项
            String clothID = unpickedOrders.get(unpickedOrders.size() - 1).getOrderItems().get(i).getOrderClothID();
            if(warehouse.searchClothNumByID(clothID) != 1){
                orderItem = new OrderItem(clothID, 1);
                initOrderItem = true;
                break;
            }
        }
        if(initOrderItem == false){
            for(int i = 0; i < unpickedOrders.get(unpickedOrders.size() - 2).getOrderItems().size(); i++){                //生成带有与倒数第二个个随机订单相同服装ID的订单项
                String clothID = unpickedOrders.get(unpickedOrders.size() - 2).getOrderItems().get(i).getOrderClothID();
                if(warehouse.searchClothNumByID(clothID) != 1){
                    orderItem = new OrderItem(clothID, 1);
                    break;
                }
            }
        }
        orderItems = new ArrayList<OrderItem>();
        orderItems.add(orderItem);
        orderItems.add(new OrderItem(allClothID.get(0), 1));
        Order unpickOrder = OrderFactory.produceOrder("3", orderItems, "unpicked");
        unpickedOrders.add(unpickOrder);
    }

    public List<Order> getUnpickedOrders() {
        return unpickedOrders;
    }

    public List<Order> getShippedOrders() {
        return shippedOrders;
    }

    public List<Order> getReturnedOrders() {
        return returnedOrders;
    }
}
