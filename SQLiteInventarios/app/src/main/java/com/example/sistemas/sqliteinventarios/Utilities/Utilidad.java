package com.example.sistemas.sqliteinventarios.Utilities;

public class Utilidad {

    //Constantes campos tabla usuario

    public static final String TABLA_USUARIO="usuario";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_TELEFONO="telefono";

    public static final String CREAR_TABLA_USUARIO="CREATE TABLE " +
            ""+TABLA_USUARIO+" ("+CAMPO_ID+" " +
            "INTEGER, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_TELEFONO+" TEXT)";

    //Constantes campos tabla mascota

    public static final String TABLA_MASCOTA="mascota";
    public static final String CAMPO_ID_MASCOTA="id_mascota";
    public static final String CAMPO_NOMBRE_MASCOTA="nombre_mascota";
    public static final String CAMPO_RAZA_MASCOTA="raza_mascota";
    public static final String CAMPO_ID_DUENIO="id_duenio";

    public static final String CREAR_TABLA_MASCOTA="CREATE TABLE " +
            ""+TABLA_MASCOTA+" ("+CAMPO_ID_MASCOTA+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CAMPO_NOMBRE_MASCOTA+" TEXT, "+CAMPO_RAZA_MASCOTA+" TEXT,"+CAMPO_ID_DUENIO+" INTEGER)";

    //Constantes campos tabla inventario

    public static final String TABLA_INVENTARIO="inventario";
    public static final String CAMPO_ID_INVENTARIO="id_Inventario";
    public static final String CAMPO_CODIGO_PRODUCTO="codigoProducto";
    public static final String CAMPO_CANTIDAD="cantidad";
    public static final String CAMPO_DESCRIPCION_PRODUCTO="descripcion";
    public static final String CAMPO_MARCA_PRODUCTO="Marca";
    public static final String CAMPO_UNIDAD_PRODUCTO="unidad";
    public static final String CAMPO_ID_USUARIO="idUsuario";

    public static final String CREAR_TABLA_INVENTARIO="CREATE TABLE " +
            ""+TABLA_INVENTARIO+" ("+CAMPO_ID_INVENTARIO+" " +
            "INTEGER, "+CAMPO_CODIGO_PRODUCTO+" TEXT,"+CAMPO_CANTIDAD+" TEXT,"+CAMPO_DESCRIPCION_PRODUCTO
            +" TEXT,"+CAMPO_MARCA_PRODUCTO+" TEXT,"+CAMPO_UNIDAD_PRODUCTO+" TEXT,"+CAMPO_ID_USUARIO+" INTEGER)";


}
