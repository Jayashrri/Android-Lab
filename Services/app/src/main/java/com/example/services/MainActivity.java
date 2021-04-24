package com.example.services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    private EditText name, roll;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name_text);
        roll = findViewById(R.id.roll_text);
        submit = findViewById(R.id.submit_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name.getText())){
                    Toasty.error(MainActivity.this, "Name field is empty", Toast.LENGTH_SHORT, true).show();

                }else if(TextUtils.isEmpty(roll.getText())){
                    Toasty.error(MainActivity.this, "Roll no field is empty", Toast.LENGTH_SHORT, true).show();

                }else{
                    Intent intent = new Intent(MainActivity.this, ActivityTwo.class);
                    intent.putExtra("Name", name.getText().toString());

                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}