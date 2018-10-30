package com.example.carlosmiyashiro.busquedadanielv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ControlBultos extends AppCompatActivity {

    Button btnlistarpedidos;
    EditText etnumerohojaruta;
    String numerohojaruta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_bultos);

        btnlistarpedidos = findViewById(R.id.btnListarPedidos);
        etnumerohojaruta = findViewById(R.id.etnumerohojarutacontrolbultos);

        numerohojaruta = etnumerohojaruta.getText().toString();

        btnlistarpedidos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(ControlBultos.this,MainActivity.class);
                intent.putExtra("NumeroHojaRuta",numerohojaruta);
                startActivity(intent);
                finish();
            }
        });



    }
}
