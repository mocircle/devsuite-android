package com.mocircle.devsuite.android.ui.presenter.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.mocircle.devsuite.android.MyApp;
import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.service.AuthenticationService;
import com.mocircle.devsuite.android.ui.presenter.SettingsPresenter;
import com.mocircle.devsuite.android.ui.presenter.impl.base.FragmentPresenterImpl;
import com.mocircle.devsuite.android.ui.view.SettingsView;

public class SettingsPresenterImpl extends FragmentPresenterImpl<SettingsView> implements SettingsPresenter {

    private LocalBroadcastManager broadcastManager;
    private LightDataService lightDataService;
    private BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            view.notifyLoggedOut();
        }
    };

    public SettingsPresenterImpl(LightDataService lightDataService) {
        this.lightDataService = lightDataService;
        broadcastManager = LocalBroadcastManager.getInstance(MyApp.getApp());
    }

    @Override
    public void onResume() {
        broadcastManager.registerReceiver(logoutReceiver, new IntentFilter(AuthenticationService.ACTION_USER_LOGGED_OUT));
        if (!lightDataService.isLoggedIn()) {
            view.hideLogoutItem();
        }
    }

    @Override
    public void onPause() {
        broadcastManager.unregisterReceiver(logoutReceiver);
    }

    @Override
    public void performLogout() {
        view.notifyLoggingOut();
        AuthenticationService.performLogout(view.getContext());
    }
}
