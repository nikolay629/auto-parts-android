package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;

import java.util.ArrayList;

public class CategoryDatabase extends DatabaseHelper{

    private Category category;
    private ArrayList<Category> categoryList;

    public CategoryDatabase(@Nullable Context context) {
        super(context);
    }

    public void insert(Category category) {
        contentValues = new ContentValues();
        contentValues.put("name", category.getName());

        database = this.getWritableDatabase();
        database.insert("category", null, contentValues);
    }


    public ArrayList<Category> getAll() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from category", null);

        categoryList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                categoryList.add(new Category(id, name));
                cursor.moveToNext();
            }
        }
        return categoryList;
    }

    public Category getById(String id) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from category where id like ?", new String[] {id});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                category = new Category(cursor.getInt(0), cursor.getString(1));
                cursor.moveToNext();
            }
        }

        return category;
    }

}
