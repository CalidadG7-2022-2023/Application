package com.example.myapplication.database;

import com.example.myapplication.model.Note;

import java.util.List;

public interface TableNotes {
    long insertData(Note note, String name);
    List<Note> obtainNotes();
}
