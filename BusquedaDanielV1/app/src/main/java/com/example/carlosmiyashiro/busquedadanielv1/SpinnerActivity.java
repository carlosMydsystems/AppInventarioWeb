package com.example.carlosmiyashiro.busquedadanielv1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carlosmiyashiro.busquedadanielv1.Request.BuscarRequest;
import com.example.carlosmiyashiro.busquedadanielv1.Request.LoginActivityRequest;
import com.example.carlosmiyashiro.busquedadanielv1.Request.SpinnerActivityRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpinnerActivity extends AppCompatActivity {

    Spinner spCambioEstadoDocumento;
    Button btnAceptarcambioestadoDocumento;
    ArrayList<String> lista;
    ArrayAdapter ComboAdapter;
    String estadopedido,Id_cliente,Idlistadocumentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        spCambioEstadoDocumento = findViewById(R.id.spestadofactura);
        btnAceptarcambioestadoDocumento = findViewById(R.id.btnAcptarCambioEstado);
        Id_cliente = getIntent().getExtras().get("Id_Cliente").toString();
        Idlistadocumentos = getIntent().getExtras().get("Indice").toString();

        Toast.makeText(this, "este es le Id" + Idlistadocumentos, Toast.LENGTH_LONG).show();

        lista = new ArrayList<>();
        lista.add("Aceptación Total");
        lista.add("Rechazo Total");
        lista.add("Rechazo Parcial");

        ComboAdapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,lista);
        spCambioEstadoDocumento.setAdapter(ComboAdapter);
        spCambioEstadoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                estadopedido = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(SpinnerActivity.this, estadopedido, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAceptarcambioestadoDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActualizarEstadoDocumento(Idlistadocumentos,estadopedido);

            }
        });

    }

    private void ActualizarEstadoDocumento(String Idlistadocumentos,String Estado) {

        // Metodo de escucha espera recibir un response

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Recibe un objeto en Json
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success){

                        Intent intent = new Intent(SpinnerActivity.this,ListaDocumentosActivity.class);
                        intent.putExtra("estadoDocumento",estadopedido);
                        intent.putExtra("Id_Cliente",Id_cliente);
                        startActivity(intent);
                        finish();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(SpinnerActivity.this);
                        builder.setMessage("Usuario o contraseña incorrecta")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // hace un llamado al metodo LoginActivityRequest
        SpinnerActivityRequest spinnerActivityRequest = new SpinnerActivityRequest(Idlistadocumentos,Estado,responseListener);
        RequestQueue queue = Volley.newRequestQueue(SpinnerActivity.this);
        queue.add( spinnerActivityRequest);

    }
}
