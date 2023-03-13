package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.example.myapplication.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.SQLiteTableUsers;
import com.example.myapplication.database.TableUsers;
import com.example.myapplication.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class Registro extends AppCompatActivity implements Serializable {


    private List<User> listaNombresRegistrados;
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

    private void createDataBase(){
        EligaNotesDB database = EligaNotesDB.getInstance(Registro.this);
        SQLiteDatabase db = database.getWritableDatabase();
        if (db != null) {
            Toast.makeText(this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "ERROR AL CREAR LA BASE DE DATOS", Toast.LENGTH_LONG).show();
        }

    }
    public boolean checkUsers(Boolean disponible, String nameUser){
        for (int i = 0; i < listaNombresRegistrados.size(); i++) {
            String nombre = listaNombresRegistrados.get(i).getName();
            if (nombre.equals(nameUser)) {
                resultado2.setText("NOMBRE DE USUARIO NO DISPONIBLE");
                disponible = false;
                limpiar();
            } else {
                disponible = true;

            }
        }
        return disponible;
    }

    public void checkPassword(TableUsers dbUsers, String nameUser, String passwordUser, String rePasswordUser){
        if (!passwordUser.equals(rePasswordUser)) {
            Toast.makeText(Registro.this, "CONTRASEÑAS NO COINCIDEN", Toast.LENGTH_LONG).show();
            resultado3.setText("CONTRASEÑAS NO COINCIDEN");
            //break;
        } else {
            User user = new User();
            user.setName(nameUser);
            user.setPassword(passwordUser);
            long id = dbUsers.insertData(user);

            if (id > 0) {
                Toast.makeText(Registro.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                limpiar();
                finish();
            } else {
                Toast.makeText(Registro.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void checkRegister(TableUsers dbUsers,String nameUser, String passwordUser, String rePasswordUser){
        User user = new User();
        user.setName(nameUser);
        user.setPassword(passwordUser);
        long id = dbUsers.insertData(user);
            if (id > 0) {
                Toast.makeText(Registro.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                limpiar();
                finish();
            } else {
                Toast.makeText(Registro.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
            }
    }
    @SuppressLint("WrongViewCast")
    public void Register(View view) {

        if (!checkDataBase("/data/data/com.example.myapplication/databases/UserData.db")) {
            createDataBase();
        }

        TableUsers dbUsers = new SQLiteTableUsers(Registro.this);
        boolean disponible = false;
        String nameUser = user.getText().toString();
        String passwordUser = password.getText().toString();
        String rePasswordUser = confirmPassword.getText().toString();
        listaNombresRegistrados = dbUsers.getUsersNames();

        if (listaNombresRegistrados.size() != 0) {
            disponible = checkUsers(disponible,nameUser);
            if (disponible) {
                boolean b = !nameUser.isEmpty() && !passwordUser.isEmpty() && !rePasswordUser.isEmpty();
                if (b) {
                    checkPassword(dbUsers,nameUser,passwordUser,rePasswordUser);
                }
            }
        }
        if (listaNombresRegistrados.size() == 0) {
            checkRegister(dbUsers,nameUser, passwordUser, rePasswordUser);

        }
    }
        private void limpiar () {
            user.setText("");
            password.setText("");
            confirmPassword.setText("");

        }
}

