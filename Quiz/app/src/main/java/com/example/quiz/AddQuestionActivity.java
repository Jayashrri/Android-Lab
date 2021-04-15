package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {

    EditText question;
    EditText optionText;
    RadioGroup options;

    Button addOption;
    Button addQuestion;

    QuizDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        question = findViewById(R.id.addQuestion);
        optionText = findViewById(R.id.addOptionText);
        options = findViewById(R.id.addOptions);

        addOption = findViewById(R.id.addOptionButton);
        addQuestion = findViewById(R.id.addQuestionButton);

        dbHelper = new QuizDbHelper(this);

        addQuestion.setOnClickListener(view -> {
            Intent reply = new Intent();
            if(TextUtils.isEmpty(question.getText()) || options.getChildCount() == 0
                    || options.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Enter All Fields.", Toast.LENGTH_LONG).show();
            } else {
                List<Option> list = new ArrayList<>();
                for(int i=0; i<options.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) options.getChildAt(i);
                    Option option = new Option(
                            i,
                            0,
                            radioButton.getText().toString()
                    );
                    list.add(option);
                }

                Question newQuestion = new Question(
                        0,
                        question.getText().toString(),
                        list,
                        options.indexOfChild(options.findViewById(options.getCheckedRadioButtonId()))
                );

                dbHelper.addQuestion(newQuestion);
                setResult(RESULT_OK, reply);
                finish();
            }
        });

        addOption.setOnClickListener(view -> {
            if(TextUtils.isEmpty(optionText.getText())) {
                Toast.makeText(this, "Enter Option Text.", Toast.LENGTH_LONG).show();
            } else {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(optionText.getText().toString());
                options.addView(radioButton);
            }
        });
    }
}