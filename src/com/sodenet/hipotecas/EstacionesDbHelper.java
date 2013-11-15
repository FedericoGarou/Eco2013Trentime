package com.sodenet.hipotecas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EstacionesDbHelper extends SQLiteOpenHelper {

private static int version = 4;
private static String name = "Estaciones" ;
private static CursorFactory factory = null;

public EstacionesDbHelper(Context context)
{
    super(context, name, factory, version);
}

@Override
public void onCreate(SQLiteDatabase db)
{
    Log.i(this.getClass().toString(), "Creando base de datos");

    db.execSQL( "CREATE TABLE ESTACIONES(" +
                " _id INTEGER PRIMARY KEY," +
                " estacion_nombre TEXT NOT NULL)" );

    //db.execSQL( "CREATE UNIQUE INDEX hip_nombre ON HIPOTECA(hip_nombre ASC)" );

    Log.i(this.getClass().toString(), "Tabla ESTACIONES creada");

    /*
     * Insertamos datos iniciales
     */
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(0,'Federico Lacroze')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(1,'Artigas')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(2,'Arata')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(3,'Beiro')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(4,'Libertador')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(5,'Devoto')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(6,'Lynch')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(7,'Fernandez Moreno')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(8,'Lourdes')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(9,'Tropezon')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(10,'Jose Maria Bosch')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(11,'Martin Coronado')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(12,'Podesta')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(13,'Newbery')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(14,'Ruben Dario')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(15,'Ejercito de los Andes')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(16,'La Salle')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(17,'Barrufaldi')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(18,'Lozano')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(19,'Agneta')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(20,'Campo De Mayo')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(21,'Sgto Cabral')");
    db.execSQL("INSERT INTO ESTACIONES(_id, estacion_nombre) VALUES(22,'Lemos')");


    Log.i(this.getClass().toString(), "Estaciones insertadas");

    Log.i(this.getClass().toString(), "Base de datos creada");

    // Aplicamos las sucesivas actualizaciones
    //upgrade_2(db);
    //upgrade_3(db);
    //upgrade_4(db);
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
{
    // Actualización a versión 2
    if (oldVersion < 2)
    {
        upgrade_2(db);
    }
}

    // ejemplo de upgrade para la base
    private void upgrade_2(SQLiteDatabase db)
    {
        //
        // Upgrade versión 4: Incluir la clasificación SITUACION para las hipotecas
        //
        db.execSQL( "CREATE TABLE SITUACION(" +
                    " _id INTEGER PRIMARY KEY," +
                    " sit_nombre TEXT NOT NULL)");

        db.execSQL( "CREATE UNIQUE INDEX sit_nombre ON SITUACION(sit_nombre ASC)" );

        db.execSQL("INSERT INTO SITUACION(_id, sit_nombre) VALUES(1,'Inicial')");
        db.execSQL("INSERT INTO SITUACION(_id, sit_nombre) VALUES(2,'Información')");
        db.execSQL("INSERT INTO SITUACION(_id, sit_nombre) VALUES(3,'Solicitada')");
        db.execSQL("INSERT INTO SITUACION(_id, sit_nombre) VALUES(4,'Negociación')");
        db.execSQL("INSERT INTO SITUACION(_id, sit_nombre) VALUES(5,'Denegada')");
        db.execSQL("INSERT INTO SITUACION(_id, sit_nombre) VALUES(6,'Desestimada')");
        db.execSQL("INSERT INTO SITUACION(_id, sit_nombre) VALUES(7,'Concedida')");
        db.execSQL("INSERT INTO SITUACION(_id, sit_nombre) VALUES(8,'Firmada')");

        db.execSQL("ALTER TABLE HIPOTECA ADD hip_sit_id INTEGER NOT NULL DEFAULT 1");

        Log.i(this.getClass().toString(), "Actualización versión 4 finalizada");
    }

}

