package com.example.frabbi.meem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapActivity extends BottomBarActivity implements OnMapReadyCallback {

    Account account=null;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_map, frameContent);
        map.setClickable(false);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        account=ISystem.loadAccountFromCache(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

    }
}