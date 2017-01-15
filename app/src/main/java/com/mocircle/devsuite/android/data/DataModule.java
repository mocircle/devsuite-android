package com.mocircle.devsuite.android.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.mocircle.devsuite.android.data.preference.LightDataServiceImpl;
import com.mocircle.devsuite.android.data.realm.MassDataServiceImpl;
import com.mocircle.devsuite.android.data.realm.RealmConstants;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class DataModule {

    @Provides
    public MassDataService provideMassDataService(Realm realm) {
        return new MassDataServiceImpl(realm);
    }

    @Provides
    public LightDataService provideLightDataService(SharedPreferences prefs) {
        return new LightDataServiceImpl(prefs);
    }

    @Provides
    public RealmConfiguration provideRealmConfiguration(Context context) {
        return new RealmConfiguration.Builder()
                .name(RealmConstants.DB_NAME)
                .schemaVersion(RealmConstants.DB_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    @Provides
    public Realm provideRealm(RealmConfiguration configuration) {
        return Realm.getInstance(configuration);
    }

}
