package com.mocircle.devsuite.android.net;

import com.mocircle.devsuite.android.model.User;
import com.mocircle.devsuite.android.net.model.LoginResponse;

import java.util.List;

public interface MyApiService {

    LoginResponse login(String userName, String password) throws ApiException;

    void logout() throws ApiException;

    List<User> getContactList() throws ApiException;

}
