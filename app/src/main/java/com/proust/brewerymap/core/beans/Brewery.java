package com.proust.brewerymap.core.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean that represent a single Brewery
 *
 * Created by proust on 30/11/2016.
 */

public class Brewery implements Parcelable {

    public static final String BREWERY = "BREWERY";

    private String mName;

    private int mFoundingDate;

    private transient LatLng mLocation;

    private List<Beer> mBeers;

    public Brewery(String name, int foundingDate,
                   double latitude, double longitude,
                   ArrayList<Beer> beers) {
        this(name, foundingDate, new LatLng(latitude, longitude), beers);
    }

    public Brewery(String name, int foundingDate, LatLng location, ArrayList<Beer> beers) {
        mName = name;
        mFoundingDate = foundingDate;
        mLocation = location;
        mBeers = beers;
    }

    public Brewery(Parcel source) {
        mName = source.readString();
        mFoundingDate = source.readInt();
        mBeers = source.readArrayList(null);
//        mLocation = source.readParcelable(LatLng.class);
    }

    public LatLng getLocation() {
        return mLocation;
    }

    public String getName() {
        return mName;
    }

    public int getFoundingDate() {
        return mFoundingDate;
    }

    public ArrayList<Beer> getBeers() {
        return (ArrayList<Beer>) mBeers;
    }

    public void initBeers() {
        mBeers = new ArrayList<>();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mFoundingDate);
        dest.writeTypedList(mBeers);
        dest.writeParcelable(mLocation, 1);
    }
    public static final Parcelable.Creator<Brewery> CREATOR = new Parcelable.Creator<Brewery>() {

        @Override
        public Brewery createFromParcel(Parcel source) {
            return new Brewery(source);
        }

        @Override
        public Brewery[] newArray(int size) {
            return new Brewery[size];
        }

    };
}
