package com.example.sistemas.pagocontado;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.pagocontado.entidades.ClienteSucursal;
import com.example.sistemas.pagocontado.entidades.Clientes;
import com.example.sistemas.pagocontado.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MostrarClienteActivity extends AppCompatActivity {

    Button btnpedido,btnregresodetallecliente;
    Clientes cliente;
    TextView tvcodigo,tvNombre,tvDireccion,tvGiro,tvTipoCiente,tvDeuda,tvestado,
             tvUsuarioUltPedido,tvDireccionFiscalCliente;
    Usuario usuario;
    Spinner spopcionesdocumento, spsucursal;
    List<String> opSucursal;
    String url;
    ArrayList<ClienteSucursal> listaClienteSucursal;
    ClienteSucursal clienteSucursal;


    private final static String[] opcionesDoc = { "Boleta", "Factura" };
    private final static String[] opcionesSucursal = { "Pricipal", "Sucursal" };
    List<String> opdoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cliente);


        cliente  = new Clientes();
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");

        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        tvcodigo = findViewById(R.id.tvCofigoProducto);
        tvNombre = findViewById(R.id.tvNombreCliente);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvGiro = findViewById(R.id.tvNomProdElegido);
        tvTipoCiente = findViewById(R.id.tvAlmProdElegido);
        tvDeuda = findViewById(R.id.tvStockElegido);
        tvestado = findViewById(R.id.tvPrecioElegido);
        tvUsuarioUltPedido = findViewById(R.id.tvTotalElegido);
        btnregresodetallecliente = findViewById(R.id.btnRetornoDetCliente);
        spopcionesdocumento = findViewById(R.id.spTipoDocumento);
        spsucursal = findViewById(R.id.spSucursal);
        tvDireccionFiscalCliente = findViewById(R.id.tvDireccionFiscalCliente);

        tvcodigo.setText(cliente.getCodCliente());
        tvNombre.setText(cliente.getNombre());
        tvDireccion.setText(cliente.getDireccion());

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,opcionesDoc);
        opdoc = new ArrayList<String>();
        opdoc.add("FACTURA");
        opdoc.add("BOLETA");
        spopcionesdocumento.setAdapter(new SpinnerAdapter(this,opdoc));

        ListaSucursalesClientes(cliente.getCodCliente(),cliente);


        spopcionesdocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String muestra = (String)parent.getItemAtPosition(position);
                if (muestra.equals("FACTURA")){
                    cliente.setTipoDocumento("FAC");
                }else if ( muestra.equals("BOLETA")){
                    cliente.setTipoDocumento("BOL");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnpedido = findViewById(R.id.btnPedido);
        btnpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MostrarClienteActivity.this,ListadoAlmacenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("listaClienteSucursal",listaClienteSucursal);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });

        btnregresodetallecliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MostrarClienteActivity.this,BusquedaClienteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ListaSucursalesClientes(String codCliente,final Clientes cliente) {

        final ProgressDialog progressDialog = new ProgressDialog(MostrarClienteActivity.this);
        progressDialog.setMessage("... Cargando");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url = "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_WEB_HERRAMIENTAS.FN_WS_LISTAR_SUC_CLIENTE&variables=%27"+codCliente+"%27";

        listaClienteSucursal = new ArrayList<ClienteSucursal>();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            if (success){

                                opSucursal = new ArrayList<String>();

                                for(int i=0;i<jsonArray.length();i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    clienteSucursal = new ClienteSucursal();
                                    opSucursal.add(jsonObject.getString("NOMBRE"));
                                    clienteSucursal.setCodSucursal(jsonObject.getString("COD_SUCCLIE"));
                                    clienteSucursal.setDireccionSucursal(jsonObject.getString("DIRECCION"));
                                    clienteSucursal.setDepartamento(jsonObject.getString("DEPARTAMENTO"));
                                    clienteSucursal.setProvincia(jsonObject.getString("PROVINCIA"));
                                    clienteSucursal.setDistrito(jsonObject.getString("DISTRITO"));

                                    listaClienteSucursal.add(clienteSucursal);
                                }

                                progressDialog.dismiss();

                                tvDireccionFiscalCliente.setText(cliente.getDireccionSucursal());

                                 spsucursal.setAdapter(new SpinnerAdapter(getApplicationContext(),opSucursal));
                                tvDireccionFiscalCliente.setText(listaClienteSucursal.get(0).getDireccionSucursal());
                                listaClienteSucursal.get(0).setCodSucursal(listaClienteSucursal.get(0).getCodSucursal());
                                 spsucursal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                     @Override
                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                         tvDireccionFiscalCliente.setText(listaClienteSucursal.get(position).getDireccionSucursal());
                                         listaClienteSucursal.get(0).setCodSucursal(listaClienteSucursal.get(position).getCodSucursal());
                                     }

                                     @Override
                                     public void onNothingSelected(AdapterView<?> parent) {

                                     }
                                 });

                            }else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(MostrarClienteActivity.this);
                                builder.setCancelable(false)
                                        .setMessage("No se llego a encontrar el registro")
                                        .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(MostrarClienteActivity.this,MostrarClienteActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("Usuario",usuario);
                                                intent.putExtras(bundle);
                                                Bundle bundle1 = new Bundle();
                                                bundle1.putSerializable("Cliente",cliente);
                                                intent.putExtras(bundle1);
                                                //intent.putExtra("fecha",fecha);
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
