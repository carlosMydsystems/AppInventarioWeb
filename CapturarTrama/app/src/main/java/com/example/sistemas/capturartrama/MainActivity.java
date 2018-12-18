package com.example.sistemas.capturartrama;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.sistemas.capturartrama.Entidades.HojaRuta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnCapturar;
    TextView tvcaptura;
    String url;
    ArrayList<HojaRuta> listahojaruta1 ;
    HojaRuta hojaRuta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvcaptura = findViewById(R.id.tvTrama);
        btnCapturar = findViewById(R.id.btnCapturar);

        listahojaruta1 = new ArrayList<>();

    btnCapturar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


           //CapturarJson();
            Recorelista();
        }
    });


    }

    public void CapturarJson(){

        final ArrayList<HojaRuta> listahojarutaenviar = new ArrayList<>();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil1.php?funcion=PKG_CD_RETROALIMENTACION.FN_OBTENER_HRUTA&variables='0030059261'";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");


                            for(int i=0;i<jsonArray.length();i++) {
                                hojaRuta = new HojaRuta();
                                jsonObject = jsonArray.getJSONObject(i);
                                hojaRuta.setNumeroHojaRuta(jsonObject.getString("NRO_HRUTA"));
                                /*
                                hojaRuta.setFechaRegistro(jsonObject.getString("FECHA_REGISTRO"));
                                hojaRuta.setMarca(jsonObject.getString("MARCA"));
                                hojaRuta.setNumeroPlaca(jsonObject.getString("NRO_PLACA"));
                                hojaRuta.setChofer(jsonObject.getString("NOMBRES"));
                                hojaRuta.setId_Clliente(jsonObject.getString("COD_CLIENTE"));
                                hojaRuta.setNombre(jsonObject.getString("CLIENTE"));
                                hojaRuta.setLugarEntrega(jsonObject.getString("LUGAR_ENTREGA"));
                                hojaRuta.setDistrito(jsonObject.getString("DISTRITO"));
                                */
                                listahojarutaenviar.add(hojaRuta);

                            }
                            Intent intent = new Intent(MainActivity.this,Main2Activity.class);


                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Lista", listahojarutaenviar);
                            intent.putExtras(bundle);
                            intent.putExtra("E","1234");
                            startActivity(intent);
                            finish();



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

    public void Recorelista (){

        final ArrayList<String> lvp1,lvp2,lvp3,lvp4;
        lvp1 = new ArrayList<>();
        lvp2 = new ArrayList<>();
        lvp3 = new ArrayList<>();
        lvp4 = new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTest3.php?funcion=pkg_movil_funciones.fn_obtener_motivos_hruta&variables='9'";


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
                                    jsonObject = jsonArray.getJSONObject(i);


                                    if (jsonObject.getString("COD_CAMPO").substring(0,2).equals("RP")){
                                        lvp1.add(jsonObject.getString("DESC_CAMPO"));
                                        lvp2.add(jsonObject.getString("COD_CAMPO"));

                                    }else if(jsonObject.getString("COD_CAMPO").substring(0,2).equals("RT")) {
                                        lvp3.add(jsonObject.getString("DESC_CAMPO"));
                                        lvp4.add(jsonObject.getString("COD_CAMPO"));
                                    }
                                }

                                Intent intent = new Intent(MainActivity.this,Main2Activity.class);


                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Lista", lvp1);
                                intent.putExtras(bundle);
                                intent.putExtra("E","1234");
                                startActivity(intent);
                                finish();


                            }else{

                                AlertDialog.Builder build1 = new AlertDialog.Builder(MainActivity.this);
                                build1.setTitle("No se encontro el registro")
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
