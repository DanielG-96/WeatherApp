package com.danielg.weatherapp.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("")
public interface WeatherService {
    @GET("forecast/{key}/{latitude},{longitude}")
    Call<WeatherData> getWeatherData(@Path("key") String key,
                                     @Path("latitude") double latitude,
                                     @Path("longitude") double longitude,
                                     @Query("units") String units,
                                     @Query("exclude") String[] blocks);
}
