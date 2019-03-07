package com.example.sistemas.sqliteinventarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sistemas.sqliteinventarios.Utilities.Utilidad;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidad.CREAR_TABLA_USUARIO);
        db.execSQL(Utilidad.CREAR_TABLA_MASCOTA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidad.TABLA_MASCOTA);
        onCreate(db);

    }
}
