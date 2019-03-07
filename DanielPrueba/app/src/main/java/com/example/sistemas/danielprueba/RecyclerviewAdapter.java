package com.example.sistemas.danielprueba;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sistemas.danielprueba.Entidades.Productos;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.BandejaProductosHolder> implements  View.OnClickListener{

    List<Productos> listaProductos;

    private View.OnClickListener listener;

    public RecyclerviewAdapter(List<Productos> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public RecyclerviewAdapter.BandejaProductosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_bandeja_productos,
                viewGroup,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.
                LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        vista.setOnClickListener(this);

        return new BandejaProductosHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull bandejaproductosadapter.BandejaProductosHolder bandejaProductosHolder, int i) {


        String cadenaaux = listaProductos.get(i).getCodigo().toString()+" - "+ listaProductos.get(i).getNombre();
        bandejaProductosHolder.tvNombreProductoBandeja.setText(cadenaaux);
        bandejaProductosHolder.tvstockProductoBandeja.setText(listaProductos.get(i).getStock().toString());
        bandejaProductosHolder.tvcantidadProductoBandeja.setText(listaProductos.get(i).getCantidad());
        bandejaProductosHolder.tvPrecioProdustoBandeja.setText(listaProductos.get(i).getPrecio());
        bandejaProductosHolder.tvFleteProductoBandeja.setText(listaProductos.get(i).getFlete());
        Double subtotal = Double.valueOf(listaProductos.get(i).getCantidad()) * Double.valueOf(listaProductos.get(i).getPrecio());
        bandejaProductosHolder.tvSubtotalProductoBandeja.setText(subtotal.toString());

    }

    @Override
    public int getItemCount() {

        return listaProductos.size();
    }

    @Override
    public void onClick(View v) {

        if (listener != null){
            listener.onClick(v);
        }
    }

    public class BandejaProductosHolder extends RecyclerView.ViewHolder {

        TextView tvNombreProductoBandeja,tvcantidadProductoBandeja,tvstockProductoBandeja,
                tvPrecioProdustoBandeja,tvSubtotalProductoBandeja,tvFleteProductoBandeja;

        public BandejaProductosHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreProductoBandeja = itemView.findViewById(R.id.tvNombreProductoBandeja);
            tvcantidadProductoBandeja = itemView.findViewById(R.id.tvcantidadProductoBandeja);
            tvstockProductoBandeja = itemView.findViewById(R.id.tvstockProductoBandeja);
            tvPrecioProdustoBandeja = itemView.findViewById(R.id.tvPrecioProdustoBandeja);
            tvSubtotalProductoBandeja = itemView.findViewById(R.id.tvSubtotalProductoBandeja);
            tvFleteProductoBandeja = itemView.findViewById(R.id.tvFleteProductoBandeja);
        }
    }
}
