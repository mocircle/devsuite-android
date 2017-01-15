package com.mocircle.devsuite.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject implements Parcelable {

    public static final int TYPE_INDIVIDUAL = 0;
    public static final int TYPE_BUSINESS = 1;

    public static final int STATUS_UNCONFIRMED = 0;
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_BLOCKED = -1;
    public static final int STATUS_DELETED = -2;

    @PrimaryKey
    public long modelId = MyModel.UNASSIGNED_PRIMARY_KEY;

    public int userType;

    public String userName;

    public String email;

    public String displayName;

    public String phone;

    public String avatar;

    public int gender;

    public String bio;

    public String country;

    public String state;

    public String city;

    public int status;

    public User() {
    }

    protected User(Parcel in) {
        modelId = in.readLong();
        userType = in.readInt();
        userName = in.readString();
        email = in.readString();
        displayName = in.readString();
        phone = in.readString();
        avatar = in.readString();
        gender = in.readInt();
        bio = in.readString();
        country = in.readString();
        state = in.readString();
        city = in.readString();
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(modelId);
        dest.writeInt(userType);
        dest.writeString(userName);
        dest.writeString(email);
        dest.writeString(displayName);
        dest.writeString(phone);
        dest.writeString(avatar);
        dest.writeInt(gender);
        dest.writeString(bio);
        dest.writeString(country);
        dest.writeString(state);
        dest.writeString(city);
        dest.writeInt(status);
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
