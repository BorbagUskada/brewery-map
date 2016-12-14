package com.proust.brewerymap.core.beans;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean that represent a single Brewery
 *
 * Created by proust on 30/11/2016.
 */

public class Brewery {


    private String mName;

    private int mFoundingDate;

    private LatLng mLocation;

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
}
