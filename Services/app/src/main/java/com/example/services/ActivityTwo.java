package com.example.services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

public class ActivityTwo extends AppCompatActivity {
    private RadioGroup action_group;
    private Button submit, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("Name");
        name = "Welcome " + name + "!";

        TextView name_tv = findViewById(R.id.name_header);
        action_group = findViewById(R.id.actionGroup);
        back = findViewById(R.id.back_btn);
        submit = findViewById(R.id.submit_btn);
        Toasty.success(ActivityTwo.this, "Welcome to Activity 2", Toast.LENGTH_SHORT, true).show();
        name_tv.setText(name);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = action_group.getCheckedRadioButtonId();
                if(selectedId==-1){
                    Toasty.error(ActivityTwo.this, "Please select an action ", Toast.LENGTH_SHORT, true).show();
                }else{
                    switch(selectedId){
                        case R.id.alarm_btn:
                            Intent alarmintent = new Intent(ActivityTwo.this, AlarmActivity.class);
                            startActivity(alarmintent);
                            break;

                        case R.id.gmail_btn:
                            Intent mailintent = new Intent(ActivityTwo.this, MailActivity.class);
                            startActivity(mailintent);
                            break;

                        case R.id.music_btn:
                            Intent musicintent = new Intent(ActivityTwo.this, MusicActivity.class);
                            startActivity(musicintent);
                            break;

                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityIntent = new Intent(ActivityTwo.this, MainActivity.class);
                Toasty.success(ActivityTwo.this, "Welcome to Activity 1", Toast.LENGTH_SHORT, true).show();
                startActivity(activityIntent);
                finish();
            }
        });
    }
}