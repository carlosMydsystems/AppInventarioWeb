package com.example.carlosmiyashiro.busquedadanielv1;

        import android.content.Intent;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Switch;
        import android.widget.Toast;

        import java.util.Objects;

public class MenuPrincipal extends AppCompatActivity {
    Button btnControlhojaruta,btncontrolbultos,btnejemploconsulta;
    String usuario,clave,Perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnControlhojaruta = findViewById(R.id.btnControlHojaRuta);
        btncontrolbultos =  findViewById(R.id.btnControlBultos);
        btnejemploconsulta = findViewById(R.id.btnEjemploConsulta);
        usuario = (String) Objects.requireNonNull(getIntent().getExtras()).get("usuario");
        clave = (String) getIntent().getExtras().get("clave");
        Perfil = (String)getIntent().getExtras().get("Perfil");

        if (Perfil.equals("Analista")){

            btnControlhojaruta.setVisibility(View.GONE);


        }else if(Perfil.equals("Jefatura")){

            btnControlhojaruta.setVisibility(View.VISIBLE);
        }


        //Boton para ingresar al menu

        btncontrolbultos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(MenuPrincipal.this,ControlBultos.class);
                startActivity(intent);
                finish();
            }
        });

        // Boton para derivar al control de hojas de Ruta
        btnControlhojaruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Boton para hacer una consulta

        btnejemploconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuPrincipal.this,ejemploConsulta.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
