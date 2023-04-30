package com.example.myapplication.database;

import com.example.myapplication.model.Note;

import java.util.Collection;
import java.util.List;

public interface TableNotes {
    long insertData(Note note, String name);
    List<Note> obtainNotes();
    Note deleteNote(String title);
    Collection<Note> obtainAllNotes();
}
