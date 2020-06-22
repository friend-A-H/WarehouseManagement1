package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.data.ReportItemAdapter;
import com.example.myapplication.data.model.LossReport;
import com.example.myapplication.data.model.ProfitReport;

public class ReportActivity extends AppCompatActivity {
    private ListView reportListView;
    private TextView reportKindTextView;
    private Button completeViewButton;
    private ProfitReport profitReport;
    private LossReport lossReport;
    private ReportItemAdapter reportItemAdapter;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportListView = findViewById(R.id.reportListView);
        reportKindTextView = findViewById(R.id.reportKindTextView);
        completeViewButton = findViewById(R.id.completeViewButton);

        profitReport = (ProfitReport) getIntent().getSerializableExtra("profitReport");
        lossReport = (LossReport) getIntent().getSerializableExtra("lossReport");

        reportKindTextView.setText("本次盘盈记录");
        reportItemAdapter = new ReportItemAdapter(ReportActivity.this, R.layout.list_view_item_report, profitReport.getInventoryRecords());
        reportListView.setAdapter(reportItemAdapter);

        completeViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 0){
                    index++;
                    reportKindTextView.setText("本次盘亏记录");
                    reportItemAdapter = new ReportItemAdapter(ReportActivity.this, R.layout.list_view_item_report, lossReport.getInventoryRecords());
                    reportListView.setAdapter(reportItemAdapter);
                }
                else{
                    ReportActivity.this.finish();
                }
            }
        });
    }
}
