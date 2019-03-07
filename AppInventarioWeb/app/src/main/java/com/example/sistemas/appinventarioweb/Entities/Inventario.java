package com.example.sistemas.appinventarioweb.Entities;

import java.io.Serializable;

public class Inventario implements Serializable {

    private String id;
    private String codigoProducto;
    private String cantidad;
    private String descripcion;
    private String idUsuario;

    public Inventario(String id, String codigoProducto, String cantidad, String descripcion, String idUsuario) {
        this.id = id;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
    }

    public Inventario() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
