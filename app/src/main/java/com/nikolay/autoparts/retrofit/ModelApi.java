package com.nikolay.autoparts.retrofit;

import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.model.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ModelApi {

    @POST("/model/get-all-new")
    Call<List<Model>> getAllNew(@Body List<Model> modelList);

    @POST("/model/get-with-difference")
    Call<List<Model>> getWithDifference(@Body List<Model> modelList);

    @POST("/model/add-list")
    Call<List<Model>> addList(@Body List<Model> modelList);

    @POST("/model/check-for-difference")
    Call<Boolean> checkForDifference(@Body List<Model> modelList);

    @POST("/model/update")
    Call<Boolean> update(@Body List<Model> modelList);
}
