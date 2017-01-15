package com.mocircle.devsuite.android.ui.presenter;

import com.mocircle.devsuite.android.ui.presenter.base.ActivityPresenter;
import com.mocircle.devsuite.android.ui.view.MainView;

public interface MainPresenter extends ActivityPresenter<MainView> {

    void navigateToSummaryPage();

    void navigateToMessagesPage();

    void navigateToSettingsPage();

}
