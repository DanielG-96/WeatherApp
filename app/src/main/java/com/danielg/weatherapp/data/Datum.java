
package com.danielg.weatherapp.data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Datum {

    @SerializedName("apparentTemperature")
    private Double mApparentTemperature;
    @SerializedName("apparentTemperatureMax")
    private Double mApparentTemperatureMax;
    @SerializedName("apparentTemperatureMaxTime")
    private Long mApparentTemperatureMaxTime;
    @SerializedName("apparentTemperatureMin")
    private Double mApparentTemperatureMin;
    @SerializedName("apparentTemperatureMinTime")
    private Long mApparentTemperatureMinTime;
    @SerializedName("cloudCover")
    private Double mCloudCover;
    @SerializedName("dewPoint")
    private Double mDewPoint;
    @SerializedName("humidity")
    private Double mHumidity;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("moonPhase")
    private Double mMoonPhase;
    @SerializedName("ozone")
    private Double mOzone;
    @SerializedName("precipIntensity")
    private Double mPrecipIntensity;
    @SerializedName("precipIntensityMax")
    private Double mPrecipIntensityMax;
    @SerializedName("precipIntensityMaxTime")
    private Long mPrecipIntensityMaxTime;
    @SerializedName("precipProbability")
    private Double mPrecipProbability;
    @SerializedName("precipType")
    private String mPrecipType;
    @SerializedName("pressure")
    private Double mPressure;
    @SerializedName("summary")
    private String mSummary;
    @SerializedName("sunriseTime")
    private Long mSunriseTime;
    @SerializedName("sunsetTime")
    private Long mSunsetTime;
    @SerializedName("temperature")
    private Double mTemperature;
    @SerializedName("temperatureMax")
    private Double mTemperatureMax;
    @SerializedName("temperatureMaxTime")
    private Long mTemperatureMaxTime;
    @SerializedName("temperatureMin")
    private Double mTemperatureMin;
    @SerializedName("temperatureMinTime")
    private Long mTemperatureMinTime;
    @SerializedName("time")
    private Long mTime;
    @SerializedName("uvIndex")
    private Long mUvIndex;
    @SerializedName("uvIndexTime")
    private Long mUvIndexTime;
    @SerializedName("windBearing")
    private Long mWindBearing;
    @SerializedName("windGust")
    private Double mWindGust;
    @SerializedName("windGustTime")
    private Long mWindGustTime;
    @SerializedName("windSpeed")
    private Double mWindSpeed;

    public Double getApparentTemperature() {
        return mApparentTemperature;
    }

    public void setApparentTemperature(Double apparentTemperature) {
        mApparentTemperature = apparentTemperature;
    }

    public Double getApparentTemperatureMax() {
        return mApparentTemperatureMax;
    }

    public void setApparentTemperatureMax(Double apparentTemperatureMax) {
        mApparentTemperatureMax = apparentTemperatureMax;
    }

    public Long getApparentTemperatureMaxTime() {
        return mApparentTemperatureMaxTime;
    }

    public void setApparentTemperatureMaxTime(Long apparentTemperatureMaxTime) {
        mApparentTemperatureMaxTime = apparentTemperatureMaxTime;
    }

    public Double getApparentTemperatureMin() {
        return mApparentTemperatureMin;
    }

    public void setApparentTemperatureMin(Double apparentTemperatureMin) {
        mApparentTemperatureMin = apparentTemperatureMin;
    }

    public Long getApparentTemperatureMinTime() {
        return mApparentTemperatureMinTime;
    }

    public void setApparentTemperatureMinTime(Long apparentTemperatureMinTime) {
        mApparentTemperatureMinTime = apparentTemperatureMinTime;
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

    public Double getMoonPhase() {
        return mMoonPhase;
    }

    public void setMoonPhase(Double moonPhase) {
        mMoonPhase = moonPhase;
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

    public Double getPrecipIntensityMax() {
        return mPrecipIntensityMax;
    }

    public void setPrecipIntensityMax(Double precipIntensityMax) {
        mPrecipIntensityMax = precipIntensityMax;
    }

    public Long getPrecipIntensityMaxTime() {
        return mPrecipIntensityMaxTime;
    }

    public void setPrecipIntensityMaxTime(Long precipIntensityMaxTime) {
        mPrecipIntensityMaxTime = precipIntensityMaxTime;
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

    public Long getSunriseTime() {
        return mSunriseTime;
    }

    public void setSunriseTime(Long sunriseTime) {
        mSunriseTime = sunriseTime;
    }

    public Long getSunsetTime() {
        return mSunsetTime;
    }

    public void setSunsetTime(Long sunsetTime) {
        mSunsetTime = sunsetTime;
    }

    public Double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Double temperature) {
        mTemperature = temperature;
    }

    public Double getTemperatureMax() {
        return mTemperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public Long getTemperatureMaxTime() {
        return mTemperatureMaxTime;
    }

    public void setTemperatureMaxTime(Long temperatureMaxTime) {
        mTemperatureMaxTime = temperatureMaxTime;
    }

    public Double getTemperatureMin() {
        return mTemperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        mTemperatureMin = temperatureMin;
    }

    public Long getTemperatureMinTime() {
        return mTemperatureMinTime;
    }

    public void setTemperatureMinTime(Long temperatureMinTime) {
        mTemperatureMinTime = temperatureMinTime;
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

    public Long getUvIndexTime() {
        return mUvIndexTime;
    }

    public void setUvIndexTime(Long uvIndexTime) {
        mUvIndexTime = uvIndexTime;
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

    public Long getWindGustTime() {
        return mWindGustTime;
    }

    public void setWindGustTime(Long windGustTime) {
        mWindGustTime = windGustTime;
    }

    public Double getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        mWindSpeed = windSpeed;
    }

}
