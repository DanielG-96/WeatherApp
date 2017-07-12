package com.danielg.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.danielg.weatherapp.data.WeatherData;
import com.danielg.weatherapp.data.WeatherService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherCurrentFragment extends Fragment {

    private static final String TAG = "WeatherCurrentFragment";
    private static final int PERMISSION_REQUEST_LOCATION = 0;
    private static double latitude, longitude;
    private FusedLocationProviderClient mFusedLocationClient;
    private Context mActivity;

    private ImageView imageWeatherIcon;
    private TextView textTemperature, textSummary, textHumidity, textFeelsLike;
    private ProgressBar progressBar;

    private Animation animFadeOut;

    public WeatherCurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getContext();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        requestCoarseLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_weather_current, container, false);
        setHasOptionsMenu(true);

        imageWeatherIcon = view.findViewById(R.id.image_weather_icon);
        textTemperature = view.findViewById(R.id.text_weather_temperature);
        textSummary = view.findViewById(R.id.text_weather_summary);
        textHumidity = view.findViewById(R.id.text_weather_humidity);
        textFeelsLike = view.findViewById(R.id.text_weather_feels_like);
        progressBar = view.findViewById(R.id.progress);

        animFadeOut = AnimationUtils.loadAnimation(mActivity, R.anim.fade_out);
        animFadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission has been granted, will now search for location");
                    requestCoarseLocation();
                } else {
                    Log.d(TAG, "Permission has been denied, user will have to enter location manually");
                    Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                            "Location permission denied.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refreshWeather();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestCoarseLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission has not yet been granted, requesting now");
            requestLocationPermission();
        } else {
            mFusedLocationClient.getLastLocation().addOnFailureListener(getActivity(), e -> {
                Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                        "Could not find current location",
                        Snackbar.LENGTH_SHORT).show();
                Log.e(TAG, e.getMessage());
            });
            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                refreshWeather();
            });
        }

    }

    private void requestLocationPermission() {
        if(ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Display snack bar detailing to user why location permission is needed
            // Otherwise, they can enter the location manually
            Log.d(TAG, "Prompting user permission for location");
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                    "Allow permission to get location.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                // Request the permission
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSION_REQUEST_LOCATION);
            }).show();
        } else {
            // For older versions of Android (5.1 and lower)?
            Log.d(TAG, "Prompting user permission for location");
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                    "Permissions prompt not available. Requesting location permission.",
                    Snackbar.LENGTH_SHORT).show();
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);
        }
    }

    private void refreshWeather() {
        showProgress();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        Call<WeatherData> call =
                service.getWeatherData("9445c94a02f70601037ae1f50cc4f12b",
                latitude, longitude, "auto", new String[]{ "minutely","hourly","daily","alerts","flags" });


        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(@NonNull Call<WeatherData> call, @NonNull Response<WeatherData> response) {
                hideProgress();
                if(response.isSuccessful()) {
                    WeatherData data = response.body();
                    if(data != null) {
                        String iconRes = data.getCurrently().getIcon();
                        iconRes = iconRes.replace('-', '_');
                        int imageId = getResources().getIdentifier(iconRes, "drawable", getActivity().getPackageName());
                        imageWeatherIcon.setImageResource(imageId);
                        textTemperature.setText(String.format(Locale.getDefault(), "%1.2f", data.getCurrently().getTemperature()));
                        textFeelsLike.setText(String.format(Locale.getDefault(), "Feels like: %1.2f", data.getCurrently().getApparentTemperature()));
                        textSummary.setText(data.getCurrently().getSummary());
                        textHumidity.setText(String.format(Locale.getDefault(), "Humidity: %d%%", Utilities.doubleToPercent(data.getCurrently().getHumidity())));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherData> call, @NonNull Throwable t) {
                hideProgress();
                Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                        "Could not get weather data",
                        Snackbar.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.startAnimation(animFadeOut);
    }
}
