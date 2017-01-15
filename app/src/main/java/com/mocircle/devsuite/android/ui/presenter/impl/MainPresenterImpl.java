package com.mocircle.devsuite.android.ui.presenter.impl;

import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.ui.presenter.MainPresenter;
import com.mocircle.devsuite.android.ui.presenter.impl.base.ActivityPresenterImpl;
import com.mocircle.devsuite.android.ui.view.AuthenticationAwareView;
import com.mocircle.devsuite.android.ui.view.MainView;

import javax.inject.Inject;

public class MainPresenterImpl extends ActivityPresenterImpl<MainView> implements MainPresenter {

    @Inject
    LightDataService lightDataService;

    public MainPresenterImpl(LightDataService lightDataService) {
        this.lightDataService = lightDataService;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check for authentication
        if (lightDataService.isLoggedIn()) {
            if (view.getCurrentViewType() != AuthenticationAwareView.ViewType.AuthenticatedView) {
                view.switchToAuthenticatedView();
                view.setupNavigationHeader(lightDataService.getMyInfo());
            }
        } else {
            if (view.getCurrentViewType() != AuthenticationAwareView.ViewType.UnauthenticatedView) {
                view.switchToUnauthenticatedView();
            }
        }
    }

    @Override
    public void navigateToSummaryPage() {
        view.showSummaryUi();
    }

    @Override
    public void navigateToMessagesPage() {
        view.showMessagesUi();
    }

    @Override
    public void navigateToSettingsPage() {
        view.showSettingsUi();
    }

}
