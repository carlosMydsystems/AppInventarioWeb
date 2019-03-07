package com.example.sistemas.sqliteinventarios.Adapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ListaPersonasAdapter extends RecyclerView.Adapter<ListaPersonasAdapter.PersonasViewHolder>{
    @NonNull
    @Override
    public PersonasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonasViewHolder personasViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PersonasViewHolder extends RecyclerView.ViewHolder {
        public PersonasViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
