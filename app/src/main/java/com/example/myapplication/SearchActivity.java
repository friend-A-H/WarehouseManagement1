package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.data.WarehouseSingleton;

public class SearchActivity extends AppCompatActivity {
    private ImageView searchButton;
    private EditText searchInputText;
    private TextView searchClothIdText, searchClothNumText;
    private Boolean found = false;
    private int i, j, searchClothNum;
    private String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        searchInputText = findViewById(R.id.searchInputText);
        searchClothIdText = findViewById(R.id.searchClothIdText);
        searchClothNumText = findViewById(R.id.searchClothNumText);
        searchButton = findViewById(R.id.searchImageButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarehouseSingleton warehouse = WarehouseSingleton.getInstance();
                searchInput = searchInputText.getText().toString();
                searchClothNum = warehouse.searchClothNumByID(searchInput);
                if(searchClothNum != -1){
                    searchClothIdText.setText(searchInput);
                    searchClothNumText.setText("服装数量为：" +  searchClothNum + "件");
                }
                else{
                    searchClothIdText.setText(searchInput);
                    searchClothNumText.setText("找不到该服装");
                }
            }
        });
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
