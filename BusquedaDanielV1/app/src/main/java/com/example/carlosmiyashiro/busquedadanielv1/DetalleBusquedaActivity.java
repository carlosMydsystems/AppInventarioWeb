package com.example.carlosmiyashiro.busquedadanielv1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carlosmiyashiro.busquedadanielv1.Entidades.DetalleHojaRuta;
import com.example.carlosmiyashiro.busquedadanielv1.Request.ActualizarEstadoPedidoHojaRuta;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetalleBusquedaActivity extends AppCompatActivity {

    TextView tvcanal, tvcliente,tvdireccion,tvdistrito,tvtienda;
    Button btnregresar,btnregistrarasistencia;
    String estadopedido,iddetallehojaruta,Factura,Id_Cliente;
    Spinner spestadopedido;
    ArrayAdapter<String> ComboAdapter;
    ArrayList<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_busqueda);

        tvcanal = findViewById(R.id.tvcanal);
        tvcliente = findViewById(R.id.tvRazonSocial);
        tvdireccion = findViewById(R.id.tvDireccion);
        tvdistrito = findViewById(R.id.tvdistrito);
        spestadopedido = findViewById(R.id.spEstadoPedido);

        btnregistrarasistencia = findViewById(R.id.btnCtacte);
        btnregistrarasistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(DetalleBusquedaActivity.this);
                dialogo1.setTitle("¿DESEA REGISTRAR PEDIDO?");
                dialogo1.setMessage("Factura N°        : "+Factura + "\n" +
                "Estado Pedido : " + estadopedido);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptar();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        cancelar();
                    }
                });
                dialogo1.show();
            }
        });

        btnregresar = findViewById(R.id.btnListarDocumento);

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleBusquedaActivity.this,ListaDocumentosActivity
                        .class);
                intent.putExtra("Id_Cliente",Id_Cliente);
                startActivity(intent);
                finish();
            }
        });
       Bundle objetoEnviado = getIntent().getExtras();
        DetalleHojaRuta client;

        if (objetoEnviado!=null){

            client = (DetalleHojaRuta)objetoEnviado.getSerializable("DetalleHojaRuta");
            iddetallehojaruta = client.getIddetallehojaruta();
            tvcanal.setText(iddetallehojaruta);
            tvcliente.setText(client.getCliente());
            tvdireccion.setText(client.getDireccion());
            tvdistrito.setText(client.getDistrito());
            Id_Cliente = client.getCodCliente();
         // tvtienda.setText(client.getBultos());
            Factura = client.getFactura();


        }
    }

    private void ActualizarEstadoPedido() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success){

                        Intent pantalla1 = new Intent(DetalleBusquedaActivity.this,
                                MainActivity.class);
                        startActivity(pantalla1);
                        finish();

                    }else{
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                DetalleBusquedaActivity.this);
                        builder.setMessage("No se pudo registrar Asistencia")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ActualizarEstadoPedidoHojaRuta actualizarEstadoPedidoHojaRuta = new ActualizarEstadoPedidoHojaRuta(estadopedido,
                iddetallehojaruta,"Entregado", responseListener);
        RequestQueue queue = Volley.newRequestQueue(DetalleBusquedaActivity.this);
        queue.add(actualizarEstadoPedidoHojaRuta);
    }

    public void aceptar() {
        Toast t=Toast.makeText(this,"Bienvenido a probar el programa.", Toast.LENGTH_SHORT);
        ActualizarEstadoPedido();
        t.show();
    }

    public void cancelar() {
        finish();
    }


}
