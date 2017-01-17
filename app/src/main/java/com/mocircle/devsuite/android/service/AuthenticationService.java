package com.mocircle.devsuite.android.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.mocircle.devsuite.android.MyApp;
import com.mocircle.devsuite.android.R;
import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.data.MassDataService;
import com.mocircle.devsuite.android.logging.MyLog;
import com.mocircle.devsuite.android.net.ApiException;
import com.mocircle.devsuite.android.net.MyApiService;
import com.mocircle.devsuite.android.net.model.LoginResponse;

import javax.inject.Inject;

public class AuthenticationService extends IntentService {

    public static final String ACTION_LOGIN = "com.mocircle.devsuite.action.LOGIN";
    public static final String ACTION_LOGOUT = "com.mocircle.devsuite.action.LOGOUT";

    public static final String ACTION_USER_LOGGED_IN = "com.mocircle.devsuite.action.USER_LOGGED_IN";
    public static final String ACTION_USER_LOGIN_FAILED = "com.mocircle.devsuite.action.USER_LOGIN_FAILED";
    public static final String ACTION_USER_LOGGED_OUT = "com.mocircle.devsuite.action.USER_LOGGED_OUT";

    public static final String EXTRA_USERNAME = "extra_username";
    public static final String EXTRA_PASSWORD = "extra_password";
    public static final String EXTRA_MSG = "extra_msg";

    private static final String TAG = "AuthenticationService";

    @Inject
    MyApiService apiService;

    @Inject
    LightDataService lightDataService;

    @Inject
    MassDataService massDataService;

    public AuthenticationService() {
        super("AuthenticationService");
    }

    public static void performLogin(Context context, String userName, String password) {
        Intent intent = new Intent(context, AuthenticationService.class);
        intent.setAction(ACTION_LOGIN);
        intent.putExtra(EXTRA_USERNAME, userName);
        intent.putExtra(EXTRA_PASSWORD, password);
        context.startService(intent);
    }

    public static void performLogout(Context context) {
        Intent intent = new Intent(context, AuthenticationService.class);
        intent.setAction(ACTION_LOGOUT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MyApp.getApp().getAppComponent().inject(this);

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals(ACTION_LOGIN)) {
                doLogin(intent);
            } else if (action.equals(ACTION_LOGOUT)) {
                doLogout(intent);
            } else {
                MyLog.w(TAG, "Unknown action: " + action);
            }
        }

        massDataService.disconnect();
    }

    private void doLogin(Intent intent) {
        String userName = intent.getStringExtra(EXTRA_USERNAME);
        String password = intent.getStringExtra(EXTRA_PASSWORD);
        if (userName != null && password != null) {
            LoginResponse result = null;
            try {
                result = apiService.login(userName, password);
            } catch (ApiException e) {
                // Login failed
                Intent action = new Intent(ACTION_USER_LOGIN_FAILED);
                String msg = getString(R.string.toast_error_network);
                if (!e.isIOException() && e.getErrorResponse() != null) {
                    msg = e.getErrorResponse().errMsg;
                }
                action.putExtra(EXTRA_MSG, msg);
                LocalBroadcastManager.getInstance(this).sendBroadcast(action);
                return;
            }
            if (result != null) {
                lightDataService.setAccessToken(result.token);
                lightDataService.setMyInfo(result.userInfo);
                lightDataService.recordLoginSession(result.userInfo.userName);

                // Send logged in broadcast
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_USER_LOGGED_IN));
            }
        } else {
            // Login failed
            Intent action = new Intent(ACTION_USER_LOGIN_FAILED);
            LocalBroadcastManager.getInstance(this).sendBroadcast(action);
        }
    }

    private void doLogout(Intent intent) {
        try {
            apiService.logout();
        } catch (ApiException e) {
            MyLog.w(TAG, e);
        }
        massDataService.deleteAll();

        // Reset local resource and notify
        lightDataService.clearLoginSession();
        lightDataService.setMyInfo(null);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_USER_LOGGED_OUT));
    }


}
