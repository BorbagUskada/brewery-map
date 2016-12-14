package com.proust.brewerymap.core.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean that represent a single beer
 *
 * Created by proust on 30/11/2016.
 */

public class Beer {

    private String mName;

    private List<Characteristic> mCharacteristics;

    public Beer(String name) {
        mName = name;
    }

    public Beer(String name, ArrayList<Characteristic> characteristics) {
        mName = name;
        mCharacteristics = characteristics;
    }

    public String getName() {
        return mName;
    }

    public ArrayList<Characteristic> getNotes() {
        return (ArrayList<Characteristic>) mCharacteristics;
    }

}
