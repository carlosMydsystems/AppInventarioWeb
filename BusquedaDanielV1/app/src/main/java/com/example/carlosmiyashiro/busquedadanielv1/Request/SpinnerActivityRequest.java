package com.example.carlosmiyashiro.busquedadanielv1.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SpinnerActivityRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://mydsystems.com/pruebaDaniel/ActualizarEstadoPedido.php";

    private Map<String, String> params;

    public SpinnerActivityRequest( String idlistadocumento,String estadoDocumento, Response.Listener <String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idlistadocumento", idlistadocumento);
        params.put("estadoDocumento", estadoDocumento);

    }

    public Map <String, String> getParams() {
        return params;
    }
}