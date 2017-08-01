package com.danielg.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.danielg.weatherapp.data.WeatherData;
import com.danielg.weatherapp.data.WeatherService;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.gson.Gson;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherCurrentFragment extends Fragment {

    private static final String TAG = WeatherCurrentFragment.class.getSimpleName();

    private static final int REQUEST_PERMISSION_LOCATION = 10;
    private static final int REQUEST_CHECK_SETTINGS = 11;
    private static final int UPDATE_INTERVAL_TIME = 10000;
    private static final int UPDATE_INTERVAL_FAST_TIME = UPDATE_INTERVAL_TIME / 2;

    private static final String KEY_LOCATION = "location";
    private static final String KEY_WEATHER_DATA = "weather-data";

    private Context mContext;

    private boolean mAutoUpdateLocation;
    private String mUnits, mAPIKey;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;
    private WeatherData mWeatherData;

    private ImageView imageWeatherIcon;
    private TextView textTemperature, textSummary, textHumidity, textFeelsLike;
    private ProgressBar progressBar;

    private Animation animFadeOut;

    public WeatherCurrentFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        loadPreferences(mContext);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        mSettingsClient = LocationServices.getSettingsClient(mContext);

        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
        setWeatherValues();
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

        animFadeOut = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
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

        // updateValuesFromBundle(savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAutoUpdateLocation && checkPermissions())
            startLocationUpdates();
        else if (!checkPermissions()) {
            requestLocationPermission();
        }
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        mWeatherData = gson.fromJson(pref.getString(KEY_WEATHER_DATA, ""), WeatherData.class);
        setWeatherValues();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseLocationUpdates();
        saveWeatherData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION: {
                if (grantResults.length <= 0) {
                    Log.i(TAG, "User cancelled permissions");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been granted, will now search for location");
                    startLocationUpdates();
                } else {
                    Log.i(TAG, "Permission has been denied, user will have to enter location manually");
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

    // TODO Work something out here
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }
            if (savedInstanceState.keySet().contains(KEY_WEATHER_DATA)) {
                mWeatherData = (WeatherData) savedInstanceState.getSerializable(KEY_WEATHER_DATA);
            }
            setWeatherValues();
        }
    }

    // TODO And here...
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // outState.putParcelable(KEY_LOCATION, mCurrentLocation);
        // super.onSaveInstanceState(outState);
    }

    private void saveWeatherData() {
        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String location = gson.toJson(mWeatherData);
        editor.putString(KEY_LOCATION, location);
        editor.commit();
    }

    private void loadPreferences(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        mAutoUpdateLocation = pref.getBoolean(AppSettingsFragment.KEY_GET_AUTO_LOCATION, true);
        if (!mAutoUpdateLocation) {
            String json = pref.getString(AppSettingsFragment.KEY_CURRENT_MANUAL_LOCATION, "");
            mCurrentLocation = new Gson().fromJson(json, Location.class);
        }
        String unit = pref.getString(AppSettingsFragment.KEY_UNITS, "");
        switch (unit) {
            case "Metric (Default)":
                mUnits = "si";
                break;
            case "Imperial":
                mUnits = "us";
                break;
            default:
                Log.d(TAG, "Could not get unit preference for some reason, defaulting to Metric (SI)");
                mUnits = "si";
                break;
        }
        mAPIKey = pref.getString(AppSettingsFragment.KEY_API_KEY, "");
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_TIME);
        mLocationRequest.setFastestInterval(UPDATE_INTERVAL_FAST_TIME);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                refreshWeather();
            }
        };
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void startLocationUpdates() {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(getActivity(), locationSettingsResponse -> {
                    Log.i(TAG, "All location settings are satisfied");
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                })
                .addOnFailureListener(getActivity(), e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.i(TAG, "Location settings not satisfied, attempting to upgrade settings.");
                            try {
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                Log.i(TAG, "PendingIntent unable to execute result.");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate and cannot be fixed here, change in settings.";
                            Log.e(TAG, errorMessage);
                            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                            mAutoUpdateLocation = false;
                    }
                });
    }

    private void pauseLocationUpdates() {
        if (!mAutoUpdateLocation)
            return;

        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Display rationale explaining why app needs permission
            Log.d(TAG, "Displaying permission rationale to user");
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                    "Location is needed for core functionality", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", view -> {
                        // Request the permission
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_PERMISSION_LOCATION);
                    }).show();
        } else {
            // Display snack bar detailing to user why location permission is needed
            // Otherwise, they can enter the location manually
            Log.d(TAG, "Prompting user permission for location");
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                    "Allow permission to get location.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                // Request the permission
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_LOCATION);
            }).show();
        }
    }

    private void refreshWeather() {
        if (mCurrentLocation != null && mAPIKey != null) {
            if (mAPIKey.length() > 0) {
                Log.d(TAG, "Weather updating");

                showProgress();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.darksky.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                WeatherService service = retrofit.create(WeatherService.class);

                Call<WeatherData> call =
                        service.getWeatherData(mAPIKey,
                                mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), mUnits, new String[]{"minutely", "hourly", "daily", "alerts", "flags"});


                call.enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherData> call, @NonNull Response<WeatherData> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            mWeatherData = response.body();
                            setWeatherValues();
                        } else {
                            Log.e(TAG, String.format(Locale.ENGLISH, "Unable to retrieve data from web: error code %s", response.code()));
                            Toast.makeText(mContext, String.format(Locale.ENGLISH, "Error retrieving data: error code %s", response.code()), Toast.LENGTH_LONG).show();
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
            } else {
                Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                        "No API key specified",
                        Snackbar.LENGTH_SHORT)
                        .setAction("Settings", view -> {

                        })
                        .show();
            }
        }
    }

    private void setWeatherValues() {
        if (mWeatherData != null) {
            String iconRes = mWeatherData.getCurrently().getIcon();
            iconRes = iconRes.replace('-', '_');
            int imageId = getResources().getIdentifier(iconRes, "drawable", getActivity().getPackageName());
            imageWeatherIcon.setImageResource(imageId);
            String tempMeasurement = "";
            if (Objects.equals(mUnits, "si")) {
                tempMeasurement = "°C";
            } else if (Objects.equals(mUnits, "us")) {
                tempMeasurement = "°F";
            }
            textTemperature.setText(String.format(Locale.getDefault(), "%1.2f%s", mWeatherData.getCurrently().getTemperature(), tempMeasurement));
            textFeelsLike.setText(String.format(Locale.getDefault(), "Feels like: %1.2f%s", mWeatherData.getCurrently().getApparentTemperature(), tempMeasurement));
            textSummary.setText(mWeatherData.getCurrently().getSummary());
            textHumidity.setText(String.format(Locale.getDefault(), "Humidity: %d%%", Utilities.doubleToPercent(mWeatherData.getCurrently().getHumidity())));
        }
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.startAnimation(animFadeOut);
    }
}
