package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText registerName;
    EditText registerUsername;
    EditText registerPassword;
    EditText registerConfirm;
    EditText registerEmail;
    EditText registerPhone;

    Button registerSubmit;

    private UserDbHelper dbHelper;
    private SQLiteDatabase readDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.registerName);
        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirm = findViewById(R.id.registerConfirm);
        registerEmail = findViewById(R.id.registerEmail);
        registerPhone = findViewById(R.id.registerPhone);
        registerSubmit = findViewById(R.id.registerSubmit);

        registerUsername.setText(getIntent().getExtras().getString("username"));

        dbHelper = new UserDbHelper(getApplicationContext());
        readDb = dbHelper.getReadableDatabase();
    }

    public void onSubmit(View v) {
        Intent replyIntent = new Intent();
        Cursor cursor = readDb.query(
                UserContract.User.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                },
                UserContract.User.COLUMN_NAME_USERNAME + " = ?",
                new String[] {
                        registerUsername.getText().toString()
                },
                null,
                null,
                null
        );

        if (TextUtils.isEmpty(registerName.getText()) || TextUtils.isEmpty(registerUsername.getText())
                || TextUtils.isEmpty(registerPassword.getText()) || TextUtils.isEmpty(registerConfirm.toString())
                || TextUtils.isEmpty(registerEmail.toString()) || TextUtils.isEmpty(registerPhone.toString())) {
            Toast.makeText(RegisterActivity.this, "Fill all fields.", Toast.LENGTH_LONG).show();
        } else if (cursor.getCount() != 0) {
            Toast.makeText(RegisterActivity.this, "Username already exists.", Toast.LENGTH_LONG).show();
        } else if (!TextUtils.equals(registerPassword.getText(), registerConfirm.getText())) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
        } else {
            replyIntent.putExtra("name", registerName.getText().toString());
            replyIntent.putExtra("username", registerUsername.getText().toString());
            replyIntent.putExtra("password", registerPassword.getText().toString());
            replyIntent.putExtra("email", registerEmail.getText().toString());
            replyIntent.putExtra("phone", registerPhone.getText().toString());
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}