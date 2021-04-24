package com.example.services;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        EditText to = findViewById(R.id.to);
        EditText subject = findViewById(R.id.subject);
        EditText message = findViewById(R.id.body);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] strTo = { to.getText()+"" };
                intent.putExtra(Intent.EXTRA_EMAIL, strTo);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText() + "");
                intent.putExtra(Intent.EXTRA_TEXT, message.getText() + "");
                intent.setType("message/rfc822");

                intent.setPackage("com.google.android.gm");

                startActivity(intent);
            }
        });

    }
}
