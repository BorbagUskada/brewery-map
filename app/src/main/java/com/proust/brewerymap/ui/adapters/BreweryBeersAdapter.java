package com.proust.brewerymap.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.proust.brewerymap.R;
import com.proust.brewerymap.core.beans.Beer;

import java.util.List;

/**
 * Created by proust on 01/02/2017.
 */

public class BreweryBeersAdapter extends ArrayAdapter<Beer> {

//    private Context mContext;
    private List<Beer> mBeers;
    private int mResource;

    @Override
    public int getCount() {
        return mBeers.size();
    }

    public BreweryBeersAdapter(Context context, int resource, List<Beer> beers) {
        super(context, resource, beers);

//        mContext = context;
        mBeers = beers;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(mResource, parent, false);
        }

        final Beer beer = mBeers.get(position);

        TextView beerName = (TextView) convertView.findViewById(R.id.tv_item_beer_name);

        beerName.setText(beer.getName());

        return convertView;
    }
}
