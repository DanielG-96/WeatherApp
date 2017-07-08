
package com.danielg.weatherapp.data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WeatherData {

    @SerializedName("currently")
    private Currently mCurrently;
    @SerializedName("daily")
    private Daily mDaily;
    @SerializedName("flags")
    private Flags mFlags;
    @SerializedName("hourly")
    private Hourly mHourly;
    @SerializedName("latitude")
    private Double mLatitude;
    @SerializedName("longitude")
    private Double mLongitude;
    @SerializedName("offset")
    private Long mOffset;
    @SerializedName("timezone")
    private String mTimezone;

    public Currently getCurrently() {
        return mCurrently;
    }

    public void setCurrently(Currently currently) {
        mCurrently = currently;
    }

    public Daily getDaily() {
        return mDaily;
    }

    public void setDaily(Daily daily) {
        mDaily = daily;
    }

    public Flags getFlags() {
        return mFlags;
    }

    public void setFlags(Flags flags) {
        mFlags = flags;
    }

    public Hourly getHourly() {
        return mHourly;
    }

    public void setHourly(Hourly hourly) {
        mHourly = hourly;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        mLatitude = latitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        mLongitude = longitude;
    }

    public Long getOffset() {
        return mOffset;
    }

    public void setOffset(Long offset) {
        mOffset = offset;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

}
