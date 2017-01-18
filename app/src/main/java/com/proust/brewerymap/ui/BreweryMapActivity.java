package com.proust.brewerymap.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.proust.brewerymap.R;
import com.proust.brewerymap.core.beans.Beer;
import com.proust.brewerymap.core.beans.Brewery;
import com.proust.brewerymap.core.tools.CustomPreferences;
import com.proust.brewerymap.ui.adapters.map.BrewClusterItem;
import com.proust.brewerymap.ui.fragments.SlidingUpFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity to display breweries on map
 */
public class BreweryMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, ClusterManager.OnClusterItemClickListener<BrewClusterItem> {

    private GoogleMap mMap;

    private SlidingUpFragment mBottomBreweryFragment;
    private SlidingUpPanelLayout mLayout;

    private TextView mTextViewBottomFragmentBrewName;
    private TextView mTextViewBottomFragmentBrewNbBeers;

    private List<Brewery> mBreweries;

    private ClusterManager<BrewClusterItem> mClusterManager;

    private OnBrewMarkerSelectedListener mMarkerSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        mLayout.setEnabled(false);

        mTextViewBottomFragmentBrewName = (TextView)findViewById(R.id.tv_bottom_fragment_brew_name);
        mTextViewBottomFragmentBrewNbBeers = (TextView)findViewById(R.id.tv_brew_beers_nb);

        CustomPreferences.getInstance(getApplicationContext()).storeBreweries(generateBreweries());

        mBreweries = CustomPreferences.getInstance(getApplicationContext()).getAllBreweries();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mClusterManager = new ClusterManager<>(this, googleMap);
//        mClusterManager.setRenderer(new ClusterRenderer(this, googleMap, mClusterManager));
        mClusterManager.setOnClusterItemClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(mBreweries.get(0).getLocation()));

        mMap.setOnMarkerClickListener(mClusterManager);

        mMap.setOnCameraIdleListener(mClusterManager);

        placeNewBreweries(mBreweries);
    }

    /**
     * Create and add a marker on the map for each brew in the List passed by parameters
     * @param breweries List of brewery to add on the map
     */
    private void placeNewBreweries(List<Brewery> breweries) {
            for (Brewery brew : breweries) {
                BrewClusterItem offsetItem = new BrewClusterItem(brew);
                mClusterManager.addItem(offsetItem);
            }
    }

    /**
     * Temp method to generate brews to store, simulating API datas
     * @return ArrayList of generated brews
     */
    private ArrayList<Brewery> generateBreweries() {
        ArrayList<Brewery> brews = new ArrayList<>();
        ArrayList<Beer> beersSetOne = new ArrayList<>();
        ArrayList<Beer> beersSetTwo = new ArrayList<>();
        Beer tmpBeer = new Beer("Pelforth Brune");
        beersSetOne.add(tmpBeer);
        tmpBeer = new Beer("Pelforth Blonde");
        beersSetOne.add(tmpBeer);
        tmpBeer = new Beer("Spanish beer");
        beersSetTwo.add(tmpBeer);


        brews.add(new Brewery("Pelforth Brew", 1925, new LatLng(46.51, -0.35789), beersSetOne));
        brews.add(new Brewery("Pelforth Brew 2", 1925, new LatLng(46.512, -0.35719), beersSetOne));
        brews.add(new Brewery("Kro Brew", 1942, new LatLng(46.47, 0.0546), new ArrayList<Beer>()));
        brews.add(new Brewery("Spanish brew", 1942, new LatLng(42, -1), beersSetTwo));
        brews.add(new Brewery("Kro Brew 2", 1942, new LatLng(46.41, 0.054), new ArrayList<Beer>()));
        brews.add(new Brewery("Kro Brew 3", 1942, new LatLng(46.48, 0.053), new ArrayList<Beer>()));
        return brews;
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "Brew : " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onClusterItemClick(BrewClusterItem brewClusterItem) {
//        Toast.makeText(this, "Brewery : " + brewClusterItem.mBrew.getName(), Toast.LENGTH_SHORT).show();
        mLayout.setEnabled(true);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        mTextViewBottomFragmentBrewName.setText(brewClusterItem.mBrew.getName());
        mTextViewBottomFragmentBrewNbBeers.setText(""+brewClusterItem.mBrew.getBeers().size());
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN && mLayout.isEnabled()) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            mLayout.setPanelHeight(0);
            mLayout.setEnabled(false);
            Log.i("BiigLogPanel", " Panel state : " + mLayout.getPanelState());
        }
        return super.dispatchTouchEvent(ev);
    }

    private void showFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mBottomBreweryFragment.getId(), newFragment);
        transaction.addToBackStack(String.valueOf(newFragment.getId())).commit();
    }

    public interface OnBrewMarkerSelectedListener {
        void onBrewMarkerSelected();
    }
}
