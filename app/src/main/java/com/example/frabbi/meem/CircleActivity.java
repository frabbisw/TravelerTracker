package com.example.frabbi.meem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class CircleActivity extends BottomBarActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_circle, frameContent);
        circle.setClickable(false);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

