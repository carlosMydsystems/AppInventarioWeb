package com.example.sistemas.appinventarioweb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.sistemas.appinventarioweb.Entities.Articulo;
import com.example.sistemas.appinventarioweb.Entities.Inventario;
import com.example.sistemas.appinventarioweb.Entities.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class RegistroInventarioActivity extends AppCompatActivity {

    TextView tvdetallearticulo,tv4,tv6,tvcontrol,tvmarca,tvunidades,textView8,textView3,tvusuario,
            tvconteo,tvcodigo,tvcontador;
    Button btnbuscar,btngrabar;
    EditText etarticulo,etcantidad;
    String TRAMA,numeroArticulo,url,codigoArticulo;
    Usuario usuario;
    ArrayList<Inventario> listaProductosInventario;
    Boolean condicionante = false;
    ImageButton ibvolverregistromenu;
    Articulo articulo;
    String trama1,acumuladorStr;
    Integer acumulador=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_inventario);

        if (acumulador<=1){

            acumuladorStr = getIntent().getStringExtra("Acumulador");
            acumulador = Integer.valueOf(acumuladorStr);
        }

        listaProductosInventario = (ArrayList<Inventario>)getIntent().getSerializableExtra("listaProductosInventario");
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        etarticulo = findViewById(R.id.etArticulo);
        etcantidad = findViewById(R.id.etCantidad);
        etcantidad.setText("");
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
        tvusuario = findViewById(R.id.tvNombreUsuario3);
        tvcodigo = findViewById(R.id.tvCodigoBarra);
        ibvolverregistromenu = findViewById(R.id.ibVolverRegistroMenu);
        tvconteo = findViewById(R.id.tvConteo);
        tvcontador = findViewById(R.id.tvContador);
        tvusuario.setText("USUARIO : " + usuario.getNombre());
        tv4.setVisibility(View.GONE);
        tv6.setVisibility(View.GONE);
        textView8.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        etcantidad.setVisibility(View.GONE);
        btngrabar.setVisibility(View.GONE);
        tvconteo.setVisibility(View.GONE);
        tvcontador.setText("");
        ibvolverregistromenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistroInventarioActivity.this,MainActivity.class);
                intent.putExtra("Acumulador",""+acumulador);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });
        etarticulo.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnbuscar.setActivated(false);
                    if (etarticulo.getText().toString().equals("")) {
                        etarticulo.requestFocus();
                    }else {

                            etcantidad.requestFocus();
                            numeroArticulo = etarticulo.getText().toString();
                            TRAMA = etarticulo.getText().toString()+"|"+usuario.getCodAlmacen();
                            tvdetallearticulo.setVisibility(View.VISIBLE);  //Se genera la trama
                            tvdetallearticulo.setText("");
                            btnbuscar.setVisibility(View.GONE);
                            ConsultarArticulo(TRAMA);
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

                    etarticulo.requestFocus();
                    etcantidad.setVisibility(View.GONE);
                    textView3.setVisibility(View.GONE);
                    textView8.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                    tv6.setVisibility(View.GONE);
                    tvmarca.setVisibility(View.GONE);
                    tvunidades.setVisibility(View.GONE);
                    tvdetallearticulo.setVisibility(View.INVISIBLE);
                    String llave = usuario.getLlave().toString().replace(" ", "%20");
                    trama1 = llave.trim()+"|"+usuario.getCodAlmacen()+"|"+usuario.getConteo()+"|"
                            +articulo.getCodArticulo()+"|"+articulo.getUniMedidaOri()+"|"+articulo.getEquiOri()
                            +"|"+articulo.getUniMedida()+"|"+articulo.getCodBarra()+"|"+articulo.getEquivalencia()
                            +"|"+etcantidad.getText().toString()+"|"+articulo.getEquivalenciaNew()+"|"+usuario.getUser();

                    ActualizarArticuloWeb(trama1.trim());

                }
            }
        });

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etarticulo.getText().toString().equals("")) {

                } else {

                    etcantidad.setText("");
                    String trama = etarticulo.getText().toString()+"|"+usuario.getCodAlmacen();
                    tvdetallearticulo.setVisibility(View.VISIBLE);
                    ConsultarArticulo(trama);
                }
            }
        });
    }

    private void ConsultarArticulo(String tramaConsulta) {

        final ProgressDialog progressDialog = new ProgressDialog(RegistroInventarioActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_INV_TIENDA_MOVIL.FN_CONSULTA_PRODUCTO&variables=%27"+tramaConsulta+"%27";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        String Mensaje = "";
                        try {
                            progressDialog.dismiss();
                            btnbuscar.setVisibility(View.VISIBLE);
                            textView8.setVisibility(View.GONE);
                            textView3.setVisibility(View.GONE);
                            tvmarca.setVisibility(View.GONE);
                            tvunidades.setVisibility(View.GONE);
                            response1 = response1.trim();
                            if(response1.equals("{\"success\":false}")){
                                progressDialog.dismiss();
                                etarticulo.setText("");
                                btnbuscar.setVisibility(View.VISIBLE);
                                etarticulo.requestFocus();
                                textView8.setVisibility(View.GONE);
                                textView3.setVisibility(View.GONE);
                                tv4.setVisibility(View.VISIBLE);
                                tv6.setVisibility(View.VISIBLE);
                                tvmarca.setVisibility(View.GONE);
                            }
                            JSONObject jsonObject = new JSONObject(response1);
                            Boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            Boolean condicion = false,error = false;
                            btnbuscar.setVisibility(View.VISIBLE);

                            if (success) {
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

                                        articulo = new Articulo();
                                        jsonObject = jsonArray.getJSONObject(i);
                                        articulo.setCodArticulo(jsonObject.getString("COD_ARTICULO"));
                                        articulo.setArticulo(jsonObject.getString("ARTICULO"));
                                        articulo.setUniMedidaOri(jsonObject.getString("UND_MEDIDA_ORI"));
                                        articulo.setEquiOri(jsonObject.getString("EQUI_ORI"));
                                        articulo.setMarca(jsonObject.getString("MARCA"));
                                        articulo.setCodBarra(jsonObject.getString("COD_BARRA_LEC"));
                                        articulo.setUniMedida(jsonObject.getString("UND_MEDIDA_LEC"));
                                        articulo.setEquivalencia(jsonObject.getString("EQUI_LEC"));
                                        articulo.setEquivalenciaNew(jsonObject.getString("FACTOR"));
                                        articulo.setLlave(jsonObject.getString("LLAVE"));
                                        articulo.setConteo(jsonObject.getString("CONTEO"));
                                        codigoArticulo = jsonObject.getString("COD_ARTICULO");
                                        tvcontrol.setText(codigoArticulo);

                                        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                                        simbolos.setDecimalSeparator('.'); // Se define el simbolo para el separador decimal
                                        simbolos.setGroupingSeparator(',');// Se define el simbolo para el separador de los miles
                                        final DecimalFormat formateador = new DecimalFormat("###,##0.00",simbolos); // Se crea el formato del numero con los simbolo


                                        Double codEquivalencia = Double.valueOf(articulo.getEquivalenciaNew());

                                        formateador.format((double) codEquivalencia);
                                        tv4.setText(articulo.getUniMedida()+" - "+ codEquivalencia);
                                        tvconteo.setVisibility(View.VISIBLE);
                                        tvconteo.setText("CONTEO : " + usuario.getConteo());
                                        tvdetallearticulo.setText(articulo.getCodArticulo() + " - " + articulo.getArticulo());

                                        tvmarca.setText(jsonObject.getString("MARCA"));
                                        String Conteo = "Conteo : "+jsonObject.getString("CONTEO");
                                        String Codigo = jsonObject.getString("UND_MEDIDA") + " - " +jsonObject.getString("EQUIVALENCIA_NEW");
                                        tv4.setText(Codigo);
                                        //etarticulo.setText("");
                                        etcantidad.requestFocus();

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
                                btngrabar.setVisibility(View.GONE);
                                tvdetallearticulo.setText("");
                                builder.setMessage("No se encontró Articulo")
                                        .setNegativeButton("Regresar", null)
                                        .create()
                                        .show();
                                etarticulo.setText("");
                                etarticulo.requestFocus();
                            }
                        } catch (JSONException e) {
                            etarticulo.requestFocus();
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
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionTestMovil.php?funcion=PKG_INV_TIENDA_MOVIL.FN_GRABA_INV_LECTURA_MOVIL&variables='"+trama+"'";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroInventarioActivity.this);
                        response1 = response1.replace("*","");
                        builder.setMessage(response1);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar",null);
                        builder.create();
                        etarticulo.setText("");
                        etcantidad.setText("");
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
                        tvconteo.setText("");
                        etarticulo.requestFocus();
                        tvcontador.setText(""+acumulador);
                        acumulador++;
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