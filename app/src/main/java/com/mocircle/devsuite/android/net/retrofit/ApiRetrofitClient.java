package com.mocircle.devsuite.android.net.retrofit;

import com.mocircle.devsuite.android.model.Message;
import com.mocircle.devsuite.android.net.model.LoginRequest;
import com.mocircle.devsuite.android.net.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiRetrofitClient {

    String HEADER_TOKEN = "x-auth-token";

    // Authentication API

    @POST("v1/open/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("v1/open/logout")
    Call<Void> logout(@Header(HEADER_TOKEN) String token);

    // Message API

    @GET("v1/messages")
    Call<List<Message>> getMessageList(@Header(HEADER_TOKEN) String token);

}
