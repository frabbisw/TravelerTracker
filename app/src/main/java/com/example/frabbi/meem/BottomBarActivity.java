package com.example.frabbi.meem;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class BottomBarActivity extends AppCompatActivity {

    protected FrameLayout frameContent;
    protected ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        frameContent = (FrameLayout) findViewById(R.id.content_frame);
        settings = (ImageView) findViewById(R.id.img_settings);
        settings.setOnClickListener(clickListener);
    }



    protected View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {




            switch (v.getId()) {
                case R.id.img_settings:
                    showSettings();
                    break;

            /*    case R.id.btn_login_account:
                    loginToAccount();
                    break;*/
            }
        }
    };

    protected void showSettings(){
        startActivity(new Intent(BottomBarActivity.this,SettingsActivity.class));
    }

}
