package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;

import com.nikolay.autoparts.model.Category;

import java.util.ArrayList;
import java.util.List;

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

    public void insert(List<Category> categoryList) {
        for (Category newCategory: categoryList) {
            contentValues = new ContentValues();
            contentValues.put("name", newCategory.getName());
            contentValues.put("rest_id", newCategory.getRestId());

            database = this.getWritableDatabase();
            database.insert("category", null, contentValues);
        }
    }

    public ArrayList<Category> getAll() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from category order by name", null);

        categoryList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int restId = cursor.getInt(2);
                categoryList.add(new Category(id, name, restId));
                cursor.moveToNext();
            }
        }
        return categoryList;
    }

    public ArrayList<Category> getAllNewData() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from category where rest_id is null", null);

        categoryList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int restId = cursor.getInt(2);
                categoryList.add(new Category(id, name, restId));
                cursor.moveToNext();
            }
        }
        return categoryList;
    }

    public ArrayList<Category> getAllOldData() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from category where rest_id is not null", null);

        categoryList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int restId = cursor.getInt(2);
                categoryList.add(new Category(id, name, restId));
                cursor.moveToNext();
            }
        }
        return categoryList;
    }

    public Category getById(int id) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from category where id = ?", new String[] {id+""});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                cursor.moveToNext();
            }
        }

        return category;
    }

    public Category getByRestId(String id) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from category where rest_id like ?", new String[] {id});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                cursor.moveToNext();
            }
        }

        return category;
    }

    public void update(Category category) {
        contentValues = new ContentValues();
        contentValues.put("name", category.getName());
        contentValues.put("rest_id", category.getRestId());

        String[] args = new String[] {category.getId() + ""};

        database = this.getWritableDatabase();
        database.update("category", contentValues, "id=?", args);
    }

    public void update(List<Category> categoryList) {
        for (Category updatedCategory: categoryList) {
            contentValues = new ContentValues();
            contentValues.put("name", updatedCategory.getName());
            contentValues.put("rest_id", updatedCategory.getRestId());

            String[] args = new String[] {updatedCategory.getId() + ""};

            database = this.getWritableDatabase();
            database.update("category", contentValues, "id=?", args);
        }
    }

    public void delete(Category category) {
        String[] args = new String[] {category.getId() + ""};
        database = this.getWritableDatabase();
        database.delete("category", "id=?", args);
    }
}
