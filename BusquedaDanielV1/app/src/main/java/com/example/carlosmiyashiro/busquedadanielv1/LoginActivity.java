package com.example.carlosmiyashiro.busquedadanielv1;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carlosmiyashiro.busquedadanielv1.Entidades.Usuario;
import com.example.carlosmiyashiro.busquedadanielv1.Request.LoginActivityRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText etusuario,etclave;
    Button btnlogeo;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etusuario = findViewById(R.id.etUsuario);
        etclave = findViewById(R.id.etClave);
        btnlogeo = findViewById(R.id.btnLogin);

        btnlogeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user = etusuario.getText().toString();
                final String pass = etclave.getText().toString();

                /*
                if ((user.equals("Carlos") && pass.equals("Mydsystems")) || (user.equals("Daniel") && pass.equals("Pepito"))  ){

                    Intent intent = new Intent(LoginActivity.this,MenuPrincipal.class);
                    intent.putExtra("usuario",user);
                    intent.putExtra("clave",pass);
                    startActivity(intent);
                    finish();
                }
                */
                // Este método permite hacer la verificación del usuario y la clave - se hace una invocación
                VerificarUsuario( user, pass);
            }
        });
    }


    private void VerificarUsuario(String User,String Clave) {

        // Metodo de escucha espera recibir un response

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Recibe un objeto en Json
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");
                    etusuario.setText("");
                    etclave.setText("");
                    Toast.makeText(LoginActivity.this, "Primera Parte", Toast.LENGTH_SHORT).show();
                    if (success){

                        Integer Id_usuario = jsonresponse.getInt("Id_Usuario");
                        String Perfil = jsonresponse.getString("Perfil");

                        Toast.makeText(LoginActivity.this,Id_usuario.toString() , Toast.LENGTH_SHORT).show();
                        Intent pantalla1 = new Intent(LoginActivity.this, MenuPrincipal.class);
                        pantalla1.putExtra("Perfil",Perfil);
                        pantalla1.putExtra("Id_usuario",Id_usuario);
                        startActivity(pantalla1);
                        finish();
                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Usuario o contraseña incorrecta")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // hace un llamado al metodo LoginActivityRequest
        LoginActivityRequest loginActivityRequest = new LoginActivityRequest(User,Clave,responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginActivityRequest);

    }
}
