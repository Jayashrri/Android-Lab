package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    Object selected;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;

    private List<Schedule> list;

    private ScheduleDbHelper dbHelper;
    private SQLiteDatabase readDb;
    private SQLiteDatabase writeDb;

    public static final int NEW_SCHEDULE_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(this.getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        dbHelper = new ScheduleDbHelper(getApplicationContext());
        readDb = dbHelper.getReadableDatabase();
        writeDb = dbHelper.getWritableDatabase();

        spinner = findViewById(R.id.days);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weekdays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);
        selected = spinner.getItemAtPosition(0);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        loadRecyclerViewItem();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, AddScheduleActivity.class);
            startActivityForResult(intent, NEW_SCHEDULE_ACTIVITY_REQUEST_CODE);
        });
    }

    private void loadRecyclerViewItem() {
        Cursor cursor = readDb.query(
                ScheduleContract.Schedule.TABLE_NAME,
                new String[] {
                        BaseColumns._ID,
                        ScheduleContract.Schedule.COLUMN_NAME_SUBJECT,
                        ScheduleContract.Schedule.COLUMN_NAME_DAY,
                        ScheduleContract.Schedule.COLUMN_NAME_TIME
                },
                ScheduleContract.Schedule.COLUMN_NAME_DAY + " = ?",
                new String[] {
                        selected.toString()
                },
                null,
                null,
                ScheduleContract.Schedule.COLUMN_NAME_TIME + " DESC"
                );
        list = new ArrayList<>();
        while(cursor.moveToNext()) {
            Schedule schedule = new Schedule(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ScheduleContract.Schedule._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Schedule.COLUMN_NAME_SUBJECT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Schedule.COLUMN_NAME_DAY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.Schedule.COLUMN_NAME_TIME))
                    );
            list.add(schedule);
        }
        cursor.close();
        recyclerAdapter = new ScheduleListAdapter(list, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position);
        loadRecyclerViewItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_SCHEDULE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Schedule schedule = new Schedule(
                    0,
                    data.getStringExtra("subject"),
                    data.getStringExtra("day"),
                    data.getStringExtra("time")
            );

            ContentValues values = new ContentValues();
            values.put(ScheduleContract.Schedule.COLUMN_NAME_SUBJECT, schedule.getSubject());
            values.put(ScheduleContract.Schedule.COLUMN_NAME_DAY, schedule.getDay());
            values.put(ScheduleContract.Schedule.COLUMN_NAME_TIME, schedule.getTime());
            long rowId = writeDb.insert(ScheduleContract.Schedule.TABLE_NAME, null, values);

            loadRecyclerViewItem();

            Toast.makeText(
                    getApplicationContext(),
                    "Schedule Saved",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Schedule Not Saved",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 1, "Edit");
        menu.add(0, 2, 2, "Delete");
    }
}