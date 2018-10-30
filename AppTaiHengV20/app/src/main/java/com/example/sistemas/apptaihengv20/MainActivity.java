package com.example.sistemas.apptaihengv20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    EditText etruc;
    Button btnbuscar;
    String ruc,tipobusqueda="ruc";


    ListView lvclientes;
    RadioButton cbruc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etruc = findViewById(R.id.etRuc);
        btnbuscar = findViewById(R.id.btnBuscar);
        final RadioGroup rgtipobusqueda = findViewById(R.id.rgtipobusqueda);
        cbruc = findViewById(R.id.rbruc);

        rgtipobusqueda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbruc){
                    tipobusqueda = "ruc";
                    etruc.setHint("Ingrese número de RUC");
                    etruc.setInputType(2);
                    etruc.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});


                }else if(checkedId == R.id.rbrazonsocial){
                    tipobusqueda = "razonsocial";
                    etruc.setHint("Ingrese su razón social");
                    etruc.setInputType(1);
                    etruc.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
                }
            }
        });

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruc = etruc.getText().toString();

                Intent pantalla1 = new Intent(MainActivity.this, Buscar.class);
                pantalla1.putExtra("tipobusqueda",tipobusqueda);
                pantalla1.putExtra("ruc",ruc);
                startActivity(pantalla1);
                finish();


            }
        });
    }
}
