package com.example.sistemas.appinventarioweb.Entities;

import java.io.Serializable;

public class Articulo implements Serializable {

    private String codArticulo;
    private String articulo;
    private String uniMedidaOri;
    private String equiOri;
    private String marca;
    private String codBarra;
    private String uniMedida;
    private String equivalencia;
    private String equivalenciaNew;
    private String llave;
    private String conteo;

    public Articulo(String codArticulo, String articulo, String uniMedidaOri, String equiOri,
                    String marca, String codBarra, String uniMedida, String equivalencia,
                    String equivalenciaNew, String llave, String conteo) {
        this.codArticulo = codArticulo;
        this.articulo = articulo;
        this.uniMedidaOri = uniMedidaOri;
        this.equiOri = equiOri;
        this.marca = marca;
        this.codBarra = codBarra;
        this.uniMedida = uniMedida;
        this.equivalencia = equivalencia;
        this.equivalenciaNew = equivalenciaNew;
        this.llave = llave;
        this.conteo = conteo;
    }

    public Articulo() {
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getUniMedidaOri() {
        return uniMedidaOri;
    }

    public void setUniMedidaOri(String uniMedidaOri) {
        this.uniMedidaOri = uniMedidaOri;
    }

    public String getEquiOri() {
        return equiOri;
    }

    public void setEquiOri(String equiOri) {
        this.equiOri = equiOri;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public String getUniMedida() {
        return uniMedida;
    }

    public void setUniMedida(String uniMedida) {
        this.uniMedida = uniMedida;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }

    public String getEquivalenciaNew() {
        return equivalenciaNew;
    }

    public void setEquivalenciaNew(String equivalenciaNew) {
        this.equivalenciaNew = equivalenciaNew;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public String getConteo() {
        return conteo;
    }

    public void setConteo(String conteo) {
        this.conteo = conteo;
    }
}
