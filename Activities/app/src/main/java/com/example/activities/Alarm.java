package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Alarm extends AppCompatActivity {

    EditText hours;
    EditText minutes;
    EditText message;
    Button submitAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        hours = (EditText)findViewById(R.id.hours);
        minutes = (EditText)findViewById(R.id.minutes);
        message = (EditText)findViewById(R.id.alarmMessage);
        submitAlarm = (Button)findViewById(R.id.submitAlarm);
    }

    public void onSubmit(View V) {
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, message.getText().toString());
        i.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(hours.getText().toString()));
        i.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(minutes.getText().toString()));
        startActivity(i);
    }
}