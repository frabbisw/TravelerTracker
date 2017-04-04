package com.example.frabbi.meem;

import android.os.Bundle;
import android.widget.Toast;

public class SettingsActivity extends BottomBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameContent);
        Toast.makeText(this, "This is settings here", Toast.LENGTH_SHORT).show();
    }
}

