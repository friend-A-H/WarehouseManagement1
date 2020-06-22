package com.example.myapplication.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.data.model.InventoryRecord;

import java.util.List;

public class ReportItemAdapter extends ArrayAdapter<InventoryRecord> {
    private int resourceId;

    public ReportItemAdapter(Context context, int resource, List<InventoryRecord> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InventoryRecord inventoryRecord = getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ((TextView)view.findViewById(R.id.reportShelfTextView)).setText(inventoryRecord.getGoodShelfID());
        ((TextView)view.findViewById(R.id.reportGridTextView)).setText(inventoryRecord.getGoodGridID());
        ((TextView)view.findViewById(R.id.reportClothIDTextView)).setText(inventoryRecord.getClothID());
        ((TextView)view.findViewById(R.id.reportClothNumTextView)).setText(String.valueOf(inventoryRecord.getDifference()));
        return view;
    }
}
