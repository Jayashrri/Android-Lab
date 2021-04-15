    package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Locale;

public class AddScheduleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    EditText subject;
    EditText startTime;
    EditText endTime;

    Object selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        subject = (EditText) findViewById(R.id.subject);
        startTime = (EditText) findViewById(R.id.startTime);
        endTime = (EditText) findViewById(R.id.endTime);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weekdays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
        selected = spinner.getItemAtPosition(0);

        startTime.setFocusable(false);
        endTime.setFocusable(false);

        startTime.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, (timePicker, hourOfDay, minutes) -> {
                startTime.setText(String.format(Locale.ENGLISH, "%02d:%02d", hourOfDay, minutes));
            }, 0, 0, false);
            timePickerDialog.show();
        });

        endTime.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddScheduleActivity.this, (timePicker, hourOfDay, minutes) -> {
                endTime.setText(String.format(Locale.ENGLISH, "%02d:%02d", hourOfDay, minutes));
            }, 0, 0, false);
            timePickerDialog.show();
        });

        final Button button = findViewById(R.id.insert);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(subject.getText()) || TextUtils.isEmpty(startTime.getText())
                    || TextUtils.isEmpty(endTime.getText()) || TextUtils.isEmpty(selected.toString())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String subjectName = subject.getText().toString();
                int startHour = Integer.parseInt(startTime.getText().toString().split(":")[0]);
                int startMinute = Integer.parseInt(startTime.getText().toString().split(":")[1]);
                int endHour = Integer.parseInt(endTime.getText().toString().split(":")[0]);
                int endMinute = Integer.parseInt(endTime.getText().toString().split(":")[1]);
                String day = selected.toString();

                replyIntent.putExtra("subject", subjectName);
                replyIntent.putExtra("time", String.format(Locale.ENGLISH, "%02d:%02d to %02d:%02d", startHour, startMinute, endHour, endMinute));
                replyIntent.putExtra("day", day);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}