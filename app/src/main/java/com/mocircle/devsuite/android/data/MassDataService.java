package com.mocircle.devsuite.android.data;


import com.mocircle.devsuite.android.model.Message;
import com.mocircle.devsuite.android.model.User;

import java.util.List;

import io.realm.RealmModel;

public interface MassDataService {

    interface DataChangeListener {
        void onChange();
    }

    void disconnect();

    void subscribeDataChange(Object dataIdentity, DataChangeListener listener);

    void unsubscribeDataChange(Object dataIdentity);

    void deleteAll();

    void saveOrUpdate(RealmModel model);

    void saveOrUpdate(Iterable<? extends RealmModel> models);

    List<Message> getMessageList();

}
