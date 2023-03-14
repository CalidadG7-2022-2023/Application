package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.TableNotes;
import com.example.myapplication.model.Note;

public class NotesMaker extends AppCompatActivity {
    private Button bttGuardar;

    private EditText title;

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesmaker);
        setUpView();
    }

    private void setUpView() {
        title = findViewById(R.id.txtTitle);
        text = findViewById(R.id.txtText);
        bttGuardar = findViewById(R.id.bttGuardar);

        bttGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainNotesMaker(view);
            }
        });
    }

    private boolean checkDataBase(String Database_path) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(Database_path, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "NO EXISTE LA BASE DE DATOS", Toast.LENGTH_LONG).show();
        }
        return checkDB != null;
    }

    @SuppressLint("WrongViewCast")
    public void mainNotesMaker(View view) {

        if (!checkDataBase("/data/data/com.example.myapplication/databases/ELIGANOTES.db")) {
            EligaNotesDB database = EligaNotesDB.getInstance(NotesMaker.this);
            SQLiteDatabase dbn = database.getWritableDatabase();
            if (dbn != null) {
                Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
            }
        }

        TableNotes dbNotes = new SQLiteTableNotes(this);

        String titulo = title.getText().toString();
        String texto = text.getText().toString();
        String nombre = MainActivity.getUserName().getText().toString();

        Note note = new Note();
        note.setTitle(titulo);
        note.setText(texto);
        long id = dbNotes.insertData(note, nombre);

        if (id > 0) {
            Toast.makeText(NotesMaker.this, "NOTA GUARDADA", Toast.LENGTH_LONG).show();
            clean();
            finish();
        } else {
            Toast.makeText(NotesMaker.this, "ERROR AL GUARDAR NOTA", Toast.LENGTH_LONG).show();
        }

    }

    private void clean() {
        title.setText("");
        text.setText("");
    }
}

