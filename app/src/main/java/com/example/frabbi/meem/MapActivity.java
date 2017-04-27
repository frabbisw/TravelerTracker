package com.example.frabbi.meem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class MapActivity extends BottomBarActivity implements OnMapReadyCallback{

    Account account=null;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    boolean shouldRefreshMap=true;
    static Activity activity;
    final ArrayList<Account>friends=new ArrayList<Account>();
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        getLayoutInflater().inflate(R.layout.activity_map, frameContent);
        map.setClickable(false);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        account=ISystem.loadAccountFromCache(this);
        ISystem.loadFriends(account, friends, getApplicationContext());
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.viewMap);
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap)
    {
        final  Account myAccount = account;
        Log.e("map","ready");
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Account friend = (Account) marker.getTag();
                Log.e(friend.id,"clicked");
                Intent intent = new Intent(MapActivity.this,ProfileMapActivity.class);
                intent.putExtra(Constants.ConstantFriend,friend);
                startActivity(intent);
                return true;
            }
        });

        new Thread()
        {
            public void run()
            {
                super.run();

                int l=0;
                for(int i=0; i<20; i++)
                {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(friends.size()!=l)   break;
                    l=friends.size();
                    Log.e("loading",i+"");
                }
                //friends.add(0,myAccount);

                final ArrayList<Marker>markers=new ArrayList<Marker>();

                if(friends.size()>0)
                {
                    for (final Account account : friends) {
                        final Bitmap bmp = customBmp(account.getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(new LatLng(account.getLatitude(), account.getLongitude()));

                                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bmp));
                                markerOptions.anchor(.5f, 1f);

                                Marker marker = mGoogleMap.addMarker(markerOptions);
                                marker.setTag(account);
                                markers.add(marker);
                            }
                        });
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(0).getPosition()));
                            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                        }
                    });


                    while (shouldRefreshMap) {
                        ISystem.loadFriends(myAccount, friends, getApplicationContext());
                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < friends.size(); i++) {
                                    markers.get(i).setPosition(new LatLng(friends.get(i).getLatitude(), friends.get(i).getLongitude()));
                                }
                            }
                        });
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }.start();
    }

    private Bitmap customBmp(String userName)
    {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(100,120,config);
        Canvas canvas = new Canvas(bmp);

        Paint color = new Paint();
        color.setColor(getResources().getColor(R.color.colorPrimary));

        Paint color2 = new Paint();
        color2.setColor(getResources().getColor(R.color.colorAccent));
        color2.setTextSize(20);

        canvas.drawRect(0,0,100,100,color);
        for(int i=0; i<=100; i++)
            canvas.drawLine(i,100,50,120,color);

        canvas.drawText(userName,0,40,color2);
        ISystem.getImage(canvas,"",getApplicationContext());

        return bmp;
    }

    @Override
    protected void onStop()
    {
        shouldRefreshMap=false;
        super.onStop();
    }
    public static Activity getInstance()
    {
        return activity;
    }
}