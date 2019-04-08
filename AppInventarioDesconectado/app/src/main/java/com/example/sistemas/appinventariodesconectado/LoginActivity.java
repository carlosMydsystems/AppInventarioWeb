package com.example.sistemas.appinventariodesconectado;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.appinventariodesconectado.Entidades.CargaMasInventario;
import com.example.sistemas.appinventariodesconectado.Entidades.Usuario;
import com.example.sistemas.appinventariodesconectado.Utilidades.Utilidad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.sistemas.appinventariodesconectado.Utilidades.Utilidad.CAMPO_COD_BODEGA_INVENTARIO_CABECERA;
import static com.example.sistemas.appinventariodesconectado.Utilidades.Utilidad.CAMPO_CONTEO_INVENTARIO_CABECERA;
import static com.example.sistemas.appinventariodesconectado.Utilidades.Utilidad.CAMPO_ESTADO_INVENTARIO_CABECERA;
import static com.example.sistemas.appinventariodesconectado.Utilidades.Utilidad.CAMPO_FECHA_INVENTARIO_CABECERA;

public class LoginActivity extends AppCompatActivity {

    EditText etusuario,etclave;
    Button btnlogeo,btnCargaDatos;
    Usuario usuario;
    CargaMasInventario cargaMasInventario;
    String url;
    ArrayList<CargaMasInventario> listaCargaMasInventario;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = new Usuario();
        etusuario = findViewById(R.id.etUsuario);
        etclave = findViewById(R.id.etClave);
        btnlogeo = findViewById(R.id.btnLogin);
        btnCargaDatos = findViewById(R.id.btnCargaDatosUsuario);

        btnCargaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificarUsuario(etusuario.getText().toString().replace(" ","").toUpperCase()
                        ,etclave.getText().toString().replace(" ","").toUpperCase());


            }
        });

        btnlogeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etusuario.getText().equals("") || etclave.getText().equals("")){

                }else{

                    verificarUsuarioLocalDB(etusuario.getText().toString().replace(" ","").toUpperCase()
                            ,etclave.getText().toString().replace(" ","").toUpperCase());

                }
            }
        });
    }

    private void verificarUsuarioLocalDB(String etusuario, String clave) {

        try {
            conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_inventarios",null,1);

            SQLiteDatabase db=conn.getReadableDatabase();
            String[] parametros={etusuario,clave};

            Toast.makeText(this, etusuario, Toast.LENGTH_SHORT).show();
            Cursor cursor=db.rawQuery("SELECT * " + " FROM " + Utilidad.TABLA_USUARIO+" WHERE "+Utilidad.CAMPO_COD_USUARIO+"=? AND " +Utilidad.CAMPO_CLAVE+"=?",parametros);
            cursor.moveToFirst();

            usuario.setUser(cursor.getString(0));
            usuario.setClave(cursor.getString(1));
            usuario.setNombre(cursor.getString(2));
            usuario.setCodTienda(cursor.getString(3));
            usuario.setCodAlmacen(cursor.getString(4));
            usuario.setEstado(cursor.getString(5));

            CargarInventario(usuario.getCodAlmacen(),"user");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Usuario",usuario);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        }catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"El Articulo no existe consulta articulo ",Toast.LENGTH_LONG).show();

        }
    }

    public void verificarUsuario(String Codigo_usuario,String Contraseña_usuario){

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_WEB_HERRAMIENTAS.FN_WS_LOGIN&variables='11|"+Codigo_usuario+"|"+Contraseña_usuario+"'"; // se debe actalizar la URL

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

                                Toast.makeText(LoginActivity.this, "" + response1, Toast.LENGTH_SHORT).show();
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

                                    android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(
                                            LoginActivity.this);
                                    dialog.setMessage(Mensaje)
                                            .setNegativeButton("Regresar",null)
                                            .create()
                                            .show();
                                }else {


                                    JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        usuario = new Usuario();
                                        jsonObject = jsonArray.getJSONObject(i);
                                        usuario.setCodAlmacen(jsonObject.getString("COD_ALMACEN"));
                                        usuario.setNombre(jsonObject.getString("NOMBRE"));
                                        usuario.setMoneda(jsonObject.getString("MONEDA"));
                                        usuario.setCodTienda(jsonObject.getString("COD_TIENDA"));
                                        usuario.setCodVendedor(jsonObject.getString("COD_VENDEDOR"));
                                        usuario.setFechaActual(jsonObject.getString("FECHA_ACTUAL"));
                                        usuario.setTipoCambio(jsonObject.getString("TIPO_CAMBIO"));
                                        usuario.setConteo(jsonObject.getString("CONTEO"));
                                        usuario.setLlave(jsonObject.getString("INVENTARIO"));
                                        usuario.setLugar("LIMA"); // Se usa en la busqueda de producto
                                        usuario.setUser(etusuario.getText().toString());
                                    }

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("Usuario", usuario);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            }else{
                                AlertDialog.Builder build1 = new AlertDialog.Builder(LoginActivity.this);
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


    private void CargarInventario(String codAlmacen, final String Administrador) {


        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        listaCargaMasInventario = new ArrayList<>();

        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_INV_TIENDA_MOVIL.FN_CARGA_MAS_INVENTARIO&variables=%27"+codAlmacen+"%27"; // se debe actalizar la URL

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
                                    cargaMasInventario = new CargaMasInventario();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    cargaMasInventario.setFecha(jsonObject.getString("FECHA"));
                                    cargaMasInventario.setBodega(jsonObject.getString("BODEGA"));
                                    cargaMasInventario.setConteo(jsonObject.getString("CONTEO"));
                                    cargaMasInventario.setEstado(jsonObject.getString("ESTADO"));
                                    listaCargaMasInventario.add(cargaMasInventario);
                                }
                                String Adm = Administrador;

                                CagarlistaMasInventarioDBQuery(listaCargaMasInventario,Adm);

                            }else{

                                AlertDialog.Builder build1 = new AlertDialog.Builder(LoginActivity.this);
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

    private void CagarlistaMasInventarioDBQuery(ArrayList<CargaMasInventario> listaCargaMasInventario,String Administrador) {

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("... Validando");
        progressDialog.setCancelable(false);
        progressDialog.show();


        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_inventarios",null,1);

        SQLiteDatabase db=conn.getWritableDatabase();

        for (int i = 0;i<listaCargaMasInventario.size();i++){

            String insert1 = "INSERT INTO " + Utilidad.TABLA_INV_CAB_MOVIL + " ( " + CAMPO_FECHA_INVENTARIO_CABECERA + " , " +
                    CAMPO_COD_BODEGA_INVENTARIO_CABECERA + " , " +CAMPO_CONTEO_INVENTARIO_CABECERA + " , " + CAMPO_ESTADO_INVENTARIO_CABECERA +  " ) " +
                    "VALUES " + "('"+listaCargaMasInventario.get(i).getFecha()+"','" +listaCargaMasInventario.get(i).getBodega()+"','" + listaCargaMasInventario.get(i).getConteo() +
                    "','" + listaCargaMasInventario.get(i).getEstado() + "')" ;
            db.execSQL(insert1);

        }
        progressDialog.dismiss();
        db.close();

        if (Administrador.equals("admin")) {

            Intent intent = new Intent(LoginActivity.this, CargaDatosActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Usuario", usuario);
            intent.putExtras(bundle);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("listaCargaMasInventario", listaCargaMasInventario);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        }else {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Usuario", usuario);
            intent.putExtras(bundle);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("listaCargaMasInventario", listaCargaMasInventario);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
        }

    }

}
