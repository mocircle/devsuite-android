package com.mocircle.devsuite.android.ui.presenter.base;

public interface FragmentPresenter<V> extends BasePresenter<V> {

    void onActivityCreated();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
