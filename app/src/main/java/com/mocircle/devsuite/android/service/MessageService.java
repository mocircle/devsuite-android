package com.mocircle.devsuite.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.mocircle.devsuite.android.MyApp;
import com.mocircle.devsuite.android.data.MassDataService;
import com.mocircle.devsuite.android.logging.MyLog;
import com.mocircle.devsuite.android.model.Message;
import com.mocircle.devsuite.android.net.ApiException;
import com.mocircle.devsuite.android.net.MyApiService;

import java.util.List;

import javax.inject.Inject;

public class MessageService extends IntentService {

    public static final String ACTION_UPDATE_MESSAGES = "com.mocircle.devsuite.action.UPDATE_MESSAGES";

    public static final String ACTION_MESSAGES_UPDATED = "com.mocircle.devsuite.action.MESSAGES_UPDATED";

    private static final String TAG = "MessageService";

    @Inject
    MyApiService apiService;

    @Inject
    MassDataService massDataService;

    public MessageService() {
        super("MessageService");
    }

    public static void startUpdateMessagesService() {
        Intent intent = new Intent(MyApp.getApp(), MessageService.class);
        intent.setAction(ACTION_UPDATE_MESSAGES);
        MyApp.getApp().startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MyApp.getApp().getAppComponent().inject(this);

        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals(ACTION_UPDATE_MESSAGES)) {
                updateMessages();
            } else {
                MyLog.w(TAG, "Unknown action: " + action);
            }
        }

        massDataService.disconnect();
    }

    private void updateMessages() {
        try {
            final List<Message> list = apiService.getMessageList();
            if (list != null) {
                massDataService.saveOrUpdate(list);
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_MESSAGES_UPDATED));
            }
        } catch (ApiException e) {
            e.printStackTrace();//TODO
        }
    }

}
