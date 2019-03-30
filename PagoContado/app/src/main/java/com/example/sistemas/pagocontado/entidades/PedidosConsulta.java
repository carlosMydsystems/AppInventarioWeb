package com.example.sistemas.pagocontado.entidades;

import java.io.Serializable;

public class PedidosConsulta implements Serializable {

    private String nroPedido;
    private String cliente;
    private String moneda;
    private String totalImporte;
    private String items;
    private String estado;

    public PedidosConsulta(String nroPedido, String cliente, String moneda, String totalImporte, String items, String estado) {
        this.nroPedido = nroPedido;
        this.cliente = cliente;
        this.moneda = moneda;
        this.totalImporte = totalImporte;
        this.items = items;
        this.estado = estado;
    }

    public PedidosConsulta() {
    }

    public String getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(String nroPedido) {
        this.nroPedido = nroPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTotalImporte() {
        return totalImporte;
    }

    public void setTotalImporte(String totalImporte) {
        this.totalImporte = totalImporte;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
