package com.example.sistemas.borrarbasedatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnBorrarDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBorrarDB = findViewById(R.id.btnBorrarDB);

        btnBorrarDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplication().deleteDatabase("bd_inventarios");
            }
        });
    }
}
