package com.mocircle.devsuite.android.data;

import com.mocircle.devsuite.android.model.User;

public interface LightDataService {

    boolean isLoggedIn();

    void recordLoginSession(String userId);

    void clearLoginSession();

    User getMyInfo();

    void setMyInfo(User user);

    String getAccessToken();

    void setAccessToken(String token);

}
