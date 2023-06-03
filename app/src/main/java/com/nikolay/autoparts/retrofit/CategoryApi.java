package com.nikolay.autoparts.retrofit;

import com.nikolay.autoparts.model.Brand;
import com.nikolay.autoparts.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CategoryApi {

    @POST("/category/get-all-new")
    Call<List<Category>> getAllNew(@Body List<Category> categoryList);

    @POST("/category/get-with-difference")
    Call<List<Category>> getWithDifference(@Body List<Category> categoryList);

    @POST("/category/add-list")
    Call<List<Category>> addList(@Body List<Category> categoryList);

    @POST("/category/check-for-difference")
    Call<Boolean> checkForDifference(@Body List<Category> categoryList);

    @POST("/category/update")
    Call<Boolean> update(@Body List<Category> categoryList);
}
