package com.example.sistemas.appinventariodesconectado.Entidades;

import java.io.Serializable;

public class Presentacion implements Serializable {

    private String undMedida;
    private String desPresentacion;
    private String equivalencia;

    public Presentacion(String undMedida, String desPresentacion, String equivalencia) {

        this.undMedida = undMedida;
        this.desPresentacion = desPresentacion;
        this.equivalencia = equivalencia;
    }

    public Presentacion() {
    }

    public String getUndMedida() {
        return undMedida;
    }

    public void setUndMedida(String undMedida) {
        this.undMedida = undMedida;
    }

    public String getDesPresentacion() {
        return desPresentacion;
    }

    public void setDesPresentacion(String desPresentacion) {
        this.desPresentacion = desPresentacion;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }
}
