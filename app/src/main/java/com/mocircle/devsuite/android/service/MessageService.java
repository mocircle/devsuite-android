package com.mocircle.devsuite.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.mocircle.devsuite.android.MyApp;
import com.mocircle.devsuite.android.data.MassDataService;
import com.mocircle.devsuite.android.logging.MyLog;
import com.mocircle.devsuite.android.model.User;
import com.mocircle.devsuite.android.net.ApiException;
import com.mocircle.devsuite.android.net.MyApiService;

import java.util.List;

import javax.inject.Inject;

public class MessageService extends IntentService {

    public static final String ACTION_UPDATE_CONTACTS = "com.mocircle.devsuite.action.UPDATE_CONTACTS";

    public static final String ACTION_CONTACTS_UPDATED = "com.mocircle.devsuite.action.CONTACTS_UPDATED";

    private static final String TAG = "MessageService";

    @Inject
    MyApiService apiService;

    @Inject
    MassDataService massDataService;

    public MessageService() {
        super("MessageService");
    }

    public static void startUpdateContactsService() {
        Intent intent = new Intent(MyApp.getApp(), MessageService.class);
        intent.setAction(ACTION_UPDATE_CONTACTS);
        MyApp.getApp().startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MyApp.getApp().getAppComponent().inject(this);

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals(ACTION_UPDATE_CONTACTS)) {
                updateContacts();
            } else {
                MyLog.w(TAG, "Unknown action: " + action);
            }
        }

        massDataService.disconnect();
    }

    private void updateContacts() {
        try {
            final List<User> list = apiService.getContactList();
            if (list != null) {
                massDataService.saveOrUpdate(list);
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_CONTACTS_UPDATED));
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
