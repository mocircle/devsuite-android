package com.mocircle.devsuite.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject implements Parcelable {

    @PrimaryKey
    public long modelId = MyModel.UNASSIGNED_PRIMARY_KEY;

    public String userName;

    public String email;

    public String displayName;

    public User() {
    }

    protected User(Parcel in) {
        modelId = in.readLong();
        userName = in.readString();
        email = in.readString();
        displayName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(modelId);
        dest.writeString(userName);
        dest.writeString(email);
        dest.writeString(displayName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
