package com.example.sistemas.pagocontado;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.pagocontado.entidades.ClienteSucursal;
import com.example.sistemas.pagocontado.entidades.Clientes;
import com.example.sistemas.pagocontado.entidades.Productos;
import com.example.sistemas.pagocontado.entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class DetalleProductoActivity extends AppCompatActivity {

    TextView tvcodigoproducto,tvnombreproducto,tvalmacenproducto,tvstock,tvprecio,
            tvtotal,tvprecioreal,tvunidades,tvtasa,tvpreciorealjson;
    Productos productos;
    Button btnguardaryrevisar, btnguardaryagregar, btndverificarproducto;
    Clientes cliente;
    ArrayList<Productos> listaproductoselegidos;
    EditText etcantidadelegida;
    Double preciounitario,cantidad, Aux,validarStock,precioDouble,Descuento, precioUnitarioDouble,Aux1;
    String url,almacen,tipoPago,id_pedido,Index;
    ProgressDialog progressDialog;
    Productos producto;
    ArrayList<String> listaProducto;
    Usuario usuario;
    BigDecimal redondeado, precioBigTotal , precioBigUnitario;
    ImageButton imgbtnvolverdetalleproducto;
    ArrayList<ClienteSucursal> listaClienteSucursal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        etcantidadelegida =  findViewById(R.id.etCantProdElegida);
        listaproductoselegidos = new ArrayList<>();
        productos  = new Productos();

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.'); // Se define el simbolo para el separador decimal
        simbolos.setGroupingSeparator(',');// Se define el simbolo para el separador de los miles
        final DecimalFormat formateador = new DecimalFormat("###,##0.00",simbolos); // Se crea el formato del numero con los simbolo

        // se recibe los datos de los productos y del que se han encontrado en el otro intent

        productos = (Productos) getIntent().getSerializableExtra("Producto");
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        cliente = (Clientes)getIntent().getSerializableExtra("Cliente");
        almacen =  getIntent().getStringExtra("Almacen");
        tipoPago = getIntent().getStringExtra("TipoPago");
        id_pedido = getIntent().getStringExtra("id_pedido");
        Index = getIntent().getStringExtra("Index");
        listaproductoselegidos = (ArrayList<Productos>) getIntent().getSerializableExtra("listaproductoselegidos");
        listaClienteSucursal = (ArrayList<ClienteSucursal>) getIntent().getSerializableExtra("listaClienteSucursal");

        // Se referencia a todas las partes del XML asociado al ProveedorActivity
        tvcodigoproducto =  findViewById(R.id.tvCofigoProducto);
        tvnombreproducto = findViewById(R.id.tvNomProdElegido);
        tvalmacenproducto = findViewById(R.id.tvAlmProdElegido);
        btndverificarproducto = findViewById(R.id.btnVerificar);
        tvprecioreal = findViewById(R.id.tvPrecioReal);
        tvunidades = findViewById(R.id.tvUnidad);
        tvtasa =  findViewById(R.id.tvTasa);
        imgbtnvolverdetalleproducto = findViewById(R.id.ibVolverDetalleProducto);
        tvpreciorealjson = findViewById(R.id.tvPrecioRealJson);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        btndverificarproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btndverificarproducto.setVisibility(View.GONE);
                progressDialog =  new ProgressDialog(DetalleProductoActivity.this);
                progressDialog.setMessage("... Por favor esperar");
                progressDialog.setCancelable(false);
                progressDialog.show();
                if(etcantidadelegida.getText().toString().equals("")|| etcantidadelegida.getText().toString().equals("0")){
                    progressDialog.dismiss();
                    Toast.makeText(DetalleProductoActivity.this, "Por favor ingrese una cantidad valida", Toast.LENGTH_SHORT).show();
                }else {
                    VerificarCantidad(etcantidadelegida.getText().toString());
                }
            }
        });

        // Se hace referencia a cada uno de los TextView del XML

        tvstock  =findViewById(R.id.tvStockElegido);
        tvprecio = findViewById(R.id.tvPrecioElegido);
        tvtotal = findViewById(R.id.tvTotalElegido);


        tvstock.setText(productos.getStock());

        Aux1 = Double.valueOf(tvstock.getText().toString());
        tvstock.setText(formateador.format((double) Aux1) + " ");


        tvunidades.setText(productos.getUnidad());

        if (tvstock.getText() == null){

            tvstock.setText("0.0");
        }

        else if (etcantidadelegida.getText()== null){

            etcantidadelegida.setText("0.0");

        }
        btnguardaryrevisar = findViewById(R.id.btnGuardarrevisar);
        btnguardaryagregar = findViewById(R.id.btnGuardaryagregar);
        imgbtnvolverdetalleproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetalleProductoActivity.this, BuscarProductoActivity.class);

                intent.putExtra("TipoPago",tipoPago);
                intent.putExtra("validador","false");
                intent.putExtra("Almacen",almacen);
                intent.putExtra("Index",Index);
                intent.putExtra("id_pedido",id_pedido);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cliente",cliente);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("listaproductoselegidos",listaproductoselegidos);
                intent.putExtras(bundle1);
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("Usuario",usuario);
                intent.putExtras(bundle3);
                Bundle bundle4 = new Bundle();
                bundle4.putSerializable("listaClienteSucursal",listaClienteSucursal);
                intent.putExtras(bundle4);
                startActivity(intent);
                finish();

            }
        });

        btnguardaryrevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double stockDouble = Double.valueOf(tvstock.getText().toString().replace(",",""));
                Double cantidadElegida = Double.valueOf(etcantidadelegida.getText().toString());

                validarStock = stockDouble - cantidadElegida;

                //if (validarStock < 0) {
                if (false) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(DetalleProductoActivity.this)
                            .setCancelable(false)
                            .setMessage("El Stock es insuficiente, desea elegir otro articulo");

                    if (stockDouble >0.0){
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    }

                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(DetalleProductoActivity.this, BuscarProductoActivity.class);

                            intent.putExtra("TipoPago",tipoPago);
                            intent.putExtra("validador","false");
                            intent.putExtra("Almacen",almacen);
                            intent.putExtra("Index",Index);
                            intent.putExtra("id_pedido",id_pedido);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Cliente",cliente);
                            intent.putExtras(bundle);
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable("listaproductoselegidos",listaproductoselegidos);
                            intent.putExtras(bundle1);
                            Bundle bundle3 = new Bundle();
                            bundle3.putSerializable("Usuario",usuario);
                            intent.putExtras(bundle3);
                            Bundle bundle4 = new Bundle();
                            bundle4.putSerializable("listaClienteSucursal",listaClienteSucursal);
                            intent.putExtras(bundle4);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.create()
                            .show();

                } else {


                    if (etcantidadelegida.getText().toString().equals("")) {
                    } else {

                        if (productos.getNumPromocion().trim().equals("null")){

                            String trama = id_pedido + "|D|" + Index + "|" + etcantidadelegida.getText() + "|" +
                                    productos.getCodigo() + "|" + tvpreciorealjson.getText().toString().replace(",","") +
                                    "|" + tvtasa.getText().toString().trim() + "||"+productos.getPresentacion()+
                                    "|"+productos.getEquivalencia()+"|N";  // Tasas
                            ActualizarProducto(trama);

                        }else {

                            String trama = id_pedido + "|D|" + Index + "|" + etcantidadelegida.getText() + "|" +
                                    productos.getCodigo() + "|" + tvpreciorealjson.getText().toString().replace(",", "") +
                                    "|" + tvtasa.getText().toString().trim() + "|" + productos.getNumPromocion().trim() + "|" + productos.getPresentacion() +
                                    "|" + productos.getEquivalencia() + "|N";  // Tasas
                            ActualizarProducto(trama);

                        }

                        productos.setCantidad(etcantidadelegida.getText().toString());
                        preciounitario = Double.valueOf(tvprecio.getText().toString().replace(",",""));
                        cantidad = Double.valueOf(etcantidadelegida.getText().toString());   // Cambio
                        redondeado = new BigDecimal(preciounitario).setScale(2, RoundingMode.HALF_EVEN);
                        productos.setPrecio(""+redondeado);
                        productos.setPrecioAcumulado(tvtotal.getText().toString()); // Se hace la definicion del precio que se va ha acumular
                        productos.setEstado(String.valueOf(redondeado)); // Se define la cantidad que se debe de tene
                        productos.setIndice(Integer.valueOf(Index));

                        listaproductoselegidos.add(productos);

                        Intent intent = new Intent(DetalleProductoActivity.this, bandejaProductosActivity.class);
                        intent.putExtra("TipoPago", tipoPago);
                        intent.putExtra("validador", "true");
                        intent.putExtra("Index", Index);
                        intent.putExtra("id_pedido", id_pedido);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("listaProductoselegidos", listaproductoselegidos);
                        intent.putExtras(bundle);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("Cliente", cliente);
                        intent.putExtras(bundle1);
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("Usuario", usuario);
                        intent.putExtras(bundle2);
                        Bundle bundle3 = new Bundle();
                        bundle3.putSerializable("Almacen", almacen);
                        intent.putExtras(bundle3);
                        Bundle bundle4 = new Bundle();
                        bundle4.putSerializable("listaClienteSucursal",listaClienteSucursal);
                        intent.putExtras(bundle4);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        btnguardaryagregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Double stockDouble = Double.valueOf(tvstock.getText().toString().replace(",",""));
                Double cantidadElegida = Double.valueOf(etcantidadelegida.getText().toString());
                validarStock = stockDouble - cantidadElegida;

                //if (validarStock < 0) {
                if (false) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(DetalleProductoActivity.this)
                            .setCancelable(false)
                            .setMessage("El Stock es insuficiente, desea elegir otro articulo");

                    if (stockDouble > 0.0) {
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    }
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(DetalleProductoActivity.this, BuscarProductoActivity.class);
                            intent.putExtra("TipoPago",tipoPago);
                            intent.putExtra("validador","false");
                            intent.putExtra("Almacen",almacen);
                            intent.putExtra("Index",Index);
                            intent.putExtra("id_pedido",id_pedido);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Cliente",cliente);
                            intent.putExtras(bundle);
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable("listaproductoselegidos",listaproductoselegidos);
                            intent.putExtras(bundle1);
                            Bundle bundle3 = new Bundle();
                            bundle3.putSerializable("Usuario",usuario);
                            intent.putExtras(bundle3);
                            Bundle bundle4 = new Bundle();
                            bundle4.putSerializable("listaClienteSucursal",listaClienteSucursal);
                            intent.putExtras(bundle4);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.create()
                            .show();

                } else {

                    if (etcantidadelegida.getText().toString().equals("")) {

                    } else {

                        if (productos.getNumPromocion().trim().equals("null")){

                            String trama = id_pedido + "|D|" + Index + "|" + etcantidadelegida.getText() + "|" +
                                    productos.getCodigo() + "|" + tvpreciorealjson.getText().toString().replace(",","") +
                                    "|" + tvtasa.getText().toString().trim() + "||"+productos.getPresentacion()+
                                    "|"+productos.getEquivalencia()+"|N";  // Tasas
                            ActualizarProducto(trama);

                        }else {

                            String trama = id_pedido + "|D|" + Index + "|" + etcantidadelegida.getText() + "|" +
                                    productos.getCodigo() + "|" + tvpreciorealjson.getText().toString().replace(",", "") +
                                    "|" + tvtasa.getText().toString().trim() + "|" + productos.getNumPromocion().trim() + "|" + productos.getPresentacion() +
                                    "|" + productos.getEquivalencia() + "|N";  // Tasas
                            ActualizarProducto(trama);
                        }

                        productos.setCantidad(etcantidadelegida.getText().toString());
                        preciounitario = Double.valueOf(producto.getPrecio());
                        cantidad = Double.valueOf(etcantidadelegida.getText().toString());
                        productos.setPrecio(tvprecio.getText().toString());
                        productos.setIndice(Integer.valueOf(Index));
                        productos.setPrecioAcumulado(tvtotal.getText().toString()); // Se hace la definicion del precio que se va ha acumular
                        productos.setEstado(String.valueOf(cantidad)); // Se define la cantidad que se debe de tener
                        listaproductoselegidos.add(productos);
                        Index = String.valueOf(Integer.valueOf(Index)+1);
                        Intent intent = new Intent(DetalleProductoActivity.this, BuscarProductoActivity.class);

                        intent.putExtra("TipoPago", tipoPago);
                        intent.putExtra("validador", "true");
                        intent.putExtra("Index",Index);
                        intent.putExtra("id_pedido",id_pedido);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("listaproductoselegidos", listaproductoselegidos);
                        intent.putExtras(bundle);
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("Cliente", cliente);
                        intent.putExtras(bundle1);
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("Usuario", usuario);
                        intent.putExtras(bundle2);
                        Bundle bundle3 = new Bundle();
                        bundle3.putSerializable("Almacen", almacen);
                        intent.putExtras(bundle3);
                        Bundle bundle4 = new Bundle();
                        bundle4.putSerializable("listaClienteSucursal",listaClienteSucursal);
                        intent.putExtras(bundle4);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        tvcodigoproducto.setText(productos.getCodigo());
        tvnombreproducto.setText(productos.getDescripcion());
        tvalmacenproducto.setText(productos.getAlmacen());

        tvalmacenproducto.setText(almacen);

        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                btndverificarproducto.setVisibility(View.VISIBLE);
                btnguardaryagregar.setVisibility(View.GONE);
                btnguardaryrevisar.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (etcantidadelegida.getText().toString().equals("") || etcantidadelegida.getText().toString().equals("0")){

                    btndverificarproducto.setEnabled(false);
                    Aux = 0d;
                    tvtotal.setText("");
                    Toast.makeText(DetalleProductoActivity.this, "Por favor ingrese un valor valido", Toast.LENGTH_SHORT).show();

                }else {
                    btndverificarproducto.setEnabled(true);
                }
            }
        };

        etcantidadelegida.addTextChangedListener(textWatcher);
    }

    private void VerificarCantidad(String cantidad) {

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.'); // Se define el simbolo para el separador decimal
        simbolos.setGroupingSeparator(',');// Se define el simbolo para el separador de los miles
        final DecimalFormat formateador = new DecimalFormat("###,##0.00",simbolos); // Se crea el formato del numero con los simbolo

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url = "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_WEB_HERRAMIENTAS.FN_WS_CONSULTAR_PRODUCTO&variables=%27"+almacen+"|"+usuario.
                getLugar()+"|"+productos.getCodigo()+"||"+cliente.getCodCliente()+"|||"+cantidad+"%27";

        listaProducto = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        btnguardaryagregar.setVisibility(View.VISIBLE);
                        btnguardaryrevisar.setVisibility(View.VISIBLE);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            if (success){

                                for(int i=0;i<jsonArray.length();i++) {
                                    producto = new Productos();
                                    jsonObject = jsonArray.getJSONObject(i);
                                    producto.setCodigo(jsonObject.getString("COD_ARTICULO"));
                                    producto.setMarca(jsonObject.getString("DES_MARCA"));
                                    producto.setDescripcion(jsonObject.getString("DES_ARTICULO"));
                                    producto.setPrecio(jsonObject.getString("PRECIO_SOLES"));
                                    BigDecimal precioBig1 = new BigDecimal(producto.getPrecio());
                                    precioBig1 = precioBig1.setScale(2,RoundingMode.HALF_EVEN);
                                    Descuento = (1 - Double.valueOf(jsonObject.getString("TASA_DESCUENTO"))/100  );
                                    precioDouble = Double.valueOf(precioBig1.toString()) * Descuento *  Double.valueOf(etcantidadelegida.getText().toString());
                                    precioBigTotal = new BigDecimal(precioDouble.toString());
                                    precioBigTotal = precioBigTotal.setScale(2,RoundingMode.HALF_EVEN);
                                    tvtotal.setText(""+precioBigTotal);
                                    precioUnitarioDouble = Double.valueOf(producto.getPrecio()) * Descuento;
                                    precioBigUnitario = new BigDecimal(precioUnitarioDouble.toString());
                                    precioBigUnitario = precioBigUnitario.setScale(4,RoundingMode.HALF_EVEN);
                                    tvprecio.setText(precioBigUnitario.toString());
                                    tvpreciorealjson.setText(jsonObject.getString("PRECIO_SOLES"));
                                    producto.setStock(jsonObject.getString("STOCK_DISPONIBLE"));
                                    producto.setUnidad(jsonObject.getString("UND_MEDIDA"));
                                    producto.setEquivalencia(jsonObject.getString("EQUIVALENCIA"));
                                    producto.setTasaDescuento(jsonObject.getString("TASA_DESCUENTO"));
                                    producto.setPresentacion(jsonObject.getString("COD_PRESENTACION"));
                                    producto.setAlmacen(almacen);
                                    tvtasa.setText(producto.getTasaDescuento());

                                    if (etcantidadelegida.getText().toString().equals("")){

                                        Toast.makeText(DetalleProductoActivity.this, "Por favor ingrese un valor valido", Toast.LENGTH_SHORT).show();

                                    }else {

                                        tvunidades.setText(producto.getUnidad().toUpperCase());
                                        Double Aux = Double.valueOf(producto.getStock());
                                        tvstock.setText(formateador.format((double)Aux) + " ");
                                    }
                                }
                            }else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DetalleProductoActivity.this);

                                builder.setCancelable(false)
                                        .setMessage("No se llego a encontrar el registro")
                                        .setNegativeButton("Aceptar",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) { e.printStackTrace(); }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void ActualizarProducto(String trama) {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionTestMovil.php?funcion=PKG_WEB_HERRAMIENTAS.FN_WS_REGISTRA_TRAMA_MOVIL&variables='"+trama+"'";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        response = response.trim();

                        if (response.equals("OK")){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}