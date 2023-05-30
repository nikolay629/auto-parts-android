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

    public ArrayList<Brand> getAll() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from brand", null);

        brandList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                brandList.add(new Brand(id, name));
                cursor.moveToNext();
            }
        }
        return brandList;
    }

}
