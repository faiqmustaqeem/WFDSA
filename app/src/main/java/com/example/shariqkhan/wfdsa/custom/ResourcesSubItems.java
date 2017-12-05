package com.example.shariqkhan.wfdsa.custom;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Codiansoft on 11/15/2017.
 */

public class ResourcesSubItems implements Parcelable {
    String id, title;

    public ResourcesSubItems(String id, String title) {
        this.id = id;
        this.title = title;
    }

    protected ResourcesSubItems(Parcel in) {
        title = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }

    public static final Creator<ResourcesSubItems> CREATOR = new Creator<ResourcesSubItems>() {
        @Override
        public ResourcesSubItems createFromParcel(Parcel in) {
            return new ResourcesSubItems(in);
        }

        @Override
        public ResourcesSubItems[] newArray(int size) {
            return new ResourcesSubItems[size];
        }
    };

}
