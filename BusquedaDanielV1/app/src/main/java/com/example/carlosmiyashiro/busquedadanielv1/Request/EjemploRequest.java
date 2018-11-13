package com.example.carlosmiyashiro.busquedadanielv1.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EjemploRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://www.taiheng.com.pe:8494/th/ConsultapruebaOracle.php";

    private Map<String, String> params;

    public EjemploRequest(Response.Listener <String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

    }

    public Map <String, String> getParams() {
        return params;
    }

}