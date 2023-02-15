package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelperNotes extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "NotesData.db";
    public static final String TABLE_DATA_NOTES = "NotesData";

    public DbHelperNotes(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //Formato tabla
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DATA_NOTES + "(" +
                "title TEXT PRIMARY KEY," +
                "text TEXT NOT NULL," +
                "nombre TEXT NOT NULL)");
    }
    //
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_DATA_NOTES); // Delete table
        onCreate(sqLiteDatabase); // Create table
    }
}
