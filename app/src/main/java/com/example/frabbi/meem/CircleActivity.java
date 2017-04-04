package com.example.frabbi.meem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CircleActivity extends BottomBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_circle, frameContent);
    }
}

