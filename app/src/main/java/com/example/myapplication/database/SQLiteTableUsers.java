package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteTableUsers implements TableUsers{

    private final EligaNotesDB database;

    public SQLiteTableUsers(Context context) {
        this.database = EligaNotesDB.getInstance(context);
    }

    public long insertData(User user) {
        long id = 0;

        try {
            SQLiteDatabase db = database.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", user.getName());
            values.put("password", user.getPassword());

            id = db.insert(database.getUsersTable(), null, values);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    public List<User> getUsersNames() {
        return getUsers();
    }

    public List<User> getUsers() {
        SQLiteDatabase db = database.getWritableDatabase();
        ArrayList<User> usersList = new ArrayList<>();
        User user;
        Cursor cursorUser;

        cursorUser = db.rawQuery("SELECT * FROM "+ database.getUsersTable(), null);
        if (cursorUser.moveToFirst()){
            do{
                user = new User();
                user.setName(cursorUser.getString(0));
                user.setPassword(cursorUser.getString(1 ));
                usersList.add(user);
            }while (cursorUser.moveToNext());

        }
        cursorUser.close();
        return usersList;

    }

    public void clearData() {
        SQLiteDatabase db = database.getWritableDatabase();
        db.execSQL("DROP TABLE " + database.getUsersTable());
        db.execSQL("CREATE TABLE " + database.getUsersTable() + "(" +
                "name TEXT PRIMARY KEY," +
                "password TEXT NOT NULL)");
    }

    public boolean existsUser(User user) {
        SQLiteDatabase db = database.getWritableDatabase();
        String[] args = new String[] {user.getName()};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  database.getUsersTable()
                + " WHERE name = ?", args);
        return cursor.moveToFirst();
    }
}
