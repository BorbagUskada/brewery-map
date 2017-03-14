package com.proust.brewerymap.core.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Class that represent characteristics and notes of beer
 * TODO Verify that a class is really needed and has real avantages over simple String (like more
 * TODO attributes)
 *
 * Created by proust on 30/11/2016.
 */

public class Characteristic implements Parcelable {

    /* This field cannot be empty as it's the only one */
    public String mName;

    public Characteristic(String name) {
        mName = name;
    }

    public Characteristic(Parcel source) {
        mName = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
    }
    public static final Parcelable.Creator<Characteristic> CREATOR = new Parcelable.Creator<Characteristic>() {

        @Override
        public Characteristic createFromParcel(Parcel source) {
            return new Characteristic(source); // RECREATE VENUE GIVEN SOURCE
        }

        @Override
        public Characteristic[] newArray(int size) {
            return new Characteristic[size]; // CREATING AN ARRAY OF VENUES
        }

    };
}
