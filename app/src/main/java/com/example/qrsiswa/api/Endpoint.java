package com.example.qrsiswa.api;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Endpoint {

    @POST("filterData")
    Call<Map<String, Object>> filterData(@Query("nis") String nis);
}
