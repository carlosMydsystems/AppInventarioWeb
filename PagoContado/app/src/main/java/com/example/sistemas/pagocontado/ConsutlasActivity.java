package com.example.sistemas.pagocontado;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.pagocontado.entidades.PedidosConsulta;
import com.example.sistemas.pagocontado.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class ConsutlasActivity extends AppCompatActivity {

    Usuario usuario;
    String fecha,url;
    PedidosConsulta pedidosconsulta;
    ArrayList<PedidosConsulta> listaPedidosConsulta;
    ListView lvlistapedidosrealizados;
    ArrayList<String> listaPedidosMostrado;
    ImageButton ibRetornoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consutlas);

        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        fecha = getIntent().getStringExtra("fecha");
        lvlistapedidosrealizados = findViewById(R.id.lvListaPedidosRealizados);
        ibRetornoMain = findViewById(R.id.ibRetornoMain);
        ibRetornoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ConsutlasActivity.this,ConsultasListadoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });

        listaPedidosConsulta = new ArrayList<>();
        ListarPedidos(usuario.getUser().toString().trim(),fecha);
    }

    private void ListarPedidos(final String usuariost, String fechaingresada) {

        final ProgressDialog progressDialog = new ProgressDialog(ConsutlasActivity.this);
        progressDialog.setMessage("... Cargando");
        progressDialog.setCancelable(false);
        progressDialog.show();
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.'); // Se define el simbolo para el separador decimal
        simbolos.setGroupingSeparator(',');// Se define el simbolo para el separador de los miles
        final DecimalFormat formateador = new DecimalFormat("###,##0.00",simbolos); // Se crea el formato del numero con los simbolo

        listaPedidosMostrado = new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url = "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_WEB_HERRAMIENTAS.FN_WS_LISTAR_PEDIDOS&variables=%27"+usuariost+"|"+fechaingresada+"%27";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            if (success){

                                for(int i=0;i<jsonArray.length();i++) {

                                    pedidosconsulta = new PedidosConsulta();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    pedidosconsulta.setNroPedido(jsonObject.getString("NRO_PEDIDO"));
                                    pedidosconsulta.setCliente(jsonObject.getString("CLIENTE"));
                                    pedidosconsulta.setMoneda(jsonObject.getString("MONEDA"));
                                    pedidosconsulta.setTotalImporte(jsonObject.getString("TOTAL_IMPORTE"));
                                    pedidosconsulta.setItems(jsonObject.getString("ITEMS"));
                                    pedidosconsulta.setEstado(jsonObject.getString("ESTADO"));
                                    listaPedidosMostrado.add(pedidosconsulta.getNroPedido()+"  -  "+pedidosconsulta.getCliente()+"\n Items : "+
                                            pedidosconsulta.getItems() + "\t\t\t\t\t\tTotal Importe : S/ "+ pedidosconsulta.getTotalImporte());
                                    listaPedidosConsulta.add(pedidosconsulta);

                                }

                                progressDialog.dismiss();

                                ListadoAlmacenActivity.CustomListAdapter listAdapter = new ListadoAlmacenActivity.
                                        CustomListAdapter(ConsutlasActivity.this, R.layout.custom_list, listaPedidosMostrado);
                                lvlistapedidosrealizados.setAdapter(listAdapter);
                                lvlistapedidosrealizados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        String numeroPedido = listaPedidosConsulta.get(position).getNroPedido();
                                        Intent intent = new Intent(ConsutlasActivity.this,DetalleActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Usuario",usuario);
                                        intent.putExtras(bundle);
                                        intent.putExtra("NroPedido",numeroPedido);
                                        intent.putExtra("fecha",fecha);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(ConsutlasActivity.this);
                                builder.setCancelable(false)
                                        .setMessage("No se llego a encontrar el registro")
                                        .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(ConsutlasActivity.this,ConsultasListadoActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("Usuario",usuario);
                                                intent.putExtras(bundle);
                                                intent.putExtra("fecha",fecha);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
