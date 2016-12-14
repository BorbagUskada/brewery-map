package com.proust.brewerymap.core.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.proust.brewerymap.core.beans.Brewery;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom SharedPreferences to store and load datas, allowing API simulation
 * This class is only used to simulate API and is no longer needed once an API is connected to the
 * app
 *
 * Created by proust on 30/11/2016.
 */

public class CustomPreferences {

    private static final String SHARED_PREFERENCES_KEY = "KEY_BREWEREY_PREFERENCES";

    private static final String PREFERENCES_KEY_BREWERIES = "KEY_BREWERIES";
    private static final String PREFERENCES_KEY_BEERS = "KEY_BEERS";

    private SharedPreferences mPreferences;

    private static final Object lock = new Object();

    private static CustomPreferences mInstance;

    private Gson mGson;

    private Context mContext;

    /**
     *
     * @param context Must be always this.getApplicationContext instead of 'this'
     * @return The instance of this class
     */
    public static CustomPreferences getInstance(Context context) {
        synchronized (lock) {
            if(null == mInstance) {
                mInstance = new CustomPreferences(context);
            }
            mInstance.init(context);
            return mInstance;
        }
    }

    /**
     * Private constructor to prevent instanciation
     */
    private CustomPreferences(Context context) {
        mPreferences = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void init(Context context) {
        mContext = context;
        mGson = new GsonBuilder().create();
    }

    /**
     * Retrieve Brewery List from SharedPreferences
     * @return ArrayList<Brewery> List of stored breweries
     */
    public List<Brewery> getAllBreweries() {
        List<Brewery> brewList = new ArrayList<>();

        if(mPreferences.contains(PREFERENCES_KEY_BREWERIES)) {
            String jsonBrews = mPreferences.getString(PREFERENCES_KEY_BREWERIES, null);

            Type type = new TypeToken<List<Brewery>>(){}.getType();
            brewList = mGson.fromJson(jsonBrews, type);
        }
        Log.i("BiigLog", "Brews number : " + brewList.size());
        return brewList;
    }


    /**
     * Save an ArrayList of brewery object in SharedPreferences
     * ! Only used once to simulate API content !
     * @param brewsToSave ArrayList<Brewery> brews to save
     */
    public void storeBreweries(List<Brewery> brewsToSave) {
        String brewsJson = mGson.toJson(brewsToSave);
        mPreferences.edit()
                .putString(PREFERENCES_KEY_BREWERIES, brewsJson)
                .apply();
    }

}
