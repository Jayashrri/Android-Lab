package com.example.addition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText number1 = (EditText) findViewById(R.id.number1);
        EditText number2 = (EditText) findViewById(R.id.number2);
        Button addButton = (Button) findViewById(R.id.button);
        TextView result = (TextView) findViewById(R.id.result);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (number1.getText().length() == 0 || number2.getText().length() == 0){
                    result.setText("");
                } else {
                    double num1 = Double.parseDouble(number1.getText().toString());
                    double num2 = Double.parseDouble(number2.getText().toString());
                    double sum = num1 + num2;
                    result.setText(Double.toString(sum));
                }
            }
        });
    }
}