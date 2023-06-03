package com.nikolay.autoparts.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.nikolay.autoparts.database.PartDatabase;
import com.nikolay.autoparts.model.Part;
import com.nikolay.autoparts.retrofit.PartApi;
import com.nikolay.autoparts.retrofit.RetrofitService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartRest {
    private Context context;
    private Part part;
    private PartApi partApi;
    private List<Part> partList;
    private RetrofitService retrofitService;
    private ProgressDialog dialog;
    private PartDatabase partDatabase;

    public PartRest(Context context) {
        this.setContext(context);
        retrofitService = new RetrofitService();
        partApi = retrofitService.getRetrofit().create(PartApi.class);
        dialog = new ProgressDialog(context);
        partDatabase = new PartDatabase(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void checkForDifference(List<Part> allPartList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        partApi.checkForDifference(allPartList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Part: Something went wrong!");
                    dialog.hide();
                    return;
                }
                String message;
                if (Boolean.TRUE.equals(response.body())) {
                    message = "Part: Have differences!";
                } else {
                    message = "Part: Already up to date";
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

    public void sendAllNewPart(List<Part> newPartList) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        partApi.addList(newPartList).enqueue(new Callback<List<Part>>() {
            @Override
            public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Part: Something went wrong!");
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    message = "Part: Complete Successfully!";
                    partDatabase.update(response.body());
                } else {
                    message = "Part: Already up to date!";
                }
                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Part>> call, Throwable t) {
                createToastMessage(t.getMessage());
                dialog.hide();

            }
        });
    }

    public void update(List<Part> partList) {
        dialog.setTitle("Update is started. Please wait.");
        dialog.show();
        partApi.update(partList).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Part: Something went wrong!");
                    dialog.hide();
                    return;
                }
                String message;
                if (Boolean.TRUE.equals(response.body())) {
                    message = "Part: Updated successfully!";
                } else {
                    message = "Part: Already up to date";
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

    public void getAllNew(List<Part> partList) {
        dialog.setTitle("Create From Service. Please wait.");
        dialog.show();
        partApi.getAllNew(partList).enqueue(new Callback<List<Part>>() {
            @Override
            public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Part: Something went wrong!");
                    Logger.getLogger(getContext().getClass().getName()).log(
                            Level.SEVERE,
                            response.message()
                    );
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    partDatabase.insert(response.body());
                    message = "Part: Complete Successfully! Created - " + response.body().size();
                } else {
                    message = "Part: Already up to date!";
                }

                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Part>> call, Throwable t) {
                createToastMessage(t.getMessage());
                dialog.hide();
            }
        });
    }

    public void getWithDifference(List<Part> partList, boolean duplicate) {
        dialog.setTitle("Check for difference. Please wait.");
        dialog.show();
        partApi.getWithDifference(partList).enqueue(new Callback<List<Part>>() {
            @Override
            public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {
                if (!response.isSuccessful()) {
                    createToastMessage("Part: Something went wrong!");
                    dialog.hide();
                    return;
                }

                String message;
                if (response.body() != null) {
                    if (!duplicate) {
                        partDatabase.update(response.body());
                    } else {
                        partDatabase.insert(response.body());
                    }
                    message = "Part: Complete Successfully!";
                } else {
                    message = "Part: Already up to date!";
                }
                createToastMessage(message);
                dialog.hide();
            }

            @Override
            public void onFailure(Call<List<Part>> call, Throwable t) {
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
