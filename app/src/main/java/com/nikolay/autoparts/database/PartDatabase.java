package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.nikolay.autoparts.model.Part;

public class PartDatabase extends DatabaseHelper{

    public PartDatabase(@Nullable Context context) {
        super(context);
    }

    public void insert(Part part) {
        contentValues = new ContentValues();
        contentValues.put("category_id", part.getCategory().getId());
        contentValues.put("model_id", part.getModel().getId());
        contentValues.put("name", part.getName());
        contentValues.put("qty", part.getQty());
        contentValues.put("price", part.getPrice());

        database = this.getWritableDatabase();
        database.insert("part", null, contentValues);
    }

}
