package com.example.sistemas.pagocontado;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.pagocontado.entidades.Clientes;
import com.example.sistemas.pagocontado.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusquedaClienteActivity extends AppCompatActivity {

    RadioGroup rggrupocliente;
    RadioButton rbnombre,rbcodigo;
    Button btnbuscar;
    ArrayList<Clientes> listaClientes;
    Clientes cliente;
    ListView lvclientes;
    ArrayList<String> listaCliente;
    EditText etcliente;
    String url, tipoConsulta = "Nombre";
    ProgressDialog progressDialog;
    Usuario usuario;
    ImageButton ibregresomenuprincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cliente);

        listaClientes = new ArrayList<>();
        listaCliente  =new ArrayList<>();
        rggrupocliente = findViewById(R.id.rgBuscar);
        rbnombre = findViewById(R.id.rbNombre);
        rbcodigo = findViewById(R.id.rbCodigo);
        btnbuscar = findViewById(R.id.btnBuscar);
        lvclientes = findViewById(R.id.lvCliente);
        etcliente = findViewById(R.id.etCliente);

        if (cliente == null){

        }else {

            cliente = (Clientes)getIntent().getSerializableExtra("Cliente");

        }

        ibregresomenuprincipal = findViewById(R.id.ibRetornoMenuPrincipal);

        ibregresomenuprincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(BusquedaClienteActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();

            }
        });

        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");  //Se pasa el parametro del usuario

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etcliente.getText().toString().equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Por favor ingrese un valor valido")
                            .setNegativeButton("Aceptar",null)
                            .create()
                            .show();
                }else {
                    progressDialog = new ProgressDialog(BusquedaClienteActivity.this);
                    progressDialog.setMessage("Cargando...");
                    progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    btnbuscar.setVisibility(View.GONE);
                    buscarCliente(etcliente.getText().toString(),tipoConsulta);
                }
            }
        });

        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cliente = new Clientes();
                cliente =  listaClientes.get(position);
                Intent intent =  new Intent(BusquedaClienteActivity.this,MostrarClienteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();
            }
        });

        rggrupocliente.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                etcliente.setText("");
                switch (rggrupocliente.getCheckedRadioButtonId()){

                    case R.id.rbNombre:
                        etcliente.setInputType(2);
                        etcliente.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
                        tipoConsulta = "Nombre";
                        break;
                    case R.id.rbCodigo:
                        etcliente.setInputType(2);
                        etcliente.setFilters(new InputFilter[] {new InputFilter.LengthFilter(11)});
                        tipoConsulta = "Codigo";
                        break;
                }
            }
        });
    }

    private void buscarCliente(String numero, String tipoConsulta) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        // la Url del servicio Web // Se hace la validacion del tipo de consulta

        if (tipoConsulta == "Nombre"){

            url = "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=PKG_WEB_HERRAMIENTAS.FN_WS_CONSULTAR_CLIENTE&variables='"+numero+"||'";
        }else {

            url = "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=PKG_WEB_HERRAMIENTAS.FN_WS_CONSULTAR_CLIENTE&variables='||"+numero+"'";
        }

        listaCliente = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            btnbuscar.setVisibility(View.VISIBLE);
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            if (success){
                                for(int i=0;i<jsonArray.length();i++) {
                                    cliente = new Clientes();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    cliente.setCodCliente(jsonObject.getString("COD_CLIENTE"));
                                    cliente.setNombre(jsonObject.getString("CLIENTE"));
                                    cliente.setDireccion(jsonObject.getString("DIRECCION"));
                                    cliente.setCodFPago(jsonObject.getString("COD_FPAGO_LIMITE"));
                                    cliente.setFormaPago(jsonObject.getString("FORMA_PAGO"));

                                    listaClientes.add(cliente);
                                    listaCliente.add(cliente.getCodCliente() + " - " + cliente.getNombre());
                                }

                                ListadoAlmacenActivity.CustomListAdapter listAdapter= new ListadoAlmacenActivity.
                                        CustomListAdapter(BusquedaClienteActivity.this , R.layout.custom_list , listaCliente);
                                lvclientes.setAdapter(listAdapter);

                            }else {
                                listaCliente.clear();
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext()
                                        , R.layout.support_simple_spinner_dropdown_item,listaCliente);

                                lvclientes.setAdapter(adapter);
                                AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaClienteActivity.this);
                                builder.setCancelable(false);
                                builder.setMessage("No se llego a encontrar el registro")
                                        .setNegativeButton("Aceptar",null)
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
