package com.example.sistemas.appinventarioweb;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Usuario usuario;
    String url;
    Inventario inventario;
    ArrayList<Inventario> listaProductosInventario;
    ArrayList<String> listadoProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");

        ConsultarListaInvenrtario();

    }

    private void ConsultarListaInvenrtario() {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.mydsystems.com/pruebaDaniel/ConsultaInventario.php";
        listadoProductos = new ArrayList<>();
        listaProductosInventario = new ArrayList<>();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("producto");
                            if (success){
                                for(int i=0;i<jsonArray.length();i++) {

                                    inventario = new Inventario();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    inventario.setId(jsonObject.getString("id"));
                                    inventario.setCodigoProducto(jsonObject.getString("cod_Producto"));
                                    inventario.setCantidad(jsonObject.getString("cantidad"));
                                    inventario.setDescripcion(jsonObject.getString("DescripcionProducto"));
                                    inventario.setIdUsuario(jsonObject.getString("Usuario_id"));

                                    listaProductosInventario.add(inventario);
                                    listadoProductos.add(inventario.getCodigoProducto()+" - "+inventario.getDescripcion());
                                }
                                    Intent intent =  new Intent(MainActivity.this,RegistroInventarioActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("listaProductosInventario", listaProductosInventario);
                                    intent.putExtras(bundle);
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putSerializable("Usuario",usuario);
                                    intent.putExtras(bundle1);
                                    startActivity(intent);
                                    finish();

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("No se pudo encontrar los tipos de pago correspondientes");
                                builder.create().show();
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
}