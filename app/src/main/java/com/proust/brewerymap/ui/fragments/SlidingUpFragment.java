package com.proust.brewerymap.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.proust.brewerymap.core.beans.Brewery;
import com.proust.brewerymap.ui.BreweryMapActivity;

/**
 * Created by proust on 14/12/2016.
 */

public class SlidingUpFragment extends Fragment implements BreweryMapActivity.OnBrewMarkerSelectedListener {

    private Brewery mBrew;

    public static SlidingUpFragment newInstance(Brewery brewery){
        SlidingUpFragment fragment = new SlidingUpFragment();

        fragment.mBrew = brewery;

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /*  */
    private void setBrewDetails() {

    }

    @Override
    public void onBrewMarkerSelected() {
        //TODO implement method (setBrewDetails, etc)
    }
}
