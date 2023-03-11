package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class Registro extends AppCompatActivity implements Serializable {


    private ArrayList<User> listaNombresRegistrados;
    private Button bttConfirm;
    private FloatingActionButton bttBack;

    private EditText user;
    private EditText password;
    private EditText confirmPassword;
    private TextView resultado2;
    private TextView resultado3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setUpView();
    }

    private void setUpView() {
        user = findViewById(R.id.txtUser);
        password = findViewById(R.id.txtPassword);
        confirmPassword = findViewById(R.id.txtConfirmPassword);
        bttConfirm = findViewById(R.id.bttconfirm);
        bttBack = findViewById(R.id.bttback);
        resultado2 = findViewById(R.id.resultado2);
        resultado3 = findViewById(R.id.registro3);

        bttConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register(view);
            }
        });

        bttBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registro.this, MainActivity.class);
                startActivity(i);
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
    public void Register(View view) {

        if (!checkDataBase("/data/data/com.example.myapplication/databases/UserData.db")) {
            DbHelper dbHelper = new DbHelper(Registro.this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if (db != null) {
                Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
            }
        }


        DbUsers dbUsers = new DbUsers(Registro.this);
//
        boolean disponible = false;
        String nameUser = user.getText().toString();
        String passwordUser = password.getText().toString();
        String rePasswordUser = confirmPassword.getText().toString();
        listaNombresRegistrados = dbUsers.mostrarNombreUsuarios();

        if (listaNombresRegistrados.size() != 0) {
            for (int i = 0; i < listaNombresRegistrados.size(); i++) {
                String nombre = listaNombresRegistrados.get(i).getName();
                if (nombre.equals(nameUser)) {
//
                    resultado2.setText("NOMBRE DE USUARIO NO DISPONIBLE");
                    disponible = false;
                    limpiar();
                } else {
//
                    disponible = true;

                }
            }
            if (disponible) {
                boolean b = !nameUser.isEmpty() && !passwordUser.isEmpty() && !rePasswordUser.isEmpty();
                if (b) {
                    if (!passwordUser.equals(rePasswordUser)) {
                        Toast.makeText(Registro.this, "CONTRASEÑAS NO COINCIDEN", Toast.LENGTH_LONG).show();
                        resultado3.setText("CONTRASEÑAS NO COINCIDEN");
                        //break;
                    } else {
                        long id = dbUsers.insertarDatos(nameUser, passwordUser, rePasswordUser);

                        if (id > 0) {
                            Toast.makeText(Registro.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                            limpiar();
                            finish();
                        } else {
                            Toast.makeText(Registro.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        }
        if (listaNombresRegistrados.size() == 0) {
            long id = dbUsers.insertarDatos(nameUser, passwordUser, rePasswordUser);
            if (id > 0) {
                Toast.makeText(Registro.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                limpiar();
                finish();
            } else {
                Toast.makeText(Registro.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
            }
        }
    }
        private void limpiar () {
            user.setText("");
            password.setText("");
            confirmPassword.setText("");

        }
}

