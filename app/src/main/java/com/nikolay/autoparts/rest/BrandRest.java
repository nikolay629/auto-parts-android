package com.nikolay.autoparts.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.nikolay.autoparts.BrandActivity;
import com.nikolay.autoparts.database.BrandDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.retrofit.BrandApi;
import com.nikolay.autoparts.retrofit.RetrofitService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandRest {

    private Context context;
    private Brand brand;
    private BrandApi brandApi;
    private List<Brand> brandList;
    private RetrofitService retrofitService;
    private ProgressDialog dialog;

    private BrandDatabase brandDatabase;

    public BrandRest(Context context) {
        this.setContext(context);
        retrofitService = new RetrofitService();
        brandApi = retrofitService.getRetrofit().create(BrandApi.class);
        dialog = new ProgressDialog(context);
        brandDatabase = new BrandDatabase(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void checkForDifference(List<Brand> allBrandList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        brandApi.checkForDifference(allBrandList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Brand: Something went wrong!");
                    dialog.hide();
                    return;
                }
                String message;
                if (Boolean.TRUE.equals(response.body())) {
                    message = "Brand: Have differences!";
                } else {
                    message = "Brand: Already up to date";
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

    public void sendAllNewBrand(List<Brand> newBrandList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        brandApi.addList(newBrandList).enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Brand: Something went wrong!");
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    message = "Brand: Complete Successfully!";
                    brandDatabase.update(response.body());
                } else {
                    message = "Brand: Already up to date!";
                }
                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                createToastMessage(t.getMessage());
                dialog.hide();

            }
        });
    }

    public void update(List<Brand> brandList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        brandApi.update(brandList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Brand: Something went wrong!");
                    dialog.hide();
                    return;
                }
                String message;
                if (Boolean.TRUE.equals(response.body())) {
                    message = "Brand: Updated successfully!";
                } else {
                    message = "Brand: Already up to date";
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

    public void getAllNew(List<Brand> brandList) {
        dialog.setTitle("Create From Service. Please wait.");
        dialog.show();
        brandApi.getAllNew(brandList).enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Brand: Something went wrong!");
                    Logger.getLogger(getContext().getClass().getName()).log(
                            Level.SEVERE,
                            response.errorBody().toString()
                    );
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    brandDatabase.insert(response.body());
                    message = "Brand: Complete Successfully! Created - " + response.body().size();
                } else {
                    message = "Brand: Already up to date!";
                }

                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
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
