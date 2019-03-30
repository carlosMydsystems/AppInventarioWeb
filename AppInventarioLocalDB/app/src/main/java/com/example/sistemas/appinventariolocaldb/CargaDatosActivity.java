package com.example.sistemas.appinventariolocaldb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.sistemas.appinventariolocaldb.Entities.Articulo;
import com.example.sistemas.appinventariolocaldb.Entities.BarrasPresentacion;
import com.example.sistemas.appinventariolocaldb.Entities.Presentacion;
import com.example.sistemas.appinventariolocaldb.Entities.Usuario;
import com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_BODEGA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CLAVE;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CODIGO_ARTICULO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CODIGO_BARRA_1;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CODIGO_BARRA_2;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_CODIGO_BARRA_3;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_COD_ARTICULO_BARRAS_PRESENTACION;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_COD_BARRA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_COD_TIENDA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_COD_USUARIO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_DESCRIPCION_ARTICULO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_DES_PRESENTACION;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_EQUIVALENCIA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_EQUIVALENCIA_PRESENTACION;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_ESTADO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_MARCA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_NOMBRE_USUARIO;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_UND_MEDIDA_BARRAS_PRESENTACION;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_UNIDAD_MEDIDA;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.CAMPO_UNIDAD_MEDIDA_PRESENTACION;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_ARTICULOS;
import static com.example.sistemas.appinventariolocaldb.Utilidades.Utilidad.TABLA_INVENTARIO;

public class CargaDatosActivity extends AppCompatActivity {

