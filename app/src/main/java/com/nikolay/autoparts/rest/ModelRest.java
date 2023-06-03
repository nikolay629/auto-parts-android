package com.nikolay.autoparts.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.nikolay.autoparts.database.ModelDatabase;
import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.model.Model;
import com.nikolay.autoparts.retrofit.ModelApi;
import com.nikolay.autoparts.retrofit.RetrofitService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelRest {
    private Context context;
    private Model model;
    private ModelApi modelApi;
    private List<Model> modelList;
    private RetrofitService retrofitService;
    private ProgressDialog dialog;
    private ModelDatabase modelDatabase;

    public ModelRest(Context context) {
        this.setContext(context);
        retrofitService = new RetrofitService();
        modelApi = retrofitService.getRetrofit().create(ModelApi.class);
        dialog = new ProgressDialog(context);
        modelDatabase = new ModelDatabase(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void checkForDifference(List<Model> allModelList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        modelApi.checkForDifference(allModelList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Model: Something went wrong!");
                    dialog.hide();
                    return;
                }
                String message;
                if (Boolean.TRUE.equals(response.body())) {
                    message = "Model: Have differences!";
                } else {
                    message = "Model: Already up to date";
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

    public void sendAllNewModel(List<Model> newModelList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        modelApi.addList(newModelList).enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Model: Something went wrong!");
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    message = "Model: Complete Successfully!";
                    modelDatabase.update(response.body());
                } else {
                    message = "Model: Already up to date!";
                }
                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
                createToastMessage(t.getMessage());
                dialog.hide();

            }
        });
    }

    public void update(List<Model> modelList) {
        dialog.setTitle("Update is started. Please wait.");
        dialog.show();
        modelApi.update(modelList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Model: Something went wrong!");
                    dialog.hide();
                    return;
                }
                String message;
                if (Boolean.TRUE.equals(response.body())) {
                    message = "Model: Updated successfully!";
                } else {
                    message = "Model: Already up to date";
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

    public void getAllNew(List<Model> modelList) {
        dialog.setTitle("Create From Service. Please wait.");
        dialog.show();
        modelApi.getAllNew(modelList).enqueue(new Callback<List<Model>>() {
            @Override
            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Model: Something went wrong!");
                    Logger.getLogger(getContext().getClass().getName()).log(
                            Level.SEVERE,
                            response.errorBody().toString()
                    );
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    modelDatabase.insert(response.body());
                    message = "Model: Complete Successfully! Created - " + response.body().size();
                } else {
                    message = "Model: Already up to date!";
                }

                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Model>> call, Throwable t) {
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
