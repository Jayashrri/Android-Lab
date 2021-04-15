package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Quiz.db";

    private static final String TABLE_QUESTION = "question";
    private static final String QUESTION_ID = "id";
    private static final String QUESTION_NAME = "question";
    private static final String QUESTION_CORRECT = "correct";

    private static final String TABLE_OPTION = "option";
    private static final String OPTION_ID = "id";
    private static final String OPTION_INDEX = "ind";
    private static final String OPTION_NAME = "option";
    private static final String OPTION_QUESTION = "question";

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUESTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + "("
                + QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + QUESTION_NAME + " TEXT,"
                + QUESTION_CORRECT + " INTEGER"
                + ")";

        String CREATE_OPTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_OPTION + "("
                + OPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + OPTION_NAME + " TEXT,"
                + OPTION_INDEX + " INTEGER,"
                + OPTION_QUESTION + " INTEGER"
                + ")";

        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_OPTION_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTION);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUESTION_NAME, question.getQuestion());
        values.put(QUESTION_CORRECT, question.getCorrect());

        long id = db.insert(TABLE_QUESTION, null, values);
        question.setId(id);

        for(Option option : question.getOptions()) {
            values = new ContentValues();
            values.put(OPTION_QUESTION, id);
            values.put(OPTION_NAME, option.getOption());
            values.put(OPTION_INDEX, option.getIndex());
            long optId = db.insert(TABLE_OPTION, null, values);
        }
    }

    public List<Option> getOptions(long id) {
        List<Option> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_OPTION,
                new String[] {
                        OPTION_INDEX,
                        OPTION_NAME,
                        OPTION_QUESTION
                },
                OPTION_QUESTION + "=?",
                new String[] {
                        String.valueOf(id)
                },
                null,
                null,
                OPTION_INDEX + " ASC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Option option = new Option(
                        cursor.getInt(0),
                        cursor.getInt(2),
                        cursor.getString(1)
                );
                list.add(option);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public List<Question> getQuestions() {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_QUESTION,
                new String[] {
                        QUESTION_ID,
                        QUESTION_NAME,
                        QUESTION_CORRECT
                },
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                List<Option> options = getOptions(id);
                Question question = new Question(
                        cursor.getLong(0),
                        cursor.getString(1),
                        options,
                        cursor.getInt(2)
                );
                list.add(question);
            } while (cursor.moveToNext());
        }

        return list;
    }
}
