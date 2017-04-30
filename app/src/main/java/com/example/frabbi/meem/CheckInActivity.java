package com.example.frabbi.meem;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CheckInActivity extends BottomBarActivity implements OnMapReadyCallback{

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    final ArrayList <CheckedInPosition> checkedInPositions = new ArrayList<CheckedInPosition>();
    boolean shouldRefreshMap=true;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_check_in, frameContent);
        checkIn.setClickable(false);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateFormat = new SimpleDateFormat(Constants.DateFormat);
        Account account = ISystem.loadAccountFromCache(getApplicationContext());
        ISystem.loadCheckedInPositions(account, checkedInPositions, getApplicationContext());

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.checkInMap);
        mapFrag.getMapAsync(this);
    }

    public void checkInAsHome(View view)
    {
        Account account = ISystem.loadAccountFromCache(getApplicationContext());
        calendar = Calendar.getInstance();
        String dateTime = dateFormat.format(calendar.getTime());
        CheckedInPosition position = new HomePosition(account.id, dateTime, account.getLatitude(), account.getLongitude());

        ISystem.checkIn(getApplicationContext(), position);
    }
    public void checkInAsWork(View view)
    {
        Account account = ISystem.loadAccountFromCache(getApplicationContext());
        calendar = Calendar.getInstance();
        String dateTime = dateFormat.format(calendar.getTime());
        CheckedInPosition position = new WorkPosition(account.id, dateTime, account.getLatitude(), account.getLongitude());

        ISystem.checkIn(getApplicationContext(), position);
    }
    public void checkInTemporary(View view)
    {
        Account account = ISystem.loadAccountFromCache(getApplicationContext());
        calendar = Calendar.getInstance();
        String dateTime = dateFormat.format(calendar.getTime());
        CheckedInPosition position = new TemporaryPosition(account.id, dateTime, account.getLatitude(), account.getLongitude());

        ISystem.checkIn(getApplicationContext(), position);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        shouldRefreshMap=false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        if(mGoogleMap==null) Log.e("","");
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Account account = ISystem.loadAccountFromCache(getApplicationContext());
        MarkerOptions op = new MarkerOptions();
        //op.position(new LatLng(account.getLatitude(),account.getLongitude()));
        op.position(new LatLng(0,0));
        final Marker presentMarker = mGoogleMap.addMarker(op);
        presentMarker.setTitle("currect position");

        new Thread()
        {

            @Override
            public void run()
            {
                super.run();

                int l = 0;
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (checkedInPositions.size() != l) break;
                    l = checkedInPositions.size();
                    Log.e("loading", i + "");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        for(CheckedInPosition cip : checkedInPositions)
                        {
                            MarkerOptions mo = new MarkerOptions();
                            mo.position(new LatLng(cip.getLatitude(),cip.getLongitude()));
                            mo.title(cip.date);

                            mGoogleMap.addMarker(mo);
                        }
                    }
                });

                while (shouldRefreshMap)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Account myAccount = ISystem.loadAccountFromCache(getApplicationContext());
                            presentMarker.setPosition(new LatLng(myAccount.getLatitude(), myAccount.getLongitude()));
                        }
                    });

                    try {
                        Thread.sleep(20 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}