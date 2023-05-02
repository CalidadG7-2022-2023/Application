package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.database.TableNotes;
import com.example.myapplication.model.Note;

import org.junit.Test;

import java.util.ArrayList;


public class DBObtainNotesTest {
    @Test
    public void obtainNotesTest() {
        Note note1 = new Note();
        note1.setTitle("Nota1");
        note1.setText("Test1");

        Note note2 = new Note();
        note1.setTitle("Nota2");
        note1.setText("Test2");

        Note note3 = new Note();
        note1.setTitle("Nota3");
        note1.setText("Test3");

        ArrayList<Note> notesList = new ArrayList<>();
        notesList.add(note1);
        notesList.add(note2);
        notesList.add(note2);

        TableNotes db = mock(SQLiteTableNotes.class);
        db.insertData(note1, "user");
        db.insertData(note2, "user");
        db.insertData(note3, "user");
        when(db.obtainNotes()).thenReturn(notesList);
        assertEquals(db.obtainNotes(), notesList);
        assertEquals(db.obtainNotes().size(), notesList.size());
    }

    @Test
    public void obtainEmptyNotesTest() {
        ArrayList<Note> notesList = new ArrayList<>();

        TableNotes db = mock(SQLiteTableNotes.class);
        when(db.obtainNotes()).thenReturn(notesList);
        assertEquals(db.obtainNotes(), notesList);
        assertEquals(db.obtainNotes().size(), 0);
    }
}