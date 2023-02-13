package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.os.Bundle;
import android.widget.Toast;

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
                MainNotesMaker(view);
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
    public void MainNotesMaker(View view) {

        if (!checkDataBase("/data/data/com.example.myapplication/databases/NotesData.db")) {
            DbHelperNotes dbHelperNotes = new DbHelperNotes(NotesMaker.this);
            SQLiteDatabase dbn = dbHelperNotes.getWritableDatabase();
            if (dbn != null) {
                Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
            }
        }

        DbNotes dbNotes = new DbNotes(NotesMaker.this);

        String titulo = title.getText().toString();
        String texto = text.getText().toString();
        String nombre = MainActivity.user.getText().toString();

        long id = dbNotes.insertData(titulo, texto, nombre);

        if (id > 0) {
            Toast.makeText(NotesMaker.this, "NOTA GUARDADA", Toast.LENGTH_LONG).show();
            clean();
           /* try {
                wait(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
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

