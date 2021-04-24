package com.example.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText rollNumber;
    EditText name;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        rollNumber = (EditText)findViewById(R.id.roll);
        name = (EditText)findViewById(R.id.name);
        next = (Button)findViewById(R.id.button);
    }

    public void onSubmit(View v) {
        Intent messageIntent = new Intent(this, MessageActivity.class);
        messageIntent.putExtra("rollNumber", rollNumber.getText().toString());
        messageIntent.putExtra("name", name.getText().toString());

        startActivity(messageIntent);
    }
}