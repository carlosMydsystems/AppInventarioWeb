package com.example.sistemas.appinventariolocaldb;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.appinventariolocaldb.Entities.Articulo;
import com.example.sistemas.appinventariolocaldb.Entities.Inventario;
import com.example.sistemas.appinventariolocaldb.Entities.Usuario;
import com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Usuario usuario;
    Button btntomainventario, btnconsultalectura, btnsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnconsultalectura = findViewById(R.id.btnconsultalectura);
        btntomainventario = findViewById(R.id.btnInventario);
        btnsalir = findViewById(R.id.btnSalirSistema);
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

        btnsalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        btnconsultalectura.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ConsultaLecturaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        btntomainventario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,RegistroDBActivity.class);
                Bundle bunble = new Bundle();
                bunble.putSerializable("Usuario",usuario);
                intent.putExtras(bunble);
                startActivity(intent);
                finish();
            }
        });
    }

}








