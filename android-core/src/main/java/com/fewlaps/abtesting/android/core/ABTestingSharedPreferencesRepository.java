package com.fewlaps.abtesting.android.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.fewlaps.abtesting.core.ABTestingRepository;

import static android.content.Context.MODE_PRIVATE;

public class ABTestingSharedPreferencesRepository implements ABTestingRepository {

    private static final String PREF_FILE_NAME = "AbTestingByFewlaps";

    private final SharedPreferences sharedPreferences;

    public ABTestingSharedPreferencesRepository(Context appContext) {
        sharedPreferences = appContext.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
    }

    @Override
    public String getOption(String experiment) {
        return sharedPreferences.getString(experiment, null);
    }

    @Override
    public void saveOption(String experiment, String option) {
        sharedPreferences.edit().putString(experiment, option).apply();
    }
}
