package com.example.frabbi.meem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.frabbi.meem.R.layout.activity_account;

public class ActivityAccount extends BottomBarActivity {

    private TextView nameview;
    private ImageView imgview;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_account, frameContent);
        ownAccount.setClickable(false);
        if(getSupportActionBar()!=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameview = (TextView) findViewById(R.id.ProfileName);
        SharedPreferences sp = getSharedPreferences("ProfileName", Context.MODE_PRIVATE);
        String name = sp.getString("ProfileName",null);
        nameview.setText(name);

        imgview = (ImageView) findViewById(R.id.profilePhoto);
        SharedPreferences imgsp = getSharedPreferences("profilePhoto", Context.MODE_PRIVATE);
        String path = imgsp.getString("image_path",null);
        if(path!=null) imgview.setImageBitmap(BitmapFactory.decodeFile(path));

    }


}
