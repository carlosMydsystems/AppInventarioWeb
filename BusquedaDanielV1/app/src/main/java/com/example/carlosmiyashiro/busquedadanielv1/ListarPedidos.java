package com.example.carlosmiyashiro.busquedadanielv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListarPedidos extends AppCompatActivity {

    ListView rvlistarpedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pedidos);

        rvlistarpedidos = findViewById(R.id.lvListarPedidos);


    }
}
