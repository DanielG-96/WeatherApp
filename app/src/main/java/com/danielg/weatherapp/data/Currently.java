
package com.danielg.weatherapp.data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Currently {

    @SerializedName("apparentTemperature")
    private Double mApparentTemperature;
    @SerializedName("cloudCover")
    private Double mCloudCover;
    @SerializedName("dewPoint")
    private Double mDewPoint;
    @SerializedName("humidity")
    private Double mHumidity;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("ozone")
    private Double mOzone;
    @SerializedName("precipIntensity")
    private Double mPrecipIntensity;
    @SerializedName("precipProbability")
    private Double mPrecipProbability;
    @SerializedName("precipType")
    private String mPrecipType;
    @SerializedName("pressure")
    private Double mPressure;
    @SerializedName("summary")
    private String mSummary;
    @SerializedName("temperature")
    private Double mTemperature;
    @SerializedName("time")
    private Long mTime;
    @SerializedName("uvIndex")
    private Long mUvIndex;
    @SerializedName("windBearing")
    private Long mWindBearing;
    @SerializedName("windGust")
    private Double mWindGust;
    @SerializedName("windSpeed")
    private Double mWindSpeed;

    public Double getApparentTemperature() {
        return mApparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        mApparentTemperature = apparentTemperature;
    }

    public Double getCloudCover() {
        return mCloudCover;
    }

    public void setCloudCover(Double cloudCover) {
        mCloudCover = cloudCover;
    }

    public Double getDewPoint() {
        return mDewPoint;
    }

    public void setDewPoint(Double dewPoint) {
        mDewPoint = dewPoint;
    }

    public Double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Double humidity) {
        mHumidity = humidity;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public Double getOzone() {
        return mOzone;
    }

    public void setOzone(Double ozone) {
        mOzone = ozone;
    }

    public Double getPrecipIntensity() {
        return mPrecipIntensity;
    }

    public void setPrecipIntensity(Double precipIntensity) {
        mPrecipIntensity = precipIntensity;
    }

    public Double getPrecipProbability() {
        return mPrecipProbability;
    }

    public void setPrecipProbability(Double precipProbability) {
        mPrecipProbability = precipProbability;
    }

    public String getPrecipType() {
        return mPrecipType;
    }

    public void setPrecipType(String precipType) {
        mPrecipType = precipType;
    }

    public Double getPressure() {
        return mPressure;
    }

    public void setPressure(Double pressure) {
        mPressure = pressure;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public Double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Double temperature) {
        mTemperature = temperature;
    }

    public Long getTime() {
        return mTime;
    }

    public void setTime(Long time) {
        mTime = time;
    }

    public Long getUvIndex() {
        return mUvIndex;
    }

    public void setUvIndex(Long uvIndex) {
        mUvIndex = uvIndex;
    }

    public Long getWindBearing() {
        return mWindBearing;
    }

    public void setWindBearing(Long windBearing) {
        mWindBearing = windBearing;
    }

    public Double getWindGust() {
        return mWindGust;
    }

    public void setWindGust(Double windGust) {
        mWindGust = windGust;
    }

    public Double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        mWindSpeed = windSpeed;
    }

}
