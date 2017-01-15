package com.mocircle.devsuite.android.ui;

import com.mocircle.devsuite.android.data.LightDataService;
import com.mocircle.devsuite.android.data.MassDataService;
import com.mocircle.devsuite.android.ui.presenter.LoginPresenter;
import com.mocircle.devsuite.android.ui.presenter.MainPresenter;
import com.mocircle.devsuite.android.ui.presenter.MessagesPresenter;
import com.mocircle.devsuite.android.ui.presenter.SettingsPresenter;
import com.mocircle.devsuite.android.ui.presenter.SummaryPresenter;
import com.mocircle.devsuite.android.ui.presenter.impl.LoginPresenterImpl;
import com.mocircle.devsuite.android.ui.presenter.impl.MainPresenterImpl;
import com.mocircle.devsuite.android.ui.presenter.impl.MessagesPresenterImpl;
import com.mocircle.devsuite.android.ui.presenter.impl.SettingsPresenterImpl;
import com.mocircle.devsuite.android.ui.presenter.impl.SummaryPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {

    @Provides
    NavigationManager provideNavigationManager() {
        return new NavigationManagerImpl();
    }

    @Provides
    MainPresenter provideMainPresenter(LightDataService lightDataService) {
        return new MainPresenterImpl(lightDataService);
    }

    @Provides
    LoginPresenter provideLoginPresenter(LightDataService lightDataService) {
        return new LoginPresenterImpl(lightDataService);
    }

    @Provides
    SummaryPresenter provideSummaryPresenter() {
        return new SummaryPresenterImpl();
    }

    @Provides
    MessagesPresenter provideMessagesPresenter(LightDataService lightDataService, MassDataService massDataService) {
        return new MessagesPresenterImpl(lightDataService, massDataService);
    }

    @Provides
    SettingsPresenter provideSettingsPresenter(LightDataService lightDataService) {
        return new SettingsPresenterImpl(lightDataService);
    }

}
