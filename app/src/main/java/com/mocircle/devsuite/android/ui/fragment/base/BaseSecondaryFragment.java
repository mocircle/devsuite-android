package com.mocircle.devsuite.android.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mocircle.devsuite.android.R;
import com.mocircle.devsuite.android.ui.presenter.base.FragmentPresenter;


public abstract class BaseSecondaryFragment<T extends FragmentPresenter> extends PresenterFragment<T> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            resetToolbarBehavior(toolbar);
            setupToolbar(toolbar);
        }

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
        if (tabLayout != null) {
            resetTabs(tabLayout);
            setupTabs(tabLayout);
        }

        FloatingActionButton actionButton = (FloatingActionButton) getActivity().findViewById(R.id.floating_button);
        if (actionButton != null) {
            resetFloatingButton(actionButton);
            setupFloatingButton(actionButton);
        }
    }

    protected void setupToolbar(Toolbar toolbar) {
    }

    protected void setupTabs(TabLayout tabLayout) {
    }

    protected void setupFloatingButton(FloatingActionButton actionButton) {
    }

    private void resetToolbarBehavior(Toolbar toolbar) {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);
    }

    private void resetTabs(TabLayout tabLayout) {
        tabLayout.setVisibility(View.GONE);
        tabLayout.setupWithViewPager(null);
        tabLayout.removeAllTabs();
    }

    private void resetFloatingButton(FloatingActionButton actionButton) {
        actionButton.setVisibility(View.GONE);
        actionButton.setImageDrawable(null);
        actionButton.setOnClickListener(null);
    }

}
