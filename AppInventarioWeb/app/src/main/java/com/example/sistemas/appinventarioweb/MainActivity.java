package com.example.sistemas.appinventarioweb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.sistemas.appinventarioweb.Entities.Inventario;
import com.example.sistemas.appinventarioweb.Entities.RegistroInventario;
import com.example.sistemas.appinventarioweb.Entities.Usuario;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Usuario usuario;
    ArrayList<Inventario> listaProductosInventario;
    TextView tvnombreusuario;
    Button btntomainventario, btnconsultalectura, btnsalir;
    RegistroInventario registroInventario;
    String acumulador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvnombreusuario =  findViewById(R.id.tvNombreUsuario2);
        btntomainventario = findViewById(R.id.btnInventario);
        btnconsultalectura = findViewById(R.id.btnconsultalectura);
        btnsalir = findViewById(R.id.btnSalirSistema);

        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        acumulador = getIntent().getStringExtra("Acumulador");


        tvnombreusuario.setText("USUARIO : " + usuario.getNombre());

        btntomainventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ConsultarListaInvenrtario();

                Intent intent =  new Intent(MainActivity.this,RegistroInventarioActivity.class);
                intent.putExtra("Acumulador",acumulador);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });

        btnconsultalectura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Cargando...");
                progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);

                progressDialog.dismiss();
                Intent intent =  new Intent(MainActivity.this,ConsultaActivity.class);
                intent.putExtra("Acumulador",acumulador);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listaProductosInventario", listaProductosInventario);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();
            }
        });

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}