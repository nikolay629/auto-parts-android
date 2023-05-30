package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.transition.Visibility;

import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Model;

import java.util.ArrayList;

public class ModelDatabase extends DatabaseHelper{

    private ArrayList<Model> modelList;

    public ModelDatabase(@Nullable Context context) {
        super(context);
    }

    public void insert(Model model) {
        contentValues = new ContentValues();
        contentValues.put("brand_id", model.getBrand().getId());
        contentValues.put("name", model.getName());

        database = this.getWritableDatabase();
        database.insert("model", null, contentValues);
    }


//    public ArrayList<Model> getAll() {
//        database = this.getReadableDatabase();
//        cursor = database.rawQuery("select * from brand", null);
//
//        modelList = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                int id = cursor.getInt(0);
//                Brand name = cursor.getString(1);
//                modelList.add(new Model(, name));
//                cursor.moveToNext();
//            }
//        }
//        return brandList;
//    }


}
