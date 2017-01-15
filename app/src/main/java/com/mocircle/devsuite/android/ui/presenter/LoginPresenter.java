package com.mocircle.devsuite.android.ui.presenter;

import com.mocircle.devsuite.android.ui.presenter.base.ActivityPresenter;
import com.mocircle.devsuite.android.ui.view.LoginView;

public interface LoginPresenter extends ActivityPresenter<LoginView> {

    void login(String userName, String password);
}
