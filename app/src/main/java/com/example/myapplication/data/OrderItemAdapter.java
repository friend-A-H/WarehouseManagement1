package com.example.myapplication.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.data.model.OrderItem;

import java.util.List;

public class OrderItemAdapter extends ArrayAdapter<OrderItem> {
    private int resourceId;

    public OrderItemAdapter(Context context, int resource, List<OrderItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderItem orderItem = getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ((TextView)view.findViewById(R.id.orderProductID)).setText(orderItem.getOrderClothID());
        ((TextView)view.findViewById(R.id.orderProNum)).setText(String.valueOf(orderItem.getOrderClothNum()));
        return view;
    }
}
