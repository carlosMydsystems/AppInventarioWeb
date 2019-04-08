package com.example.sistemas.appinventariodesconectado;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sistemas.appinventariodesconectado.Utilidades.Utilidad;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidad.CREAR_TABLA_USUARIO);
        db.execSQL(Utilidad.CREAR_TABLA_INVENTARIO);
        db.execSQL(Utilidad.CREAR_VTA_BARRAS_PRESENTACION);
        db.execSQL(Utilidad.CREAR_PRESENTACION);
        db.execSQL(Utilidad.CREAR_TABLA_ARTICULOS);
        db.execSQL(Utilidad.CREAR_TABLE_INV_CAB_MOVIL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_INVENTARIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_VTA_BARRAS_PRESENTACION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_PRESENTACION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_ARTICULOS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_INV_CAB_MOVIL);

        onCreate(db);

    }
}
