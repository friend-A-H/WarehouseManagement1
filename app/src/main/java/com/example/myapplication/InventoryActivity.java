package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.data.WarehouseSingleton;
import com.example.myapplication.data.model.GoodShelf;
import com.example.myapplication.data.model.InventoryRecord;
import com.example.myapplication.data.model.InventoryReport;
import com.example.myapplication.data.model.InventoryReportFactory;
import com.example.myapplication.data.model.LossReport;
import com.example.myapplication.data.model.LossReportFactory;
import com.example.myapplication.data.model.ProfitReport;
import com.example.myapplication.data.model.ProfitReportFactory;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {
    private EditText searchShelfEditText, searchGridEditText, inputClothIDEditText, inputClothNumEditText;
    private TextView searchClothIDResultTextView, searchClothNumResultTextView;
    private Button searchClothButton, updateInventoryButton, completeInventoryButton;
    private int searchShelfNum = -1, searchGridNum = -1, searchClothNum = -1;
    private String searchClothID, searchShelfID, searchGridID;
    private WarehouseSingleton warehouse = WarehouseSingleton.getInstance();
    private List<GoodShelf> goodShelves = warehouse.getGoodShelves();
    private InventoryReportFactory profitReportFactory = new ProfitReportFactory();
    private InventoryReportFactory lossReportFactory = new LossReportFactory();
    private InventoryReport profitReport = profitReportFactory.produceInventoryReport();
    private InventoryReport lossReport = lossReportFactory.produceInventoryReport();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("WarehouseSerializable.txt"));
            objectOutputStream.writeObject(goodShelves);
            objectOutputStream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        searchShelfEditText = findViewById(R.id.searchShelfEditText);
        searchGridEditText = findViewById(R.id.searchGridEditText);
        inputClothIDEditText = findViewById(R.id.inputClothIDEditText);
        inputClothNumEditText = findViewById(R.id.inputClothNumEditText);
        searchClothIDResultTextView = findViewById(R.id.searchClothIDResultTextView);
        searchClothNumResultTextView = findViewById(R.id.searchClothNumResultTextView);
        searchClothButton = findViewById(R.id.searchClothButton);
        updateInventoryButton = findViewById(R.id.updateInventoryButton);
        completeInventoryButton = findViewById(R.id.completeInventoryButton);

        searchClothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchShelfID = searchShelfEditText.getText().toString();
                searchGridID = searchGridEditText.getText().toString();
                try {
                    searchGridNum = Integer.parseInt(searchGridID) - 1;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    searchGridNum = -1;
                    Toast ts = Toast.makeText(InventoryActivity.this,"输入的货位号不正确",Toast.LENGTH_SHORT);
                    ts.show();
                    searchClothIDResultTextView.setText("服装ID：");
                    searchClothNumResultTextView.setText("服装数量：");
                }
                if(searchGridNum > 11 || searchGridNum < 0){
                    Toast ts = Toast.makeText(InventoryActivity.this,"输入的货位号不正确",Toast.LENGTH_SHORT);
                    ts.show();
                    searchClothIDResultTextView.setText("服装ID：");
                    searchClothNumResultTextView.setText("服装数量：");
                }

                if(searchShelfID.equals("A")) searchShelfNum = 0;
                else if(searchShelfID.equals("B")) searchShelfNum = 1;
                else if(searchShelfID.equals("C")) searchShelfNum = 2;
                else if(searchShelfID.equals("D")) searchShelfNum = 3;
                else{
                    searchShelfNum = -1;
                    Toast ts = Toast.makeText(InventoryActivity.this,"输入的货架号不正确",Toast.LENGTH_SHORT);
                    ts.show();
                    searchClothIDResultTextView.setText("服装ID：");
                    searchClothNumResultTextView.setText("服装数量：");
                }

                if(searchShelfNum >= 0 && searchShelfNum <= 3 && searchGridNum >= 0 && searchGridNum <= 11){
                    searchClothID = goodShelves.get(searchShelfNum).getGoodGrids().get(searchGridNum).getClothID();
                    searchClothNum = goodShelves.get(searchShelfNum).getGoodGrids().get(searchGridNum).getClothNumber();

                    if(searchClothID == null){
                        searchClothIDResultTextView.setText("该货位没有服装");
                        searchClothNumResultTextView.setText("");
                    }
                    else{
                        searchClothIDResultTextView.setText("服装ID：" + searchClothID);
                        searchClothNumResultTextView.setText("服装数量：" + searchClothNum);
                    }
                }
            }
        });


        updateInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchShelfNum >= 0 && searchShelfNum <= 3 && searchGridNum >= 0 && searchGridNum <= 11){
                    String resetClothID = inputClothIDEditText.getText().toString();
                    int resetClothNum;
                    try {
                        resetClothNum = Integer.parseInt(inputClothNumEditText.getText().toString());
                        if(resetClothNum > 0){
                            addRecordInReport(resetClothID, resetClothNum);
                            goodShelves.get(searchShelfNum).getGoodGrids().get(searchGridNum).setClothID(resetClothID);
                            goodShelves.get(searchShelfNum).getGoodGrids().get(searchGridNum).setClothNumber(resetClothNum);
                        }
                        else if(resetClothNum == 0){
                            lossReport.addRecord(searchShelfID, searchGridID, resetClothID, searchClothNum);
                            goodShelves.get(searchShelfNum).getGoodGrids().get(searchGridNum).setClothID(null);
                            goodShelves.get(searchShelfNum).getGoodGrids().get(searchGridNum).setClothNumber(0);
                        }
                        else{
                            Toast ts = Toast.makeText(InventoryActivity.this,"输入的库存数量不正确",Toast.LENGTH_SHORT);
                            ts.show();
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast ts = Toast.makeText(InventoryActivity.this,"输入的库存数量不正确",Toast.LENGTH_SHORT);
                        ts.show();
                    }
                    inputClothIDEditText.setText("");
                    inputClothNumEditText.setText("");
                }
                else{
                    Toast ts = Toast.makeText(InventoryActivity.this,"请先完成正确的查询",Toast.LENGTH_SHORT);
                    ts.show();
                }
            }
        });

        completeInventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(InventoryActivity.this,ReportActivity.class);
                reportIntent.putExtra("profitReport", (ProfitReport)profitReport);
                reportIntent.putExtra("lossReport", (LossReport)lossReport);
                InventoryActivity.this.startActivity(reportIntent);
                InventoryActivity.this.finish();
            }
        });
    }

    private void addRecordInReport(String resetClothID, int resetClothNum){
        if(searchClothID == null){
            profitReport.addRecord(searchShelfID, searchGridID, resetClothID, resetClothNum);
        }
        else{
            if(resetClothID.equals(searchClothID)){
                if(resetClothNum > searchClothNum){
                    profitReport.addRecord(searchShelfID, searchGridID, resetClothID, resetClothNum - searchClothNum);
                }
                else if(resetClothNum < searchClothNum){
                    lossReport.addRecord(searchShelfID, searchGridID, resetClothID, resetClothNum - searchClothNum);
                }
            }
            else{
                profitReport.addRecord(searchShelfID, searchGridID, resetClothID, resetClothNum);
                lossReport.addRecord(searchShelfID, searchGridID, resetClothID, 0 - searchClothNum);
            }
        }
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
}
