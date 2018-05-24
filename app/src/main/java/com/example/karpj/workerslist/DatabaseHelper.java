package com.example.karpj.workerslist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "workers.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "person"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRSTNAME = "firstName";
    public static final String COLUMN_LASTNAME = "lastName";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRSTNAME
                + " TEXT, " + COLUMN_LASTNAME + " TEXT, " + COLUMN_SEX + " INTEGER, " + COLUMN_DATE + " TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
