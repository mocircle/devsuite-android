package com.mocircle.devsuite.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mocircle.devsuite.android.MyApp;
import com.mocircle.devsuite.android.R;
import com.mocircle.devsuite.android.model.User;
import com.mocircle.devsuite.android.ui.activity.base.PresenterActivity;
import com.mocircle.devsuite.android.ui.fragment.MessagesFragment;
import com.mocircle.devsuite.android.ui.fragment.SummaryFragment;
import com.mocircle.devsuite.android.ui.presenter.MainPresenter;
import com.mocircle.devsuite.android.ui.view.MainView;

public class MainActivity extends PresenterActivity<MainPresenter>
        implements MainView, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private View authenticatedLayout;
    private View unauthenticatedLayout;

    private TextView nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        presenter.setView(this);
        setupView();
    }

    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void setupInjections() {
        MyApp.getApp().getUiComponent().inject(this);
    }

    @Override
    protected void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        authenticatedLayout = headerView.findViewById(R.id.authenticated_layout);
        unauthenticatedLayout = headerView.findViewById(R.id.unauthenticated_layout);
        nameText = (TextView) headerView.findViewById(R.id.name_text);
        headerView.findViewById(R.id.show_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Set up and select first item
        navigationView.setCheckedItem(R.id.nav_summary);
        presenter.navigateToSummaryPage();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_summary) {
            presenter.navigateToSummaryPage();
        } else if (id == R.id.nav_messages) {
            presenter.navigateToMessagesPage();
        } else if (id == R.id.nav_settings) {
            presenter.navigateToSettingsPage();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showSummaryUi() {
        Fragment fragment = new SummaryFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void showMessagesUi() {
        Fragment fragment = new MessagesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void showSettingsUi() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void setupNavigationHeader(final User user) {
        if (user != null) {
            nameText.setText(user.displayName);
        }
    }

    @Override
    public ViewType getCurrentViewType() {
        return ViewType.UnknownView;
    }

    @Override
    public void switchToAuthenticatedView() {
        authenticatedLayout.setVisibility(View.VISIBLE);
        unauthenticatedLayout.setVisibility(View.GONE);
    }

    @Override
    public void switchToUnauthenticatedView() {
        authenticatedLayout.setVisibility(View.GONE);
        unauthenticatedLayout.setVisibility(View.VISIBLE);
    }
}
