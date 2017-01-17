package com.mocircle.devsuite.android.data.realm;

import com.mocircle.devsuite.android.data.MassDataService;
import com.mocircle.devsuite.android.model.Message;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MassDataServiceImpl implements MassDataService {

    private Realm realm;

    public MassDataServiceImpl(Realm realm) {
        this.realm = realm;
    }

    @Override
    public void disconnect() {
        realm.close();
    }

    @Override
    public void subscribeDataChange(Object dataIdentity, final DataChangeListener listener) {
        if (dataIdentity instanceof RealmResults) {
            RealmResults<RealmObject> realmResults = (RealmResults<RealmObject>) dataIdentity;
            realmResults.addChangeListener(new RealmChangeListener<RealmResults<RealmObject>>() {
                @Override
                public void onChange(RealmResults<RealmObject> element) {
                    if (listener != null) {
                        listener.onChange();
                    }
                }
            });
        }
    }

    @Override
    public void unsubscribeDataChange(Object dataIdentity) {
        if (dataIdentity instanceof RealmResults) {
            RealmResults<RealmObject> realmResults = (RealmResults<RealmObject>) dataIdentity;
            realmResults.removeChangeListeners();
        }
    }

    @Override
    public void deleteAll() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    @Override
    public void saveOrUpdate(final RealmModel model) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(model);
            }
        });
    }

    @Override
    public void saveOrUpdate(final Iterable<? extends RealmModel> models) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(models);
            }
        });
    }

    @Override
    public List<Message> getMessageList() {
        return realm.where(Message.class).findAll();
    }

}
