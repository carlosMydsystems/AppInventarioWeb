package com.example.sistemas.sqliteinventarios;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.sqliteinventarios.Entities.Inventario;
import com.example.sistemas.sqliteinventarios.Utilities.Utilidad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConsultarUsuariosActivity extends AppCompatActivity {

    EditText etarticulo,etcantidad;
    TextView tvmarca,tvdetalle,tvunidad;
    Button btnbuscar,btngrabar;

    ArrayList<Inventario> listaProductosInventario;
    Inventario inventario;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_usuarios);


        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_inventarios",null,1);

        etarticulo = findViewById(R.id.etArticulo);
        etcantidad = findViewById(R.id.etCantidad);

        tvdetalle = findViewById(R.id.tvDetalle);
        tvmarca = findViewById(R.id.tvMarca);
        tvunidad = findViewById(R.id.tvUnidades);
        btnbuscar = findViewById(R.id.btnBuscar);
        btngrabar = findViewById(R.id.btnGrabar);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar();
            }
        });

        btngrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void eliminarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={etarticulo.getText().toString()};

        db.delete(Utilidad.TABLA_USUARIO,Utilidad.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó el usuario",Toast.LENGTH_LONG).show();
        etarticulo.setText("");
        limpiar();
        db.close();
    }
/*
    private void actualizarUsuario() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={etarticulo.getText().toString()};
        ContentValues values=new ContentValues();
        values.put(Utilidad.CAMPO_NOMBRE,tv.getText().toString());
        values.put(Utilidad.CAMPO_TELEFONO,campoTelefono.getText().toString());

        db.update(Utilidad.TABLA_USUARIO,values,Utilidad.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizó el usuario",Toast.LENGTH_LONG).show();
        db.close();
    }

    private void consultarSql() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoId.getText().toString()};

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor=db.rawQuery("SELECT "+Utilidad.CAMPO_CODIGO_PRODUCTO+","+Utilidad.CAMPO_DESCRIPCION_PRODUCTO+
                    " FROM "+Utilidad.TABLA_INVENTARIO+" WHERE "+Utilidad.CAMPO_ID_INVENTARIO+"=? ",parametros);

            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoTelefono.setText(cursor.getString(1));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }
    }
    */

    private void consultar() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={etarticulo.getText().toString()};
        String[] campos={Utilidad.CAMPO_UNIDAD_PRODUCTO,Utilidad.CAMPO_DESCRIPCION_PRODUCTO,Utilidad.CAMPO_MARCA_PRODUCTO};

        try {
            Cursor cursor =db.query(Utilidad.TABLA_INVENTARIO,campos,Utilidad.CAMPO_CODIGO_PRODUCTO+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            tvunidad.setText(cursor.getString(0));
            tvdetalle.setText(cursor.getString(1));
            tvmarca.setText(cursor.getString(2));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }
    }

    private void limpiar() {

        //campoNombre.setText("");
        //campoTelefono.setText("");

    }
    private void grabar(){



    }
}
