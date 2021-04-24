package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        EditText number1 = (EditText) findViewById(R.id.number1);
        EditText number2 = (EditText) findViewById(R.id.number2);
        Button addButton = (Button) findViewById(R.id.button);
        TextView result = (TextView) findViewById(R.id.result);
        RadioGroup operation = (RadioGroup) findViewById(R.id.operation);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (number1.getText().length() == 0 || number2.getText().length() == 0){
                    result.setText("");
                } else {
                    int selected = operation.getCheckedRadioButtonId();
                    double num1 = Double.parseDouble(number1.getText().toString());
                    double num2 = Double.parseDouble(number2.getText().toString());
                    double answer = 0;

                    switch (selected){
                        case R.id.addition:
                            answer = num1 + num2;
                            break;
                        case R.id.subtraction:
                            answer = num1 - num2;
                            break;
                        case R.id.multiplication:
                            answer = num1 * num2;
                            break;
                        case R.id.division:
                            if (num2 != 0)
                                answer = num1 / num2;
                            else {
                                result.setText((""));
                                return;
                            }
                            break;
                    }

                    result.setText(String.format("%.5f", answer));
                }
            }
        });
    }
}