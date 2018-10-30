package com.example.carlosmiyashiro.busquedadanielv1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carlosmiyashiro.busquedadanielv1.Entidades.DetalleHojaRuta;
import com.example.carlosmiyashiro.busquedadanielv1.Request.BuscarRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuscarActivity extends AppCompatActivity {

    public DetalleHojaRuta detalleHojaRuta;
    ArrayList<DetalleHojaRuta> clientesarray;
    ArrayList<String> listaInformacion;
    ListView lvclientes;
    String tipobusqueda, numeroHojaRuta;
    ArrayList<DetalleHojaRuta>  listacliente;
    Button btncancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        lvclientes = findViewById(R.id.listClientes);

        tipobusqueda = "";
        numeroHojaRuta = getIntent().getExtras().getString("numeroHojaRuta");
        btncancelar = findViewById(R.id.btnCancelar);
        Toast.makeText(getApplicationContext(), numeroHojaRuta, Toast.LENGTH_SHORT).show();

        listacliente = new ArrayList<>();
        buscar();
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscarActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void buscar() {

        clientesarray = new ArrayList<>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success){

                        JSONArray jsonArray=jsonresponse.getJSONArray("detalleHojaRuta");
                        for(int i=0;i<jsonArray.length();i++) {
                            detalleHojaRuta = new DetalleHojaRuta();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            detalleHojaRuta.setNumerohojaruta(jsonObject1.getString("numerohojaruta"));
                            detalleHojaRuta.setCliente(jsonObject1.getString("Cliente"));
                            detalleHojaRuta.setFactura(jsonObject1.getString("Factura"));
                            detalleHojaRuta.setDireccion(jsonObject1.getString("Direccion"));
                            detalleHojaRuta.setTelefono(jsonObject1.getString("Telefono"));
                            detalleHojaRuta.setBultos(jsonObject1.getString("Bultos"));
                            detalleHojaRuta.setEstado(jsonObject1.getString("Estado"));
                            detalleHojaRuta.setIddetallehojaruta(jsonObject1.getString("iddetallehojaruta"));

                            clientesarray.add(detalleHojaRuta);
                        }

                        ObtenerLista();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                BuscarActivity.this);
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

        BuscarRequest buscarRequest = new BuscarRequest(numeroHojaRuta, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BuscarActivity.this);
        queue.add(buscarRequest);
    }

    private void ObtenerLista() {
        listaInformacion = new ArrayList<>();

        for (int i=0; i< clientesarray.size();i++){

            listaInformacion.add(clientesarray.get(i).getNumerohojaruta() + " \n" + clientesarray.get(i).getCliente() );

        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaInformacion);
        lvclientes.setAdapter(adaptador);

        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DetalleHojaRuta client = clientesarray.get(position);
                String prueba = clientesarray.get(position).getCliente();
                Toast.makeText(getApplicationContext(), prueba, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("DetalleHojaRuta",client);
                Intent intent = new Intent(BuscarActivity.this,DetalleBusquedaActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });
    }
}
