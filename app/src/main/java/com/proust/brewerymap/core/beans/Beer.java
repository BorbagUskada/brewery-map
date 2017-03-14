package com.proust.brewerymap.core.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean that represent a single beer
 *
 * Created by proust on 30/11/2016.
 */

public class Beer implements Parcelable {

    private String mName;

    private List<Characteristic> mCharacteristics;

    public Beer(String name) {
        mName = name;
    }

    public Beer(String name, ArrayList<Characteristic> characteristics) {
        mName = name;
        mCharacteristics = characteristics;
    }

    public Beer(Parcel source) {
        mName = source.readString();
//        mCharacteristics=  new Arrajacteristics, null);
    }

    public String getName() {
        return mName;
    }

    public ArrayList<Characteristic> getNotes() {
        return (ArrayList<Characteristic>) mCharacteristics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeTypedList(mCharacteristics);
    }

    public static final Parcelable.Creator<Beer> CREATOR = new Parcelable.Creator<Beer>() {

        @Override
        public Beer createFromParcel(Parcel source) {
            return new Beer(source);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size]; // CREATING AN ARRAY OF VENUES
        }

    };
}
