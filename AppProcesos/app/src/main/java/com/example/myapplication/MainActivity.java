package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private EditText pass;
    static EditText user;
    private TextView resultado;
    private ArrayList<User> listUser;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargar acciones
        user = (EditText)findViewById(R.id.nomb_txt);
        pass = (EditText)findViewById(R.id.pass_txt);
        resultado = (TextView)findViewById(R.id.resultado);

    }

    public void Register(View view){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

    public void SetUpNotesMaker(View view){
        Intent i = new Intent(this, NotesMaker.class);
        startActivity(i);
    }

    public void Login(View view){
        //obtenermos la info de la base de datos
        DbUsers dbuser = new DbUsers(this);
        listUser = dbuser.obtenerListaUser();

        //obtenemos la info del user
        String SUser = user.getText().toString();
        String SPass = pass.getText().toString();

        //creamos objeto user para bpoder buscarlo en la lista
        User newUser = new User();
        newUser.setName(SUser);
        newUser.setPassword(SPass);

        //buscamos user en la lista
        boolean bol = false;
        if (!listUser.isEmpty()) {
            int num = 0;
            while (!(num >= listUser.size()) && !bol) {
                User thisUser = listUser.get(num);
                if ((thisUser.getName().equals(newUser.getName())) && (thisUser.getPassword().equals(newUser.getPassword())))
                    bol = true;

                num++;
            }
        }

        //qué hacer según el resultado
        if (!bol) {
            resultado.setText("Usuario no encontrado.");

        }
        else{
            //ir a cuenta
            resultado.setText("Usuario encontrado.");
            Intent i = new Intent(MainActivity.this, NotesView.class);
            startActivity(i);

        }
    }


}

