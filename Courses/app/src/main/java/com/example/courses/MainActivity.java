package com.example.courses;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView enterRollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        enterRollNumber = (TextView) findViewById(R.id.enterRollNumber);
    }

    public void onSubmit(View v) {
        String rollNumber = enterRollNumber.getText().toString();

        if(rollNumber.isEmpty()) {
            return;
        }

        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
        } catch (Exception e) {
        }

        Bundle bundle = new Bundle();
        bundle.putString("roll_number", rollNumber);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, CourseSelection.class, bundle)
                .commit();
    }
}