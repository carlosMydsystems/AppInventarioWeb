package com.example.sistemas.appinventariodesconectado.Entidades;

import java.io.Serializable;

public class Inventario implements Serializable {

    private String fecha;
    private String almacen;
    private String conteo;
    private String codArticulo;
    private String unidadMedidaOrigen;
    private String equivalencia;
    private String undBarraLec;
    private String codBarraLec;
    private String equiBarraLec;
    private String cantDigitadaLec;
    private String factor;
    private String codUsuario;
    private String fechayhora;
    private String estado;


    public Inventario(String fecha, String almacen, String conteo, String codArticulo, String unidadMedidaOrigen,
                      String equivalencia, String undBarraLec, String codBarraLec, String equiBarraLec,
                      String cantDigitadaLec, String factor, String codUsuario, String fechayhora, String estado) {
        this.fecha = fecha;
        this.almacen = almacen;
        this.conteo = conteo;
        this.codArticulo = codArticulo;
        this.unidadMedidaOrigen = unidadMedidaOrigen;
        this.equivalencia = equivalencia;
        this.undBarraLec = undBarraLec;
        this.codBarraLec = codBarraLec;
        this.equiBarraLec = equiBarraLec;
        this.cantDigitadaLec = cantDigitadaLec;
        this.factor = factor;
        this.codUsuario = codUsuario;
        this.fechayhora = fechayhora;
        this.estado = estado;
    }

    public Inventario() {
    }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    public String getFechayhora() { return fechayhora; }

    public void setFechayhora(String fechayhora) { this.fechayhora = fechayhora; }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getConteo() {
        return conteo;
    }

    public void setConteo(String conteo) {
        this.conteo = conteo;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getUnidadMedidaOrigen() {
        return unidadMedidaOrigen;
    }

    public void setUnidadMedidaOrigen(String unidadMedidaOrigen) {
        this.unidadMedidaOrigen = unidadMedidaOrigen;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }

    public String getUndBarraLec() {
        return undBarraLec;
    }

    public void setUndBarraLec(String undBarraLec) {
        this.undBarraLec = undBarraLec;
    }

    public String getCodBarraLec() {
        return codBarraLec;
    }

    public void setCodBarraLec(String codBarraLec) {
        this.codBarraLec = codBarraLec;
    }

    public String getEquiBarraLec() {
        return equiBarraLec;
    }

    public void setEquiBarraLec(String equiBarraLec) {
        this.equiBarraLec = equiBarraLec;
    }

    public String getCantDigitadaLec() {
        return cantDigitadaLec;
    }

    public void setCantDigitadaLec(String cantDigitadaLec) {
        this.cantDigitadaLec = cantDigitadaLec;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }
}
