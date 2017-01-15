package com.mocircle.devsuite.android.ui.presenter.base;

public interface ActivityPresenter<V> extends BasePresenter<V> {

    void onCreate();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
