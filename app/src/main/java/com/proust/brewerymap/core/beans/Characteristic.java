package com.proust.brewerymap.core.beans;

/**
 * Class that represent characteristics and notes of beer
 * TODO Verify that a class is really needed and has real avantages over simple String (like more
 * TODO attributes)
 *
 * Created by proust on 30/11/2016.
 */

public class Characteristic {

    /* This field cannot be empty as it's the only one */
    public String mName;

    public Characteristic(String name) {
        mName = name;
    }

}
