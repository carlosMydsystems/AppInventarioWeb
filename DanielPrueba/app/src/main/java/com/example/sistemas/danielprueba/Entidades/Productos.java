package com.example.sistemas.danielprueba.Entidades;

import java.io.Serializable;

public class Productos implements Serializable {

private String Codigo;
private String Nombre;
private String Stock;

    public Productos(String codigo, String nombre, String stock) {
        Codigo = codigo;
        Nombre = nombre;
        Stock = stock;
    }

    public Productos() {
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }
}
