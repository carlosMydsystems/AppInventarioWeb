package com.example.carlosmiyashiro.busquedadanielv1.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ActualizarEstadoPedidoHojaRuta extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://mydsystems.com/pruebaDaniel/ActualizarEntregaPedido.php";

    private Map<String, String> params;

    public ActualizarEstadoPedidoHojaRuta( String EstadoPedido,String iddetallehojaruta, String documentos,  Response.Listener <String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("EstadoPedido", EstadoPedido);
        params.put("iddetallehojaruta", iddetallehojaruta);
        params.put("documentos", documentos);
    }

    public Map <String, String> getParams() {
        return params;
    }
}