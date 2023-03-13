package com.example.myapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

// Esto es para tener acceso a la vista custom de las notas con los iconos y eso, extiende el adapter del recycleview
public class NotesViewAdapter extends RecyclerView.Adapter<NotesViewAdapter.ViewHolder>{
    Context context;
    String[] nombreNotas;
    String[] descripcionNotas;
    int[] imagenes;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView rowName;
        TextView rowDescription;
        ImageView rowImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rowName = itemView.findViewById(R.id.textView1);
            rowDescription = itemView.findViewById(R.id.textView4);
            rowImage = itemView.findViewById(R.id.imageView);
        }

    }

    public NotesViewAdapter(Context context, String[] nombreNotas, String[] programDescriptionList, int[] imagenes){

        this.context = context;
        this.nombreNotas = nombreNotas;
        this.descripcionNotas = programDescriptionList;
        this.imagenes = imagenes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.listanotaindividual, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.rowName.setText(nombreNotas[position]);
        holder.rowDescription.setText(descripcionNotas[position]);
        holder.rowImage.setImageResource(imagenes[0]);

    }

    @Override
    public int getItemCount() {
        return nombreNotas.length;
    }
}
