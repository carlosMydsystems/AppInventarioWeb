package com.example.sistemas.appinventariodesconectado.Entidades;

import java.io.Serializable;

public class Articulo implements Serializable {

    private String codArticulo;
    private String articulo;
    private String uniMedida;
    private String equiOri;
    private String marca;
    private String codBarra1;
    private String codBarra2;
    private String codBarra3;
    private String equivalencia;


    public Articulo(String codArticulo, String articulo, String uniMedida, String equiOri, String marca,
                    String codBarra1, String codBarra2, String codBarra3, String equivalencia) {
        this.codArticulo = codArticulo;
        this.articulo = articulo;
        this.uniMedida = uniMedida;
        this.equiOri = equiOri;
        this.marca = marca;
        this.codBarra1 = codBarra1;
        this.codBarra2 = codBarra2;
        this.codBarra3 = codBarra3;
        this.equivalencia = equivalencia;
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

    public String getUniMedida() {
        return uniMedida;
    }

    public void setUniMedida(String uniMedida) {
        this.uniMedida = uniMedida;
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

    public String getCodBarra1() {
        return codBarra1;
    }

    public void setCodBarra1(String codBarra1) {
        this.codBarra1 = codBarra1;
    }

    public String getCodBarra2() {
        return codBarra2;
    }

    public void setCodBarra2(String codBarra2) {
        this.codBarra2 = codBarra2;
    }

    public String getCodBarra3() {
        return codBarra3;
    }

    public void setCodBarra3(String codBarra3) {
        this.codBarra3 = codBarra3;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }
}
