package com.example.frabbi.meem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class ProfileMapActivity extends BottomBarActivity implements OnMapReadyCallback
{
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    boolean shouldRefreshMap=true;
    Account account;
    ArrayList<CheckedInPosition>checkedInPositions=new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        account = (Account) getIntent().getSerializableExtra(Constants.ConstantFriend);
        ISystem.loadCheckedInPositions(account, checkedInPositions, getApplicationContext());

        getLayoutInflater().inflate(R.layout.activity_profile_map, frameContent);
        map.setClickable(false);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.profileMap);
        mapFrag.getMapAsync(this);
    }

    @Override
    protected void showSettings(){
        startActivity(new Intent(ProfileMapActivity.this,SettingsActivity.class));
        MapActivity.getInstance().finish();
        finish();
    }

    @Override
    protected void showCircle(){
        startActivity(new Intent(ProfileMapActivity.this,CircleActivity.class));
        MapActivity.getInstance().finish();
        finish();
    }

    @Override
    protected void showCheckIn(){
        startActivity(new Intent(ProfileMapActivity.this,CheckInActivity.class));
        MapActivity.getInstance().finish();
        finish();
    }

    @Override
    protected void showMap(){
        startActivity(new Intent(ProfileMapActivity.this,MapActivity.class));
        MapActivity.getInstance().finish();
        finish();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        shouldRefreshMap=false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        new Thread()
        {
            @Override
            public void run() {
                super.run();

                while (shouldRefreshMap)
                {
                int l = 0;
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (checkedInPositions.size() != l) break;
                    l = checkedInPositions.size();
                    Log.e("loading", i + "");
                }

                for (final CheckedInPosition cip : checkedInPositions) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(cip.getLatitude(), cip.getLongitude()));

                            String str [] = cip.date.split(" ");

                            markerOptions.title(str[0]);
                            markerOptions.snippet(str[1]);

                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(customBitmap(cip)));
                            markerOptions.anchor(.5f, 1f);

                            mGoogleMap.addMarker(markerOptions);
                        }
                    });
                }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(checkedInPositions.get(checkedInPositions.size()-1).getPosition()));
                            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                        }
                    });

                try {
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }
        }.start();
    }
    public Bitmap customBitmap(CheckedInPosition cip)
    {
        Bitmap icon;
        if(cip.type.equals("home"))    icon= BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_home);
        else if(cip.type.equals("work"))   icon= BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_work);
        else    icon= BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_temp);
        icon = Bitmap.createScaledBitmap(icon, 90, 90, false);

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(100,120,config);
        Canvas canvas = new Canvas(bmp);

        Paint color = new Paint();
        color.setColor(getResources().getColor(R.color.colorPrimary));

        Paint color2 = new Paint();
        color2.setColor(getResources().getColor(R.color.colorAccent));

        canvas.drawRect(0,0,100,100,color);
        for(int i=0; i<=100; i++)
            canvas.drawLine(i,100,50,120,color);

        canvas.drawBitmap(icon, 5, 5, color2);

        return bmp;
    }
}
