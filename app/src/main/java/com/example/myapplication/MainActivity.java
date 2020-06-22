package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.data.WarehouseSingleton;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {
    private LinearLayout searchLayout, orderLayout, inventoryLayout;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WarehouseSingleton warehouseSingleton = WarehouseSingleton.getInstance();
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("WarehouseSerializable.txt"));
            objectOutputStream.writeObject(warehouseSingleton.getGoodShelves());
            objectOutputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchLayout = findViewById(R.id.searchLayout);
        orderLayout = findViewById(R.id.orderLayout);
        inventoryLayout = findViewById(R.id.inventoryLayout);

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OrderActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        inventoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InventoryActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
