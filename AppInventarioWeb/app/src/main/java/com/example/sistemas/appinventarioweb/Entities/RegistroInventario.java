package com.example.sistemas.appinventarioweb.Entities;

import java.io.Serializable;

public class RegistroInventario implements Serializable {

    private String codArticulo;
    private String cantidad;
    private String fecha;
    private String hora;

    public RegistroInventario(String cantidad, String fecha, String hora) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.hora = hora;
    }

    public RegistroInventario() {
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
