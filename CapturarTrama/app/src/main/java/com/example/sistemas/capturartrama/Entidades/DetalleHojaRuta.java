package com.example.sistemas.capturartrama.Entidades;

public class DetalleHojaRuta {

    private String numeroHojaRuta;
    private String id_Cliente;
    private String numerodocumento;
    private String fechaDocumento;
    private String moneda;
    private String importeDocumento;

    public DetalleHojaRuta(String numeroHojaRuta, String id_Cliente, String numerodocumento,
                           String fechaDocumento, String moneda, String importeDocumento) {
        this.numeroHojaRuta = numeroHojaRuta;
        this.id_Cliente = id_Cliente;
        this.numerodocumento = numerodocumento;
        this.fechaDocumento = fechaDocumento;
        this.moneda = moneda;
        this.importeDocumento = importeDocumento;
    }

    public DetalleHojaRuta() {
    }

    public String getNumeroHojaRuta() {
        return numeroHojaRuta;
    }

    public void setNumeroHojaRuta(String numeroHojaRuta) {
        this.numeroHojaRuta = numeroHojaRuta;
    }

    public String getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(String id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getImporteDocumento() {
        return importeDocumento;
    }

    public void setImporteDocumento(String importeDocumento) {
        this.importeDocumento = importeDocumento;
    }
}
