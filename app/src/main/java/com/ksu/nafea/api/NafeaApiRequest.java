package com.ksu.nafea.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface NafeaApiRequest
{
    @GET
    Call<List<Object>> retrieveData(@Url String requestPath);

    @FormUrlEncoded
    @POST("/insert")
    Call<Object> insertData(@Field("table") String table, @Field("values") String values);

    @FormUrlEncoded
    @POST("/update")
    Call<Object> updateData(@Field("table") String table, @Field("valuesSet") String valuesSet, @Field("condition") String condition);

    @FormUrlEncoded
    @POST("/delete")
    Call<Object> deleteData(@Field("table") String table, @Field("condition") String condition);
}
