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
    ArrayList<RegistroInventario> listainventario;
    Button btntomainventario, btnconsultalectura, btnsalir;
    RegistroInventario registroInventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        tvnombreusuario =  findViewById(R.id.tvNombreUsuario2);
        btntomainventario = findViewById(R.id.btnInventario);
        btnconsultalectura = findViewById(R.id.btnconsultalectura);
        btnsalir = findViewById(R.id.btnSalirSistema);

        tvnombreusuario.setText("USUARIO : " + usuario.getNombre());

        listainventario = new ArrayList<>();
        registroInventario = new RegistroInventario();

        registroInventario.setCantidad("12");
        registroInventario.setFecha("06/03/19");
        registroInventario.setHora("19:02");

        listainventario.add(registroInventario);

        registroInventario = new RegistroInventario();

        registroInventario.setCantidad("16");
        registroInventario.setFecha("06/03/19");
        registroInventario.setHora("19:03");

        listainventario.add(registroInventario);

        registroInventario = new RegistroInventario();

        registroInventario.setCantidad("6");
        registroInventario.setFecha("06/03/19");
        registroInventario.setHora("19:05");
        listainventario.add(registroInventario);

        btntomainventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ConsultarListaInvenrtario();

                Intent intent =  new Intent(MainActivity.this,RegistroInventarioActivity.class);
                intent.putExtra("Acumulador","1");
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("listaProductosInventario", listaProductosInventario);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("listainventario",listainventario);
                intent.putExtras(bundle2);
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