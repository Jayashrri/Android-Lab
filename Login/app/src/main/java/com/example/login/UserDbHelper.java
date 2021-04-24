package com.example.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Schedule.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.User.TABLE_NAME + " (" +
                    UserContract.User._ID + " INTEGER PRIMARY KEY," +
                    UserContract.User.COLUMN_NAME_NAME + " TEXT," +
                    UserContract.User.COLUMN_NAME_USERNAME + " TEXT,"+
                    UserContract.User.COLUMN_NAME_PASSWORD + " TEXT,"+
                    UserContract.User.COLUMN_NAME_EMAIL + " TEXT,"+
                    UserContract.User.COLUMN_NAME_PHONE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.User.TABLE_NAME;

    public UserDbHelper(Context context) {
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
