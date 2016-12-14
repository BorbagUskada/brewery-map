package com.proust.brewerymap.ui.adapters.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.proust.brewerymap.core.beans.Brewery;

/**
 * Class that represent
 *
 * Created by proust on 14/12/2016.
 */

public class BrewClusterItem implements ClusterItem {

    public Brewery mBrew;

    public BrewClusterItem(Brewery brew) {
        mBrew = brew;
    }

    @Override
    public LatLng getPosition() {
        return mBrew.getLocation();
    }

}
