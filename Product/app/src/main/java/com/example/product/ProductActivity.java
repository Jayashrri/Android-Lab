package com.example.product;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    EditText searchId;
    TextView showId;
    TextView showName;
    TextView showMrp;
    TextView showPrice;

    Button button;

    private ProductDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        searchId = findViewById(R.id.searchId);
        showId = findViewById(R.id.showId);
        showName = findViewById(R.id.showName);
        showMrp = findViewById(R.id.showMrp);
        showPrice = findViewById(R.id.showPrice);

        button = findViewById(R.id.search);

        dbHelper = new ProductDbHelper(this);
    }

    public void onSearch(View v) {
        if (TextUtils.isEmpty(searchId.getText())) {
            Toast.makeText(this, "Enter ID.", Toast.LENGTH_LONG).show();
        } else {
            Product product = dbHelper.getProduct(Integer.parseInt(searchId.getText().toString()));
            showId.setText(String.format("ID: %d", product.getId()));
            showName.setText(String.format("Name: %s", product.getName()));
            showMrp.setText(String.format("MRP: %.2f", product.getMrp()));
            showPrice.setText(String.format("Price: %.2f", product.getPrice()));
        }
    }
}