package com.example.sistemas.obtenercoordenadav1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.obtenercoordenadav1.Request.EnvioRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tvlatitud,tvdireccion,tvlongitud,tvplaca;
    Button btnRegistrarUbicacion;
    String latitud,longitud,direccion, fechaRegistro,horaRegistro,placa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvlatitud = findViewById(R.id.tvlatitud);
        tvlongitud = findViewById(R.id.tvlongitud);
        tvdireccion = findViewById(R.id.tvdireccion);
        latitud = tvlatitud.getText().toString();
        longitud = tvlongitud.getText().toString();
        direccion = tvdireccion.getText().toString();
        btnRegistrarUbicacion =  findViewById(R.id.btnRegistrarUbicacion);
        tvplaca = findViewById(R.id.tvPlaca);

        Calendar fecha = Calendar.getInstance();
        Integer dia = fecha.get(Calendar.DAY_OF_MONTH);
        Integer mes = fecha.get(Calendar.MONTH) + 1;
        Integer anio = fecha.get(Calendar.YEAR);
        Integer hora = fecha.get(Calendar.HOUR_OF_DAY);
        Integer minuto = fecha.get(Calendar.MINUTE);

        fechaRegistro = anio+"-"+mes+"-"+dia;
        horaRegistro = hora+":"+minuto;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        btnRegistrarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro();
            }
        });
    }

    private void Registro() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success){
                        Toast.makeText(MainActivity.this, "Se hizo el registro de forma correcta", Toast.LENGTH_SHORT).show();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                MainActivity.this);
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

        latitud = tvlatitud.getText().toString();
        longitud = tvlongitud.getText().toString();
        direccion = tvdireccion.getText().toString();
        placa = tvplaca.getText().toString();
        Toast.makeText(this, placa, Toast.LENGTH_SHORT).show();
        EnvioRequest envioRequest = new EnvioRequest(latitud,longitud,direccion, fechaRegistro,horaRegistro,placa, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(envioRequest);
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        tvlatitud.setText("Localización agregada");
        tvdireccion.setText("");
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    tvdireccion.setText("" + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        MainActivity mainActivity;
        public MainActivity getMainActivity() {
            return mainActivity;
        }
        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            loc.getLatitude();
            loc.getLongitude();
            String Text = loc.getLatitude()+"";

            String longitudTxt = loc.getLongitude()+"";
            tvlatitud.setText(Text);
            tvlongitud.setText(longitudTxt);
            this.mainActivity.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            tvlatitud.setText("GPS Desactivado");
        }
        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            tvlatitud.setText("GPS Activado");
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
