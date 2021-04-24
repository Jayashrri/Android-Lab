package com.example.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProductActivity extends AppCompatActivity {

    EditText editName;
    EditText editId;
    EditText editMRP;
    EditText editPrice;

    Button editButton;
    Button editSearch;

    private ProductDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        editButton = findViewById(R.id.addButton);
        editSearch = findViewById(R.id.editSearch);
        editName= findViewById(R.id.editName);
        editMRP = findViewById(R.id.editMRP);
        editPrice = findViewById(R.id.editPrice);
        editId = findViewById(R.id.editId);

        dbHelper = new ProductDbHelper(this);
    }

    public void onFind(View v) {
        if (TextUtils.isEmpty(editId.getText())) {
            Toast.makeText(this, "Enter ID.", Toast.LENGTH_LONG).show();
        } else {
            Product product = dbHelper.getProduct(Integer.parseInt(editId.getText().toString()));
            editName.setText(String.format("%s", product.getName()));
            editMRP.setText(String.format("%.2f", product.getMrp()));
            editPrice.setText(String.format("%.2f", product.getPrice()));

            editId.setEnabled(false);
        }
    }

    public void onEdit(View v) {
        if (TextUtils.isEmpty(editId.getText()) || TextUtils.isEmpty(editName.getText())
                || TextUtils.isEmpty(editMRP.getText()) || TextUtils.isEmpty(editPrice.getText())) {
            Toast.makeText(this, "Fill All Fields.", Toast.LENGTH_LONG).show();
        } else {
            Product product = new Product(
                    Integer.parseInt(editId.getText().toString()),
                    editName.getText().toString(),
                    Float.parseFloat(editMRP.getText().toString()),
                    Float.parseFloat(editPrice.getText().toString())
            );

            dbHelper.updateProduct(product);
            Toast.makeText(this, "Product Edited.", Toast.LENGTH_LONG).show();
        }
    }
}