package com.example.sistemas.appinventariodesconectado.Entidades;

import java.io.Serializable;

public class CargaMasInventario implements Serializable {

    private String fecha;
    private String bodega;
    private String conteo;
    private String estado;

    public CargaMasInventario(String fecha, String bodega, String conteo, String estado) {

        this.fecha = fecha;
        this.bodega = bodega;
        this.conteo = conteo;
        this.estado = estado;
    }

    public CargaMasInventario() {
    }

    public String getConteo() { return conteo; }

    public void setConteo(String conteo) { this.conteo = conteo; }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getBodega() { return bodega; }

    public void setBodega(String bodega) { this.bodega = bodega; }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }
}
