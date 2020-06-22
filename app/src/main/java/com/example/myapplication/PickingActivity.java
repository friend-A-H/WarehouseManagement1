package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.model.UnpickOrder;
import com.example.myapplication.data.model.OrderItem;
import com.example.myapplication.data.model.PickOrderAdapter;
import com.example.myapplication.data.model.PickOrderItem;
import com.example.myapplication.data.WarehouseSingleton;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PickingActivity extends AppCompatActivity {
    TextView pickShelfTextView, pickGridTextView, pickNumTextView, pickIncompleteIDTextView;
    EditText inputLastFourIDEditText;
    Button comfirmPickButton;
    String mode;
    UnpickOrder unpickOrder, smartUnpickOrder;
    List<OrderItem> orderItems;
    List<Integer> mergedIndexs;
    WarehouseSingleton warehouse;
    int index = 0;

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
        setContentView(R.layout.activity_picking);

        warehouse = WarehouseSingleton.getInstance();

        pickShelfTextView = findViewById(R.id.pickShelfTextView);
        pickGridTextView = findViewById(R.id.pickGridTextView);
        pickNumTextView = findViewById(R.id.pickNumTextView);
        pickIncompleteIDTextView = findViewById(R.id.pickIncompleteIDTextView);
        inputLastFourIDEditText = findViewById(R.id.inputLastFourIDEditText);
        comfirmPickButton = findViewById(R.id.comfirmPickButton);

        mode = getIntent().getStringExtra("mode");

        init();

        comfirmPickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderItem nowOrderItem = orderItems.get(index);
                if(inputLastFourIDEditText.getText().toString().equals(nowOrderItem.getOrderClothID().substring(nowOrderItem.getOrderClothID().length() - 4, nowOrderItem.getOrderClothID().length()))){
                    int[] position = warehouse.searchClothPositionByID(nowOrderItem.getOrderClothID());
                    int nowClothNum = warehouse.getGoodShelves().get(position[0]).getGoodGrids().get(position[1]).getClothNumber();
                    if(nowClothNum - nowOrderItem.getOrderClothNum() == 0){
                        warehouse.getGoodShelves().get(position[0]).getGoodGrids().get(position[1]).setClothID(null);
                    }
                    warehouse.getGoodShelves().get(position[0]).getGoodGrids().get(position[1]).setClothNumber(nowClothNum - nowOrderItem.getOrderClothNum());
                    if(index != orderItems.size() - 1){
                        index++;
                        showNowOrderItem();
                        inputLastFourIDEditText.setText("");
                    }
                    else{
                        if(mode.equals("single")){
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK,resultIntent);
                            PickingActivity.this.finish();
                            Intent sortIntent = new Intent(PickingActivity.this,SortActivity.class);
                            sortIntent.putExtra("mode", "single");
                            sortIntent.putExtra("UnpickOrder", unpickOrder);
                            PickingActivity.this.startActivity(sortIntent);
                        }
                        else{
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK,resultIntent);
                            PickingActivity.this.finish();
                            Intent sortIntent = new Intent(PickingActivity.this,SortActivity.class);
                            sortIntent.putExtra("mode", "smart");
                            sortIntent.putExtra("UnpickOrder", unpickOrder);
                            sortIntent.putExtra("smartUnpickOrder", smartUnpickOrder);
                            PickingActivity.this.startActivity(sortIntent);
                        }
                    }
                }
                else{
                    Toast ts = Toast.makeText(getBaseContext(),"输入的后四位ID错误，请重新输入",Toast.LENGTH_SHORT);
                    ts.show();
                    inputLastFourIDEditText.setText("");
                }
            }
        });
    }

    private void init(){
        if(mode.equals("single")){
            unpickOrder = (UnpickOrder) getIntent().getSerializableExtra("UnpickOrder");
            orderItems = unpickOrder.getOrderItems();
            showNowOrderItem();
        }
        else{
            unpickOrder = (UnpickOrder) getIntent().getSerializableExtra("UnpickOrder");
            smartUnpickOrder = (UnpickOrder) getIntent().getSerializableExtra("smartUnpickOrder");
            orderItems = new ArrayList<OrderItem>();
            mergedIndexs = new ArrayList<Integer>();
            Boolean idEqual = false;
            for(int i = 0; i < unpickOrder.getOrderItems().size(); i++){
                for(int j = 0; j < smartUnpickOrder.getOrderItems().size(); j++){
                    if(unpickOrder.getOrderItems().get(i).getOrderClothID().equals(smartUnpickOrder.getOrderItems().get(j).getOrderClothID())){
                        OrderItem mergedOrderItem = new OrderItem(unpickOrder.getOrderItems().get(i).getOrderClothID(), unpickOrder.getOrderItems().get(i).getOrderClothNum() + smartUnpickOrder.getOrderItems().get(j).getOrderClothNum());
                        orderItems.add(mergedOrderItem);
                        mergedIndexs.add(j);
                        idEqual = true;
                        break;
                    }
                }
                if(idEqual == true){
                    idEqual = false;
                }
                else{
                    orderItems.add(unpickOrder.getOrderItems().get(i));
                }
            }

            for(int i = 0; i < smartUnpickOrder.getOrderItems().size(); i++){
                if(mergedIndexs.contains(i) == false){
                    orderItems.add(smartUnpickOrder.getOrderItems().get(i));
                }
            }
            showNowOrderItem();
        }
    }

    private void showNowOrderItem(){
        OrderItem nowOrderItem = orderItems.get(index);
        int[] position = warehouse.searchClothPositionByID(nowOrderItem.getOrderClothID());
        if(position[0] != -1 && position[1] != -1){
            pickShelfTextView.setText(numToLetter(position[0]));
            pickGridTextView.setText(String.valueOf(position[1] + 1));
            pickNumTextView.setText(String.valueOf(nowOrderItem.getOrderClothNum()) + "件");
            PickOrderItem pickOrderItem = new PickOrderAdapter(nowOrderItem);
            pickIncompleteIDTextView.setText(pickOrderItem.getOrderIncompleteClothID());
        }
    }

    private String numToLetter(int num){
        char temp = (char) (num + (int) 'A');
        String result = "" + temp;
        return result;
    }
}
