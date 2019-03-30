package com.example.sistemas.appinventariolocaldb.Entities;

import java.io.Serializable;
import java.security.SecureRandom;

public class BarrasPresentacion implements Serializable {

    private String codArticulo;
    private String codBarras;
    private String undMedida;

    public BarrasPresentacion(String codArticulo, String codBarras, String undMedida) {
        this.codArticulo = codArticulo;
        this.codBarras = codBarras;
        this.undMedida = undMedida;
    }

    public BarrasPresentacion() {
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getUndMedida() {
        return undMedida;
    }

    public void setUndMedida(String undMedida) {
        this.undMedida = undMedida;
    }
}
