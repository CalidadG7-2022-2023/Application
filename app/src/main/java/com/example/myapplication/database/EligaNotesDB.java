package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EligaNotesDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "ELIGANOTES.db";

    private static final String USERS_TABLE = "Users";

    private static final String NOTES_TABLE = "Notes";

    private static EligaNotesDB database;

    public static EligaNotesDB getInstance(Context context) {
        if (EligaNotesDB.database == null)
            database = new EligaNotesDB(context);
        return database;
    }

    private EligaNotesDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.createUsersTable(sqLiteDatabase);
        this.createNotesTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + USERS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE " + NOTES_TABLE);
        onCreate(sqLiteDatabase);
    }

    private void createUsersTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + USERS_TABLE + "(" +
                "name TEXT PRIMARY KEY," +
                "password TEXT NOT NULL)");
    }

    private void createNotesTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + NOTES_TABLE + "(" +
                "title TEXT PRIMARY KEY," +
                "text TEXT NOT NULL," +
                "name TEXT NOT NULL)");
    }

    public String getUsersTable() {
        return USERS_TABLE;
    }

    public String getNotesTable() {
        return NOTES_TABLE;
    }
}
