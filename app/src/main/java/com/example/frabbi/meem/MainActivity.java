package com.example.frabbi.meem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
/*
* hey Rabbi you cannot work alone, imm is here :/
*
* hahaha
*
* */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
    }
    /*public void showReg(View view)
    {
        RelativeLayout logRel = (RelativeLayout) findViewById(R.id.loginLayout);
        RelativeLayout regRel = (RelativeLayout) findViewById(R.id.regLayout);
        logRel.setVisibility(View.GONE);
        clearForm(logRel);
        regRel.setVisibility(View.VISIBLE);
    }
    public void showLogin(View view)
    {
        RelativeLayout logRel = (RelativeLayout) findViewById(R.id.loginLayout);
        RelativeLayout regRel = (RelativeLayout) findViewById(R.id.regLayout);
        regRel.setVisibility(View.GONE);
        clearForm(regRel);
        logRel.setVisibility(View.VISIBLE);
    }
    private void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }
*/}
