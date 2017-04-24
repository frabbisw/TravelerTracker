package com.example.frabbi.meem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
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
        final  Account myAccount = ISystem.loadAccountFromCache(this);
        Log.e("map","ready");
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        new Thread()
        {
            public void run()
            {
                super.run();
                final ArrayList<Account>friends=new ArrayList<Account>();
                ISystem.loadFriends(myAccount, friends, getApplicationContext());

                int l=0;
                for(int i=10; i<20; i++)
                {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(friends.size()!=l)   break;
                    l=friends.size();
                }

                final ArrayList<Marker>markers=new ArrayList<Marker>();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(Account account:friends)
                        {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(account.getLatitude(), account.getLongitude()));
                            markerOptions.title(account.getId());
                            markerOptions.snippet(account.getName());
                            markerOptions.anchor(.5f,1);
                            Marker marker = mGoogleMap.addMarker(markerOptions);
                            markers.add(marker);
                        }
                    }
                });


                /*
                while (shouldRefreshMap)
                {


                    if(friends.size()!=0)
                        for(int i=0; i<friends.size(); i++)
                        {
                            Log.e(friends.get(i).getName(),friends.get(i).getLatitude()+"-"+friends.get(i).getLongitude());
                        }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
                */
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