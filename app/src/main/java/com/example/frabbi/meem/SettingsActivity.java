package com.example.frabbi.meem;

import android.os.Bundle;

public class SettingsActivity extends BottomBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameContent);

    }
}

