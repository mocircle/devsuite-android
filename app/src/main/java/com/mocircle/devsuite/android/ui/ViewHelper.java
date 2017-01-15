package com.mocircle.devsuite.android.ui;

import android.content.Context;
import android.view.View;

import com.mocircle.devsuite.android.R;

public final class ViewHelper {

    public static void setupLoginRequiredLayout(final Context context, View layout,
                                                final NavigationManager navigationManager) {
        View signinButton = layout.findViewById(R.id.signin_button);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationManager.navigateToLoginPage(context);
            }
        });
        View signupButton = layout.findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationManager.navigateToSignUpPage(context);
            }
        });
    }

}
