package com.example.sistemas.appcontroldeinventarios.Entity;

import java.io.Serializable;

public class Producto implements Serializable {

    private String idProducto;
    private String codProducto;
    private String descripcion;
    private String generico;
    private String marca;
    private String tipo;
    private String stock;
    private String precio;
    private String unidad;
    private String estado;


    public Producto(String idProducto, String codProducto, String descripcion, String generico,
                    String marca, String tipo, String stock, String precio, String unidad, String estado) {
        this.idProducto = idProducto;
        this.codProducto = codProducto;
        this.descripcion = descripcion;
        this.generico = generico;
        this.marca = marca;
        this.tipo = tipo;
        this.stock = stock;
        this.precio = precio;
        this.unidad = unidad;
        this.estado = estado;
    }

    public Producto() {
    }

    public String getUnidad() {
        return unidad;
    }

    public String getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(String codProducto) {
        this.codProducto = codProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenerico() {
        return generico;
    }

    public void setGenerico(String generico) {
        this.generico = generico;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}