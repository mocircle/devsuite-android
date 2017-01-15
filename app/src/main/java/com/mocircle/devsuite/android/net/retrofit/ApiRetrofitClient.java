package com.mocircle.devsuite.android.net.retrofit;

import com.mocircle.devsuite.android.model.User;
import com.mocircle.devsuite.android.net.model.LoginRequest;
import com.mocircle.devsuite.android.net.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRetrofitClient {

    // Authentication API

    @POST("v1/open/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("v1/open/logout")
    void logout();

    // Contact API

    @GET("{user}/contacts")
    Call<List<User>> getContactList();

}
