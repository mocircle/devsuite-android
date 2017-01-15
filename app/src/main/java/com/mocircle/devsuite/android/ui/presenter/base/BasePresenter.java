package com.mocircle.devsuite.android.ui.presenter.base;

public interface BasePresenter<V> {

    V getView();

    void setView(V view);

}
