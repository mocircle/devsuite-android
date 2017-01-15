package com.mocircle.devsuite.android.net;

import com.mocircle.devsuite.android.model.User;
import com.mocircle.devsuite.android.net.model.LoginResponse;

import java.util.List;

public class MyApiServiceMock implements MyApiService {

    @Override
    public LoginResponse login(String userName, String password) throws ApiException {
        return null;
    }

    @Override
    public void logout() throws ApiException {

    }

    @Override
    public List<User> getContactList() throws ApiException {
        return null;
    }

    private void delay(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
