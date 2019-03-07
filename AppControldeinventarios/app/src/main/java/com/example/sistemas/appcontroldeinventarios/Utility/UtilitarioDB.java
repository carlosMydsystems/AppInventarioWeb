package com.example.sistemas.appcontroldeinventarios.Utility;

public class UtilitarioDB {

    /*
    public static final String CAMPO_ID_PRODUCTO = "IdProducto";
    public static final String CAMPO_CODIGO_PRODUCTO = "CodigoProducto";
    public static final String CAMPO_DESCRIPCION_PRODUCTO = "DescripcionProducto";
    public static final String CAMPO_MARCA = "Marca";
    public static final String CAMPO_GENERICO = "Generico";
    public static final String CAMPO_TIPO = "Tipo";
    public static final String CAMPO_STOCK = "Stock";
    public static final String CAMPO_UNIDAD= "Unidad";
    public static final String CAMPO_ESTADO = "Estado";
    public static final String TABLA_PRODUCTOS = "Productos";
*/
    public static final String CAMPO_COD_PRODUCTO = "cod_Producto";
    public static final String CAMPO_CANTIDAD = "cantidad";
    public static final String CAMPO_ID_USUARIO = "Usuario_id";
    public static final String TABLA_INVENTARIO = "Inventario";

/*
    public static final String CREATE_TABLE_PRODUCTO = "CREATE TABLE " + TABLA_PRODUCTOS + " ( "+
            CAMPO_ID_PRODUCTO + " TEXT, " + CAMPO_CODIGO_PRODUCTO + " TEXT, "  + CAMPO_DESCRIPCION_PRODUCTO +
            " TEXT," + CAMPO_MARCA + " TEXT," + CAMPO_GENERICO + " TEXT," + CAMPO_TIPO + " TEXT," + CAMPO_STOCK +
            " TEXT," + CAMPO_UNIDAD + " TEXT," + CAMPO_ESTADO + " TEXT)";
*/
    public static final String CREATE_TABLE_INVENTARIO = "CREATE TABLE " + TABLA_INVENTARIO + " ( "+
            CAMPO_COD_PRODUCTO + " TEXT, " + CAMPO_CANTIDAD + " TEXT, "  + CAMPO_ID_USUARIO + " TEXT)";

}
