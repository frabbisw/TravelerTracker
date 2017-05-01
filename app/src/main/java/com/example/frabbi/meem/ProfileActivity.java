package com.example.frabbi.meem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends BottomBarActivity {

    protected Button add;
    protected Button remove;
    protected Button confirm;
    protected Button cancel;
    protected TextView username;
    protected ImageView userphoto;
    private User profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile, frameContent);

        add = (Button) findViewById(R.id.Add_btn);
        add.setOnClickListener(clickListener);

        remove = (Button) findViewById(R.id.Remove_btn);
        remove.setOnClickListener(clickListener);

        confirm = (Button) findViewById(R.id.confirm_btn);
        confirm.setOnClickListener(clickListener);

        cancel = (Button) findViewById(R.id.cancel_req_btn);
        cancel.setOnClickListener(clickListener);

        username = (TextView) findViewById(R.id.text_UserName);
        userphoto = (ImageView) findViewById(R.id.UserPhoto);

        profile = getIntent().getParcelableExtra(Constants.USERTEST);
        if(profile.getType().equals(Constants.FRIEND)) showProfileAsFriend();
        if(profile.getType().equals(Constants.SEARCH)) showProfileAsOutOfCircle();
        if(profile.getType().equals(Constants.REQUEST)) showProfileFromRequest();

        // TODO have to set user profile photo into userphoto imageview, using Bitmap
    }

    private void showProfileFromRequest() {
        username.setText(profile.getName());
        add.setVisibility(View.GONE);
        confirm.setVisibility(View.VISIBLE);
        remove.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }

    private void showProfileAsFriend() {
        username.setText(profile.getName());
        add.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);
        remove.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
    }

    private void showProfileAsOutOfCircle() {
        username.setText(profile.getName());
        add.setVisibility(View.VISIBLE);
        confirm.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }

    private void showProfileAsRequested() {
        username.setText(profile.getName());
        add.setVisibility(View.GONE);
        confirm.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);
        cancel.setVisibility(View.VISIBLE);
    }

    protected View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.Add_btn:
                    addFriend();
                    break;

                case R.id.Remove_btn:
                    removeFriend();
                    break;

                case R.id.confirm_btn:
                    confirmFriend();
                    break;

                case R.id.cancel_req_btn:
                    cancelRequest();
                    break;

            }
        }
    };

    private void cancelRequest() {
        ISystem.cancelRequest(getApplicationContext(), self.getId(), profile.getID());
        Toast.makeText(this, "Friend Request Cancel !", Toast.LENGTH_SHORT).show();
        showProfileAsOutOfCircle();
        updateNotificationIcon();
    }

    protected void confirmFriend() {
        ISystem.acceptRequest(getApplicationContext(), self.getId(), profile.getID());
        Toast.makeText(this, "Friendship Confirmed !", Toast.LENGTH_SHORT).show();
        showProfileAsFriend();
        updateNotificationIcon();
    }

    private void removeFriend() {

        ISystem.removeFriendship(getApplicationContext(),self.getId(),profile.getID());
        showProfileAsOutOfCircle();
        updateNotificationIcon();
    }

    private void addFriend() {

        ISystem.requestFriend(getApplicationContext(),self.getId(),profile.getID());
        showProfileAsRequested();

    }

}
