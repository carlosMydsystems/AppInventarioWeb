package com.example.sistemas.appbasedatos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistemas.appbasedatos.Utilidades.ConexionSQLiteHelper;
import com.example.sistemas.appbasedatos.Utilidades.UtilitarioDB;

import static com.example.sistemas.appbasedatos.Utilidades.UtilitarioDB.CAMPO_ID;

public class ConsultaActivity extends AppCompatActivity {

    Button btnconsultar, btnactualiar , btneliminar;
    EditText etdocumento, etnomnbre, ettelefono;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        btnconsultar =  findViewById(R.id.btnConsultar);
        btnactualiar = findViewById(R.id.btnactualizar);
        btneliminar = findViewById(R.id.btnEliminar);
        etdocumento = findViewById(R.id.etDocumento);
        etnomnbre = findViewById(R.id.etNombre);
        ettelefono = findViewById(R.id.etTelefono);
        btnconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conn = new ConexionSQLiteHelper(getApplicationContext(),"db_Usuario",null,1);

                SQLiteDatabase db = conn.getReadableDatabase();
                String[] parametros ={etdocumento.getText().toString()};
                String[] campos = {UtilitarioDB.CAMPO_NOMBRE,UtilitarioDB.CAMPO_TELEFONO};

                try {
                    Cursor cursor = db.query(UtilitarioDB.TABLA_USUARIOS, campos, UtilitarioDB.CAMPO_ID + "=?", parametros, null, null, null);
                    cursor.moveToFirst();

                    etnomnbre.setText(cursor.getString(0));
                    ettelefono.setText(cursor.getString(1));
                    cursor.close();
                } catch (Exception e){

                    Toast.makeText(ConsultaActivity.this, "EL registro no se encuentra", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
