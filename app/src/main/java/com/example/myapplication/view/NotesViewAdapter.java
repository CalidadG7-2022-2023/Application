package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.EligaNotesDB;
import com.example.myapplication.database.SQLiteTableNotes;
import com.example.myapplication.model.Note;

import java.util.List;

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
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rowName = itemView.findViewById(R.id.textView1);
            rowDescription = itemView.findViewById(R.id.textView4);
            rowImage = itemView.findViewById(R.id.imageView);
            deleteButton = itemView.findViewById(R.id.button3);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.rowName.setText(nombreNotas[position]);
        holder.rowDescription.setText(descripcionNotas[position]);
        holder.rowImage.setImageResource(imagenes[0]);

        SQLiteTableNotes db = new SQLiteTableNotes(holder.rowImage.getContext());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(nombreNotas[position]);
                List<Note> listaNotas = db.obtainNotes();
                nombreNotas = new String[listaNotas.size()];
                descripcionNotas = new String[listaNotas.size()];
                for(int i = 0; i < listaNotas.size(); i++){
                    nombreNotas[i] = listaNotas.get(i).getTitle() ;
                    descripcionNotas[i] = listaNotas.get(i).getText();

                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return nombreNotas.length;
    }
}
