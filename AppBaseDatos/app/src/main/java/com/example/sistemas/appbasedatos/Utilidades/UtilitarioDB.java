package com.example.sistemas.appbasedatos.Utilidades;

public class UtilitarioDB {

    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String TABLA_USUARIOS = "Usuarios";

    public static final String CREATE_TABLE_USER = "CREATE TABLE "+TABLA_USUARIOS+" ("+CAMPO_ID+" integer, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_TELEFONO+" TEXT)";

    public static final String SELECT_TABLE_USER = "SELECT * FROM "+ TABLA_USUARIOS;

}
