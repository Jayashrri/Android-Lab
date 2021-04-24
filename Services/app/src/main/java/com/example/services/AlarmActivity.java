package com.example.services;


import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        EditText h = findViewById(R.id.hours);
        EditText m = findViewById(R.id.minutes);
        EditText s = findViewById(R.id.message);


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
                i.putExtra(AlarmClock.EXTRA_MESSAGE, s.getText() +"");
                i.putExtra(AlarmClock.EXTRA_HOUR, (Integer.parseInt(h.getText() + "")));
                i.putExtra(AlarmClock.EXTRA_MINUTES, (Integer.parseInt(m.getText() + "")));
                startActivity(i);
            }
        });
    }
}
