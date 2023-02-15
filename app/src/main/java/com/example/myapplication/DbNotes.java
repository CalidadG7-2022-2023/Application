package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DbNotes extends DbHelperNotes{
    Context context;

    public DbNotes(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    //

    public long insertData(String title, String text, String nombre){
        long id = 0;

        try {
            DbHelperNotes dbHelperNotes = new DbHelperNotes(context);
            SQLiteDatabase dbn = dbHelperNotes.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("text", text);
            values.put("nombre", nombre);

            id = dbn.insert(TABLE_DATA_NOTES, null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }
    public ArrayList<Note> obtenerNotas(){
        DbHelperNotes dbHelperNotes = new DbHelperNotes(context);
        SQLiteDatabase db = dbHelperNotes.getWritableDatabase();

        //se inicializan las distintas variables
        ArrayList<Note> listaNotas = new ArrayList<>();
        Note nota = null;
        Cursor cursorNota = null;

        //consulta a la tabla de notas, nos devuelve un tipo cursor y lo pasamos a ArrayList
        cursorNota = db.rawQuery("SELECT * FROM " +  TABLE_DATA_NOTES, null);
        //validación del cursor, después pasar al cursor el primer elemento o resultado de la consulta
        if(cursorNota.moveToFirst()){
            do{
                if(MainActivity.user.getText().toString().equals(cursorNota.getString(2))) {
                    nota = new Note();
                    nota.setTitle(cursorNota.getString(0));
                    nota.setText(cursorNota.getString(1));
                    listaNotas.add(nota);
                }
            }while(cursorNota.moveToNext());
        }

        cursorNota.close();

        return listaNotas;
    }
}