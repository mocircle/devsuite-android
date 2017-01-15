package com.mocircle.devsuite.android.ui.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.mocircle.devsuite.android.MyApp;
import com.mocircle.devsuite.android.R;
import com.mocircle.devsuite.android.ui.presenter.SettingsPresenter;
import com.mocircle.devsuite.android.ui.view.SettingsView;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat implements SettingsView {

    @Inject
    SettingsPresenter presenter;

    private Preference logoutPref;
    private ProgressDialog progressDialog;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyApp.getApp().getUiComponent().inject(this);
        presenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void hideLogoutItem() {
        if (logoutPref != null) {
            logoutPref.setVisible(false);
        }
    }

    @Override
    public void notifyLoggingOut() {
        progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.dialog_logging_out), true, false);
    }

    @Override
    public void notifyLoggedOut() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(getContext(), R.string.toast_logged_out, Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings);

        logoutPref = findPreference(getString(R.string.pref_logout));
        logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                askForLogout();
                return true;
            }
        });
    }

    private void askForLogout() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.dialog_logout_title)
                .setMessage(R.string.dialog_logout_msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.performLogout();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

}
