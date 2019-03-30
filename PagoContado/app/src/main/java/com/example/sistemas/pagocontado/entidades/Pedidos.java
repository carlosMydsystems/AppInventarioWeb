package com.example.sistemas.pagocontado.entidades;

import java.io.Serializable;

public class Pedidos implements Serializable {

    private String idPedido;
    private String cliente;
    private String fecha;

    public Pedidos(String idPedido, String cliente, String fecha) {
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.fecha = fecha;
    }

    public Pedidos() {
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