    Button btncargaususrio, btncargaarticulo, btncargabarrasarticulo, btncargapresentacion,
            btnborrardatabase,btnregistro,btnenviarinventario,btnDropUsuario;
    Usuario usuario;
    Articulo articulo;
    BarrasPresentacion barrasPresentacion;
    ArrayList<Usuario> listadoCargaUsuarios;
    ArrayList<Articulo> listaArticulo;
    ArrayList<BarrasPresentacion> listaBarrasPresentacion;
    String url,insert;
    TextView tvmensaje;
    Presentacion presentacion;
    ArrayList<Presentacion> listapresentacion;
    Integer enterito;
    ConexionSQLiteHelper conn;
    ImageButton ibretornologeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_datos);

        btncargaususrio =  findViewById(R.id.btnCargaUsuario);
        btncargaarticulo = findViewById(R.id.btnCargaArticulos);
        btncargabarrasarticulo = findViewById(R.id.btnCargaBarrasArticulo);
        btncargapresentacion = findViewById(R.id.btnCargaPresentacion);
        btnborrardatabase  = findViewById(R.id.btnBorraDataBase);
        btnregistro = findViewById(R.id.btnRegistro);
        tvmensaje = findViewById(R.id.tvMensaje);
        btnenviarinventario = findViewById(R.id.btnEnviarInventario);
        ibretornologeo = findViewById(R.id.ibretornologeo);
        btnDropUsuario = findViewById(R.id.btnDropUsuario);

        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_inventarios",null,1);

        ibretornologeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(CargaDatosActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btncargaususrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CargarUsuarios();

            }
        });

        btncargaarticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CargarArticulos();

            }
        });

        btncargabarrasarticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CargarPrsentacion();

            }
        });

        btncargapresentacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CargarBarrasPresentacion();

            }
        });
        btnborrardatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplication().deleteDatabase("bd_inventarios");
            }
        });
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CargaDatosActivity.this,RegistroDBActivity.class);
                Bundle bunble = new Bundle();
                bunble.putSerializable("Usuario",usuario);
                intent.putExtras(bunble);
                startActivity(intent);
                finish();
            }
        });

        btnenviarinventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EnviarInventario();

            }
        });
        btnDropUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BorrarTablaUsuario();

            }
        });
    }

    private void BorrarTablaUsuario() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();


        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_USUARIO);

        //tvmensaje.setText(""+listadoCargaUsuarios.size());
        db.close();

    }

    private void EnviarInventario() {


        SQLiteDatabase db=conn.getReadableDatabase();

        try {

            Cursor cursor=db.rawQuery("SELECT * " + " FROM " + TABLA_INVENTARIO ,null);
            cursor.moveToFirst();

            do {

                String trama = cursor.getString(1)+"|"+cursor.getString(2)+"|"+cursor.getString(3)+"|"+cursor.getString(4)+"|"+cursor.getString(5)+"|"+
                        cursor.getString(6)+"|"+cursor.getString(7)+"|"+cursor.getString(8)+"|"+cursor.getString(9)+"|"+cursor.getString(10)+"|"+
                        cursor.getString(11)+"|"+cursor.getString(12);
                ActualizarArticuloWeb(trama.trim());

            } while (cursor.moveToNext());

        }catch (Exception e){

            Toast.makeText(getApplicationContext(),"El Articulo no existe en codigo de barras",Toast.LENGTH_LONG).show();
        }
    }

    private void ActualizarArticuloWeb(String trama) {

        final ProgressDialog progressDialog = new ProgressDialog(CargaDatosActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);


        // TODO se debe realizar la actualizacion del articulo

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionTestMovil.php?funcion=PKG_INV_TIENDA_MOVIL.FN_GRABA_INV_LECTURA_MOVIL&variables='"+trama+"'";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(CargaDatosActivity.this);
                        response1 = response1.replace("*","");
                        builder.setMessage(response1);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Aceptar",null);
                        builder.create();

                        progressDialog.dismiss();

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

    private void CargarPrsentacion() {



        listapresentacion = new ArrayList<Presentacion>();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=PKG_INV_TIENDA_MOVIL.FN_CARGA_MAS_PRESENTACION"; // se debe actalizar la URL
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        try {
                            JSONObject jsonObject=new JSONObject(response1);
                            boolean success = jsonObject.getBoolean("success");

                            if (success){

                                    JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        presentacion = new Presentacion();
                                        jsonObject = jsonArray.getJSONObject(i);
                                        presentacion.setUndMedida(jsonObject.getString("UND_MEDIDA"));
                                        presentacion.setDesPresentacion(jsonObject.getString("DES_PRESENTACION"));
                                        presentacion.setEquivalencia(jsonObject.getString("EQUIVALENCIA"));
                                        listapresentacion.add(presentacion);
                                    }
                                    CargarPresentaciondbQuery(listapresentacion);

                            }else{
                                AlertDialog.Builder build1 = new AlertDialog.Builder(CargaDatosActivity.this);
                                build1.setTitle("Usuario  o Clave incorrecta")
                                        .setCancelable(false)
                                        .setNegativeButton("Regresar",null)
                                        .create()
                                        .show();
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

    private void CargarPresentaciondbQuery(ArrayList<Presentacion> listapresentacion) {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();


        for (int i = 0;i<listapresentacion.size();i++){

            String insert1 = "INSERT INTO " + Utilidad.TABLA_PRESENTACION + " ( " + CAMPO_UNIDAD_MEDIDA_PRESENTACION + " , " +
                    CAMPO_DES_PRESENTACION + " , " + CAMPO_EQUIVALENCIA_PRESENTACION +  " ) " +
                    "VALUES " + "('"+listapresentacion.get(i).getUndMedida()+"','" + listapresentacion.get(i).getDesPresentacion() +
                    "','" + listapresentacion.get(i).getEquivalencia() + "')" ;
            db.execSQL(insert1);
        }

        tvmensaje.setText(""+listapresentacion.size());
        db.close();

    }

    private void CargarArticulos() {

        listaArticulo = new ArrayList<Articulo>();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=PKG_INV_TIENDA_MOVIL.FN_CARGA_MAS_ARTICULO"; // se debe actalizar la URL
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        String Mensaje = "";
                        try {
                            JSONObject jsonObject=new JSONObject(response1);
                            boolean success = jsonObject.getBoolean("success");

                            if (success){

                                    JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        articulo = new Articulo();
                                        jsonObject = jsonArray.getJSONObject(i);
                                        articulo.setCodArticulo(jsonObject.getString("COD_ARTICULO"));
                                        articulo.setArticulo(jsonObject.getString("ARTICULO").replace("'",""));
                                        articulo.setUniMedida(jsonObject.getString("UND_MEDIDA"));
                                        articulo.setEquivalencia(jsonObject.getString("EQUIVALENCIA"));
                                        articulo.setMarca(jsonObject.getString("DES_MARCA").replace("'",""));
                                        articulo.setCodBarra1(jsonObject.getString("COD_BARRA1"));
                                        articulo.setCodBarra2(jsonObject.getString("COD_BARRA2"));
                                        articulo.setCodBarra3(jsonObject.getString("COD_BARRA3"));
                                        listaArticulo.add(articulo);
                                    }
                                    tvmensaje.setText("se hizo la carga en memoria");
                                    //CargarArticulosdb(listaArticulo,progressDialog);

                                    CargarArticulosdbQuery(listaArticulo);

                            }else{
                                AlertDialog.Builder build1 = new AlertDialog.Builder(CargaDatosActivity.this);
                                build1.setTitle("Usuario  o Clave incorrecta")
                                        .setCancelable(false)
                                        .setNegativeButton("Regresar",null)
                                        .create()
                                        .show();
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

    private void CargarArticulosdbQuery(ArrayList<Articulo> listaArticulo) {


        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        // insert into ARTICULO (COD_ARTICULO , ARTICULO , ) VALUES ('listaArticulo.get(i).getCodArticulo()' , );

        for (int i = 0; i<listaArticulo.size();i++) {

             insert = "INSERT INTO " + Utilidad.TABLA_ARTICULOS + " ( " + CAMPO_CODIGO_ARTICULO + " , " +
             CAMPO_DESCRIPCION_ARTICULO + " , " + CAMPO_UNIDAD_MEDIDA + " , " + CAMPO_EQUIVALENCIA + " , " +
             CAMPO_MARCA + " , " + CAMPO_CODIGO_BARRA_1 + " , " + CAMPO_CODIGO_BARRA_2 + " , " + CAMPO_CODIGO_BARRA_3 + " ) " +
             "VALUES " + "('"+listaArticulo.get(i).getCodArticulo()+"','" + listaArticulo.get(i).getArticulo() +
             "','" + listaArticulo.get(i).getUniMedida() + "','" + listaArticulo.get(i).getEquivalencia() + "','" +
             listaArticulo.get(i).getMarca().toString() + "','" + listaArticulo.get(i).getCodBarra1().toString() + "','" +
             listaArticulo.get(i).getCodBarra2().toString() + "','" + listaArticulo.get(i).getCodBarra3().toString()+ "')" ;

            db.execSQL(insert);
        }


        tvmensaje.setText(""+listaArticulo.size());
        CargarBarrasPresentacion();
        db.close();
    }

    public void CargarUsuarios(){

        listadoCargaUsuarios = new ArrayList<Usuario>();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=PKG_INV_TIENDA_MOVIL.FN_CARGA_MAS_USUARIOS"; // se debe actalizar la URL
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        String Mensaje = "";
                        try {
                            JSONObject jsonObject=new JSONObject(response1);
                            boolean success = jsonObject.getBoolean("success");
                            Boolean condicion = false,error = false;

                            if (success){

                                    JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        usuario = new Usuario();
                                        jsonObject = jsonArray.getJSONObject(i);
                                        usuario.setUser(jsonObject.getString("COD_USUARIO"));
                                        usuario.setClave(jsonObject.getString("CLAVE"));
                                        usuario.setNombre(jsonObject.getString("NOMBRE"));
                                        usuario.setCodTienda(jsonObject.getString("COD_TIENDA"));
                                        usuario.setCodAlmacen(jsonObject.getString("BODEGA"));
                                        usuario.setEstado(jsonObject.getString("ESTADO"));
                                        listadoCargaUsuarios.add(usuario);
                                    }

                                    CargarUsuariosdbQuery(listadoCargaUsuarios);

                                    /*
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("Usuario", usuario);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();

                                    */

                            }else{
                                AlertDialog.Builder build1 = new AlertDialog.Builder(CargaDatosActivity.this);
                                build1.setTitle("Usuario  o Clave incorrecta")
                                        .setCancelable(false)
                                        .setNegativeButton("Regresar",null)
                                        .create()
                                        .show();
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

    public void CargarBarrasPresentacion(){

        listaBarrasPresentacion = new ArrayList<BarrasPresentacion>();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=PKG_INV_TIENDA_MOVIL.FN_CARGA_MAS_BARRAS_ARTICULO"; // se debe actalizar la URL
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        try {
                            JSONObject jsonObject=new JSONObject(response1);
                            boolean success = jsonObject.getBoolean("success");

                            if (success){
                                    JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        barrasPresentacion = new BarrasPresentacion();
                                        jsonObject = jsonArray.getJSONObject(i);

                                        barrasPresentacion.setUndMedida(jsonObject.getString("UND_MEDIDA"));
                                        barrasPresentacion.setCodBarras(jsonObject.getString("COD_BARRA"));
                                        barrasPresentacion.setCodArticulo(jsonObject.getString("COD_ARTICULO"));
                                        listaBarrasPresentacion.add(barrasPresentacion);
                                    }

                                    CargarBarrasPresentaciondbQuery(listaBarrasPresentacion);

                            }else{
                                AlertDialog.Builder build1 = new AlertDialog.Builder(CargaDatosActivity.this);
                                build1.setTitle("Usuario  o Clave incorrecta")
                                        .setCancelable(false)
                                        .setNegativeButton("Regresar",null)
                                        .create()
                                        .show();
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

    private void CargarUsuariosdbQuery(ArrayList<Usuario> listaUsuario) {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        for (int i = 0; i<listaUsuario.size();i++) {

            insert = "INSERT INTO " + Utilidad.TABLA_USUARIO + " ( " + CAMPO_COD_USUARIO + " , " +
                     CAMPO_CLAVE + " , " + CAMPO_NOMBRE_USUARIO + " , " +
                    CAMPO_COD_TIENDA + " , " + CAMPO_BODEGA + " , " + CAMPO_ESTADO  + " ) " +
                    "VALUES " + "('"+listaUsuario.get(i).getUser()+"','" + listaUsuario.get(i).getClave() +
                    "','" + listaUsuario.get(i).getNombre() + "','" + listaUsuario.get(i).getCodTienda() + "','" +
                    listaUsuario.get(i).getCodAlmacen().toString() + "','" + listaUsuario.get(i).getEstado().toString() + "')" ;
            db.execSQL(insert);
        }

        db.close();
    }

    private void CargarBarrasPresentaciondbQuery(ArrayList<BarrasPresentacion> listaBarrasPresentacion) {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();

        for (int i = 0;i<listaBarrasPresentacion.size();i++){

            String insert1 = "INSERT INTO " + Utilidad.TABLA_VTA_BARRAS_PRESENTACION + " ( " + CAMPO_COD_ARTICULO_BARRAS_PRESENTACION + " , " +
                    CAMPO_COD_BARRA + " , " + CAMPO_UND_MEDIDA_BARRAS_PRESENTACION +  " ) " +
                    "VALUES " + "('"+listaBarrasPresentacion.get(i).getCodArticulo()+"','" + listaBarrasPresentacion.get(i).getCodBarras() +
                    "','" + listaBarrasPresentacion.get(i).getUndMedida() + "')" ;
            db.execSQL(insert1);

        }

        CargarPrsentacion();
        tvmensaje.setText(""+listaBarrasPresentacion.size());
        db.close();
    }
}
