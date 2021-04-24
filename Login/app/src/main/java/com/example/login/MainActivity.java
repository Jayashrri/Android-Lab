package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mainLogin;
    Button mainRegister;

    EditText mainUsername;
    EditText mainPassword;

    private UserDbHelper dbHelper;
    private SQLiteDatabase readDb;
    private SQLiteDatabase writeDb;

    public static final int REGISTER_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLogin = findViewById(R.id.mainLogin);
        mainRegister = findViewById(R.id.mainRegister);
        mainUsername = findViewById(R.id.mainUsername);
        mainPassword = findViewById(R.id.mainPassword);

        dbHelper = new UserDbHelper(getApplicationContext());
        readDb = dbHelper.getReadableDatabase();
        writeDb = dbHelper.getWritableDatabase();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String username = savedInstanceState.getString("username");
        Intent redirect = new Intent(MainActivity.this, LoginActivity.class);
        redirect.putExtra("username", username);
        startActivity(redirect);
    }

    @Override
    public void onBackPressed() { exit(); }

    public void onLogin(View v) {
        Cursor cursor = readDb.query(
                UserContract.User.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                },
                UserContract.User.COLUMN_NAME_USERNAME + " = ? AND " + UserContract.User.COLUMN_NAME_PASSWORD + " = ?",
                new String[] {
                        mainUsername.getText().toString(),
                        mainPassword.getText().toString()
                },
                null,
                null,
                null
        );

        if(cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, "Invalid credentials.", Toast.LENGTH_LONG).show();
        } else {
            Bundle loginBundle = new Bundle();
            loginBundle.putString("username", mainUsername.getText().toString());

            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            loginIntent.putExtras(loginBundle);
            startActivity(loginIntent);
        }
    }

    public void onRegister(View v) {
        Bundle registerBundle = new Bundle();
        registerBundle.putString("username", mainUsername.getText().toString());

        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        registerIntent.putExtras(registerBundle);
        startActivityForResult(registerIntent, REGISTER_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            User user = new User(
                    0,
                    data.getStringExtra("name"),
                    data.getStringExtra("username"),
                    data.getStringExtra("password"),
                    data.getStringExtra("email"),
                    data.getStringExtra("phone")
            );

            ContentValues values = new ContentValues();
            values.put(UserContract.User.COLUMN_NAME_NAME, user.getName());
            values.put(UserContract.User.COLUMN_NAME_USERNAME, user.getUsername());
            values.put(UserContract.User.COLUMN_NAME_PASSWORD, user.getPassword());
            values.put(UserContract.User.COLUMN_NAME_EMAIL, user.getEmail());
            values.put(UserContract.User.COLUMN_NAME_PHONE, user.getPhone());
            long rowId = writeDb.insert(UserContract.User.TABLE_NAME, null, values);

            Toast.makeText(
                    getApplicationContext(),
                    "User created.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "User not created.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void exit() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory( Intent.CATEGORY_HOME );
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}