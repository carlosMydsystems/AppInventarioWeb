package com.example.sistemas.appinventarioweb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.appinventarioweb.Adaptadores.CustomAdapter;
import com.example.sistemas.appinventarioweb.Entities.Articulo;
import com.example.sistemas.appinventarioweb.Entities.RegistroInventario;
import com.example.sistemas.appinventarioweb.Entities.Usuario;
import com.example.sistemas.appinventarioweb.Entities.listaConsulta;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ConsultaActivity extends AppCompatActivity {

    TextView tvnombreusuario2, tvdetalleconsulta;
    Button btnbuscarconsulta,btnprueba;
    ListView lvinventario;
    Usuario usuario ;
    String url,acumulador;
    EditText etartcunsul;
    View mview;
    ListView listView;
    ArrayList<String> listaMostrar,listadoString;
    ImageButton ibvolverconsultamenu;
    ArrayList<RegistroInventario> listainventario;
    CustomAdapter adapter;
    Articulo articulo;
    ArrayList<Articulo> listaarticulos;
    listaConsulta listaconsulta;
    ArrayList<listaConsulta> listado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        tvnombreusuario2 = findViewById(R.id.tvUsuarioConsulta);
        tvdetalleconsulta = findViewById(R.id.tvDetalleConsulta);
        btnbuscarconsulta =  findViewById(R.id.btnBuscarConsulta);
        ibvolverconsultamenu = findViewById(R.id.ibVolverConsultaMenu);
        btnprueba = findViewById(R.id.btnprueba);
        etartcunsul = findViewById(R.id.etartconsulta);
        lvinventario = findViewById(R.id.lvInventario);

        acumulador = getIntent().getStringExtra("Acumulador");


        btnprueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lvinventario.setVisibility(View.VISIBLE);
                String trama2 = etartcunsul.getText().toString()+"|"+usuario.getCodAlmacen();
                ConsultarArticulo(trama2);

            }
        });


        etartcunsul.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnbuscarconsulta.setActivated(false);
                    if (etartcunsul.getText().toString().equals("")) {
                        etartcunsul.requestFocus();
                    }else {

                        lvinventario.setVisibility(View.VISIBLE);
                        String trama2 = etartcunsul.getText().toString()+"|"+usuario.getCodAlmacen();
                        ConsultarArticulo(trama2);

                    }
                    return true;
                }
                return false;
            }
        });

        listainventario = (ArrayList<RegistroInventario>) getIntent().getSerializableExtra("listainventario");

        String valorEt = getIntent().getStringExtra("etarticulo");
        if (valorEt == null){

        }else {

            etartcunsul.setText(valorEt);
        }

        mview = getLayoutInflater().inflate(R.layout.listview_dialog, null);

        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");

        tvnombreusuario2.setText("USUARIO : " + usuario.getNombre());

        btnbuscarconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etartcunsul.getText().toString().equals("")) {

                }else {

                    lvinventario.setVisibility(View.VISIBLE);
                    String trama2 = etartcunsul.getText().toString()+"|"+usuario.getCodAlmacen();
                    ConsultarArticulo(trama2);
                }
            }
        });

        ibvolverconsultamenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ConsultaActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }


    private void ConsultarArticulo(String TramaConsulta) {

        final ProgressDialog progressDialog = new ProgressDialog(ConsultaActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        listaMostrar = new ArrayList<>();
        listaarticulos = new ArrayList<>();
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_INV_TIENDA_MOVIL.FN_CONSULTA_PRODUCTO&variables=%27"+TramaConsulta+"%27";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        String Mensaje = "";
                        try {
                            btnbuscarconsulta.setVisibility(View.VISIBLE);
                            response1 = response1.trim();

                            if(response1.equals("{\"success\":false}")){

                                progressDialog.dismiss();
                                etartcunsul.setText("");
                                btnbuscarconsulta.setVisibility(View.VISIBLE);
                                etartcunsul.requestFocus();

                            }
                            JSONObject jsonObject = new JSONObject(response1);
                            Boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            Boolean condicion = false,error = false;
                            btnbuscarconsulta.setVisibility(View.VISIBLE);


                            if (success) {

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
                                    etartcunsul.setText("");
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(
                                            ConsultaActivity.this);
                                    dialog.setMessage(Mensaje)
                                            .setNegativeButton("Regresar",null)
                                            .create()
                                            .show();
                                }else {
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        articulo = new Articulo();
                                        jsonObject = jsonArray.getJSONObject(i);

                                        if (jsonObject.getString("COD_BARRA_LEC").equals(etartcunsul.getText().toString().trim())){

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
                                        listaarticulos.add(articulo);

                                        }
                                    }
                                    tvdetalleconsulta.setText(articulo.getArticulo().toString());

                                    progressDialog.dismiss();
                                    String trama3 = articulo.getLlave()+"|"+usuario.getCodAlmacen()+"|"+usuario.getConteo()+"|"+articulo.getCodArticulo()+"|"+usuario.getUser().trim();

                                    ListarConsulta(progressDialog,trama3);

                                    /*
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConsultaActivity.this,
                                            android.R.layout.simple_list_item_1, listaMostrar);
                                    lvinventario.setAdapter(adapter);
                                    */

                                }

                            } else {
                                progressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        ConsultaActivity.this);
                                builder.setMessage("No se encontró Articulo")
                                        .setNegativeButton("Regresar", null)
                                        .create()
                                        .show();
                                etartcunsul.setText("");
                                etartcunsul.requestFocus();
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

    private void ListarConsulta(ProgressDialog progressdialog, String trama) {

        progressdialog.dismiss();


        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        listaMostrar = new ArrayList<>();
        listaarticulos = new ArrayList<>();
        listado = new ArrayList<>();
        listadoString = new ArrayList<>();
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_INV_TIENDA_MOVIL.FN_LISTA_LEC_CONTEO_INV&variables=%27"+trama+"%27";
        //url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=pkg_inv_tienda_movil.FN_LISTA_LEC_CONTEO_INV&variables=%2715032019071137|T01|1|010021|JVELASQUEZ%27";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        String Mensaje = "";
                        try {
                            btnbuscarconsulta.setVisibility(View.VISIBLE);
                            response1 = response1.trim();

                            if(response1.equals("{\"success\":false}")){

                                etartcunsul.setText("");
                                btnbuscarconsulta.setVisibility(View.VISIBLE);
                                etartcunsul.requestFocus();

                            }
                            JSONObject jsonObject = new JSONObject(response1);
                            Boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            Boolean condicion = false,error = false;
                            btnbuscarconsulta.setVisibility(View.VISIBLE);


                            if (success) {

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
                                    etartcunsul.setText("");
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(
                                            ConsultaActivity.this);
                                    dialog.setMessage(Mensaje)
                                            .setNegativeButton("Regresar",null)
                                            .create()
                                            .show();
                                }else {
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        listaconsulta = new listaConsulta();
                                        jsonObject = jsonArray.getJSONObject(i);

                                        listaconsulta.setCantidadConsulta(jsonObject.getString("CAN_DIGITADA_LEC"));
                                        listaconsulta.setFecha(jsonObject.getString("FCH_CREACION"));
                                        listaconsulta.setHora(jsonObject.getString("FCH_CREACION"));
                                        listaconsulta.setSecuencia(jsonObject.getString("SECUENCIA"));
                                        listado.add(listaconsulta);
                                        listadoString.add("\t\t\t\t"+listaconsulta.getCantidadConsulta()+" \t\t\t\t\t\t "+listaconsulta.getFecha());
                                        tvdetalleconsulta.setText(articulo.getArticulo().toString());

                                    }

                                    etartcunsul.setText("");
                                    etartcunsul.requestFocus();

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConsultaActivity.this,
                                            android.R.layout.simple_list_item_1, listadoString);
                                    lvinventario.setAdapter(adapter);


                                    lvinventario.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                                            final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultaActivity.this);
                                            builder.setCancelable(false);

                                            listView = mview.findViewById(R.id.lvopciones);
                                            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                                                    ConsultaActivity.this, android.R.layout.simple_list_item_1,
                                                    getResources().getStringArray(R.array.opciones));
                                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

                                            listView.setAdapter(adapter);

                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                                                    usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
                                                    Intent intent = new Intent(ConsultaActivity.this,ConsultaActivity.class);
                                                    Bundle bundle = new Bundle();
                                                    Bundle bundle1 = new Bundle();
                                                    bundle.putSerializable("Usuario",usuario);
                                                    intent.putExtras(bundle);
                                                    bundle1.putSerializable("listainventario",listainventario);
                                                    intent.putExtras(bundle1);

                                                    switch (i) {
                                                        case 0:

                                                            AlertDialog.Builder builder = new  AlertDialog.Builder(ConsultaActivity.this);
                                                            builder.setCancelable(false);
                                                            builder.setMessage("¿Está seguro que desea eliminar el registro?");
                                                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                    Intent intent = new Intent(ConsultaActivity.this,ConsultaActivity.class);
                                                                    Bundle bundle = new Bundle();
                                                                    Bundle bundle1 = new Bundle();
                                                                    bundle.putSerializable("Usuario",usuario);
                                                                    intent.putExtras(bundle);
                                                                    bundle1.putSerializable("listainventario",listainventario);
                                                                    intent.putExtras(bundle1);
                                                                    String trama = articulo.getLlave()+"|"+
                                                                            usuario.getCodAlmacen()+"|"+usuario.getConteo()+"|"+articulo.getCodArticulo()
                                                                            +"|"+usuario.getUser().trim()+"|"+listado.get(position).getSecuencia();

                                                                    EliminarRegistroInventario(trama);
                                                                    //listainventario.remove(position);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            }
                                                            );

                                                            builder.setNegativeButton("Cancelar", null);
                                                            builder.create();
                                                            builder.show();
                                                            break;

                                                        case 1:
                                                            intent.putExtra("etarticulo",etartcunsul.getText().toString());
                                                            startActivity(intent);
                                                            finish();
                                                            break;
                                                    }
                                                }
                                            });
                                            builder.setView(mview);
                                            AlertDialog dialog = builder.create();
                                            if (mview.getParent() != null)
                                                ((ViewGroup) mview.getParent()).removeView(mview); // <- fix
                                            dialog.show();
                                            return true;
                                        }
                                    });
                                }

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(
                                        ConsultaActivity.this);
                                builder.setMessage("No se encontró Articulo")
                                        .setNegativeButton("Regresar", null)
                                        .create()
                                        .show();
                                etartcunsul.setText("");
                                etartcunsul.requestFocus();
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

    private void EliminarRegistroInventario(String trama) {

        final ProgressDialog progressDialog = new ProgressDialog(ConsultaActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        // TODO se debe realizar la actualizacion del articulo

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionTestMovil.php?funcion=PKG_INV_TIENDA_MOVIL.FN_ELIMINAR_LEC_CONTEO_INV&variables='"+trama+"'";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

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
