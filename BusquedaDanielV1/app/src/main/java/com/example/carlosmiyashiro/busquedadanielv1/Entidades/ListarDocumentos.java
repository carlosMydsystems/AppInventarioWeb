package com.example.carlosmiyashiro.busquedadanielv1.Entidades;

public class ListarDocumentos {

    private String Id_ListaDocumento;
    private String ClienteId;
    private String TipoDocumento;
    private String NumeroDocumento;
    private String Fecha_Emision;
    private String Monto;
    private  String Estado_Documento;

    public ListarDocumentos(String id_ListaDocumento, String clienteId, String tipoDocumento,
                            String numeroDocumento, String fecha_Emision, String monto, String estado_Documento) {
        Id_ListaDocumento = id_ListaDocumento;
        ClienteId = clienteId;
        TipoDocumento = tipoDocumento;
        NumeroDocumento = numeroDocumento;
        Fecha_Emision = fecha_Emision;
        Monto = monto;
        Estado_Documento = estado_Documento;
    }

    public ListarDocumentos() {
    }

    public String getId_ListaDocumento() {
        return Id_ListaDocumento;
    }

    public void setId_ListaDocumento(String id_ListaDocumento) {
        Id_ListaDocumento = id_ListaDocumento;
    }

    public String getClienteId() {
        return ClienteId;
    }

    public void setClienteId(String clienteId) {
        ClienteId = clienteId;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return NumeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        NumeroDocumento = numeroDocumento;
    }

    public String getFecha_Emision() {
        return Fecha_Emision;
    }

    public void setFecha_Emision(String fecha_Emision) {
        Fecha_Emision = fecha_Emision;
    }

    public String getMonto() {
        return Monto;
    }

    public void setMonto(String monto) {
        Monto = monto;
    }

    public String getEstado_Documento() {
        return Estado_Documento;
    }

    public void setEstado_Documento(String estado_Documento) {
        Estado_Documento = estado_Documento;
    }
}
