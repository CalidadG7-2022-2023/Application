package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.view.MainActivity;
import com.example.myapplication.model.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLiteTableNotes implements TableNotes{

    private final EligaNotesDB database;

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

    public Note deleteNote(String title) {
        SQLiteDatabase db = database.getWritableDatabase();
        String[] args = new String[] {title};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  database.getNotesTable()
                + " WHERE title = ?", args);
        cursor.moveToFirst();
        Note note = new Note(cursor.getString(0), cursor.getString(1));
        db.delete(database.getNotesTable(), "title=?", args);

        return note;
    }

    public Collection<Note> obtainAllNotes() {
        SQLiteDatabase db = database.getWritableDatabase();

        ArrayList<Note> notesList = new ArrayList<>();
        Note note;
        Cursor cursorNote;

        cursorNote = db.rawQuery("SELECT * FROM " +  database.getNotesTable(), null);
        if(cursorNote.moveToFirst()){
            do{
                note = new Note();
                note.setTitle(cursorNote.getString(0));
                note.setText(cursorNote.getString(1));
                notesList.add(note);
            }while(cursorNote.moveToNext());
        }

        cursorNote.close();

        return (Collection<Note>) notesList;
    }


    public void clearData() {
        SQLiteDatabase db = database.getWritableDatabase();
        db.execSQL("DROP TABLE " + database.getNotesTable());
        db.execSQL("CREATE TABLE " + database.getNotesTable() + "(" +
                "title TEXT PRIMARY KEY," +
                "text TEXT NOT NULL," +
                "name TEXT NOT NULL)");
    }

    public boolean existsNote(Note note) {
        SQLiteDatabase db = database.getWritableDatabase();
        String[] args = new String[] {note.getTitle()};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  database.getNotesTable()
                + " WHERE title = ?", args);
        return cursor.moveToFirst();
    }

    public Note obtainNoteById(String title) {
        SQLiteDatabase db = database.getWritableDatabase();
        String[] args = new String[] {title};
        Cursor cursor = db.rawQuery("SELECT * FROM " +  database.getNotesTable()
                + " WHERE title = ?", args);
        cursor.moveToFirst();
        return new Note(cursor.getString(0), cursor.getString(1));
    }
}
