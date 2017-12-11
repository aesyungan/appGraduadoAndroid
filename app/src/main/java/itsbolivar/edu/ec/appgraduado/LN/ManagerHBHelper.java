package itsbolivar.edu.ec.appgraduado.LN;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by XL on 7/12/2017.
 */

public class ManagerHBHelper extends SQLiteOpenHelper {

    public ManagerHBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table oferta_laboral(_id int primary key,descripcion text)");
        sqLiteDatabase.execSQL("create table graduado(_id int primary key,descripcion text)");
        sqLiteDatabase.execSQL("create table aplicar_oferta(_id int primary key,descripcion text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table oferta_laboral");
        sqLiteDatabase.execSQL("drop table graduado ");
        sqLiteDatabase.execSQL("drop table aplicar_oferta ");

        sqLiteDatabase.execSQL("create table oferta_laboral(_id int primary key,descripcion text)");
        sqLiteDatabase.execSQL("create table graduado(_id int primary key,descripcion text)");
        sqLiteDatabase.execSQL("create table aplicar_oferta(_id int primary key,descripcion text)");

    }
}