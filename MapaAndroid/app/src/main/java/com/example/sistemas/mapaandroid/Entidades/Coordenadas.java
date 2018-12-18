package com.example.sistemas.mapaandroid.Entidades;

import java.io.Serializable;

public class Coordenadas implements Serializable {


    private String id_Ubicacion;
    private String latitud;
    private String longitud;
    private String fechaRegistro;
    private  String horaRegistro;
    private String direccion;
    private String placa;

    public Coordenadas(String id_Ubicacion, String latitud, String longitud, String fechaRegistro,
                       String horaRegistro, String direccion, String placa) {
        this.id_Ubicacion = id_Ubicacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaRegistro = fechaRegistro;
        this.horaRegistro = horaRegistro;
        this.direccion = direccion;
        this.placa = placa;
    }

    public Coordenadas() {
    }

    public String getId_Ubicacion() {
        return id_Ubicacion;
    }

    public void setId_Ubicacion(String id_Ubicacion) {
        this.id_Ubicacion = id_Ubicacion;
    }

    public String getPlaca() { return placa; }

    public void setPlaca(String placa) { this.placa = placa; }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(String horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
