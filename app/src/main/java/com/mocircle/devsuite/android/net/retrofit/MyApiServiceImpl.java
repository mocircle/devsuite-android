package com.mocircle.devsuite.android.net.retrofit;

import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.model.Message;
import com.mocircle.devsuite.android.net.ApiException;
import com.mocircle.devsuite.android.net.MyApiService;
import com.mocircle.devsuite.android.net.model.LoginRequest;
import com.mocircle.devsuite.android.net.model.LoginResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MyApiServiceImpl implements MyApiService {

    private ApiRetrofitClient client;
    private LightDataService lightDataService;

    public MyApiServiceImpl(ApiRetrofitClient client, LightDataService lightDataService) {
        this.client = client;
        this.lightDataService = lightDataService;
    }

    @Override
    public LoginResponse login(String userName, String password) throws ApiException {
        LoginRequest request = new LoginRequest();
        request.userName = userName;
        request.password = password;
        return executeRequest(client.login(request));
    }

    @Override
    public void logout() throws ApiException {
        executeRequest(client.logout(lightDataService.getAccessToken()));
    }

    @Override
    public List<Message> getMessageList() throws ApiException {
        return executeRequest(client.getMessageList(lightDataService.getAccessToken()));
    }

    private <T> T executeRequest(Call<T> call) throws ApiException {
        try {
            Response<T> resp = call.execute();
            if (resp.isSuccessful()) {
                return resp.body();
            } else {
                throw new ApiException(resp.code(), resp.message());
            }
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

}
