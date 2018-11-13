package com.example.carlosmiyashiro.busquedadanielv1.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ListtaDocumentosRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://mydsystems.com/pruebaDaniel/ConsultaListaDocumento.php";

    private Map<String, String> params;

    public ListtaDocumentosRequest(String idCliente,Response.Listener <String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("idCliente",idCliente);
    }

    public Map <String, String> getParams() {
        return params;
    }
}