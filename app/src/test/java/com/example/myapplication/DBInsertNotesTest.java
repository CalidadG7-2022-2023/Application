package com.example.myapplication;

import org.junit.Test;
import org.mockito.Mockito;

import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.database.TableNotes;
import com.example.myapplication.model.Note;
import com.example.myapplication.view.NotesMaker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class DBInsertNotesTest {
    @Test
    public void insertNotesTest() {
        Note note = new Note();
        note.setTitle("Nota");
        note.setText("Test");

        TableNotes db = mock(SQLiteTableNotes.class);
        when(db.insertData(note, "user")).thenReturn(1L);
        assertNotEquals(db.insertData(note, "user"), 0L);
    }

    @Test
    public void insertEmptyNotesTest() {
        Note emptyNote = new Note();

        TableNotes db = mock(SQLiteTableNotes.class);
        when(db.insertData(emptyNote, "user")).thenReturn(0L);
        assertEquals(db.insertData(emptyNote, "user"), 0L);
    }
}