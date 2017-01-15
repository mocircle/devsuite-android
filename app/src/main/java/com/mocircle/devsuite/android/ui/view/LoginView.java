package com.mocircle.devsuite.android.ui.view;

public interface LoginView extends BaseView {

    void notifyLoggingIn();

    void notifyLoggedIn();

    void notifyLoginFailed(String msg);

}
