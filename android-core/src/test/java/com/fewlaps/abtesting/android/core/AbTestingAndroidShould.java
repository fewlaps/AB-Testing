package com.fewlaps.abtesting.android.core;

import com.fewlaps.abtesting.android.BuildConfig;
import com.fewlaps.abtesting.core.Experiment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class AbTestingAndroidShould {

    private static final String EXPERIMENT_NAME = "experiment";

    @Test
    public void alwaysReturnSameExperimentOption() {
        List<String> options = new ArrayList<>();
        options.add("option1");
        options.add("option2");
        Experiment experiment = new Experiment(EXPERIMENT_NAME, options);

        List<Experiment> experiments = Collections.singletonList(experiment);

        AbTestingAndroid.init(RuntimeEnvironment.application, experiments);

        String firstOption = AbTestingAndroid.getCurrentOptionFor(EXPERIMENT_NAME);
        System.out.println("Old option: " + firstOption);
        for (int i = 0; i < 10; i++) {
            String newOption = AbTestingAndroid.getCurrentOptionFor("experiment");
            System.out.println("New option: " + newOption);
            assertEquals(firstOption, newOption);
        }
    }
}