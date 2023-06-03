package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.nikolay.autoparts.model.Brand;

import java.util.ArrayList;
import java.util.List;

public class BrandDatabase extends DatabaseHelper{

    private Brand brand;
    private ArrayList<Brand> brandList;

    public BrandDatabase(@Nullable Context context) {
        super(context);
    }

    public void insert(Brand brand) {
        contentValues = new ContentValues();
        contentValues.put("name", brand.getName());

        database = this.getWritableDatabase();
        database.insert("brand", null, contentValues);
    }

    public void insert(List<Brand> brandList) {
        for (Brand newBrand: brandList) {
            contentValues = new ContentValues();
            contentValues.put("name", newBrand.getName());
            contentValues.put("rest_id", newBrand.getRestId());

            database = this.getWritableDatabase();
            database.insert("brand", null, contentValues);
        }
    }

    public ArrayList<Brand> getAll() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from brand order by name", null);

        brandList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int restId = cursor.getInt(2);
                brandList.add(new Brand(id, name, restId));
                cursor.moveToNext();
            }
        }
        return brandList;
    }

    public ArrayList<Brand> getAllNewData() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from brand where rest_id is null", null);

        brandList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int restId = cursor.getInt(2);
                brandList.add(new Brand(id, name, restId));
                cursor.moveToNext();
            }
        }
        return brandList;
    }

    public ArrayList<Brand> getAllOldData() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from brand where rest_id is not null", null);

        brandList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int restId = cursor.getInt(2);
                brandList.add(new Brand(id, name, restId));
                cursor.moveToNext();
            }
        }
        return brandList;
    }

    public Brand getById(String id) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from brand where id like ?", new String[] {id});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                brand = new Brand(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                cursor.moveToNext();
            }
        }

        return brand;
    }

    public Brand getByRestId(String id) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from brand where rest_id = ?", new String[] {id});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                brand = new Brand(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                cursor.moveToNext();
            }
        }

        return brand;
    }

    public void update(Brand brand) {
        contentValues = new ContentValues();
        contentValues.put("name", brand.getName());
        contentValues.put("rest_id", brand.getRestId());

        String[] args = new String[] {brand.getId() + ""};

        database = this.getWritableDatabase();
        database.update("brand", contentValues, "id=?", args);
    }

    public void update(List<Brand> brandList) {
        for (Brand updatedBrand: brandList) {
            contentValues = new ContentValues();
            contentValues.put("name", updatedBrand.getName());
            contentValues.put("rest_id", updatedBrand.getRestId());

            String[] args = new String[] {updatedBrand.getId() + ""};

            database = this.getWritableDatabase();
            database.update("brand", contentValues, "id=?", args);
        }
    }

    public void delete(Brand brand) {
        String[] args = new String[] {brand.getId() + ""};
        database = this.getWritableDatabase();
        database.delete("brand", "id=?", args);
    }
}
