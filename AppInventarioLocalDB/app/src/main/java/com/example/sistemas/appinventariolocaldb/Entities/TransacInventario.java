package com.example.sistemas.appinventariolocaldb.Entities;

public class TransacInventario {

    private String codigoProducto;
    private String idUsuario;
    private String cantidad;
    private String unidad;
    private String marca;


    public TransacInventario(String codigoProducto, String idUsuario, String cantidad, String unidad,
                             String marca) {
        this.codigoProducto = codigoProducto;
        this.idUsuario = idUsuario;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.marca = marca;
    }

    public TransacInventario() {
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
