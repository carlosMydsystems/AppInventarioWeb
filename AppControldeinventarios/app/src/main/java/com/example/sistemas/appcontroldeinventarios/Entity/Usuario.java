package com.example.sistemas.appcontroldeinventarios.Entity;

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


    public Usuario(String nombre, String user, String fechaActual, String tipoCambio, String moneda,
                   String codVendedor, String codAlmacen, String codTienda, String lugar) {
        this.nombre = nombre;
        this.user = user;
        this.fechaActual = fechaActual;
        this.tipoCambio = tipoCambio;
        this.moneda = moneda;
        this.codVendedor = codVendedor;
        this.codAlmacen = codAlmacen;
        this.codTienda = codTienda;
        this.lugar = lugar;
    }

    public Usuario() {
    }

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

