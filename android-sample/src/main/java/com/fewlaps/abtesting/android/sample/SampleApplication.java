package com.fewlaps.abtesting.android.sample;

import android.app.Application;

import com.fewlaps.abtesting.android.core.AbTestingAndroid;
import com.fewlaps.abtesting.core.Experiment;

import java.util.ArrayList;
import java.util.List;

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<String> options = new ArrayList<>();
        options.add("Option A");
        options.add("Option B");
        Experiment sampleExperiment = new Experiment("Sample experiment", options);

        List<Experiment> experiments = new ArrayList<>();
        experiments.add(sampleExperiment);

        AbTestingAndroid.init(this, experiments);
    }
}
