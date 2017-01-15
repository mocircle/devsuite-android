package com.mocircle.devsuite.android.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mocircle.devsuite.android.ui.presenter.base.FragmentPresenter;

import javax.inject.Inject;

public abstract class PresenterFragment<T extends FragmentPresenter> extends BaseFragment {

    @Inject
    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjections();
        presenter.setView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    protected abstract void setupInjections();

}
