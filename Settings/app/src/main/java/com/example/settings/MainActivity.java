package com.example.settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Context context;
    SharedPreferences shared;
    SharedPreferences.Editor editor;

    EditText name;
    EditText email;
    EditText phone;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        boolean canWrite = Settings.System.canWrite(context);
        if(!canWrite) {
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName())), 200);
        }

        shared = context.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        editor = shared.edit();

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
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
            case R.id.help:
                openHelp();
                return true;
            case R.id.settings:
                openSettings();
                return true;
            case R.id.control:
                openControls();
                return true;
            case R.id.exit:
                exitApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openHelp() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://support.google.com/android/?hl=en#topic=7313011"));
        startActivity(i);
    }

    public void openSettings() {
        Intent i = new Intent(android.provider.Settings.ACTION_SETTINGS);
        startActivity(i);
    }

    public void exitApp() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory( Intent.CATEGORY_HOME );
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void openControls() {
        Intent i = new Intent(this, ControlActivity.class);
        startActivity(i);
    }

    public void submit(View V) {
        editor.putString("name", name.getText().toString());
        editor.putString("email", email.getText().toString());
        editor.putString("phone", phone.getText().toString());
        editor.apply();
    }

    public void retrieve(View V) {
        name.setText(shared.getString("name", ""));
        email.setText(shared.getString("email", ""));
        phone.setText(shared.getString("phone", ""));
    }
}