package com.example.sistemas.mapaandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.mapaandroid.Entidades.Coordenadas;
import com.example.sistemas.mapaandroid.Request.ConsultaCoordenadasRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class IngresoCoordenadasActivity extends AppCompatActivity {

    Button btnaceptar;
    EditText etfechainicio,etfechafinal;
    Coordenadas coordenadas;
    ArrayList<Coordenadas> listaejemplo;
    Calendar calendario = Calendar.getInstance();
    ArrayList<String> listaVehiculos;
    Spinner spVehiculo;
    String captura;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_coordenadas);

        etfechainicio = findViewById(R.id.etFechaInicio);
        etfechafinal =  findViewById(R.id.etFechafinal);
        btnaceptar = findViewById(R.id.btnAceptar);
        spVehiculo = findViewById(R.id.spVehiculo);

        ListarVehiculos();
        spVehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                captura = listaVehiculos.get(position).toString();
                Toast.makeText(IngresoCoordenadasActivity.this, captura, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, monthOfYear);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                actualizarInput(etfechafinal);
            }
        };
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, monthOfYear);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                actualizarInput(etfechainicio);
            }
        };


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(IngresoCoordenadasActivity.this,
                                    android.R.layout.simple_spinner_item,listaVehiculos );
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spVehiculo.setAdapter(adapter1);


        etfechafinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(IngresoCoordenadasActivity.this, date, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etfechainicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(IngresoCoordenadasActivity.this, date1, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro();
            }
        });
    }

    private void Registro() {

        coordenadas = new Coordenadas();
        listaejemplo = new ArrayList<>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");
                    if (success){
                        JSONArray jsonArray=jsonresponse.getJSONArray("coordenadas");
                        for(int i=0;i<jsonArray.length();i++) {
                            coordenadas = new Coordenadas();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            coordenadas.setLatitud(jsonObject1.getString("Latitud"));
                            coordenadas.setLongitud(jsonObject1.getString("Longitud"));
                            coordenadas.setFechaRegistro(jsonObject1.getString("FechaRegistro"));
                            coordenadas.setPlaca(jsonObject1.getString("Placa"));
                            coordenadas.setHoraRegistro(jsonObject1.getString("HoraRegistro"));
                            coordenadas.setDireccion(jsonObject1.getString("Direccion"));
                            coordenadas.setId_Ubicacion(jsonObject1.getString("Id_Ubicacion"));
                            listaejemplo.add(coordenadas);
                        }
                        Intent intent = new Intent(IngresoCoordenadasActivity.this,MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Lista",  listaejemplo);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                IngresoCoordenadasActivity.this);
                        builder.setMessage("No se encontraron registros")
                                .setNegativeButton("Regresar",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ConsultaCoordenadasRequest consultaCoordenadasRequest = new ConsultaCoordenadasRequest(
               etfechainicio.getText().toString().replace("-",""),etfechafinal.
                getText().toString().replace("-",""),"MostrarMapa" , captura, responseListener);
        RequestQueue queue = Volley.newRequestQueue(IngresoCoordenadasActivity.this);
        queue.add(consultaCoordenadasRequest);
    }

    private void actualizarInput(EditText et) {
        String formatoDeFecha = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(formatoDeFecha);
        et.setText(sdf.format(calendario.getTime()));
    }

    public void ListarVehiculos(){

        listaVehiculos = new ArrayList<>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");
                    if (success){
                        JSONArray jsonArray=jsonresponse.getJSONArray("coordenadas");
                        for(int i=0;i<jsonArray.length();i++) {
                            coordenadas = new Coordenadas();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            listaVehiculos.add(jsonObject1.getString("Placa"));
                        }

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(IngresoCoordenadasActivity.this,
                                android.R.layout.simple_spinner_item,listaVehiculos );
                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spVehiculo.setAdapter(adapter1);

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                IngresoCoordenadasActivity.this);
                        builder.setMessage("No se encontraron registros")
                                .setNegativeButton("Regresar",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ConsultaCoordenadasRequest consultaCoordenadasRequest = new ConsultaCoordenadasRequest(
                "-","-","MostrarVehiculo" ,"-", responseListener);
        RequestQueue queue = Volley.newRequestQueue(IngresoCoordenadasActivity.this);
        queue.add(consultaCoordenadasRequest);

    }
}