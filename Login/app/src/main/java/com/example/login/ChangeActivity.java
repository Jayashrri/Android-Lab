package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeActivity extends AppCompatActivity {

    EditText changeCurrent;
    EditText changeNew;

    Button changeSubmit;

    String username;

    private UserDbHelper dbHelper;
    private  SQLiteDatabase readDb;
    private SQLiteDatabase writeDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        changeCurrent = findViewById(R.id.changeCurrent);
        changeNew = findViewById(R.id.changeNew);
        changeSubmit = findViewById(R.id.changeSubmit);

        dbHelper = new UserDbHelper(getApplicationContext());
        readDb = dbHelper.getReadableDatabase();
        writeDb = dbHelper.getWritableDatabase();

        username = getIntent().getExtras().getString("username");
    }

    public void onChange(View v) {
        Cursor cursor = readDb.query(
                UserContract.User.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                },
                UserContract.User.COLUMN_NAME_USERNAME + " = ? AND " + UserContract.User.COLUMN_NAME_PASSWORD + " = ?",
                new String[] {
                        username,
                        changeCurrent.getText().toString()
                },
                null,
                null,
                null
        );

        if (cursor.getCount() == 1) {
            ContentValues values = new ContentValues();
            values.put(UserContract.User.COLUMN_NAME_PASSWORD, changeNew.getText().toString());
            writeDb.update(
                    UserContract.User.TABLE_NAME,
                    values,
                    UserContract.User.COLUMN_NAME_USERNAME + " = ?",
                    new String[] {
                            username
                    }
            );
            Toast.makeText(ChangeActivity.this, "Password changed successfully.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ChangeActivity.this, "Current password does not match.", Toast.LENGTH_LONG).show();
        }
    }
}