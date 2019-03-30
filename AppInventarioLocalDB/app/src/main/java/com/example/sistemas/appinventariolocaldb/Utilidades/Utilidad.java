package com.example.sistemas.appinventariolocaldb.Utilidades;

public class Utilidad {

    //Constantes campos tabla usuario

    public static final String TABLA_USUARIO = "USUARIO_MOVIL";
    public static final String CAMPO_COD_USUARIO = "COD_USUARIO";
    public static final String CAMPO_CLAVE = "CLAVE";
    public static final String CAMPO_NOMBRE_USUARIO = "NOMBRE";
    public static final String CAMPO_COD_TIENDA = "COD_TIENDA";
    public static final String CAMPO_BODEGA = "BODEGA";
    public static final String CAMPO_ESTADO = "ESTADO";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " (" +
            CAMPO_COD_USUARIO + " TEXT, " + CAMPO_CLAVE + " TEXT," + CAMPO_NOMBRE_USUARIO + " TEXT," +
            CAMPO_COD_TIENDA + " TEXT," + CAMPO_BODEGA + " TEXT," + CAMPO_ESTADO + " TEXT)";


    //Constantes campos tabla Articulo

    public static final String TABLA_ARTICULOS="ARTICULO";
    public static final String CAMPO_CODIGO_ARTICULO="COD_ARTICULO";
    public static final String CAMPO_DESCRIPCION_ARTICULO="ARTICULO";
    public static final String CAMPO_UNIDAD_MEDIDA ="UND_MEDIDA";
    public static final String CAMPO_EQUIVALENCIA ="EQUIVALENCIA";
    public static final String CAMPO_MARCA="DES_MARCA";
    public static final String CAMPO_CODIGO_BARRA_1 ="COD_BARRA1";
    public static final String CAMPO_CODIGO_BARRA_2="COD_BARRA2";
    public static final String CAMPO_CODIGO_BARRA_3="COD_BARRA3";

    public static final String CREAR_TABLA_ARTICULOS = "CREATE TABLE " + TABLA_ARTICULOS + " " +
            "(" + CAMPO_CODIGO_ARTICULO + " TEXT," + CAMPO_DESCRIPCION_ARTICULO +" TEXT," +
            CAMPO_UNIDAD_MEDIDA + " TEXT," + CAMPO_EQUIVALENCIA + " TEXT," +
            CAMPO_MARCA + " TEXT," + CAMPO_CODIGO_BARRA_1 + " TEXT," + CAMPO_CODIGO_BARRA_2 + " TEXT," +
            CAMPO_CODIGO_BARRA_3 + " TEXT)";


    //Constantes campos tabla VTA_BARRAS_PRESENTACION

    public static final String TABLA_VTA_BARRAS_PRESENTACION="VTA_BARRAS_PRESENTACION";
    public static final String CAMPO_COD_ARTICULO_BARRAS_PRESENTACION="COD_ARTICULO";
    public static final String CAMPO_COD_BARRA="COD_BARRA";
    public static final String CAMPO_UND_MEDIDA_BARRAS_PRESENTACION ="UND_MEDIDA";

    public static final String CREAR_VTA_BARRAS_PRESENTACION = "CREATE TABLE " + TABLA_VTA_BARRAS_PRESENTACION + " " +
            "(" + CAMPO_COD_ARTICULO_BARRAS_PRESENTACION + " TEXT," + CAMPO_COD_BARRA +" TEXT," + CAMPO_UND_MEDIDA_BARRAS_PRESENTACION + " TEXT)";


    //Constantes campos tabla PRESENTACION

    public static final String TABLA_PRESENTACION="PRESENTACION";
    public static final String CAMPO_DES_PRESENTACION="DES_PRESENTACION";
    public static final String CAMPO_UNIDAD_MEDIDA_PRESENTACION="UND_MEDIDA";
    public static final String CAMPO_EQUIVALENCIA_PRESENTACION="EQUIVALENCIA";

    public static final String CREAR_PRESENTACION = "CREATE TABLE " + TABLA_PRESENTACION + " " +
            "(" + CAMPO_UNIDAD_MEDIDA_PRESENTACION + " TEXT," + CAMPO_DES_PRESENTACION +" TEXT," + CAMPO_EQUIVALENCIA_PRESENTACION + " TEXT)";

    //Constantes campos tabla inventario

    public static final String TABLA_INVENTARIO = "INVENTARIO";
    public static final String CAMPO_SECUENCIA = "SECUENCIA";
    public static final String CAMPO_FECHA = "FECHA";
    public static final String CAMPO_ALMACEN = "ALMACEN";
    public static final String CAMPO_CONTEO = "CONTEO";
    public static final String CAMPO_COD_ARTICULO = "COD_ARTICULO";
    public static final String CAMPO_UND_MEDIDA_ORIG="UND_MEDIDA_ORIG";
    public static final String CAMPO_EQU_MEDIDA_ORIG="EQU_MEDIDA_ORIG";
    public static final String CAMPO_UND_BARRA_LEC = "UND_BARRA_LEC";
    public static final String CAMPO_COD_BARRA_LEC = "COD_BARRA_LEC";
    public static final String CAMPO_EQU_BARRA_LEC = "EQU_BARRA_LEC";
    public static final String CAMPO_CAN_DIGITADA_LEC="CAN_DIGITADA_LEC";
    public static final String CAMPO_FACTOR="FACTOR";
    public static final String CAMPO_COD_USUARIO_INVENTARIO="COD_USUARIO";
    public static final String CAMPO_FCH_CREACION="FCH_CREACION";

    public static final String CREAR_TABLA_INVENTARIO = "CREATE TABLE " + TABLA_INVENTARIO + " (" +
            CAMPO_SECUENCIA + " " + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            CAMPO_FECHA + " " + "TEXT, " +
            CAMPO_ALMACEN + " TEXT," +
            CAMPO_CONTEO + " TEXT," +
            CAMPO_COD_ARTICULO + " TEXT,"+
            CAMPO_UND_MEDIDA_ORIG + " TEXT," +
            CAMPO_EQU_MEDIDA_ORIG + " TEXT," +
            CAMPO_UND_BARRA_LEC + " TEXT," +
            CAMPO_COD_BARRA_LEC + " TEXT," +
            CAMPO_EQU_BARRA_LEC + " TEXT," +
            CAMPO_CAN_DIGITADA_LEC + " TEXT," +
            CAMPO_FACTOR + " TEXT," +
            CAMPO_COD_USUARIO_INVENTARIO + " TEXT," +
            CAMPO_FCH_CREACION + " TEXT)";


    //Constantes campos tabla INVENTARIO CABECERA

    public static final String TABLA_INV_CAB_MOVIL="INV_CAB_MOVIL";
    public static final String CAMPO_FECHA_INVENTARIO_CABECERA="FECHA";
    public static final String CAMPO_COD_BODEGA_INVENTARIO_CABECERA="BODEGA";
    public static final String CAMPO_CONTEO_INVENTARIO_CABECERA ="CONTEO";
    public static final String CAMPO_ESTADO_INVENTARIO_CABECERA ="ESTADO";

    public static final String CREAR_TABLE_INV_CAB_MOVIL = "CREATE TABLE " + TABLA_INV_CAB_MOVIL + " " + "(" +
            CAMPO_FECHA_INVENTARIO_CABECERA + " TEXT," + CAMPO_COD_BODEGA_INVENTARIO_CABECERA +" TEXT," +
            CAMPO_CONTEO_INVENTARIO_CABECERA +" TEXT," + CAMPO_ESTADO_INVENTARIO_CABECERA + " TEXT)";


}
