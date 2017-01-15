package com.mocircle.devsuite.android.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Message extends RealmObject {

    @PrimaryKey
    public long modelId = MyModel.UNASSIGNED_PRIMARY_KEY;

    public String title;

    public String body;

}
