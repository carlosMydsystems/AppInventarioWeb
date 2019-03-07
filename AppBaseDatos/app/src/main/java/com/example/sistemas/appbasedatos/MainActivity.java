package com.example.sistemas.appbasedatos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnregistrarusuario, btnconsultausuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnregistrarusuario = findViewById(R.id.btnOpcionRegistro);
        btnconsultausuario =  findViewById(R.id.btnConsultaUsuario);

        btnregistrarusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegistrarSQLiteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnconsultausuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ConsultaActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
