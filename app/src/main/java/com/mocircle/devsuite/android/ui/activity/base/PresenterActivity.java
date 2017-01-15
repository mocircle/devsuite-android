package com.mocircle.devsuite.android.ui.activity.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mocircle.devsuite.android.ui.presenter.base.ActivityPresenter;
import com.mocircle.devsuite.android.ui.view.BaseView;

import javax.inject.Inject;

public abstract class PresenterActivity<T extends ActivityPresenter> extends AppCompatActivity implements BaseView {

    @Inject
    protected T presenter;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjections();
        if (getContentView() > 0) {
            setContentView(getContentView());
            presenter.setView(this);
            setupView();
        }
        presenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    protected abstract int getContentView();

    protected abstract void setupInjections();

    protected abstract void setupView();

}
