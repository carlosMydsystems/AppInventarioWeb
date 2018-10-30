package com.example.sistemas.apptaihengv20.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BuscarRequest extends StringRequest {

private static final String LOGIN_REQUEST_URL = "http://mydsystems.com/pruebaDaniel/Consultaprueba1.php";

private Map<String, String> params;

public BuscarRequest(String ruc, String tipobusqueda, Response.Listener <String> listener)

        {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("ruc", 	ruc);
        params.put("tipobusqueda", 	tipobusqueda);
        }


public Map<String, String> getParams() {
        return params;
        }
}