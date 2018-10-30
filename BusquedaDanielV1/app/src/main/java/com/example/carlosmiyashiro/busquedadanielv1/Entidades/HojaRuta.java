package com.example.carlosmiyashiro.busquedadanielv1.Entidades;

import java.io.Serializable;

public class HojaRuta implements Serializable {

    private String numeroHojaRuta;
    private String usuario;
    private String vehiculo;
    private String chofer;
    private String ayudantes;
    private String horaPartida;
    private String horaLlegada;
    private String kilometrajeInicial;
    private String kilometrajeFinal;

    public HojaRuta(String numeroHojaRuta, String usuario, String vehiculo, String chofer,
                    String ayudantes, String horaPartida, String horaLlegada, String kilometrajeInicial,
                    String kilometrajeFinal) {
        this.numeroHojaRuta = numeroHojaRuta;
        this.usuario = usuario;
        this.vehiculo = vehiculo;
        this.chofer = chofer;
        this.ayudantes = ayudantes;
        this.horaPartida = horaPartida;
        this.horaLlegada = horaLlegada;
        this.kilometrajeInicial = kilometrajeInicial;
        this.kilometrajeFinal = kilometrajeFinal;
    }

    public HojaRuta() {
    }

    public String getNumeroHojaRuta() {
        return numeroHojaRuta;
    }

    public void setNumeroHojaRuta(String numeroHojaRuta) {
        this.numeroHojaRuta = numeroHojaRuta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getAyudantes() {
        return ayudantes;
    }

    public void setAyudantes(String ayudantes) {
        this.ayudantes = ayudantes;
    }

    public String getHoraPartida() {
        return horaPartida;
    }

    public void setHoraPartida(String horaPartida) {
        this.horaPartida = horaPartida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getKilometrajeInicial() {
        return kilometrajeInicial;
    }

    public void setKilometrajeInicial(String kilometrajeInicial) {
        this.kilometrajeInicial = kilometrajeInicial;
    }

    public String getKilometrajeFinal() {
        return kilometrajeFinal;
    }

    public void setKilometrajeFinal(String kilometrajeFinal) {
        this.kilometrajeFinal = kilometrajeFinal;
    }

}
