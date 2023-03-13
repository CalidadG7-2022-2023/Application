package com.example.myapplication.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class NotasHolder extends RecyclerView.ViewHolder{

    private TextView titulo;

    public NotasHolder(@NonNull View itemView) {
        super(itemView);
        titulo = itemView.findViewById(R.id.titulo);

    }

    public void setTitulo(TextView titulo) {
        this.titulo = titulo;
    }

    public TextView getTitulo() {
        return titulo;
    }
}
