package com.mocircle.devsuite.android.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mocircle.devsuite.android.MyApp;
import com.mocircle.devsuite.android.R;
import com.mocircle.devsuite.android.ui.activity.base.PresenterActivity;
import com.mocircle.devsuite.android.ui.presenter.LoginPresenter;
import com.mocircle.devsuite.android.ui.view.LoginView;

public class LoginActivity extends PresenterActivity<LoginPresenter> implements LoginView {

    private EditText userNameText;
    private EditText passwordText;
    private ProgressDialog loginDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupInjections() {
        MyApp.getApp().getUiComponent().inject(this);
    }

    @Override
    protected void setupView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.login_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userNameText = (EditText) findViewById(R.id.username_text);
        passwordText = (EditText) findViewById(R.id.password_text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickSignInButton(View v) {
        if (TextUtils.isEmpty(userNameText.getText())) {
            userNameText.setError(getString(R.string.login_error_empty));
            return;
        }
        if (TextUtils.isEmpty(passwordText.getText())) {
            passwordText.setError(getString(R.string.login_error_empty));
            return;
        }
        presenter.login(userNameText.getText().toString(), passwordText.getText().toString());
    }

    @Override
    public void notifyLoggingIn() {
        loginDialog = ProgressDialog.show(this, null, getString(R.string.dialog_logging_in), true, false);
    }

    @Override
    public void notifyLoggedIn() {
        if (loginDialog != null) {
            loginDialog.dismiss();
        }
        Toast.makeText(this, R.string.toast_logged_in, Toast.LENGTH_SHORT).show();
        finish();

        // Start main ui
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void notifyLoginFailed(String msg) {
        if (loginDialog != null) {
            loginDialog.dismiss();
        }
        if (TextUtils.isEmpty(msg)) {
            msg = getString(R.string.toast_login_error_default);
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
