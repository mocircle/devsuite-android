package com.mocircle.devsuite.android.ui;

import android.content.Context;
import android.content.Intent;

import com.mocircle.devsuite.android.ui.activity.LoginActivity;

public class NavigationManagerImpl implements NavigationManager {

    @Override
    public void navigateToLoginPage(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void navigateToSignUpPage(Context context) {
        // Show sign up activity
    }
}
