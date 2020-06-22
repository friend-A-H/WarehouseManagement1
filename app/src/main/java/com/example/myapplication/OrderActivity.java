package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.model.Order;
import com.example.myapplication.data.model.OrderFactory;
import com.example.myapplication.data.model.UnpickOrder;
import com.example.myapplication.data.OrderItemAdapter;
import com.example.myapplication.data.OrderManagementSingleton;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SINGLE_PICK = 901;
    public static final int REQUEST_CODE_SMART_PICK = 902;
    TextView orderIDTextView;
    Button previousOrderButton, nextOrderButton, singleOrderPickButton, smartOrderPickButton;
    ListView orderItemListView;
    OrderManagementSingleton orderManagement = OrderManagementSingleton.getInstance();
    List<Order> unpickedOrders = orderManagement.getUnpickedOrders();
    OrderItemAdapter orderItemAdapter;
    Order showUnpickOrder;
    int nowOrderIndex = 0;
    int smartPickOrderIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        previousOrderButton = findViewById(R.id.previousOrderButton);
        nextOrderButton = findViewById(R.id.nextOrderButton);
        orderIDTextView = findViewById(R.id.orderIDTextView);
        orderItemListView = findViewById(R.id.orderListView);
        singleOrderPickButton = findViewById(R.id.singleOrderPickButton);
        smartOrderPickButton = findViewById(R.id.smartOrderPickButton);

        showNowOrder();

        previousOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowOrderIndex != 0){
                    nowOrderIndex--;
                    showNowOrder();
                }
                else{
                    Toast ts = Toast.makeText(getBaseContext(),"已经是第一个订单了",Toast.LENGTH_SHORT);
                    ts.show();
                }
            }
        });

        nextOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowOrderIndex != unpickedOrders.size() - 1){
                    nowOrderIndex++;
                    showNowOrder();
                }
                else{
                    Toast ts = Toast.makeText(getBaseContext(),"已经是最后一个订单了",Toast.LENGTH_SHORT);
                    ts.show();
                }
            }
        });

        singleOrderPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this,PickingActivity.class);
                intent.putExtra("mode","single");
                intent.putExtra("UnpickOrder", (UnpickOrder)unpickedOrders.get(nowOrderIndex));
                startActivityForResult(intent, REQUEST_CODE_SINGLE_PICK);
            }
        });

        smartOrderPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, j, k;
                loop:
                for(i = 0; i < unpickedOrders.size(); i++){
                    if(i == nowOrderIndex){
                        continue;
                    }
                    else{
                        for(j = 0; j < unpickedOrders.get(nowOrderIndex).getOrderItems().size(); j++){
                            for(k = 0; k < unpickedOrders.get(i).getOrderItems().size(); k++){
                                if(unpickedOrders.get(nowOrderIndex).getOrderItems().get(j).getOrderClothID().equals(unpickedOrders.get(i).getOrderItems().get(k).getOrderClothID())){
                                    smartPickOrderIndex = i;
                                    break loop;
                                }
                            }
                        }
                    }
                }
                if(smartPickOrderIndex == -1){
                    Intent intent = new Intent(OrderActivity.this,PickingActivity.class);
                    intent.putExtra("mode","single");
                    intent.putExtra("UnpickOrder", (UnpickOrder)unpickedOrders.get(nowOrderIndex));
                    startActivityForResult(intent, REQUEST_CODE_SINGLE_PICK);
                }
                else{
                    Intent intent = new Intent(OrderActivity.this,PickingActivity.class);
                    intent.putExtra("mode","smart");
                    intent.putExtra("UnpickOrder", (UnpickOrder)unpickedOrders.get(nowOrderIndex));
                    intent.putExtra("smartUnpickOrder", (UnpickOrder)unpickedOrders.get(smartPickOrderIndex));
                    startActivityForResult(intent, REQUEST_CODE_SMART_PICK);
                }
            }
        });
    }

    private void showNowOrder(){
        showUnpickOrder = unpickedOrders.get(nowOrderIndex);
        orderIDTextView.setText(showUnpickOrder.getId());
        orderItemAdapter = new OrderItemAdapter(OrderActivity.this, R.layout.list_view_item_order, showUnpickOrder.getOrderItems());
        orderItemListView.setAdapter(orderItemAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SINGLE_PICK:
                if(resultCode == RESULT_OK){
                    showUnpickOrder = unpickedOrders.get(nowOrderIndex);
                    Order shipOrder = OrderFactory.produceOrder(showUnpickOrder.getId(), showUnpickOrder.getOrderItems(), "shipped");
                    orderManagement.getShippedOrders().add(shipOrder);
                    orderManagement.getUnpickedOrders().remove(nowOrderIndex);
                    nowOrderIndex = 0;
                    showNowOrder();
                }
                break;

            case REQUEST_CODE_SMART_PICK:
                if(resultCode == RESULT_OK){
                    showUnpickOrder = unpickedOrders.get(nowOrderIndex);
                    Order smartUnpickOrder =  unpickedOrders.get(smartPickOrderIndex);
                    Order shipOrder = OrderFactory.produceOrder(showUnpickOrder.getId(), showUnpickOrder.getOrderItems(), "shipped");
                    orderManagement.getShippedOrders().add(shipOrder);
                    orderManagement.getUnpickedOrders().remove(nowOrderIndex);
                    shipOrder = OrderFactory.produceOrder(smartUnpickOrder.getId(), smartUnpickOrder.getOrderItems(), "shipped");
                    orderManagement.getShippedOrders().add(shipOrder);
                    orderManagement.getUnpickedOrders().remove(smartUnpickOrder);
                    nowOrderIndex = 0;
                    smartPickOrderIndex = -1;
                    showNowOrder();
                }
                break;
        }
    }
}
