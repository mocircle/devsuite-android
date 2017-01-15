package com.mocircle.devsuite.android.ui.presenter;

import com.mocircle.devsuite.android.ui.presenter.base.FragmentPresenter;
import com.mocircle.devsuite.android.ui.view.SettingsView;

public interface SettingsPresenter extends FragmentPresenter<SettingsView> {

    void performLogout();

}
