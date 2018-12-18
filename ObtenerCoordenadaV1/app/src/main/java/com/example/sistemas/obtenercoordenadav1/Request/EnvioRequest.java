package com.example.sistemas.obtenercoordenadav1.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EnvioRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://mydsystems.com/pruebaDaniel/RegistrarUbicacion.php";

    private Map<String, String> params;

    public EnvioRequest( String Latitud,String Longitud, String Direccion, String FechaRegistro,String HoraRegistro,String Placa, Response.Listener <String> listener)

    {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Latitud", Latitud);
        params.put("Longitud", Longitud);
        params.put("Direccion", Direccion);
        params.put("FechaRegistro", FechaRegistro);
        params.put("HoraRegistro", HoraRegistro);
        params.put("Placa", Placa);
    }

    public Map <String, String> getParams() {
        return params;
    }
}