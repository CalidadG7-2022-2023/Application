package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "UserData.db";
    public static final String TABLE_DATA = "t_data";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //Formato de creacion de la tabla
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DATA + "(" +
                "name TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "repassword TEXT NOT NULL)");
    }
//
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_DATA); // ELimina la tabla que tenemos
        onCreate(sqLiteDatabase); // Agrega de nuevo la tabla
    }
}
