package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.transition.Visibility;

import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Model;

import java.util.ArrayList;

public class ModelDatabase extends DatabaseHelper{

    private Brand brand;
    private Model model;
    private BrandDatabase brandDatabase;
    private ArrayList<Model> modelList;

    public ModelDatabase(@Nullable Context context) {
        super(context);
        brandDatabase = new BrandDatabase(context);
    }

    public void insert(Model model) {
        contentValues = new ContentValues();
        contentValues.put("brand_id", model.getBrand().getId());
        contentValues.put("name", model.getName());

        database = this.getWritableDatabase();
        database.insert("model", null, contentValues);
    }


    public ArrayList<Model> getAll() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from model", null);

        modelList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                brand = brandDatabase.getById(cursor.getInt(1) + "");
                String name = cursor.getString(2);
                modelList.add(new Model(id, brand, name));
                cursor.moveToNext();
            }
        }
        return modelList;
    }

    public Model getById(String id) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from model where id like ?", new String[] {id});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                brand = brandDatabase.getById(cursor.getInt(1) + "");
                model = new Model(cursor.getInt(0), brand,  cursor.getString(2));
                cursor.moveToNext();
            }
        }

        return model;
    }

    public ArrayList<Model> getByBrand(Brand brand) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery(
                "select * from model where brand_id like ?",
                new String[] {brand.getId() + ""}
        );

        modelList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                brand = brandDatabase.getById(cursor.getInt(1) + "");
                String name = cursor.getString(2);
                modelList.add(new Model(id, brand, name));
                cursor.moveToNext();
            }
        }
        return modelList;
    }

    public void update(Model model) {
        contentValues = new ContentValues();
        contentValues.put("brand_id", model.getBrand().getId());
        contentValues.put("name", model.getName());

        String[] args = new String[] {model.getId() + ""};
        database = this.getWritableDatabase();
        database.update("model", contentValues, "id=?", args);
    }

    public void delete(Model model) {
        String[] args = new String[] {model.getId() + ""};
        database = this.getWritableDatabase();
        database.delete("model", "id=?", args);
    }

}
