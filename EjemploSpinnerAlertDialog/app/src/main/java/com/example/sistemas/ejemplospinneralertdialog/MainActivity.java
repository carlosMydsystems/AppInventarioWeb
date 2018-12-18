package com.example.sistemas.ejemplospinneralertdialog;

        import android.content.DialogInterface;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Spinner spopciones;
    Button btnAceptarSeleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spopciones = findViewById(R.id.spmuestra);
        btnAceptarSeleccion= findViewById(R.id.btnAceptar);

        btnAceptarSeleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }
}
