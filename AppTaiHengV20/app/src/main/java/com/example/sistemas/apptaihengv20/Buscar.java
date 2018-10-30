package com.example.sistemas.apptaihengv20;

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
import com.example.sistemas.apptaihengv20.Entidades.Cliente;
import com.example.sistemas.apptaihengv20.Request.BuscarRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Buscar extends AppCompatActivity {

    public Cliente cliente ;
    ArrayList<Cliente> clientesarray;
    ArrayList<String> listaInformacion;
    ListView lvclientes;
    String tipobusqueda,ruc;
    ArrayList<Cliente>  listacliente;
    Button btncancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        lvclientes = findViewById(R.id.listClientes);

        Intent intent = new Intent(Buscar.this,MainActivity.class);
        tipobusqueda = getIntent().getExtras().getString("tipobusqueda");
        ruc = getIntent().getExtras().getString("ruc");
        Toast.makeText(this, tipobusqueda, Toast.LENGTH_SHORT).show();
        btncancelar = findViewById(R.id.btnCancelar);
        Toast.makeText(getApplicationContext(), ruc, Toast.LENGTH_SHORT).show();

        listacliente = new ArrayList<Cliente>();
        buscar();
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Buscar.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void buscar() {

        clientesarray = new ArrayList<Cliente>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success){

                        JSONArray jsonArray=jsonresponse.getJSONArray("cliente");
                        for(int i=0;i<jsonArray.length();i++) {
                            cliente = new Cliente();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            cliente.setRuc(jsonObject1.getString("Ruc"));
                            cliente.setCanal(jsonObject1.getString("Canal"));
                            cliente.setCodigo(jsonObject1.getString("Codigo"));
                            cliente.setCliente(jsonObject1.getString("Cliente"));
                            cliente.setDireccion(jsonObject1.getString("Direccion"));
                            cliente.setProvincia(jsonObject1.getString("Provincia"));
                            cliente.setTienda(jsonObject1.getString("Tienda"));
                            cliente.setVendedor(jsonObject1.getString("Vendedor"));
                            cliente.setTelefono1(jsonObject1.getString("Telefono1"));
                            cliente.setTelefono2(jsonObject1.getString("Telefono2"));
                            cliente.setTelefono3(jsonObject1.getString("Telefono3"));
                            cliente.setCorreo(jsonObject1.getString("Correo"));
                            clientesarray.add(cliente);
                        }

                        ObtenerLista();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                Buscar.this);
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

        BuscarRequest buscarRequest = new BuscarRequest(ruc, tipobusqueda,responseListener);
        RequestQueue queue = Volley.newRequestQueue(Buscar.this);
        queue.add(buscarRequest);
    }

    private void ObtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i=0; i< clientesarray.size();i++){

            listaInformacion.add(clientesarray.get(i).getRuc() + " \n" + clientesarray.get(i).getCliente() );

        }
        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        lvclientes.setAdapter(adaptador);

        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Cliente client = clientesarray.get(position);

                String prueba = clientesarray.get(position).getCodigo();
                Toast.makeText(getApplicationContext(), prueba, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",client);
                Intent intent = new Intent(Buscar.this,MainActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });
    }
}
