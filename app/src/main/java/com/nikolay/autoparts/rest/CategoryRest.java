package com.nikolay.autoparts.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.nikolay.autoparts.database.CategoryDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.retrofit.CategoryApi;
import com.nikolay.autoparts.retrofit.RetrofitService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRest {

    private Context context;
    private Category category;
    private CategoryApi categoryApi;
    private List<Category> categoryList;
    private RetrofitService retrofitService;
    private ProgressDialog dialog;
    private CategoryDatabase categoryDatabase;

    public CategoryRest(Context context) {
        this.setContext(context);
        retrofitService = new RetrofitService();
        categoryApi = retrofitService.getRetrofit().create(CategoryApi.class);
        dialog = new ProgressDialog(context);
        categoryDatabase = new CategoryDatabase(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void checkForDifference(List<Category> allCategoryList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        categoryApi.checkForDifference(allCategoryList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Category: Something went wrong!");
                    dialog.hide();
                    return;
                }

                String message;
                if (Boolean.TRUE.equals(response.body())) {
                    message = "Category: Have differences!";
                } else {
                    message = "Category: Already up to date";
                }
                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                createToastMessage(t.getMessage());
                dialog.hide();
            }
        });
    }

    public void sendAllNewCategory(List<Category> newCategoryList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        categoryApi.addList(newCategoryList).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Category: Something went wrong!");
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    message = "Category: Complete Successfully!";
                    categoryDatabase.update(response.body());
                } else {
                    message = "Category: Already up to date!";
                }
                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                createToastMessage(t.getMessage());
                dialog.hide();

            }
        });
    }

    public void update(List<Category> categoryList) {
        dialog.setTitle("Update is started. Please wait.");
        dialog.show();
        categoryApi.update(categoryList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Category: Something went wrong!");
                    dialog.hide();
                    return;
                }
                String message;
                if (Boolean.TRUE.equals(response.body())) {
                    message = "Category: Updated successfully!";
                } else {
                    message = "Category: Already up to date";
                }
                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                createToastMessage(t.getMessage());
                dialog.hide();

            }
        });
    }


    public void getAllNew(List<Category> categoryList) {
        dialog.setTitle("Create From Service. Please wait.");
        dialog.show();
        categoryApi.getAllNew(categoryList).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Category: Something went wrong!");
                    Logger.getLogger(getContext().getClass().getName()).log(
                            Level.SEVERE,
                            response.errorBody().toString()
                    );
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    categoryDatabase.insert(response.body());
                    message = "Category: Complete Successfully! Created - " + response.body().size();
                } else {
                    message = "Category: Already up to date!";
                }

                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                createToastMessage(t.getMessage());
                dialog.hide();
            }
        });
    }

    private void createToastMessage(String message) {
        Toast.makeText(
                getContext(),
                message,
                Toast.LENGTH_SHORT
        ).show();
    }
}
