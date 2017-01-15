package com.mocircle.devsuite.android.ui.presenter.impl.base;

import com.mocircle.devsuite.android.ui.presenter.base.BasePresenter;

public abstract class BasePresenterImpl<V> implements BasePresenter<V> {

    protected V view;

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

}
