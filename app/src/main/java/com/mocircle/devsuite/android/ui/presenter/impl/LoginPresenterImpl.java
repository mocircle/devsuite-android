package com.mocircle.devsuite.android.ui.presenter.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.mocircle.devsuite.android.MyApp;
import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.service.AuthenticationService;
import com.mocircle.devsuite.android.ui.presenter.LoginPresenter;
import com.mocircle.devsuite.android.ui.presenter.impl.base.ActivityPresenterImpl;
import com.mocircle.devsuite.android.ui.view.LoginView;

public class LoginPresenterImpl extends ActivityPresenterImpl<LoginView> implements LoginPresenter {

    private LightDataService lightDataService;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver loginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(AuthenticationService.ACTION_USER_LOGGED_IN)) {
                view.notifyLoggedIn();
            } else if (action.equals(AuthenticationService.ACTION_USER_LOGIN_FAILED)) {
                String msg = intent.getStringExtra(AuthenticationService.EXTRA_MSG);
                view.notifyLoginFailed(msg);
            }
        }
    };

    public LoginPresenterImpl(LightDataService lightDataService) {
        this.lightDataService = lightDataService;
        broadcastManager = LocalBroadcastManager.getInstance(MyApp.getApp());
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AuthenticationService.ACTION_USER_LOGGED_IN);
        filter.addAction(AuthenticationService.ACTION_USER_LOGIN_FAILED);
        broadcastManager.registerReceiver(loginReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        broadcastManager.unregisterReceiver(loginReceiver);
    }

    @Override
    public void login(String userName, String password) {
        view.notifyLoggingIn();
        AuthenticationService.performLogin(view.getContext(), userName, password);
    }


}
