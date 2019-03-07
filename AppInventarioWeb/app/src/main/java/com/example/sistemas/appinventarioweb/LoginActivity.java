package com.example.sistemas.appinventarioweb;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.example.sistemas.appinventarioweb.Entities.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {



    EditText etusuario,etclave;
    Button btnlogeo;
    Usuario usuario;
    String url;

    // Actividad en la que se tiene que hacer el logeo , se usa la funcion del Oracle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = new Usuario();
        etusuario = findViewById(R.id.etUsuario);
        etclave = findViewById(R.id.etClave);
        btnlogeo = findViewById(R.id.btnLogin);

        btnlogeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etusuario.getText().equals("") || etclave.getText().equals("")){

                }else{
                    verificarUsuario(etusuario.getText().toString().replace(" ","").toUpperCase()
                            ,etclave.getText().toString().replace(" ","").toUpperCase());
                }
            }
        });
    }

    public void verificarUsuario(String Codigo_usuario,String Contraseña_usuario){

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_WEB_HERRAMIENTAS.FN_WS_LOGIN&variables='7|"+Codigo_usuario+"|"+Contraseña_usuario+"'"; // se debe actalizar la URL

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        try {
                            JSONObject jsonObject=new JSONObject(response1);
                            boolean success = jsonObject.getBoolean("success");
                            if (success){
                                JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");

                                for(int i=0;i<jsonArray.length();i++) {
                                    usuario = new Usuario();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    usuario.setCodAlmacen(jsonObject.getString("COD_ALMACEN"));
                                    usuario.setNombre(jsonObject.getString("NOMBRE"));
                                    usuario.setMoneda(jsonObject.getString("MONEDA"));
                                    usuario.setCodTienda(jsonObject.getString("COD_TIENDA"));
                                    usuario.setCodVendedor(jsonObject.getString("COD_VENDEDOR"));
                                    usuario.setFechaActual(jsonObject.getString("FECHA_ACTUAL"));
                                    usuario.setTipoCambio(jsonObject.getString("TIPO_CAMBIO"));
                                    usuario.setLugar("LIMA"); // Se usa en la busqueda de producto
                                    usuario.setUser(etusuario.getText().toString());

                                }
                                Toast.makeText(LoginActivity.this, usuario.getCodVendedor(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Usuario",usuario);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
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
}
