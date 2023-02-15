package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DbUsers extends DbHelper{
    Context context;

    public DbUsers(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    //

    public long insertarDatos(String name, String password, String repassword){
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("password", password);
            values.put("repassword", repassword);

            id = db.insert(TABLE_DATA, null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    public ArrayList<User> mostrarNombreUsuarios(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<User> listaUsuarios = new ArrayList<>();
        User user;
        Cursor cursorUser;

        cursorUser = db.rawQuery("SELECT * FROM "+ TABLE_DATA, null);
        if (cursorUser.moveToFirst()){
            do{
                user = new User();
                user.setName(cursorUser.getString(0));
                listaUsuarios.add(user);
            }while (cursorUser.moveToNext());

        }
        cursorUser.close();
        return listaUsuarios;
    }

    public ArrayList<User> obtenerListaUser(){
        //creamos lsita ususarios
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //inicializamos variables
        ArrayList<User> listaUser = new ArrayList<>();
        User usuario = null;
        Cursor cursorUser = null;

        //consulta a la tabla de ussuario, nos devuelve un tipo cursor y lo pasamos a ArrayList
        cursorUser = db.rawQuery("SELECT * FROM " +  TABLE_DATA, null);

        //validación del cursor, después pasar al cursor el primer elemento o resultado de la consulta
        if(cursorUser.moveToFirst()){
            do{
                usuario = new User();
                usuario.setName(cursorUser.getString(0 ));
                usuario.setPassword(cursorUser.getString(1 ));
                listaUser.add(usuario);
            }while(cursorUser.moveToNext());
        }

        cursorUser.close();

        return listaUser;

    }


}
