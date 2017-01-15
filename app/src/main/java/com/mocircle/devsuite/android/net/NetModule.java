package com.mocircle.devsuite.android.net;

import android.util.Log;

import com.mocircle.devsuite.android.BuildConfig;
import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.net.retrofit.ApiRetrofitClient;
import com.mocircle.devsuite.android.net.retrofit.MyApiServiceImpl;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    @Provides
    public ApiRetrofitClient provideApiClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(ApiRetrofitClient.class);
    }

    @Provides
    public MyApiService provideApiService(ApiRetrofitClient client, LightDataService lightDataService) {
        //return new MyApiServiceMock();
        return new MyApiServiceImpl(client, lightDataService);
    }


}
