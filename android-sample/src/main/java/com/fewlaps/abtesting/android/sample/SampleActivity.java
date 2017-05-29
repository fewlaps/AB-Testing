package com.fewlaps.abtesting.android.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fewlaps.abtesting.R;
import com.fewlaps.abtesting.android.core.AbTestingAndroid;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView optionTextView = (TextView) findViewById(R.id.option);
        String randomOption = AbTestingAndroid.getCurrentOptionFor("Sample experiment");
        optionTextView.setText(randomOption);
    }
}
