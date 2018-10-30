package com.example.carlosmiyashiro.busquedadanielv1.Entidades;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String usuario;
    private String clave;

    public Usuario() {

    }

    public Usuario(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
