package com.danielg.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

public class AppSettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = AppSettingsFragment.class.getSimpleName();
    public static final String KEY_GET_AUTO_LOCATION = "pref_get_auto_location";
    public static final String KEY_CURRENT_MANUAL_LOCATION = "pref_curr_manual_location";
    public static final String KEY_UNITS = "pref_units";
    public static final String KEY_API_KEY = "pref_api_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        Preference unitPref = findPreference(KEY_UNITS);
        unitPref.setSummary(sp.getString(KEY_UNITS, ""));
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_UNITS)) {
            Preference unitPref = findPreference(key);
            unitPref.setSummary(sharedPreferences.getString(key, ""));
        }
    }
}
