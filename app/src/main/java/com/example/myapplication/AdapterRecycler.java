package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecycler extends RecyclerView.Adapter <NotasHolder> {
    private List<Note> listNotasAdapter =  new ArrayList<>();
    private Context c;

    public AdapterRecycler(ArrayList<Note> lista) throws IllegalAccessException, InstantiationException {
        this.listNotasAdapter = lista;
    }

    @NonNull
    @Override
    public NotasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_notes,parent,false);
        return new NotasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotasHolder holder, int position) {
        holder.getTitulo().setText(listNotasAdapter.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return listNotasAdapter.size();
    }

}
