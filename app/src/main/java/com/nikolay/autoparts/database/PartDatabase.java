package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.model.Model;
import com.nikolay.autoparts.model.Part;

import java.util.ArrayList;

public class PartDatabase extends DatabaseHelper{

    private Model model;
    private Category category;
    private Part part;
    private ArrayList<Part> partList;

    private ModelDatabase modelDatabase;
    private CategoryDatabase categoryDatabase;

    public PartDatabase(@Nullable Context context) {
        super(context);
        modelDatabase = new ModelDatabase(context);
        categoryDatabase = new CategoryDatabase(context);
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

    public ArrayList<Part> getAll() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from part", null);

        partList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                category = categoryDatabase.getById(cursor.getInt(1));
                model = modelDatabase.getById(cursor.getString(2));
                String name = cursor.getString(3);
                int qty = cursor.getInt(4);
                float price = cursor.getFloat(5);
                partList.add(new Part(id, category, model, name, qty, price));
                cursor.moveToNext();
            }
        }
        return partList;
    }

    public Part getById(String id) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from part where id like ?", new String[] {id});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int partId = cursor.getInt(0);
                category = categoryDatabase.getById(cursor.getInt(1));
                model = modelDatabase.getById(cursor.getString(2));
                String name = cursor.getString(3);
                int qty = cursor.getInt(4);
                float price = cursor.getFloat(5);
                part = new Part(partId, category, model, name, qty, price);
                cursor.moveToNext();
            }
        }

        return part;
    }

    public ArrayList<Part> getByModelAndCategory(String modelId, String categoryId) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery(
            "select * from part where model_id = ? and category_id = ?",
            new String[] {modelId, categoryId}
        );

        partList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int partId = cursor.getInt(0);
                category = categoryDatabase.getById(cursor.getInt(1));
                model = modelDatabase.getById(cursor.getString(2));
                String name = cursor.getString(3);
                int qty = cursor.getInt(4);
                float price = cursor.getFloat(5);
                partList.add(new Part(partId, category, model, name, qty, price));
                cursor.moveToNext();
            }
        }

        return partList;
    }

    public ArrayList<Part> getByModel(String modelId) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery(
            "select * from part where model_id = ?",
            new String[] {modelId}
        );

        partList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int partId = cursor.getInt(0);
                category = categoryDatabase.getById(cursor.getInt(1));
                model = modelDatabase.getById(cursor.getString(2));
                String name = cursor.getString(3);
                int qty = cursor.getInt(4);
                float price = cursor.getFloat(5);
                partList.add(new Part(partId, category, model, name, qty, price));
                cursor.moveToNext();
            }
        }

        return partList;
    }

    public ArrayList<Part> getByCategory(String categoryId) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery(
            "select * from part where category_id = ?",
            new String[] {categoryId}
        );

        partList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int partId = cursor.getInt(0);
                category = categoryDatabase.getById(cursor.getInt(1));
                model = modelDatabase.getById(cursor.getString(2));
                String name = cursor.getString(3);
                int qty = cursor.getInt(4);
                float price = cursor.getFloat(5);
                partList.add(new Part(partId, category, model, name, qty, price));
                cursor.moveToNext();
            }
        }

        return partList;
    }

    public void update(Part part) {
        contentValues = new ContentValues();
        contentValues.put("category_id", part.getCategory().getId());
        contentValues.put("model_id", part.getModel().getId());
        contentValues.put("name", part.getName());
        contentValues.put("qty", part.getQty());
        contentValues.put("price", part.getPrice());

        String[] args = new String[] {part.getId() + ""};

        database = this.getWritableDatabase();
        database.update("part", contentValues, "id=?", args);
    }

    public void delete(Part part) {
        String[] args = new String[] {part.getId() + ""};
        database = this.getWritableDatabase();
        database.delete("part", "id=?", args);
    }

}
