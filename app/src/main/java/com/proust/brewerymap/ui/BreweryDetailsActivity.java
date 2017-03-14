package com.proust.brewerymap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.proust.brewerymap.R;
import com.proust.brewerymap.core.beans.Brewery;
import com.proust.brewerymap.ui.adapters.BreweryBeersAdapter;

/**
 * Created by proust on 18/01/2017.
 */

public class BreweryDetailsActivity extends AppCompatActivity {

    private TextView mTextViewBrewName;
    private BreweryBeersAdapter mBeerAdapter;

    private Brewery mBrewery;

    private boolean mHasNoBeer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_details);

        Intent i = getIntent();
        mBrewery = (Brewery) i.getParcelableExtra(Brewery.BREWERY);

        String tmpString;
        mTextViewBrewName = (TextView)findViewById(R.id.tv_details_brew_name);

        TextView textViewNbBeers = (TextView)findViewById(R.id.tv_details_nb_beers);
        mTextViewBrewName.setText(mBrewery.getName());
        if(mBrewery.getBeers() == null || mBrewery.getBeers().size() == 0)
            tmpString = "0";
        else
            tmpString = ""+ mBrewery.getBeers().size();
        textViewNbBeers.setText(tmpString);

        TextView textViewFoundingDate = (TextView)findViewById(R.id.tv_details_year);
        tmpString = mBrewery.getFoundingDate() + "";
        textViewFoundingDate.setText(tmpString);

        ListView listview = (ListView)findViewById(R.id.lv_details_beers);
        if(mBrewery.getBeers() == null) {
            Log.d("BiigLog", "Beers are null");
            mBrewery.initBeers();
        }
        mBeerAdapter = new BreweryBeersAdapter(this, R.layout.item_beer, mBrewery.getBeers());
        listview.setAdapter(mBeerAdapter);
    }


}
