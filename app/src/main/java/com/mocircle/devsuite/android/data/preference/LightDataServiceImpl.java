package com.mocircle.devsuite.android.data.preference;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.model.User;

public class LightDataServiceImpl implements LightDataService {

    private static final String PREF_LOGIN_USER = "pref_login_user";
    private static final String PREF_LOGIN_TIMESTAMP = "pref_login_timestamp";
    private static final String PREF_MY_INFO = "pref_my_info";
    private static final String PREF_ACCESS_TOKEN = "pref_access_token";

    private SharedPreferences prefs;
    private Gson gson = new Gson();

    public LightDataServiceImpl(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public boolean isLoggedIn() {
        return prefs.getLong(PREF_LOGIN_TIMESTAMP, 0) > 0;
    }

    @Override
    public void recordLoginSession(String userId) {
        prefs.edit().putString(PREF_LOGIN_USER, userId)
                .putLong(PREF_LOGIN_TIMESTAMP, System.currentTimeMillis())
                .apply();
    }

    @Override
    public void clearLoginSession() {
        prefs.edit().remove(PREF_LOGIN_USER)
                .remove(PREF_LOGIN_TIMESTAMP)
                .apply();
    }

    @Override
    public User getMyInfo() {
        String myInfo = prefs.getString(PREF_MY_INFO, null);
        if (myInfo != null) {
            return gson.fromJson(myInfo, User.class);
        } else {
            return null;
        }
    }

    @Override
    public void setMyInfo(User user) {
        String userInfo = gson.toJson(user);
        prefs.edit().putString(PREF_MY_INFO, userInfo).apply();
    }

    @Override
    public String getAccessToken() {
        return prefs.getString(PREF_ACCESS_TOKEN, "");
    }

    @Override
    public void setAccessToken(String token) {
        prefs.edit().putString(PREF_ACCESS_TOKEN, token)
                .apply();
    }
}
