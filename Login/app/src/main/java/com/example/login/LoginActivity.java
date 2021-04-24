package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    TextView welcome;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;

    private UserDbHelper dbHelper;
    private SQLiteDatabase readDb;

    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcome = findViewById(R.id.welcome);
        welcome.setText(String.format("Welcome, %s", getIntent().getExtras().getString("username")));

        dbHelper = new UserDbHelper(getApplicationContext());
        readDb = dbHelper.getReadableDatabase();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        loadRecyclerViewItem();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("username", getIntent().getExtras().getString("username"));
    }

    @Override
    public void onBackPressed() { exit(); }

    private void loadRecyclerViewItem() {
        Cursor cursor = readDb.query(
                UserContract.User.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                        UserContract.User.COLUMN_NAME_NAME,
                },
                null,
                new String[] {},
                null,
                null,
                UserContract.User.COLUMN_NAME_NAME + " ASC"
        );
        list = new ArrayList<>();
        while(cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.User.COLUMN_NAME_NAME)));
        }
        cursor.close();
        recyclerAdapter = new UserListAdapter(list, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuLogout:
                logout();
                return true;
            case R.id.menuChange:
                changePassword();
                return true;
            case R.id.menuExit:
                exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout() {
        Intent logout = new Intent(LoginActivity.this, MainActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logout);
    }

    public void changePassword() {
        Intent change = new Intent(LoginActivity.this, ChangeActivity.class);
        change.putExtra("username", getIntent().getExtras().getString("username"));
        startActivity(change);
    }

    public void exit() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory( Intent.CATEGORY_HOME );
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}