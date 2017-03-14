package com.proust.brewerymap.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import com.proust.brewerymap.ui.adapters.map.ClusterRenderer;
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

    private SupportMapFragment mMapFragment;

    private SlidingUpFragment mBottomBreweryFragment;
    private SlidingUpPanelLayout mLayout;
    private View mViewMapDisabler;

    private Brewery mCurrentBrew;

    private TextView mTextViewBottomFragmentBrewName;
    private TextView mTextViewBottomFragmentBrewNbBeers;
    private Button mButtonGoToDetails;

    private List<Brewery> mBreweries;

    private ClusterManager<BrewClusterItem> mClusterManager;

    private OnBrewMarkerSelectedListener mMarkerSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_map);

        CustomPreferences.getInstance(getApplicationContext()).storeBreweries(generateBreweries());

        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        mLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);
        mLayout.setEnabled(true);
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        mViewMapDisabler = findViewById(R.id.v_map_disabler);
        mViewMapDisabler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                enableMap();
                return true;
            }
        });

        mTextViewBottomFragmentBrewName = (TextView)findViewById(R.id.tv_bottom_fragment_brew_name);
        mTextViewBottomFragmentBrewNbBeers = (TextView)findViewById(R.id.tv_brew_beers_nb);
        mButtonGoToDetails = (Button)findViewById(R.id.btn_go_to_brew_details);
        mButtonGoToDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBrewDetailsActivity(mCurrentBrew);
            }
        });

        /* Commented as I don't know why LatLng is null when retrieve from SharedPreferences */
        // mBreweries = CustomPreferences.getInstance(getApplicationContext()).getAllBreweries();
        Log.d("BiigLogBrewCreate", "LatLng : " + mBreweries.get(0).getLocation());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mClusterManager = new ClusterManager<>(this, googleMap);
        mClusterManager.setRenderer(new ClusterRenderer(this, googleMap, mClusterManager));
        mClusterManager.setOnClusterItemClickListener(this);

        mMap.setOnMarkerClickListener(mClusterManager);

        mMap.setOnCameraIdleListener(mClusterManager);
        Log.d("BiigLog", "Breweries : " + mBreweries.get(0).getLocation());
        placeNewBreweries(mBreweries);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(mBreweries.get(0).getLocation()));
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

        mBreweries = brews;
        Log.d("BiigLogBrew", "LatLng : " + brews.get(0).getLocation());
        return brews;
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "Brew : " + marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onClusterItemClick(BrewClusterItem brewClusterItem) {

        mTextViewBottomFragmentBrewName.setText(brewClusterItem.mBrew.getName());
        mTextViewBottomFragmentBrewNbBeers.setText(""+brewClusterItem.mBrew.getBeers().size());
        mCurrentBrew = brewClusterItem.mBrew;
        Log.i("BiigLogPanel", " Panel state : " + mLayout.getPanelState());
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        mLayout.setEnabled(true);
        disableMap();
//        mLayout.setPanelHeight(150);
        return false;
    }

    private void showFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mBottomBreweryFragment.getId(), newFragment);
        transaction.addToBackStack(String.valueOf(newFragment.getId())).commit();
    }

    public interface OnBrewMarkerSelectedListener {
        void onBrewMarkerSelected();
    }

    private void disableMap() {
        mViewMapDisabler.setVisibility(View.VISIBLE);
    }
    private void enableMap() {
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        mViewMapDisabler.setVisibility(View.GONE);
    }


    private void goToBrewDetailsActivity(Brewery brew) {

        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        Intent brewIntent = new Intent(this, BreweryDetailsActivity.class);
        brewIntent.putExtra(Brewery.BREWERY, brew);
        startActivity(brewIntent);
    }
}
