package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.data.model.UnpickOrder;
import com.example.myapplication.data.OrderItemAdapter;

public class SortActivity extends AppCompatActivity {
    private ListView smartPickOrderListView;
    private TextView sortParcelTextView;
    private Button completePickButton;
    private String mode;
    private UnpickOrder unpickOrder, smartUnpickOrder;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        smartPickOrderListView = findViewById(R.id.smartPickOrderListView);
        sortParcelTextView = findViewById(R.id.sortParcelTextView);
        completePickButton = findViewById(R.id.completePickButton);

        mode = getIntent().getStringExtra("mode");
        if(mode.equals("single")){
            unpickOrder = (UnpickOrder) getIntent().getSerializableExtra("UnpickOrder");
            showNowOrder(unpickOrder);
            completePickButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SortActivity.this.finish();
                }
            });
        }
        else{
            unpickOrder = (UnpickOrder) getIntent().getSerializableExtra("UnpickOrder");
            smartUnpickOrder = (UnpickOrder) getIntent().getSerializableExtra("smartUnpickOrder");
            showNowOrder(unpickOrder);
            completePickButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(index == 0){
                        showNowOrder(smartUnpickOrder);
                        index = 1;
                    }
                    else{
                        SortActivity.this.finish();
                    }
                }
            });
        }
    }

    private void showNowOrder(UnpickOrder unpickOrder){
        sortParcelTextView.setText("分拣入订单"+ unpickOrder.getId()+"的包裹中");
        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(SortActivity.this, R.layout.list_view_item_order, unpickOrder.getOrderItems());
        smartPickOrderListView.setAdapter(orderItemAdapter);
    }
}
