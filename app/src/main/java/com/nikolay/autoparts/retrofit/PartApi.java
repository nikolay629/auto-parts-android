package com.nikolay.autoparts.retrofit;

import com.nikolay.autoparts.model.Category;
import com.nikolay.autoparts.model.Part;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PartApi {

    @POST("/part/get-all-new")
    Call<List<Part>> getAllNew(@Body List<Part> partList);

    @POST("/part/get-with-difference")
    Call<List<Part>> getWithDifference(@Body List<Part> partList);

    @POST("/part/add-list")
    Call<List<Part>> addList(@Body List<Part> partList);

    @POST("/part/check-for-difference")
    Call<Boolean> checkForDifference(@Body List<Part> partList);

    @POST("/part/update")
    Call<Boolean> update(@Body List<Part> partList);
}
