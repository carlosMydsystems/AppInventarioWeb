package com.example.sistemas.appinventariolocaldb.Entities;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String nombre;
    private String user;
    private String fechaActual;
    private String tipoCambio;
    private String moneda;
    private String codVendedor;
    private String codAlmacen;
    private String codTienda;
    private String lugar;
    private String llave;
    private String conteo;
    private String clave;
    private String estado;


    public Usuario(String nombre, String user, String fechaActual, String tipoCambio, String moneda,
                   String codVendedor, String codAlmacen, String codTienda, String lugar, String llave,
                   String conteo, String clave, String estado) {
        this.nombre = nombre;
        this.user = user;
        this.fechaActual = fechaActual;
        this.tipoCambio = tipoCambio;
        this.moneda = moneda;
        this.codVendedor = codVendedor;
        this.codAlmacen = codAlmacen;
        this.codTienda = codTienda;
        this.lugar = lugar;
        this.llave = llave;
        this.conteo = conteo;
        this.clave = clave;
        this.estado = estado;
    }


    public Usuario() {
    }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    public String getClave() { return clave; }

    public void setClave(String clave) { this.clave = clave; }

    public String getLlave() { return llave; }

    public void setLlave(String llave) { this.llave = llave; }

    public String getConteo() { return conteo; }

    public void setConteo(String conteo) { this.conteo = conteo; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(String codVendedor) {
        this.codVendedor = codVendedor;
    }

    public String getCodAlmacen() {
        return codAlmacen;
    }

    public void setCodAlmacen(String codAlmacen) {
        this.codAlmacen = codAlmacen;
    }

    public String getCodTienda() {
        return codTienda;
    }

    public void setCodTienda(String codTienda) {
        this.codTienda = codTienda;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
