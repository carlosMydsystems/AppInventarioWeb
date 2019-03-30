package com.example.sistemas.pagocontado.entidades;

import java.io.Serializable;

public class ClienteSucursal implements Serializable {

    private String codSucursal;
    private String direccionSucursal;
    private String distrito;
    private String provincia;
    private String departamento;

    public ClienteSucursal(String codSucursal, String direccionSucursal, String distrito, String provincia,
                           String departamento) {

        this.codSucursal = codSucursal;
        this.direccionSucursal = direccionSucursal;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
    }

    public ClienteSucursal() {
    }

    public String getCodSucursal() {
        return codSucursal;
    }

    public void setCodSucursal(String codSucursal) {
        this.codSucursal = codSucursal;
    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
