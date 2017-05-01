package com.example.frabbi.meem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CircleActivity extends BottomBarActivity implements ClickCallback, View.OnClickListener {

    private RecyclerView tempFriendlist;
    EditText searchItem;
    Button search_Btn;

    ArrayList<Object> items = new ArrayList<Object>();
    FriendListAdapter adapter;
    ArrayList<User> foundUser = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_circle, frameContent);
        circle.setClickable(false);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tempFriendlist = (RecyclerView) findViewById(R.id.friends);
        searchItem = (EditText) findViewById(R.id.edit_text_search);

        search_Btn = (Button) findViewById(R.id.search_btn);
        search_Btn.setOnClickListener(this);

        ArrayList<User> requests = getRequest();
        ArrayList<User> friends = getFriends();
        ArrayList<Notification> notifications = getNotified();

        if(notifications.size() > 0){
            items.add(new Label("Confirmations"));
            items.addAll(notifications);
        }

        if(requests.size() > 0) {
            items.add(new Label("Friend requests"));
            items.addAll(requests);
        }

        if(friends.size() > 0) {
            items.add(new Label("My Friends"));
            items.addAll(friends);
        }

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tempFriendlist.setLayoutManager(manager);

        adapter = new FriendListAdapter(this, items, this);
        tempFriendlist.setAdapter(adapter);
    }

    public void search() {
        String searchID = searchItem.getText().toString();
        if (searchID.isEmpty()) return;

        int size = foundUser.size();
        //if(size > 0) size++;
        adapter.removeItems(size);
        foundUser = getSearchResult(searchID);
        adapter.addItems(foundUser);
        //adapter.addLabel(new Label("Showing results for " + searchID));
    }


    @Override
    public void onAcceptClick(User request) {
        Toast.makeText(this, "Request Accepted !", Toast.LENGTH_SHORT).show();
        ISystem.acceptRequest(getApplicationContext(), self.getId(), request.getID());
        updateNotificationIcon();
    }

    @Override
    public void onIgnoreClick(User request) {
        Toast.makeText(this, "Request Ignored !", Toast.LENGTH_SHORT).show();
        ISystem.declineRequest(getApplicationContext(), self.getId(), request.getID());
        updateNotificationIcon();
    }


   @Override
    public void onOkayClick(Notification n) {
        Toast.makeText(this, "Confirmation seen", Toast.LENGTH_SHORT).show();
       // TODO remove notification from database
       updateNotificationIcon();
    }


    @Override
    public void onViewClick(User user) {
        Intent in = new Intent(CircleActivity.this, ProfileActivity.class);
        in.putExtra(Constants.USERTEST, user);
        startActivity(in);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                search();
                break;
        }
    }
}
