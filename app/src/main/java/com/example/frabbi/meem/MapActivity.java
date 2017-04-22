package com.example.frabbi.meem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MapActivity extends BottomBarActivity implements OnMapReadyCallback {

    Account account=null;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    boolean shouldRefreshMap=true;
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
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.viewMap);
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap)
    {
        Log.e("map","ready");
        mGoogleMap=googleMap;
        final Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(90, 23)).title("My position"));
        final Random random = new Random();

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        new Thread()
        {
            public void run()
            {
                while (shouldRefreshMap)
                {
                    final double x = 23+random.nextDouble();
                    final double y = 90+random.nextDouble();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LatLng lt = new LatLng(x,y);
                            marker.setPosition(lt);
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(lt));
                            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(6));
                        }
                    });

                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onStop()
    {
        shouldRefreshMap=false;
        super.onStop();
    }
}