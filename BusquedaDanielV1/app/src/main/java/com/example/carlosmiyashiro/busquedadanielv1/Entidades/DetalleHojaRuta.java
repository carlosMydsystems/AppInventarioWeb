package com.example.carlosmiyashiro.busquedadanielv1.Entidades;

import java.io.Serializable;

public class DetalleHojaRuta implements Serializable{

    private String iddetallehojaruta;
    private String numerohojaruta;
    private String cliente;
    private String direccion;
    private String telefono;
    private String bultos;
    private String estado;
    private String factura;
    private String formaPago;
    private String guiaRemision;
    private String pedido;
    private String importe;
    private String observaciones;

    public DetalleHojaRuta(String iddetallehojaruta, String numerohojaruta, String cliente,
                           String direccion, String telefono, String bultos, String estado,
                           String factura, String formaPago, String guiaRemision, String pedido,
                           String importe, String observaciones) {
        this.iddetallehojaruta = iddetallehojaruta;
        this.numerohojaruta = numerohojaruta;
        this.cliente = cliente;
        this.direccion = direccion;
        this.telefono = telefono;
        this.bultos = bultos;
        this.estado = estado;
        this.factura = factura;
        this.formaPago = formaPago;
        this.guiaRemision = guiaRemision;
        this.pedido = pedido;
        this.importe = importe;
        this.observaciones = observaciones;
    }

    public DetalleHojaRuta() {
    }


    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(String guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFactura() {return factura;}

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getNumerohojaruta() {
        return numerohojaruta;
    }

    public String getIddetallehojaruta() {
        return iddetallehojaruta;
    }

    public void setIddetallehojaruta(String iddetallehojaruta) {
        this.iddetallehojaruta = iddetallehojaruta;
    }

    public void setNumerohojaruta(String numerohojaruta) {
        this.numerohojaruta = numerohojaruta;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getBultos() {
        return bultos;
    }

    public void setBultos(String bultos) {
        this.bultos = bultos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}