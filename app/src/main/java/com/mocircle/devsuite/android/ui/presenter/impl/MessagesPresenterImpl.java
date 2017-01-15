package com.mocircle.devsuite.android.ui.presenter.impl;

import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.data.MassDataService;
import com.mocircle.devsuite.android.ui.presenter.MessagesPresenter;
import com.mocircle.devsuite.android.ui.presenter.impl.base.FragmentPresenterImpl;
import com.mocircle.devsuite.android.ui.view.AuthenticationAwareView;
import com.mocircle.devsuite.android.ui.view.MessagesView;

public class MessagesPresenterImpl extends FragmentPresenterImpl<MessagesView> implements MessagesPresenter {

    private LightDataService lightDataService;
    private MassDataService massDataService;

    public MessagesPresenterImpl(LightDataService lightDataService, MassDataService massDataService) {
        this.lightDataService = lightDataService;
        this.massDataService = massDataService;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        massDataService.disconnect();
    }
}
