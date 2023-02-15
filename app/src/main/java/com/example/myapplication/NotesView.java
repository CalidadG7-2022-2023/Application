package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class NotesView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Note> listaNotas;
    private String[] listaNombres;
    private String[] listaDescripcion;
    private DbNotes dbnote;

    private Button exit;
    private Button newNote;



    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;

    // Esto lo que hace es meter texto plano, lo he hecho para testear que funciona, la idea es que en vez de esto se muestren las notas de la bbdd

    String[] programNameList = {"Nota1", "Nota2", "Nota3", "Nota4", "Nota5", "Nota6"};
    String[] programDescriptionList = {"Nota1desc", "Nota2desc", "Nota3desc", "Nota4desc", "Nota5desc", "Nota6desc"};

    // De aqui se cogen las imagenes, están repetidas por si en algun momento se quieren meter mas o algo. se pueden meter distintas

    int[] programImages = {R.drawable.notarecortardef, R.drawable.notarecortardef,
            R.drawable.notarecortardef, R.drawable.notarecortardef, R.drawable.notarecortardef,
            R.drawable.notarecortardef};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        programAdapter = new NotesViewAdapter(this, listaNombres, listaDescripcion, programImages);
        recyclerView.setAdapter(programAdapter);

    }

    private void setUpView(){
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
