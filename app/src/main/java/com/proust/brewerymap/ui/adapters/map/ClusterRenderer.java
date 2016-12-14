package com.proust.brewerymap.ui.adapters.map;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.proust.brewerymap.R;

/**
 * Created by proust on 14/12/2016.
 */

public class ClusterRenderer extends DefaultClusterRenderer<BrewClusterItem> {

    private Context mContext;

    private IconGenerator mClusterIconGenerator;
    private TextView mTextViewCluster;

    public ClusterRenderer(Context context, GoogleMap map, ClusterManager<BrewClusterItem> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;

        IconGenerator singleIconGenerator = new IconGenerator(context);
        mClusterIconGenerator = new IconGenerator(context);

//        mTextViewCluster = (TextView) View.inflate(context, R.layout.item_cluster, null);
//
//        singleIconGenerator.setContentView(new ImageView(context));
//        mClusterIconGenerator.setContentView(mTextViewCluster);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//            mClusterIconGenerator.setBackground(context.getDrawable(R.drawable.transparent));
//        else
//            mClusterIconGenerator.setBackground(context.getResources().getDrawable(R.drawable.transparent));
    }

    @Override
    protected void onBeforeClusterItemRendered(BrewClusterItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);

        //TODO Create / Import map marker
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
        markerOptions.title(item.mBrew.getName());
    }

}
