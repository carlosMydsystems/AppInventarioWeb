package com.example.sistemas.capturartrama.Entidades;

import java.io.Serializable;

public class HojaRuta implements Serializable {

    private String NumeroHojaRuta;
    private String NumeroPlaca;
    private String Placa;
    private String Marca;
    private String Chofer;
    private String Id_Clliente;
    private String Nombre;
    private String fechaRegistro;
    private String LugarEntrega;
    private String Distrito;

    public HojaRuta()  {
    }

    public HojaRuta(String numeroHojaRuta, String numeroPlaca, String placa, String marca, String chofer,
                    String id_Clliente, String nombre, String fechaRegistro, String lugarEntrega, String distrito) {
        NumeroHojaRuta = numeroHojaRuta;
        NumeroPlaca = numeroPlaca;
        Placa = placa;
        Marca = marca;
        Chofer = chofer;
        Id_Clliente = id_Clliente;
        Nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        LugarEntrega = lugarEntrega;
        Distrito = distrito;
    }

    public String getLugarEntrega() {
        return LugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        LugarEntrega = lugarEntrega;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNumeroHojaRuta() {
        return NumeroHojaRuta;
    }

    public void setNumeroHojaRuta(String numeroHojaRuta) {
        NumeroHojaRuta = numeroHojaRuta;
    }

    public String getNumeroPlaca() {
        return NumeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        NumeroPlaca = numeroPlaca;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getChofer() {
        return Chofer;
    }

    public void setChofer(String chofer) {
        Chofer = chofer;
    }

    public String getId_Clliente() {
        return Id_Clliente;
    }

    public void setId_Clliente(String id_Clliente) {
        Id_Clliente = id_Clliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
