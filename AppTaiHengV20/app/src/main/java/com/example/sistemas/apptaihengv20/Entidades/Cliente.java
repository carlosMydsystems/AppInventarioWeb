package com.example.sistemas.apptaihengv20.Entidades;

import java.io.Serializable;

public class Cliente implements Serializable {

    public String Codigo, Ruc, Cliente,Direccion,Departamento,Provincia,Distrito,Tienda,
            Vendedor,Canal,Telefono1,Telefono2,Telefono3,Correo;

    public Cliente() {
    }

    public Cliente(String codigo, String ruc, String cliente, String direccion, String departamento,
                   String provincia, String distrito, String tienda, String vendedor, String canal,
                   String telefono1, String telefono2, String telefono3, String correo) {
        Codigo = codigo;
        Ruc = ruc;
        Cliente = cliente;
        Direccion = direccion;
        Departamento = departamento;
        Provincia = provincia;
        Distrito = distrito;
        Tienda = tienda;
        Vendedor = vendedor;
        Canal = canal;
        Telefono1 = telefono1;
        Telefono2 = telefono2;
        Telefono3 = telefono3;
        Correo = correo;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getRuc() {
        return Ruc;
    }

    public void setRuc(String ruc) {
        Ruc = ruc;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String provincia) {
        Provincia = provincia;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getTienda() {
        return Tienda;
    }

    public void setTienda(String tienda) {
        Tienda = tienda;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String vendedor) {
        Vendedor = vendedor;
    }

    public String getCanal() {
        return Canal;
    }

    public void setCanal(String canal) {
        Canal = canal;
    }

    public String getTelefono1() {
        return Telefono1;
    }

    public void setTelefono1(String telefono1) {
        Telefono1 = telefono1;
    }

    public String getTelefono2() {
        return Telefono2;
    }

    public void setTelefono2(String telefono2) {
        Telefono2 = telefono2;
    }

    public String getTelefono3() {
        return Telefono3;
    }

    public void setTelefono3(String telefono3) {
        Telefono3 = telefono3;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }
}
