package com.example.sistemas.pagocontado.entidades;

import java.io.Serializable;

public class FormaPago implements Serializable {

    private String codFormaPago;
    private String descripcionFormaPago;

    public FormaPago(String codFormaPago, String descripcionFormaPago) {
        this.codFormaPago = codFormaPago;
        this.descripcionFormaPago = descripcionFormaPago;
    }

    public FormaPago() {
    }

    public String getCodFormaPago() { return codFormaPago; }

    public void setCodFormaPago(String codFormaPago) { this.codFormaPago = codFormaPago; }

    public String getDescripcionFormaPago() { return descripcionFormaPago; }

    public void setDescripcionFormaPago(String descripcionFormaPago) { this.descripcionFormaPago = descripcionFormaPago; }
}
