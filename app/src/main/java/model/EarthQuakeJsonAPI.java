package model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by krrish on 2/10/2016.
 */
public class EarthQuakeJsonAPI {
    @SerializedName("features")
    ArrayList<features> earthQuakeList= new ArrayList<>();

    public ArrayList<features> getEarthQuakeList() {
        return earthQuakeList;
    }

    public void setEarthQuakeList(ArrayList<features> earthQuakeList) {
        this.earthQuakeList = earthQuakeList;
    }
}
