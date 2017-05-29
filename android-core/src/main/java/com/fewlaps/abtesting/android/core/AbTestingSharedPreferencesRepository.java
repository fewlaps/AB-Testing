package com.fewlaps.abtesting.android.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.fewlaps.abtesting.core.ABTestingRepository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class AbTestingSharedPreferencesRepository implements ABTestingRepository {

    private static final String PREF_FILE_NAME = "AbTestingByFewlaps";

    private final SharedPreferences sharedPreferences;

    public AbTestingSharedPreferencesRepository(Context appContext) {
        sharedPreferences = appContext.getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
    }

    @Nullable
    @Override
    public String getOption(@NotNull String experiment) {
        return sharedPreferences.getString(experiment, null);
    }

    @Override
    public void saveOption(@NotNull String experiment, @NotNull String option) {
        sharedPreferences.edit().putString(experiment, option).apply();
    }
}
