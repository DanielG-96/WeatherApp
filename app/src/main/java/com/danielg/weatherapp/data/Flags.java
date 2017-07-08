
package com.danielg.weatherapp.data;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Flags {

    @SerializedName("isd-stations")
    private List<String> mIsdStations;
    @SerializedName("sources")
    private List<String> mSources;
    @SerializedName("units")
    private String mUnits;

    public List<String> getIsdStations() {
        return mIsdStations;
    }

    public void setIsdStations(List<String> isdStations) {
        mIsdStations = isdStations;
    }

    public List<String> getSources() {
        return mSources;
    }

    public void setSources(List<String> sources) {
        mSources = sources;
    }

    public String getUnits() {
        return mUnits;
    }

    public void setUnits(String units) {
        mUnits = units;
    }

}
