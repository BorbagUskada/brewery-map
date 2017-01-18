package com.proust.brewerymap.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.proust.brewerymap.R;

/**
 * Created by proust on 18/01/2017.
 */

public class BreweryDetailsActivity extends AppCompatActivity {

    private TextView mTextViewBrewName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextViewBrewName = (TextView)findViewById(R.id.tv_brew_name);


    }


}
