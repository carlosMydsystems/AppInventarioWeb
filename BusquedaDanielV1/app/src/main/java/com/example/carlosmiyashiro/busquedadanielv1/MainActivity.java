package com.example.carlosmiyashiro.busquedadanielv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    EditText etnumerohojaruta;
    Button btnbuscar;
    String numeroHojaRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etnumerohojaruta = findViewById(R.id.etNumeroHojaRuta);
        btnbuscar = findViewById(R.id.btnBuscar);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numeroHojaRuta = etnumerohojaruta.getText().toString();

                Intent pantalla1 = new Intent(MainActivity.this, BuscarActivity.class);
                pantalla1.putExtra("numeroHojaRuta", numeroHojaRuta);
                startActivity(pantalla1);
                finish();


            }
        });
    }
}
