package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.nikolay.autoparts.model.Category;

public class CategoryDatabase extends DatabaseHelper{

    public CategoryDatabase(@Nullable Context context) {
        super(context);
    }

    public void insert(Category category) {
        database = this.getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put("name", category.getName());

        database.insert("category", null, contentValues);
    }


}
