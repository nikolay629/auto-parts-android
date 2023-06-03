package com.nikolay.autoparts.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.transition.Visibility;

import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.model.Model;

import java.util.ArrayList;
import java.util.List;

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

    public void insert(List<Model> modelList) {
        for (Model newModel: modelList) {
            contentValues = new ContentValues();
            brand = brandDatabase.getByRestId(newModel.getBrand().getRestId() + "");
            contentValues.put("brand_id", brand.getId());
            contentValues.put("name", newModel.getName());
            contentValues.put("rest_id", newModel.getRestId());

            database = this.getWritableDatabase();
            database.insert("model", null, contentValues);
        }
    }

    public ArrayList<Model> getAll() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery(
                "select * from model m " +
                    "order By (Select name from brand b where b.id = m.brand_id) ASC",
                null
        );

        modelList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                brand = brandDatabase.getById(cursor.getInt(1) + "");
                String name = cursor.getString(2);
                int restId = cursor.getInt(3);
                modelList.add(new Model(id, brand, name, restId));
                cursor.moveToNext();
            }
        }
        return modelList;
    }

    public ArrayList<Model> getAllNewData() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from model where rest_id is null", null);

        modelList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                brand = brandDatabase.getById(cursor.getInt(1) + "");
                String name = cursor.getString(2);
                int restId = cursor.getInt(3);
                modelList.add(new Model(id, brand, name, restId));
                cursor.moveToNext();
            }
        }
        return modelList;
    }

    public ArrayList<Model> getAllOldData() {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from model where rest_id is not null", null);

        modelList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                brand = brandDatabase.getById(cursor.getInt(1) + "");
                String name = cursor.getString(2);
                int restId = cursor.getInt(3);
                modelList.add(new Model(id, brand, name, restId));
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
                model = new Model(cursor.getInt(0), brand,  cursor.getString(2), cursor.getInt(3));
                cursor.moveToNext();
            }
        }

        return model;
    }

    public Model getByRestId(String id) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery("select * from model where rest_id like ?", new String[] {id});

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                brand = brandDatabase.getById(cursor.getInt(1) + "");
                model = new Model(cursor.getInt(0), brand,  cursor.getString(2), cursor.getInt(3));
                cursor.moveToNext();
            }
        }

        return model;
    }

    public ArrayList<Model> getByBrand(Brand brand) {
        database = this.getReadableDatabase();
        cursor = database.rawQuery(
                "select * from model where brand_id like ? order by name",
                new String[] {brand.getId() + ""}
        );

        modelList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                brand = brandDatabase.getById(cursor.getInt(1) + "");
                String name = cursor.getString(2);
                int restId = cursor.getInt(3);
                modelList.add(new Model(id, brand, name, restId));
                cursor.moveToNext();
            }
        }
        return modelList;
    }

    public void update(Model model) {
        contentValues = new ContentValues();
        contentValues.put("brand_id", model.getBrand().getId());
        contentValues.put("name", model.getName());
        contentValues.put("rest_id", model.getRestId());

        String[] args = new String[] {model.getId() + ""};
        database = this.getWritableDatabase();
        database.update("model", contentValues, "id=?", args);
    }

    public void update(List<Model> modelList) {
        for (Model updatedModel: modelList) {
            contentValues = new ContentValues();
            contentValues.put("brand_id", updatedModel.getBrand().getId());
            contentValues.put("name", updatedModel.getName());
            contentValues.put("rest_id", updatedModel.getRestId());

            String[] args = new String[] {updatedModel.getId() + ""};

            database = this.getWritableDatabase();
            database.update("model", contentValues, "id=?", args);
        }
    }

    public void delete(Model model) {
        String[] args = new String[] {model.getId() + ""};
        database = this.getWritableDatabase();
        database.delete("model", "id=?", args);
    }

}
