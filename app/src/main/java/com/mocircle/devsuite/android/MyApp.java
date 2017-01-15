package com.mocircle.devsuite.android;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.mocircle.devsuite.android.data.DataModule;
import com.mocircle.devsuite.android.net.NetModule;
import com.mocircle.devsuite.android.ui.DaggerUiComponent;
import com.mocircle.devsuite.android.ui.UiComponent;
import com.mocircle.devsuite.android.ui.UiModule;

import io.realm.Realm;

public class MyApp extends Application {

    private static MyApp app;
    private AppComponent appComponent;
    private UiComponent uiComponent;

    public static MyApp getApp() {
        return app;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public UiComponent getUiComponent() {
        return uiComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        Realm.init(this);
        setupDaggerComponents();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    private void setupDaggerComponents() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dataModule(new DataModule())
                .netModule(new NetModule())
                .build();

        uiComponent = DaggerUiComponent.builder()
                .appModule(new AppModule(this))
                .uiModule(new UiModule())
                .dataModule(new DataModule())
                .netModule(new NetModule())
                .build();
    }

}
