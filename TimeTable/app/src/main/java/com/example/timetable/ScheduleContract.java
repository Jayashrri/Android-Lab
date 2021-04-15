package com.example.timetable;

import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public final class ScheduleContract {

    private ScheduleContract() {}

    public static class Schedule implements BaseColumns {
        public static final String TABLE_NAME = "schedule";
        public static final String COLUMN_NAME_SUBJECT = "subject";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_DAY = "day";
    }
}
