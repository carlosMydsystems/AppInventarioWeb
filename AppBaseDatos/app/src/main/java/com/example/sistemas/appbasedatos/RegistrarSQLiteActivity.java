package com.example.sistemas.appbasedatos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistemas.appbasedatos.Entities.Usuario;
import com.example.sistemas.appbasedatos.Utilidades.ConexionSQLiteHelper;
import com.example.sistemas.appbasedatos.Utilidades.UtilitarioDB;

import java.util.ArrayList;

public class RegistrarSQLiteActivity extends AppCompatActivity {

    EditText etid,etnombre,ettelefono;
    Button btnregistrarusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sqlite);

        etid = findViewById(R.id.etId);
        etnombre = findViewById(R.id.etNombre);
        ettelefono = findViewById(R.id.etTelefono);

        btnregistrarusuario = findViewById(R.id.btnRegistraUsuario);

        btnregistrarusuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ingresoDuro();

            }
        });
    }

    private void RegistrarUsuarioSql() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"db_Usuario",null,1);

        SQLiteDatabase db = conn.getWritableDatabase();

        String insert = "INSERT INTO "+ UtilitarioDB.TABLA_USUARIOS + "(" + UtilitarioDB.CAMPO_ID +
                "," + UtilitarioDB.CAMPO_NOMBRE + "," + UtilitarioDB.CAMPO_TELEFONO + ") VALUES ("+
                etid.getText().toString()+","+etnombre.getText().toString()+"," + ettelefono.getText().toString() + ")";

        Toast.makeText(this, "" + insert, Toast.LENGTH_LONG).show();

        db.execSQL(insert);
        db.close();
    }

    private void ingresoDuro(){

        ArrayList<Usuario> listadousuarios = new ArrayList<>();

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"db_Usuario",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        Usuario usuario = new Usuario();

        for (int i = 0 ; i < 7 ; i++){

            usuario.setId(i+1);
            usuario.setNombre("Primero " + i);
            usuario.setTelefono("123456 " + i);
            listadousuarios.add(usuario);
        }

        for (int i = 0 ; i< listadousuarios.size(); i ++) {

            ContentValues values = new ContentValues();
            values.put(UtilitarioDB.CAMPO_ID,listadousuarios.get(i).getId());
            values.put(UtilitarioDB.CAMPO_NOMBRE,listadousuarios.get(i).getNombre());
            values.put(UtilitarioDB.CAMPO_TELEFONO,listadousuarios.get(i).getTelefono());

            Long idresultante = db.insert(UtilitarioDB.TABLA_USUARIOS,UtilitarioDB.CAMPO_ID,values);
            Toast.makeText(this, ""+idresultante, Toast.LENGTH_SHORT).show();

        }
        db.close();

    }



}
