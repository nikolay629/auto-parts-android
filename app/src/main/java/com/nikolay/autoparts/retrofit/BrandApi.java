package com.nikolay.autoparts.retrofit;

import com.nikolay.autoparts.model.Brand;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BrandApi {

    @POST("/brand/get-all-new")
    Call<List<Brand>> getAllNew(@Body List<Brand> brandList);

    @POST("/brand/get-with-difference")
    Call<List<Brand>> getWithDifference(@Body List<Brand> brandList);

    @POST("/brand/add-list")
    Call<List<Brand>> addList(@Body List<Brand> brandList);

    @POST("/brand/check-for-difference")
    Call<Boolean> checkForDifference(@Body List<Brand> brandList);

    @POST("/brand/update")
    Call<Boolean> update(@Body List<Brand> brandList);
}
