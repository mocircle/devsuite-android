package com.mocircle.devsuite.android.ui;

import com.mocircle.devsuite.android.AppModule;
import com.mocircle.devsuite.android.data.DataModule;
import com.mocircle.devsuite.android.net.NetModule;
import com.mocircle.devsuite.android.ui.activity.LoginActivity;
import com.mocircle.devsuite.android.ui.activity.MainActivity;
import com.mocircle.devsuite.android.ui.fragment.MessagesFragment;
import com.mocircle.devsuite.android.ui.fragment.SettingsFragment;
import com.mocircle.devsuite.android.ui.fragment.SummaryFragment;

import dagger.Component;

@Component(modules = {
        AppModule.class,
        UiModule.class,
        DataModule.class,
        NetModule.class,
})
public interface UiComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(SummaryFragment fragment);

    void inject(MessagesFragment fragment);

    void inject(SettingsFragment fragment);

}
