package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Email extends AppCompatActivity {

    EditText to;
    EditText subject;
    EditText message;
    Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        to = (EditText)findViewById(R.id.to);
        subject = (EditText)findViewById(R.id.subject);
        message = (EditText)findViewById(R.id.message);
        sendEmail = (Button)findViewById(R.id.sendEmail);
    }

    public void onSubmit(View V) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("message/rfc822");
        mailIntent.setPackage("com.google.android.gm");

        String[] recipients = {to.getText().toString()};
        mailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
        mailIntent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
        startActivity(mailIntent);
    }
}