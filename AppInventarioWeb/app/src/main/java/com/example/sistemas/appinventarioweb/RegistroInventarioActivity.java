package com.example.sistemas.appinventarioweb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.example.sistemas.appinventarioweb.Entities.Inventario;
import com.example.sistemas.appinventarioweb.Entities.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class RegistroInventarioActivity extends AppCompatActivity {

    TextView tvdetallearticulo,tv4,tv6,tvcontrol,tvmarca,tvunidades,textView8,textView3;
    Button btnbuscar,btngrabar;
    EditText etarticulo,etcantidad;
    String TRAMA,numeroArticulo,url,codigoArticulo;
    Usuario usuario;
    ArrayList<Inventario> listaProductosInventario;
    Boolean condicionante = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_inventario);

        listaProductosInventario = (ArrayList<Inventario>)getIntent().getSerializableExtra("listaProductosInventario");
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        etarticulo = findViewById(R.id.etArticulo);
        etcantidad = findViewById(R.id.etCantidad);
        tvdetallearticulo = findViewById(R.id.tvDetalle);
        btnbuscar = findViewById(R.id.btnBuscar);
        btngrabar = findViewById(R.id.btnGrabar);
        tv4 = findViewById(R.id.textView4);
        tv6 = findViewById(R.id.textView6);
        tvcontrol = findViewById(R.id.tvControl);
        tvmarca = findViewById(R.id.tvMarca);
        tvunidades = findViewById(R.id.tvUnidades);
        textView8 = findViewById(R.id.textView8);
        textView3 = findViewById(R.id.textView3);
        tv4.setVisibility(View.GONE);
        tv6.setVisibility(View.GONE);
        textView8.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        etcantidad.setVisibility(View.GONE);
        btngrabar.setVisibility(View.GONE);
        etarticulo.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnbuscar.setActivated(false);
                    if (etarticulo.getText().toString().equals("")) {
                        etarticulo.requestFocus();
                    }else {

                            etcantidad.requestFocus();
                            numeroArticulo = etarticulo.getText().toString();
                            TRAMA = "T07|LIMA|" + numeroArticulo + "||11111111||000|1|"+ usuario.getCodVendedor();  //Se genera la trama
                            tvdetallearticulo.setText("");
                            btnbuscar.setVisibility(View.GONE);
                            EnviarTrama(TRAMA);
                    }
                    return true;
                }
                return false;
            }
        });

        btngrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numerost = etcantidad.getText().toString();
                if (numerost.equals("")||numerost.equals("0")||numerost.equals("00")){
                    Toast.makeText(RegistroInventarioActivity.this, "Por favor ingrese una cantidad valida", Toast.LENGTH_SHORT).show();
                    etcantidad.setText("");
                }else{
                    String trama = tvcontrol.getText().toString();

                    etarticulo.requestFocus();
                    etcantidad.setVisibility(View.GONE);
                    textView3.setVisibility(View.GONE);
                    textView8.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                    tv6.setVisibility(View.GONE);
                    tvmarca.setVisibility(View.GONE);
                    tvunidades.setVisibility(View.GONE);
                    tvdetallearticulo.setVisibility(View.INVISIBLE);


                    final String trama1 = "CodUsuario="+usuario.getCodVendedor()+
                            "&CodProducto="+ etarticulo.getText().toString()+
                            "&Cantidad="+ etcantidad.getText().toString()+
                            "&Unidad="+ tvunidades.getText().toString()+
                            "&Marca="+ tvmarca.getText().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroInventarioActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Desea registrar el Producto : "+ tvdetallearticulo.getText()
                            .toString() + "\n" + "Cantidad : " + etcantidad.getText().toString() +" "
                            + tvunidades.getText().toString() );
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            btngrabar.setVisibility(View.GONE);
                        }
                    });
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActualizarArticuloWeb(trama1);
                        }
                    });
                    builder.create()
                            .show();


                }
            }
        });

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i<listaProductosInventario.size() ; i++){
                    if (listaProductosInventario.get(i).getCodigoProducto().equals(etarticulo.getText().toString().trim())){
                        condicionante =  true;
                    }
                }

                if (condicionante){

                    if (etarticulo.getText().toString().equals("")) {

                            Toast.makeText(RegistroInventarioActivity.this, "Por favor Ingrese una cantidad valida", Toast.LENGTH_SHORT).show();

                        }else{

                            btnbuscar.setActivated(false);
                            numeroArticulo = etarticulo.getText().toString();
                            TRAMA = "T07|LIMA|" + numeroArticulo + "||11111111||000|1|" + usuario.getCodVendedor();  //Se genera la trama
                            tvmarca.setText("");
                            tvdetallearticulo.setVisibility(View.VISIBLE);
                            btnbuscar.setVisibility(View.GONE);
                            tv6.setVisibility(View.GONE);
                            tv4.setVisibility(View.GONE);

                            condicionante = false;
                            EnviarTrama(TRAMA);
                        }
                }else{

                    Toast.makeText(RegistroInventarioActivity.this, "El código del árticulo ingresado no se puede inventariar", Toast.LENGTH_SHORT).show();
                    tvmarca.setText("");
                    tvdetallearticulo.setText("");
                    tvunidades.setText("");
                    etcantidad.setText("");
                    etarticulo.setText("");
                    etcantidad.setVisibility(View.GONE);
                    textView3.setVisibility(View.GONE);
                    textView8.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                    tv6.setVisibility(View.GONE);
                    tvmarca.setVisibility(View.GONE);
                    tvunidades.setVisibility(View.GONE);
                }
            }
        });
    }

    private void ActualizarArticulo(String trama) {

        btngrabar.setVisibility(View.GONE);


        // TODO se debe realizar la actualizacion del articulo

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionTestMovil.php?funcion=FN_INSERTA_ART&variables='"+trama+"'";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                            tv4.setVisibility(View.GONE);
                            tv6.setVisibility(View.GONE);
                            btngrabar.setVisibility(View.GONE);
                            etcantidad.setVisibility(View.GONE);
                            etarticulo.setText("");
                            etarticulo.requestFocus();
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

    public void EnviarTrama (String trama ){
        final ProgressDialog progressDialog = new ProgressDialog(RegistroInventarioActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_MOVIL_FUNCIONES.FN_CONSULTAR_PRODUCTO_WS_SP&variables='"+trama+"'";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        String Mensaje = "";
                            try {
                                progressDialog.dismiss();
                                btnbuscar.setVisibility(View.VISIBLE);
                                textView8.setVisibility(View.VISIBLE);
                                textView3.setVisibility(View.VISIBLE);
                                tvmarca.setVisibility(View.VISIBLE);
                                tvunidades.setVisibility(View.VISIBLE);
                                response1 = response1.trim();
                                    if(response1.equals("{\"success\":false}")){
                                        progressDialog.dismiss();
                                        etarticulo.setText("");
                                        btnbuscar.setVisibility(View.VISIBLE);
                                        etarticulo.requestFocus();
                                        textView8.setVisibility(View.VISIBLE);
                                        textView3.setVisibility(View.VISIBLE);
                                        tv4.setVisibility(View.VISIBLE);
                                        tv6.setVisibility(View.VISIBLE);
                                        tvmarca.setVisibility(View.VISIBLE);
                                    }
                                JSONObject jsonObject = new JSONObject(response1);
                                Boolean success = jsonObject.getBoolean("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                                Boolean condicion = false,error = false;
                                btnbuscar.setVisibility(View.VISIBLE);

                                if (success) {
                                    etcantidad.requestFocus();
                                    tv4.setVisibility(View.VISIBLE);
                                    tv6.setVisibility(View.VISIBLE);
                                    btngrabar.setVisibility(View.VISIBLE);
                                    etcantidad.setVisibility(View.VISIBLE);
                                    etcantidad.requestFocus();

                                    String Aux = response1.replace("{","|");
                                    Aux = Aux.replace("}","|");
                                    Aux = Aux.replace("[","|");
                                    Aux = Aux.replace("]","|");
                                    Aux = Aux.replace("\"","|");
                                    Aux = Aux.replace(","," ");
                                    Aux = Aux.replace("|","");
                                    Aux = Aux.replace(":"," ");
                                    String partes[] = Aux.split(" ");
                                    for (String palabras : partes){
                                        if (condicion){ Mensaje += palabras+" "; }
                                        if (palabras.equals("ERROR")){
                                            condicion = true;
                                            error = true;
                                        }
                                    }
                                    if (error) {
                                        tvdetallearticulo.setText("");
                                        tvmarca.setText("");
                                        etarticulo.setText("");
                                        tv4.setVisibility(View.GONE);
                                        tv6.setVisibility(View.GONE);
                                        btngrabar.setVisibility(View.GONE);
                                        etcantidad.setVisibility(View.GONE);
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(
                                                RegistroInventarioActivity.this);
                                        dialog.setMessage(Mensaje)
                                                .setNegativeButton("Regresar",null)
                                                .create()
                                                .show();
                                    }else {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            jsonObject = jsonArray.getJSONObject(i);
                                            codigoArticulo = jsonObject.getString("COD_ARTICULO");
                                            tvcontrol.setText(codigoArticulo);
                                            String descripcion = jsonObject.getString(
                                                    "COD_ARTICULO") +" - "+ jsonObject.getString("DESCRIPCION");
                                            tvdetallearticulo.setText(descripcion);
                                            tvmarca.setText(jsonObject.getString("MARCA"));
                                            tvunidades.setText(jsonObject.getString("UND_MEDIDA"));
                                            Double stock = Double.parseDouble(jsonObject.getString("STOCK"));
                                            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                                            simbolos.setDecimalSeparator('.'); // Se define el simbolo para el separador decimal
                                            simbolos.setGroupingSeparator(',');// Se define el simbolo para el separador de los miles
                                            DecimalFormat formateador = new DecimalFormat("###,###.00",simbolos); // Se crea el formato del numero con los simbolo
                                        }
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(
                                            RegistroInventarioActivity.this);
                                    tvunidades.setVisibility(View.GONE);
                                    tvmarca.setVisibility(View.GONE);
                                    textView8.setVisibility(View.GONE);
                                    textView3.setVisibility(View.GONE);
                                    builder.setMessage("No se encontró Articulo")
                                            .setNegativeButton("Regresar", null)
                                            .create()
                                            .show();
                                    etarticulo.setText("");
                                    etarticulo.requestFocus();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    private void ActualizarArticuloWeb(String trama) {

        final ProgressDialog progressDialog = new ProgressDialog(RegistroInventarioActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        btngrabar.setVisibility(View.GONE);
        btnbuscar.setVisibility(View.GONE);

        // TODO se debe realizar la actualizacion del articulo

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.mydsystems.com/pruebaDaniel/RegistrarInventario.php?"+trama;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                        progressDialog.dismiss();
                        btnbuscar.setVisibility(View.VISIBLE);
                        tv4.setVisibility(View.GONE);
                        tv6.setVisibility(View.GONE);
                        textView3.setVisibility(View.GONE);
                        textView8.setVisibility(View.GONE);
                        btngrabar.setVisibility(View.GONE);
                        etcantidad.setVisibility(View.GONE);
                        tvmarca.setVisibility(View.GONE);
                        tvunidades.setVisibility(View.GONE);
                        etarticulo.setText("");
                        tvunidades.setText("");
                        tvmarca.setText("");
                        etarticulo.requestFocus();
                        Toast.makeText(RegistroInventarioActivity.this, "Se Hizo el registro con Exito", Toast.LENGTH_SHORT).show();
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