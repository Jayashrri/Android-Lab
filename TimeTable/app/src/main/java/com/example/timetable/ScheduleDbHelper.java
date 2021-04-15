package com.example.timetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScheduleDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Schedule.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ScheduleContract.Schedule.TABLE_NAME + " (" +
                    ScheduleContract.Schedule._ID + " INTEGER PRIMARY KEY," +
                    ScheduleContract.Schedule.COLUMN_NAME_SUBJECT + " TEXT," +
                    ScheduleContract.Schedule.COLUMN_NAME_TIME + " TEXT,"+
                    ScheduleContract.Schedule.COLUMN_NAME_DAY + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ScheduleContract.Schedule.TABLE_NAME;

    public ScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
