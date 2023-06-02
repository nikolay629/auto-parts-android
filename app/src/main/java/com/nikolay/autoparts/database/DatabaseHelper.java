package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public abstract class DatabaseHelper extends SQLiteOpenHelper {

    public static final String NAME = "AutoPartsMobile.db";

    protected ContentValues contentValues;
    protected SQLiteDatabase database;
    protected Cursor cursor;

    public DatabaseHelper(@Nullable Context context) {
        super(context, NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table category(" +
                "id integer primary key," +
                "name text unique"+
                ")");
        db.execSQL("create table brand(" +
                "id integer primary key, " +
                "name text unique" +
                ")");
        db.execSQL("create table model(" +
                "id integer primary key," +
                "brand_id integer," +
                "name text," +
                "Foreign Key(brand_id) References brand(id)" +
                ")");
        db.execSQL("create  table part(" +
                "id integer primary key," +
                "model_id integer," +
                "category_id integer," +
                "name text," +
                "qty int," +
                "price decimal(19,2)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS brand");
        db.execSQL("DROP TABLE IF EXISTS model");
        db.execSQL("DROP TABLE IF EXISTS part");
        onCreate(db);
    }

}
