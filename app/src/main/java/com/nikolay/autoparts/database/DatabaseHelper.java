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
        super(context, NAME, null, 11);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table category(" +
                "id integer primary key," +
                "name text," +
                "rest_id int"+
                ")");
        db.execSQL("create table brand(" +
                "id integer primary key, " +
                "name text," +
                "rest_id int"+
                ")");
        db.execSQL("create table model(" +
                "id integer primary key," +
                "brand_id integer," +
                "name text," +
                "rest_id int,"+
                "Foreign Key(brand_id) References brand(id) ON DELETE CASCADE " +
                ")");
        db.execSQL("create  table part(" +
                "id integer primary key," +
                "model_id integer," +
                "category_id integer," +
                "name text," +
                "qty int," +
                "price decimal(19,2)," +
                "rest_id int," +
                "Foreign Key(model_id) References model(id) ON DELETE CASCADE ," +
                "Foreign Key(category_id) References category(id) ON DELETE CASCADE "+
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
