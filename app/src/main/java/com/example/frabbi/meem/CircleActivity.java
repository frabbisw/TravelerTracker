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

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

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

        addNotificationToAdapter();
        addRequestToAdapter();
        addFriendsToAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tempFriendlist.setLayoutManager(manager);

        adapter = new FriendListAdapter(this, items, this);
        tempFriendlist.setAdapter(adapter);
    }

    private  void addNotificationToAdapter()
    {
        final ArrayList<Notification> notifications = new ArrayList<>();

        ISystem.getNotifications(getApplicationContext(), self.getId(), new VolleyCallBack() {
            @Override
            public void success(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray!=null)
                    {
                        for(int i=0; i<jsonArray.length(); i++)
                            notifications.add(new Gson().fromJson(jsonArray.getString(i), Notification.class));

                        if(notifications.size() > 0){
                            items.add(new Label("Confirmations"));
                            items.addAll(notifications);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addRequestToAdapter()
    {
        final ArrayList<Account> accounts = new ArrayList<>();
        final ArrayList<User> requests = new ArrayList<>();

        ISystem.loadRequestedUsers(getApplicationContext(), self.getId(), new VolleyCallBack() {
            @Override
            public void success(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray!=null)
                    {
                        for(int i=0; i<jsonArray.length(); i++)
                            accounts.add(new Gson().fromJson(jsonArray.getString(i), Account.class));

                        for (Account a : accounts)
                            requests.add(User.getUserTest(a, Constants.REQUEST));

                        if(requests.size() > 0) {
                            items.add(new Label("Friend requests"));
                            items.addAll(requests);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addFriendsToAdapter()
    {
        final ArrayList<Account> accounts = new ArrayList<>();
        final ArrayList<User> friends = new ArrayList<>();

        ISystem.loadMyFriends(self, getApplicationContext(), new VolleyCallBack() {
            @Override
            public void success(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray!=null)
                    {
                        for(int i=0; i<jsonArray.length(); i++)
                            accounts.add(new Gson().fromJson(jsonArray.getString(i), Account.class));

                        for (Account a : accounts)
                            friends.add(User.getUserTest(a, Constants.FRIEND));

                        if(friends.size() > 0) {
                            items.add(new Label("My Friends"));
                            items.addAll(friends);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void search() {
        String searchID = searchItem.getText().toString();
        if (searchID.isEmpty()) return;

        int size = foundUser.size();
        //if(size > 0) size++;
        adapter.removeItems(size);

        //sorry meem, i had to change these codes :(
        final ArrayList<Account> accounts = new ArrayList<>();
        final ArrayList<User> result = new ArrayList<>();
        foundUser=new ArrayList<>();

        ISystem.searchAccount(getApplicationContext(), searchID, new VolleyCallBack() {
            @Override
            public void success(String response) {
                try
                {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray!=null)
                    {
                        for(int i=0; i<jsonArray.length(); i++)
                            accounts.add(new Gson().fromJson(jsonArray.getString(i), Account.class));
                        for (Account a : accounts)
                            foundUser.add(User.getUserTest(a, Constants.SEARCH));
                        adapter.addItems(foundUser);
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });

        //foundUser = getSearchResult(searchID);
        //adapter.addItems(foundUser);
        //adapter.addLabel(new Label("Showing results for " + searchID));
    }

    @Override
    public void onAcceptClick(User request) {
        ISystem.acceptRequest(getApplicationContext(), self.getId(), request.getID(), new VolleyCallBack() {
            @Override
            public void success(String response) {
                Toast.makeText(getApplicationContext(), "Request Accepted !", Toast.LENGTH_SHORT).show();
                updateNotificationIcon();
            }
        });

    }

    @Override
    public void onIgnoreClick(User request) {

        ISystem.declineRequest(getApplicationContext(), self.getId(), request.getID(), new VolleyCallBack() {
            @Override
            public void success(String response) {
                Toast.makeText(getApplicationContext(), "Request Ignored !", Toast.LENGTH_SHORT).show();
                updateNotificationIcon();
            }
        });
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
