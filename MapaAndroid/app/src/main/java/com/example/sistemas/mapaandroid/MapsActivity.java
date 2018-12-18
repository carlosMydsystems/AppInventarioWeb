package com.example.sistemas.mapaandroid;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sistemas.mapaandroid.Entidades.Coordenadas;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String Latitudst,Longitudst,Direccion,Fecharegistro,Horaregistro,Placa,Id_Ubicacion;
    Double latitud,longitud;
    ArrayList<Coordenadas> listacoordenadas;
    ArrayAdapter<String> listalatitud,listalongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        listacoordenadas = (ArrayList<Coordenadas>) getIntent().getSerializableExtra("Lista");

       // Toast.makeText(this, listacoordenadas.get(0).getLatitud(), Toast.LENGTH_SHORT).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i =0 ; i<listacoordenadas.size();i++){
            Latitudst = listacoordenadas.get(i).getLatitud();
            latitud = Double.valueOf(Latitudst);
            Longitudst = listacoordenadas.get(i).getLongitud();
            longitud = Double.valueOf(Longitudst);
            Direccion = listacoordenadas.get(i).getDireccion();
            Fecharegistro = listacoordenadas.get(i).getFechaRegistro();
            Horaregistro = listacoordenadas.get(i).getHoraRegistro();
            Placa = listacoordenadas.get(i).getPlaca();
            Id_Ubicacion = listacoordenadas.get(i).getId_Ubicacion();
            String I = String.valueOf(i);
            // Se genera cada uno de los puntos en el mapa
            LatLng CalleCapon = new LatLng(latitud, longitud);
            mMap.addMarker(new MarkerOptions().position(CalleCapon).title( I + ".-"  + Placa+" - "+Fecharegistro+" : "+Horaregistro ));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CalleCapon,14));
        }
        Polyline line = mMap.addPolyline(new PolylineOptions().add(new LatLng(-12.050,-77.26)
                ,new LatLng(-12.053,-77.25)).width(5).color(Color.GREEN));
    }
}
