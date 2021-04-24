package com.example.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText addName;
    EditText addId;
    EditText addMRP;
    EditText addPrice;

    Button addButton;

    private ProductDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        addName = findViewById(R.id.addName);
        addMRP = findViewById(R.id.addMRP);
        addPrice = findViewById(R.id.addPrice);
        addId = findViewById(R.id.addId);

        dbHelper = new ProductDbHelper(this);
    }

    public void onAdd(View v) {
        if (TextUtils.isEmpty(addId.getText()) || TextUtils.isEmpty(addName.getText())
                || TextUtils.isEmpty(addMRP.getText()) || TextUtils.isEmpty(addPrice.getText())) {
            Toast.makeText(this, "Fill All Fields.", Toast.LENGTH_LONG).show();
        } else {
            Product product = new Product(
                    Integer.parseInt(addId.getText().toString()),
                    addName.getText().toString(),
                    Float.parseFloat(addMRP.getText().toString()),
                    Float.parseFloat(addPrice.getText().toString())
            );

            dbHelper.addProduct(product);
            Toast.makeText(this, "Product Saved.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allProductItem:
                Intent i = new Intent(MainActivity.this, AllProductsActivity.class);
                startActivity(i);
                return true;

            case R.id.showProductItem:
                Intent j = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(j);
                return true;

            case R.id.editProductItem:
                Intent k = new Intent(MainActivity.this, EditProductActivity.class);
                startActivity(k);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}