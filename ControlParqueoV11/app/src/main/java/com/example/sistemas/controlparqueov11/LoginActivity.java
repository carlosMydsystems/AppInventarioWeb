package com.example.sistemas.controlparqueov11;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.controlparqueov11.Entidades.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {


    EditText etusuario,etclave;
    Button btnlogeo;
    String imei;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usuario = new Usuario();
        etusuario = findViewById(R.id.etUsuario);
        etclave = findViewById(R.id.etClave);
        btnlogeo = findViewById(R.id.btnLogin);
        numbers = new ArrayList<String>();
        btnlogeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = etusuario.getText().toString();
                final String pass = etclave.getText().toString();
                // Este método permite hacer la verificación del usuario y la clave - se hace una invocación
                VerificarUsuario( user, pass);
            }
        });
    }

    private void VerificarUsuario(String User,String Clave) {

        // Metodo de escucha espera recibir un response

        consultarPermiso(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Recibe un objeto en Json
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");
                    etusuario.setText("");
                    etclave.setText("");
                    if (success){

                        usuario.setId_Usuario(jsonresponse.getString("Id_Usuario"));
                        usuario.setNickname(jsonresponse.getString("Usuario"));
                        usuario.setNombre(jsonresponse.getString("Nombre"));
                        usuario.setApellido(jsonresponse.getString("Apellido"));
                        usuario.setPerfil(jsonresponse.getString("Perfil"));
                        Intent pantalla1 = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Usuario",usuario);
                        pantalla1.putExtras(bundle);
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

        LoginRequest loginActivityRequest = new LoginRequest(User,Clave,imei,responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginActivityRequest);

    }

    private void consultarPermiso(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permission)) {

                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            imei = obtenerIMEI();
        }
    }


    // Con este método consultamos al usuario si nos puede dar acceso a leer los datos internos del móvil
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1: {

                // Validamos si el usuario acepta el permiso para que la aplicación acceda a los datos internos del equipo, si no denegamos el acceso
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    imei = obtenerIMEI();

                } else {

                    Toast.makeText(LoginActivity.this, "Has negado el permiso a la aplicación", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private String obtenerIMEI() {
        final TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Hacemos la validación de métodos, ya que el método getDeviceId() ya no se admite para android Oreo en adelante, debemos usar el método getImei()
            return telephonyManager.getImei();
        }
        else {
            return telephonyManager.getDeviceId();
        }

    }





}
