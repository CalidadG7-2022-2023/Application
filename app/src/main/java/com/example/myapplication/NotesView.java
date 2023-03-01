package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NotesView extends AppCompatActivity {

    private ArrayList<Note> listaNotas;

    private String[] listaNombres;

    private String[] listaDescripcion;

    RecyclerView.Adapter programAdapter;

    RecyclerView.LayoutManager layoutmanager;

    // De aqui se cogen las imagenes, están repetidas por si en algun momento
    // se quieren meter mas o algo. se pueden meter distintas

    int[] programImages = {R.drawable.notarecortardef,
                            R.drawable.notarecortardef,
                            R.drawable.notarecortardef,
                            R.drawable.notarecortardef,
                            R.drawable.notarecortardef,
                            R.drawable.notarecortardef};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView;
        setContentView(R.layout.activity_vernotas);
        setUpView();

        DbNotes dbnote = new DbNotes(this);
        listaNotas = dbnote.obtenerNotas();
        listaNombres = new String[listaNotas.size()];
        listaDescripcion = new String[listaNotas.size()];
        obtenerListaNombresDescripcion(listaNotas);

        recyclerView = findViewById(R.id.listanotas);

        recyclerView.setHasFixedSize(true);

        layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);
        programAdapter = new NotesViewAdapter(this, listaNombres,
                                                listaDescripcion, programImages);
        recyclerView.setAdapter(programAdapter);

    }

    private void setUpView(){
        Button exit;
        Button newNote;
        exit = findViewById(R.id.exit);
        newNote = findViewById(R.id.newNote);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotesView.this, MainActivity.class);
                startActivity(i);
            }
        });

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotesView.this, NotesMaker.class);
                startActivity(i);
            }
        });
    }

    private void obtenerListaNombresDescripcion(ArrayList<Note>listaNotas){
        for(int i = 0; i < listaNotas.size(); i++){
            listaNombres[i] = listaNotas.get(i).getTitle() ;
            listaDescripcion[i] = listaNotas.get(i).getText();

        }
    }

    public ArrayList<Note> getListaNotas() {
        return listaNotas;
    }
    
}
