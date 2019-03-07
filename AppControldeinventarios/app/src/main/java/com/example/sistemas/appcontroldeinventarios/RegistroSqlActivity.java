package com.example.sistemas.appcontroldeinventarios;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sistemas.appcontroldeinventarios.Entity.Inventario;
import com.example.sistemas.appcontroldeinventarios.Entity.Producto;
import com.example.sistemas.appcontroldeinventarios.Entity.Usuario;
import com.example.sistemas.appcontroldeinventarios.Utility.ConexionSQLiteHelper;
import com.example.sistemas.appcontroldeinventarios.Utility.UtilitarioDB;

import java.util.ArrayList;

public class RegistroSqlActivity extends AppCompatActivity {

    ArrayList<Inventario> listaProductosInventario;
    Usuario usuario;
    Button btnDroptabla;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_sql);

        conn = new ConexionSQLiteHelper(this,"db_Inventario",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        listaProductosInventario = (ArrayList<Inventario>) getIntent().getSerializableExtra("listaProductosInventario");
        usuario = (Usuario) getIntent().getSerializableExtra("usuario");
        btnDroptabla =  findViewById(R.id.btnDropDataTable);
        InsertarProductos(listaProductosInventario);

        Toast.makeText(this, ""+ listaProductosInventario.get(0).getCodigoProducto(), Toast.LENGTH_SHORT).show();

        btnDroptabla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }

    private void InsertarProductos (ArrayList<Inventario> listaProductos){

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"db_Inventario",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        Producto producto = new Producto();

        for (int i = 0 ; i< listaProductos.size(); i ++) {

            ContentValues values = new ContentValues();
            values.put(UtilitarioDB.CAMPO_COD_PRODUCTO,listaProductos.get(i).getCodigoProducto());
            values.put(UtilitarioDB.CAMPO_CANTIDAD,listaProductos.get(i).getCantidad());
            values.put(UtilitarioDB.CAMPO_ID_USUARIO,listaProductos.get(i).getIdUsuario());

            Long idresultante = db.insert(UtilitarioDB.TABLA_INVENTARIO,UtilitarioDB.CAMPO_COD_PRODUCTO,values);

            Toast.makeText(this, "" + idresultante, Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

}
