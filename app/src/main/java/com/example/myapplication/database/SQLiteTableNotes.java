package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.view.MainActivity;
import com.example.myapplication.model.Note;

import java.util.ArrayList;
import java.util.List;

public class SQLiteTableNotes implements TableNotes{

    private EligaNotesDB database;

    public SQLiteTableNotes(Context context) {
        this.database = EligaNotesDB.getInstance(context);
    }

    public long insertData(Note note, String name) {
        long id = 0;

        try {
            SQLiteDatabase dbn = database.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("title", note.getTitle());
            values.put("text", note.getText());
            values.put("name", name);

            id = dbn.insert(database.getNotesTable(), null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    public List<Note> obtainNotes(){
        SQLiteDatabase db = database.getWritableDatabase();

        ArrayList<Note> notesList = new ArrayList<>();
        Note note;
        Cursor cursorNote;

        cursorNote = db.rawQuery("SELECT * FROM " +  database.getNotesTable(), null);
        if(cursorNote.moveToFirst()){
            do{
                if(MainActivity.getUserName().getText().toString().equals(cursorNote.getString(2))) {
                    note = new Note();
                    note.setTitle(cursorNote.getString(0));
                    note.setText(cursorNote.getString(1));
                    notesList.add(note);
                }
            }while(cursorNote.moveToNext());
        }

        cursorNote.close();

        return notesList;
    }
}
