package com.mocircle.devsuite.android.net;

import com.mocircle.devsuite.android.BuildConfig;
import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.net.retrofit.ApiRetrofitClient;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
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
        return new MyApiServiceMock();
        // Uncomment this to enable real API request
        //return new MyApiServiceImpl(client, lightDataService);
    }


}
