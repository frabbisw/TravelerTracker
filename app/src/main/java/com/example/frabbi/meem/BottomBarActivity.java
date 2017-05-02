package com.example.frabbi.meem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BottomBarActivity extends AppCompatActivity {

    protected FrameLayout frameContent;
    protected ImageView circle;
    protected ImageView checkIn;
    protected ImageView map;
    protected ImageView settings;
    protected TextView notificationIcon;
    protected Account self;
    protected int totalCount;
    protected ArrayList<User> requests=new ArrayList<>();
    protected ArrayList<Notification> notifications=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        self = ISystem.loadAccountFromCache(this);

        frameContent = (FrameLayout) findViewById(R.id.content_frame);

        settings = (ImageView) findViewById(R.id.img_settings);
        settings.setOnClickListener(clickListener);

        circle = (ImageView) findViewById(R.id.img_circle);
        circle.setOnClickListener(clickListener);

        checkIn = (ImageView) findViewById(R.id.img_checkIn);
        checkIn.setOnClickListener(clickListener);

        map = (ImageView) findViewById(R.id.img_map);
        map.setOnClickListener(clickListener);

        notificationIcon= (TextView) findViewById(R.id.icon_notification);

    }
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        updateNotificationIcon();
    }
    protected void updateNotificationIcon() {
        /*notifications = getNotified();
        requests = getRequest();
        totalCount = requests.size()+notifications.size();

        if(totalCount>0) {
            notificationIcon.setVisibility(View.VISIBLE);
            notificationIcon.setText( "" + totalCount);
        }
        else notificationIcon.setVisibility(View.GONE);
        */
    }

    protected View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.img_settings:
                    showSettings();
                    break;

                case R.id.img_circle:
                    showCircle();
                    //notificationIcon.setVisibility(View.GONE);
                    break;

                case R.id.img_checkIn:
                    showCheckIn();
                    break;

                case R.id.img_map:
                    showMap();
                    break;

            }
        }
    };

    protected void showSettings(){
        startActivity(new Intent(BottomBarActivity.this,SettingsActivity.class));
        finish();
    }

    protected void showCircle(){
        startActivity(new Intent(BottomBarActivity.this,CircleActivity.class));
        finish();
    }

    protected void showCheckIn(){
        startActivity(new Intent(BottomBarActivity.this,CheckInActivity.class));
        finish();
    }

    protected void showMap(){
        startActivity(new Intent(BottomBarActivity.this,MapActivity.class));
        finish();
    }

    /*
    protected ArrayList<User> getRequest() {

        ArrayList<Account> account = new ArrayList<>();
        ArrayList<User> result = new ArrayList<>();

        ISystem.loadRequestedUsers(getApplicationContext(),self.getId(),account);

        for (Account a : account) {
            result.add(User.getUserTest(a, Constants.REQUEST));
        }

        return result;

    }
    */

    /*
    protected ArrayList<User> getFriends() {

        ArrayList<Account> account = new ArrayList<>();
        ArrayList<User> result = new ArrayList<>();

        ISystem.loadFriends(self, account, getApplicationContext());

        for (Account a : account) {
            result.add(User.getUserTest(a, Constants.FRIEND));
        }

        return result;

    }
    */

    /*
    protected ArrayList<User> getSearchResult(String searchKey) {

        /
        ArrayList<Account> account = new ArrayList<>();
        ArrayList<User> result = new ArrayList<>();

        ISystem.searchAccount(getApplicationContext(), searchKey, account);

        for (Account a : account) {
            result.add(User.getUserTest(a, Constants.SEARCH));
        }

        return result;
    }
    */

    /*
    protected ArrayList<Notification> getNotified(){

        ArrayList<Notification> notificationList = new ArrayList<>();
        ISystem.getNotifications(getApplicationContext(),self.getId(),notificationList);
        return notificationList;
    }
    */

}
