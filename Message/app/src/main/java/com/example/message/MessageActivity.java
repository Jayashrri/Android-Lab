package com.example.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class MessageActivity extends AppCompatActivity {

    Intent intent;
    String rollNumber;
    String name;

    EditText message;
    EditText to;
    TextView welcome;
    RadioGroup action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        message = (EditText)findViewById(R.id.message);
        to = (EditText)findViewById(R.id.to);
        welcome = (TextView)findViewById(R.id.welcome);
        action = (RadioGroup)findViewById(R.id.action);

        intent = getIntent();
        rollNumber = intent.getStringExtra("rollNumber");
        name = intent.getStringExtra("name");

        welcome.setText("Welcome, " + name);
        Toast.makeText(MessageActivity.this, "Welcome, " + name + " - " + rollNumber, Toast.LENGTH_LONG).show();
    }

    public void onSubmit(View v) {
        int selected = action.getCheckedRadioButtonId();
        if(selected == -1) {
            Toast.makeText(MessageActivity.this, "Please select an action", Toast.LENGTH_SHORT).show();
        } else {
            switch(selected) {
                case R.id.sms:
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                    smsIntent.setData(Uri.parse("smsto:" + to.getText().toString()));
                    smsIntent.putExtra("sms_body", message.getText().toString());
                    startActivity(smsIntent);
                    break;

                case R.id.gmail:
                    Intent mailIntent = new Intent(Intent.ACTION_SEND);
                    mailIntent.setType("message/rfc822");
                    mailIntent.setPackage("com.google.android.gm");

                    String[] recipients = {to.getText().toString()};
                    mailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    mailIntent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
                    startActivity(mailIntent);
                    break;

                case R.id.google:
                    Uri uri = Uri.parse("http://www.google.com/search?q=" + message.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    break;
            }
        }
    }

    public void onBack(View v) {
        Intent backIntent = new Intent(this, MainActivity.class);
        startActivity(backIntent);
    }
}