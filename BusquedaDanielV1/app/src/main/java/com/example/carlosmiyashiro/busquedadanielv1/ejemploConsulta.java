package com.example.carlosmiyashiro.busquedadanielv1;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carlosmiyashiro.busquedadanielv1.Entidades.DetalleHojaRuta;
import com.example.carlosmiyashiro.busquedadanielv1.Request.BuscarRequest;
import com.example.carlosmiyashiro.busquedadanielv1.Request.EjemploRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ejemploConsulta extends AppCompatActivity {

    public DetalleHojaRuta detalleHojaRuta;
    ArrayList<DetalleHojaRuta> clientesarray;
    ArrayList<String> listaInformacion;
    ListView lvclientes;
    String periodo, cantidadhojaruta, concatena = "";
    ArrayList<DetalleHojaRuta>  listacliente;
    ArrayList<String> listaejemplo;
    Button btncancelar;
    EditText tvconcatena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo_consulta);
        listaejemplo = new ArrayList<>();
        lvclientes = findViewById(R.id.lvlistadoPrueba);

        buscar();
    }

    private void buscar() {


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success){

                        JSONArray jsonArray=jsonresponse.getJSONArray("hojaruta");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            periodo = jsonObject1.getString("PERIODO");
                            cantidadhojaruta = jsonObject1.getString("CANT_HRUTA");
                            listaejemplo.add(periodo+"     "+cantidadhojaruta);
                        }

                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,listaejemplo);

                        lvclientes.setAdapter(adapter);

                        Toast.makeText(ejemploConsulta.this, listaejemplo.get(0), Toast.LENGTH_SHORT).show();
                        //ObtenerLista();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                ejemploConsulta.this);
                        builder.setMessage("No se encontraron registros")
                                .setNegativeButton("Regresar",null)
                                .create()
                                .show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        EjemploRequest ejemploRequest = new EjemploRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(ejemploConsulta.this);
        queue.add(ejemploRequest);
    }

    private void ObtenerLista() {

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaejemplo);
        lvclientes.setAdapter(adaptador);
        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DetalleHojaRuta client = clientesarray.get(position);
                String prueba = clientesarray.get(position).getCliente();
                Toast.makeText(getApplicationContext(), prueba, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("DetalleHojaRuta",client);
                Intent intent = new Intent(ejemploConsulta.this,DetalleBusquedaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
