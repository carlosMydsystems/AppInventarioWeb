package com.example.carlosmiyashiro.busquedadanielv1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carlosmiyashiro.busquedadanielv1.Entidades.DetalleHojaRuta;
import com.example.carlosmiyashiro.busquedadanielv1.Entidades.ListarDocumentos;
import com.example.carlosmiyashiro.busquedadanielv1.Request.BuscarRequest;
import com.example.carlosmiyashiro.busquedadanielv1.Request.ListtaDocumentosRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaDocumentosActivity extends AppCompatActivity {

    Button btnCerrar;
    ArrayList<ListarDocumentos>  ListarDocumentosArray;
    ListarDocumentos listadocumento;
    ArrayList<String> listaInformacion, listaEstadoDocumento;
    ListView lvdocumentos;
    String Id_cliente;
    Spinner spestado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_documentos);

        lvdocumentos = findViewById(R.id.lvDocumentos);
        listaEstadoDocumento = new ArrayList<>();

        Id_cliente = getIntent().getExtras().getString("Id_Cliente");

        Toast.makeText(this, Id_cliente.toString(), Toast.LENGTH_SHORT).show();

        buscar();

        btnCerrar = findViewById(R.id.btnCerrarListaDocumentos);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(ListaDocumentosActivity.this,MenuPrincipal.class);
        startActivity(intent);
        finish();
    }
});

    }

    private void buscar() {

        ListarDocumentosArray = new ArrayList<>();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success){


                        JSONArray jsonArray=jsonresponse.getJSONArray("listadocumento");
                        for(int i=0;i<jsonArray.length();i++) {
                            listadocumento = new ListarDocumentos();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            listadocumento.setClienteId(jsonObject1.getString("Cliente_Id"));
                            listadocumento.setTipoDocumento(jsonObject1.getString("TipoDocumento"));
                            listadocumento.setNumeroDocumento(jsonObject1.getString("NumeroDocumento"));
                            listadocumento.setFecha_Emision(jsonObject1.getString("Fecha_Emision"));
                            listadocumento.setMonto(jsonObject1.getString("Monto"));
                            listadocumento.setEstado_Documento(jsonObject1.getString("Estado_Documento"));
                            listadocumento.setId_ListaDocumento(jsonObject1.getString("id_ListaDocumento"));
                            ListarDocumentosArray.add(listadocumento);
                        }

                        ObtenerLista();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                ListaDocumentosActivity.this);
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

        ListtaDocumentosRequest listtaDocumentosRequest = new ListtaDocumentosRequest(Id_cliente, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ListaDocumentosActivity.this);
        queue.add(listtaDocumentosRequest);
    }

    private void ObtenerLista() {
        listaInformacion = new ArrayList<>();

        for (int i=0; i< ListarDocumentosArray.size();i++){

            listaInformacion.add(ListarDocumentosArray.get(i).getTipoDocumento() + "   :   "+ ListarDocumentosArray.get(i).getNumeroDocumento() + " \n" + ListarDocumentosArray.get(i).getFecha_Emision() +
                    "       S/ " + ListarDocumentosArray.get(i).getMonto() + " \n" + ListarDocumentosArray.get(i).getEstado_Documento());

        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaInformacion);
        lvdocumentos.setAdapter(adaptador);

        lvdocumentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(ListaDocumentosActivity.this);
                        View mview = getLayoutInflater().inflate(R.layout.spinner_layout,null);
                        mbuilder.setTitle("Spinner customizado");
                        Spinner mspinner = mview.findViewById(R.id.spinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListaDocumentosActivity.this,android.R.layout.simple_spinner_item,
                                getResources().getStringArray(R.array.Opciones));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mspinner.setAdapter(adapter);
                */

                Intent intent = new Intent(ListaDocumentosActivity.this,SpinnerActivity.class);
                intent.putExtra("Indice",ListarDocumentosArray.get(position).getId_ListaDocumento());
                intent.putExtra("Id_Cliente",Id_cliente);
                startActivity(intent);
                finish();

            }
        });
    }


}
