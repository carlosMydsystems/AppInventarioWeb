package com.example.sistemas.taihengapp.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivityRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://mydsystems.com/pruebaDaniel/LoginSystem.php";

    private Map<String, String> params;

    public LoginActivityRequest(String Usuario, String Clave, Response.Listener <String> listener)

    {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("Usuario", Usuario);
        params.put("Clave", Clave);

    }

    public Map<String, String> getParams() {
        return params;
    }
}