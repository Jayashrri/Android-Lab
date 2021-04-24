package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup activityChoice;
    Button submitActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        activityChoice = (RadioGroup)findViewById(R.id.activityChoice);
        submitActivity = (Button)findViewById(R.id.chooseActivity);
    }

    public void onSubmit(View V) {
        int selected = activityChoice.getCheckedRadioButtonId();
        if(selected == -1) {
            Toast.makeText(MainActivity.this, "Please select an action", Toast.LENGTH_SHORT).show();
        } else {
            switch (selected) {
                case R.id.setAlarm:
                    Intent alarmIntent = new Intent(this, Alarm.class);
                    startActivity(alarmIntent);
                    break;
                case R.id.playMusic:
                    Intent musicIntent = new Intent(this, Music.class);
                    startActivity(musicIntent);
                    break;
                case R.id.compose:
                    Intent mailIntent = new Intent(this, Email.class);
                    startActivity(mailIntent);
                    break;
            }
        }
    }
}