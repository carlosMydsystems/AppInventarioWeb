package com.example.sistemas.appinventarioweb.Entities;

import java.io.Serializable;

public class listaConsulta implements Serializable {

    private String cantidadConsulta;
    private String fecha;
    private String hora;
    private String secuencia;

    public listaConsulta(String cantidadConsulta, String fecha, String hora, String secuencia) {
        this.cantidadConsulta = cantidadConsulta;
        this.fecha = fecha;
        this.hora = hora;
        this.secuencia = secuencia;
    }

    public listaConsulta() {
    }

    public String getCantidadConsulta() {
        return cantidadConsulta;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }

    public void setCantidadConsulta(String cantidadConsulta) {
        this.cantidadConsulta = cantidadConsulta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
